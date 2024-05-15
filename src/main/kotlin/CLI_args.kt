package org.example

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument

import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.float
import com.github.ajalt.clikt.parameters.types.int
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

/**
 * Command Line Interface to produce an image of 10 spheres
 */
class Demo : CliktCommand(printHelpOnEmptyArgs = true, help="Create a png image and a pfm file of 10 Spheres")
{
    val rotation_angle by option("--rotation", "-r", "--angle", help="Angle of rotation").float().default(0f)

    val camera:Camera by option( "--camera", "-c", help="Choose Perspective or Orthogonal Camera")
        .choice(
            "ortogonal" to OrthogonalCamera(aspect_ratio = 1f, transformation = rotation(Vec(0f,0f,1f), this.rotation_angle) * traslation(Vec(-1f, 0f, 0f))),
            "perspective" to PerspectiveCamera(aspect_ratio = 1f, transformation = rotation(Vec(0f,0f,1f), this.rotation_angle) * traslation(Vec(-1f, 0f, 0f)), distance = 1f),
            ignoreCase = true,
            )
        .default(PerspectiveCamera())

    val width by option("--width", "-w", help="Width of the PNG image").int().default(480)
    val height by option("--height", "-he", help="height of the PNG image").int().default(480)
    val name:String by option("--name", "-n", help="Name of the outputs files").default("output")

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


        println("Creation of HdrImage")
        val img:HdrImage=HdrImage(width = this.width, height=this.height)

        println("ImageTracer initialization")
        val tracer:ImageTracer=ImageTracer(camera= this.camera, image = img)


        val WHITE=Color(255f, 255f, 255f)
        val BLACK=Color()

        println("Fire all ray")
        tracer.fire_all_ray() { ray: Ray ->
            val hit=world.ray_intersection(ray)!=null

            if (hit)
            {
                //print("\rHIT!")
                WHITE
            }
            else {
                //print("\rNOT HIT!")
                BLACK
            }
        }
        println()

        println("Intersection done")


        try {
            img.write_pfm_image(FileOutputStream("${this.name}.pfm"))
        }catch (e1: FileNotFoundException)
        {
            println("Impossible to write on file ${this.name}.pfm")
            println("Error: $e1")

            return
        }

        println("Image saved in pfm format")



        val param:Parameters=Parameters(output_png_filename = "${this.name}.png")

        img.normalize_image(factor = param.factor)
        img.clamp_image()

        try {

            val out_stream: FileOutputStream = FileOutputStream(param.output_png_filename)
            img.write_ldr_image(stream = out_stream, format = "PNG", gamma = param.gamma)

        }catch (e:Error)
        {
            println("Impossible to write on file ${param.output_png_filename}")
            println("Error: $e")

            return
        }

    }

}

/**
 * Command Line Interface to produce an image of 10 spheres in png format and a PFM file
 *
 * Parameters:
 *      @ pfm_input = input PFM file
 *      @ a = clamp value
 *      @ gamma = gamma value of the screen
 *      @ png output = output name of the png file
 *
 */

class pfm2png:CliktCommand(printHelpOnEmptyArgs = true, help="Conversion from a PFM file to a PNG image")
{
//    val args by option(help = "").split(" " )
    val pfm_input:String by argument("--input_pfm", help="name of the input file PFM (specify .pfm format) to load")
    val a:Float by argument("-a", help="clamp value (float)").float()
    val gamma:Float by argument("--gamma", help="gamma value of the screen").float()
    val png_output:String by argument("--output_png", help = "name of the output file PNG (specify .png format)")


    override fun run()
    {

        // conversion pfm --> PNG

        try {
            //parameters.parse_command_line(argv)
        } catch (e:RuntimeException){
            
        }

//        try {
            val parameters:Parameters = Parameters(
                pfm_input,
                a,
                gamma,
                png_output,
            )
            //parameters.parse_command_line(args)
/*        } catch (e:RuntimeException)
>>>>>>> dbc347943470057c2e73331abfb25e22623f9478
        {
            println("Error:$e")
            return
        }
*/

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

class Selection:CliktCommand(printHelpOnEmptyArgs = true, help="Select the feature that you want to utilize")
{

    val demo by option(help="demo construct 10 spheres")
    val pfm2png by option(help="convert PFM File to PNG")


    override fun run()=Unit
}

