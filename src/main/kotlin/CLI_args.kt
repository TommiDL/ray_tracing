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
        "Insert:\n- rotation angle  \n- Camera type: perspective or orthogonal"
    ).multiple()

    val width by option("--width", "-w", help="Width of the PNG image").int().default(480)
    val height by option("--height", "-he", help="Height of the PNG image").int().default(480)
    val pfm:String by option("--pfm-output", "-pfm", help="Name of the pfm output file").default("output")
    val alg:String by option("--algorithm", "-a", help = "Rendering type: onoff -> rendering use black&white format, flat -> rendering allow to use the color of objects").default("onoff")
    val png:String? by option("--png-output", help = "Name of the png output file")
    override fun run()
    {

        try {
            val rotation_angle:Float = argv[0].toFloat()*2*PI.toFloat()/360f

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

                Sphere(
                    transformation = scalar_transformation(0.1f, 0.1f, 0.1f),
                    material = Material(
                        emitted_radiance = UniformPigment( Color(127f, 0f, 255f))
                    )
                ),

                Sphere(
                    transformation = traslation(Vec(0.5f,0.5f, 0.5f)) * scalar_transformation(0.1f, 0.1f, 0.1f),
                    material = Material(
                        emitted_radiance = UniformPigment( Color(0f, 204f, 0f))
                    )
                ),

                Sphere(
                    transformation = traslation(Vec(0.5f,0.5f, -0.5f)) * scalar_transformation(0.1f, 0.1f, 0.1f),
                    material = Material(
                        emitted_radiance = UniformPigment( Color(255f, 0f, 0f)))
                ),

                Sphere(
                    transformation = traslation(Vec(0.5f,-0.5f, -0.5f)) * scalar_transformation(0.1f, 0.1f, 0.1f),
                    material = Material(
                        emitted_radiance = UniformPigment( Color(0f, 255f, 0f)))
                ),

                Sphere(
                    transformation = traslation(Vec(-0.5f,-0.5f, -0.5f)) * scalar_transformation(0.1f, 0.1f, 0.1f),
                    material = Material(
                        emitted_radiance = UniformPigment( Color(0f, 0f, 255f)))
                ),

                Sphere(
                    transformation = traslation(Vec(-0.5f,0.5f, 0.5f))*scalar_transformation(0.1f, 0.1f, 0.1f),
                    material = Material(
                        emitted_radiance = UniformPigment( Color(255f, 128f, 0f)))
                ),

                Sphere(
                    transformation = traslation(Vec(-0.5f,-0.5f, 0.5f))*scalar_transformation(0.1f, 0.1f, 0.1f),
                    material = Material(
                        emitted_radiance = UniformPigment( Color(122f, 0f, 204f)))
                ),

                Sphere(
                    transformation = traslation(Vec(0.5f,-0.5f, 0.5f))*scalar_transformation(0.1f, 0.1f, 0.1f),
                    material = Material(
                        emitted_radiance = UniformPigment( Color(100f, 155f, 150f)))
                ),

                Sphere(
                    transformation = traslation(Vec(-0.5f,0.5f, -0.5f))*scalar_transformation(0.1f, 0.1f, 0.1f),
                    material = Material(
                        emitted_radiance = UniformPigment( Color(0f, 255f, 100f)))
                ),

                Sphere(
                    transformation = traslation(Vec(0f,0.5f, 0f))*scalar_transformation(0.1f, 0.1f, 0.1f),
                    material = Material(
                        emitted_radiance = UniformPigment( Color(100f, 100f, 255f)))
                ),

                Sphere(
                    transformation = traslation(Vec(0f,0f, -0.5f))*scalar_transformation(0.1f, 0.1f, 0.1f),
                    material = Material(
                        emitted_radiance = UniformPigment( Color(255f, 255f, 100f)))
                ),

                Plane(
                    transformation = traslation(Vec(z=10f)),
                    material = Material(UniformPigment(Color(55f,55f,0f)))
                ),
                Plane(
                    transformation = traslation(Vec(z=-10f)),
                    material = Material(UniformPigment(Color(0f,0f,55f)))
                )

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

            val renderer:Renderer
            if(alg=="onoff")
            {
                renderer=OnOffRenderer(world)
            }
            else //not the best solution but fine by now
            {
                renderer=FlatRenderer(world)
            }

            tracer.fire_all_ray(){ray: Ray ->  renderer(ray)}
            /*{ ray: Ray ->
                val hit=world.ray_intersection(ray)!=null

                if (hit) WHITE
                else BLACK
            }*/
            println()

            println("\r\tIntersection done")
            print("\r\t                                  ")
            print("\r")


            try {
                // Save image in PFM file
                img.write_pfm_image(FileOutputStream("${this.pfm}.pfm"))
                println("Image saved in PFM format at PATH: ${this.pfm}.pfm")
            }catch (e1: FileNotFoundException)
            {
                println("Impossible to write on file ${this.pfm}.pfm")
                println("Error: $e1")
                return
            }



            if (png!=null)
            {

                val param:Parameters=Parameters(output_png_filename = "${this.png}.png")

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

class pfm2png:CliktCommand(printHelpOnEmptyArgs = true, help="Conversion from a PFM file to a PNG image")
{
    private val args: List<String> by argument(
        help="insert:"+
                "\n- input PFM file name (.pfm format)" +
                "\n- clamp value (float)"+
                "\n- gamma value of the screen (float)"+
                "\n- output png file name (.png format)"

    ).multiple()

    /*
//    val args by option(help = "").split(" " )
    val pfm_input:String by argument("--input_pfm", help="name of the input file PFM (specify .pfm format) to load")
    val a:Float by argument("-a", help="clamp value (float)").float()
    val gamma:Float by argument("--gamma", help="gamma value of the screen").float()
    val png_output:String by argument("--output_png", help = "name of the output file PNG (specify .png format)")
*/

    override fun run()
    {

        val parameters:Parameters=Parameters()

        try {

            parameters.parse_command_line(args)
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



class pathtracing:CliktCommand(help = "Path tracing algorithm to render a photorealistic image")
{
    val args:List<String> by argument(help = "").multiple()
    var rotation_angle:Float=0f
    lateinit var camera:Camera

    val width by option("--width", "-w", help="Width of the PNG image").int().default(480)
    val height by option("--height", "-he", help="Height of the PNG image").int().default(480)
    val pfm:String by option("--pfm-output", "-pfm", help="Name of the pfm output file").default("output")
    val png:String? by option("--png-output", help = "Name of the png output file")

    override fun run() {
        try {
            rotation_angle = args[0].toFloat()*2*PI.toFloat()/360f

            camera =
                if (args[1]=="orthogonal")
                    OrthogonalCamera(aspect_ratio = (this.width.toFloat()/this.height), transformation = rotation(Vec(x=0f,y=0f,z=1f), rotation_angle) * traslation(Vec(-1f, 0f, 0f)))
                else if (args[1]=="perspective")
                    PerspectiveCamera(aspect_ratio = (this.width.toFloat()/this.height), transformation = rotation(Vec(0f,0f,1f), rotation_angle) * traslation(Vec(-1f, 0f, 0f)), distance = 1f)
                else
                    PerspectiveCamera(aspect_ratio = (this.width.toFloat()/this.height), transformation = rotation(Vec(0f,0f,1f), rotation_angle) * traslation(Vec(-1f, 0f, 0f)), distance = 1f)
        }catch (e:Error)
        {
            //error in conversion from args
            println("Usage: <rotation_angle(Float)> <camera_type: perspective/orthogonal (String)>")
            println("Error: $e")

        }

        val pcg:PCG=PCG()

        val world:World=World(
            mutableListOf<Shape>(
                Sphere(material = Material(
                    emitted_radiance = UniformPigment(Color(pcg.random_float(), pcg.random_float(), pcg.random_float()) *150 )
                )),
                Plane(
                    transformation = traslation(Vec(z=-10f)),
                    material = Material(
                        emitted_radiance = UniformPigment(Color(pcg.random_float(), pcg.random_float(), pcg.random_float()) *100 )
                    )
                ),
                Sphere(
                    transformation = traslation(Vec(z=-10f)),
                    material = Material(
                        emitted_radiance = UniformPigment(Color(pcg.random_float(),pcg.random_float(),pcg.random_float())*255)
                    )
                )

            )
        )


        val img:HdrImage=HdrImage(width = this.width, height=this.height)

        val tracer:ImageTracer= ImageTracer(image = img, camera=this.camera)

        val renderer:pathtracer=pathtracer(
            world = world,
            russian_roulette_limit = 3
        )

        tracer.fire_all_ray(){ray: Ray ->  renderer(ray)}
        /*{ ray: Ray ->
            val hit=world.ray_intersection(ray)!=null

            if (hit) WHITE
            else BLACK
        }*/
        println()

        println("\r\tIntersection done")
        print("\r\t                                  ")
        print("\r")


        try {
            // Save image in PFM file
            img.write_pfm_image(FileOutputStream("${this.pfm}.pfm"))
            println("Image saved in PFM format at PATH: ${this.pfm}.pfm")
        }catch (e1: FileNotFoundException)
        {
            println("Impossible to write on file ${this.pfm}.pfm")
            println("Error: $e1")
            return
        }



        if (png!=null)
        {

            val param:Parameters=Parameters(output_png_filename = "${this.png}.png")

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

        }


    }


}
