package org.example

import com.github.ajalt.clikt.core.*
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.option
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

class Demo : CliktCommand(help="Create 10 Spheres")
{
    val camera by option(help="Choose Perspective or Orthogonal Camera")
    val rotation by option(help="Angle of rotation")

    override fun run()
    {
        //demo

        println("initialization of objs in world")
        val objs:MutableList<Shape> = mutableListOf(
            //Sphere(scalar_transformation(100f, 100f, 100f)),
            Sphere(scalar_transformation(0.1f, 0.1f, 0.1f)),
            Sphere(traslation(Vec(0.5f,0.5f, 0.5f)) * scalar_transformation(0.1f, 0.1f, 0.1f)),
            Sphere(traslation(Vec(0.5f,0.5f, -0.5f)) * scalar_transformation(0.1f, 0.1f, 0.1f)),
            Sphere(traslation(Vec(0.5f,-0.5f, -0.5f)) * scalar_transformation(0.1f, 0.1f, 0.1f)),
            Sphere(traslation(Vec(-0.5f,-0.5f, -0.5f)) * scalar_transformation(0.1f, 0.1f, 0.1f)),

            Sphere(traslation(Vec(-0.5f,0.5f, 0.5f))*scalar_transformation(0.1f, 0.1f, 0.1f)  ),
            Sphere(traslation(Vec(-0.5f,-0.5f, 0.5f))*scalar_transformation(0.1f, 0.1f, 0.1f)  ),
            Sphere(traslation(Vec(0.5f,-0.5f, 0.5f))*scalar_transformation(0.1f, 0.1f, 0.1f)  ),
            Sphere(traslation(Vec(-0.5f,0.5f, -0.5f))*scalar_transformation(0.1f, 0.1f, 0.1f)  ),

            Sphere(traslation(Vec(0f,0.5f, 0f))*scalar_transformation(0.1f, 0.1f, 0.1f)  ),
            Sphere(traslation(Vec(0f,0f, -0.5f))*scalar_transformation(0.1f, 0.1f, 0.1f)  ),
        )



        val world:World=World( objs )

        println("World init done")


        println("Camera definition and creation of HdrImage")
        val camera:Camera = OrthogonalCamera(aspect_ratio = 1f, transformation = traslation(Vec(-1f, 0f, 0f)))

        val width:Int=480
        val height:Int=480

        val img:HdrImage=HdrImage(width = width, height=height)

        println("ImageTracer initialization")
        val tracer:ImageTracer=ImageTracer(camera=camera, image = img)


        val WHITE=Color(255f, 255f, 255f)
        val BLACK=Color()

        println("Fire all ray")
        tracer.fire_all_ray() { ray: Ray ->
            val hit=world.ray_intersection(ray)!=null

            if (hit)
            {
                print("\rHIT!")
                WHITE
            }
            else {
                print("\rNOT HIT!")
                BLACK
            }
        }
        println()

        println("Intersection done")


        try {
            img.write_pfm_image(FileOutputStream("output.pfm"))
        }catch (e1: FileNotFoundException)
        {
            println("Impossible to write on file output.pfm")
            println("Error: $e1")

            return
        }

        println("Image saved in pfm format")



        val param:Parameters=Parameters(output_png_filename = "Spheres.png")


        try {
            val inp_stream: FileInputStream = FileInputStream("output.pfm")
            val img2:HdrImage= read_pfm_image(inp_stream)

            println("File has been read from the disk")

            img2.normalize_image(factor = param.factor)
            img2.clamp_image()
            val out_stream: FileOutputStream = FileOutputStream(param.output_png_filename)
            img2.write_ldr_image(stream = out_stream, format = "PNG", gamma = param.gamma)

        }catch (e:Error)
        {
            println("Sad =(")
        }

    }

}


class pfm2png:CliktCommand(help="Conversion from a PFM file to a PNG image")
{
    val argv by option(help="Insert the ")
    val rotation by option(help="Angle of rotation")

    override fun run()
    {

        // conversion pfm --> PNG
        val parameters:Parameters = Parameters()

        try {
            //parameters.parse_command_line(argv)
        } catch (e:RuntimeException)
        {
            println("Error:$e")
            return
        }


        try {
            val inp_stream:FileInputStream=FileInputStream(parameters.input_pfm_file_name)
            val img:HdrImage= read_pfm_image(stream=inp_stream)
            println("File ${parameters.input_pfm_file_name} has been read from the disk")

            img.normalize_image(factor = parameters.factor)
            img.clamp_image()

            try {
                val out_stream:FileOutputStream= FileOutputStream(parameters.output_png_filename)
                img.write_ldr_image(stream = out_stream, format = "PNG", gamma = parameters.gamma)

                println("${parameters.output_png_filename} has been written to disk")

            }catch (e1:FileNotFoundException)
            {
                println("Impossible to write on file ${parameters.output_png_filename}")
                println("Error: $e1")

                return
            }

        } catch (e2:FileNotFoundException)
        {
            println("Impossible to open file ${parameters.input_pfm_file_name}")
            println("Error: $e2")
            return
        }



    }

}

class Selection:CliktCommand(help="Select the feature that you want to utilize")
{
    val demo by option(help="demo construct 10 spheres")
    val pfm2png by option(help="convert PFM File to PNG")

    override fun run()
    {

    }
}

