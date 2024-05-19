package org.example

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import kotlin.math.PI


/**
 * Selection class:
 *      Use to inherit subcommands
 */
class Selection:CliktCommand(name="selection") {
    override fun run()=Unit
}

/**
 * Command Line Interface to produce an image of 10 spheres
 * Parameters:
 *      @ rotation angle
 *      @ camera type
 */
class Demo : CliktCommand(printHelpOnEmptyArgs = true,help="Create a png image and a pfm file of 10 Spheres")
{
    val argv: List<String> by argument( help =
        "Insert:\n- rotation angle  \n- Camera type"
    ).multiple()

/*    val angle by argument("--angle", help = "Rotation angle of the camera").float().default(0f)
    val camera by argument("--camera", help="Camera type: perspective or orthogonal")
        .choice(
            "perspective" to PerspectiveCamera(aspect_ratio = 1f, transformation = rotation(Vec(0f,0f,1f), angle) * traslation(Vec(-1f, 0f, 0f)), distance = 1f),
            "orthogonal" to OrthogonalCamera(aspect_ratio = 1f, transformation = rotation(Vec(x=0f,y=0f,z=1f), rotation_angle) * traslation(Vec(-1f, 0f, 0f))),
        )
*/
    val width by option("--width", "-w", help="Width of the PNG image").int().default(480)
    val height by option("--height", "-he", help="Height of the PNG image").int().default(480)
    val name:String by option("--name", "-n", help="Name of the outputs files").default("output")
    override fun run()
    {

        try {
            val rotation_angle:Float = if (argv[0].toFloat()>2*PI) 2*PI.toFloat()*argv[0].toFloat()/360 else argv[0].toFloat()

            val camera:Camera =
                if (argv[1]=="orthogonal")
                    OrthogonalCamera(aspect_ratio = (this.width.toFloat()/this.height), transformation = rotation(Vec(x=0f,y=0f,z=1f), rotation_angle) * traslation(Vec(-1f, 0f, 0f)))
                else if (argv[1]=="perspective")
                    PerspectiveCamera(aspect_ratio = (this.width.toFloat()/this.height), transformation = rotation(Vec(0f,0f,1f), rotation_angle) * traslation(Vec(-1f, 0f, 0f)), distance = 1f)
                else
                    PerspectiveCamera(aspect_ratio = (this.width.toFloat()/this.height), transformation = rotation(Vec(0f,0f,1f), rotation_angle) * traslation(Vec(-1f, 0f, 0f)), distance = 1f)

            print("\tinitialization of objs in world...")
            print("\r\t                                  ")

            // Declare 10 Spheres objs, ray 1/10 in the vertex of a cube
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
            print("\r\tWorld init done")
            print("\r\t                                  ")


            print("\r\tCreation of HdrImage")
            print("\r\t                                  ")
            val img:HdrImage=HdrImage(width = this.width, height=this.height)

            print("\r\tImageTracer initialization")
            print("\r\t                                  ")
            val tracer:ImageTracer=ImageTracer(camera= camera, image = img)


            val WHITE=Color(255f, 255f, 255f)
            val BLACK=Color()

            print("\r\tFire all ray")
            print("\r\t                                  ")
            tracer.fire_all_ray() { ray: Ray ->
                val hit=world.ray_intersection(ray)!=null

                if (hit) WHITE
                else BLACK
            }
            println()

            println("\r\tIntersection done")
            print("\r\t                                  ")
            print("\r")


            try {
                // Save image in PFM file
                img.write_pfm_image(FileOutputStream("${this.name}.pfm"))
                println("Image saved in PFM format at PATH: ${this.name}.pfm")
            }catch (e1: FileNotFoundException)
            {
                println("Impossible to write on file ${this.name}.pfm")
                println("Error: $e1")
                return
            }



            val param:Parameters=Parameters(output_png_filename = "${this.name}.png")

            img.normalize_image(factor = param.factor)
            img.clamp_image()

            try {
                //save image in PNG file
                val out_stream: FileOutputStream = FileOutputStream(param.output_png_filename)
                img.write_ldr_image(stream = out_stream, format = "PNG", gamma = param.gamma)
                println("Image saved in PNG format at PATH: ${param.output_png_filename}")

            }catch (e:Error)
            {
                println("Impossible to write on file ${param.output_png_filename}")
                println("Error: $e")

                return
            }

        } catch (e:Error)
        {
            //error in conversion from args
            println("Usage: <rotation_angle(Float)> <camera_type: perspective/orthogonal (String)>")
            println("Error: $e")
        }

    }



}

/**
 * Command Line Interface to convert PFM file in PNG image
 *
 * Parameters:
 *      @ pfm_input = input PFM file
 *      @ a = clamp value
 *      @ gamma = gamma value of the screen
 *      @ png output = output name of the png file
 *
 */
/*
class pfm2png:CliktCommand(printHelpOnEmptyArgs = true, help="Conversion from a PFM file to a PNG image")
{
    private val args: List<String> by argument(
        help="insert:"+
                "\n- input PFM file name (.pfm format)" +
                "\n- clamp value (float)"+
                "\n- gamma value of the screen (float)"+
                "\n- output png file name (.png format)"

    ).multiple()
}
    /*
//    val args by option(help = "").split(" " )
    val pfm_input:String by argument("--input_pfm", help="name of the input file PFM (specify .pfm format) to load")
    val a:Float by argument("-a", help="clamp value (float)").float()
    val gamma:Float by argument("--gamma", help="gamma value of the screen").float()
    val png_output:String by argument("--output_png", help = "name of the output file PNG (specify .png format)")
*/
/*
    override fun run()
    {

        val parameters:Parameters=Parameters()

        try {

            //parameters.parse_command_line(argv)
        } catch (e:RuntimeException){

        }
    }
/*
        try {
            val parameters:Parameters = Parameters(
                pfm_input,
                a,
                gamma,
                png_output,
            )
    }*/
            //parameters.parse_command_line(args)
/*        } catch (e:RuntimeException)
>>>>>>> dbc347943470057c2e73331abfb25e22623f9478
=======
            parameters.parse_command_line(args)
        } catch (e:RuntimeException)
>>>>>>> c3369f17adb26d8343363448516938144a1f91d9
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
*/