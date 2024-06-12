
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.float
import org.example.HdrImage
import org.example.Parameters
import org.example.pfm_from_png
import java.io.FileInputStream
import java.io.FileNotFoundException

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

class png2pfm: CliktCommand(printHelpOnEmptyArgs = true, help="Conversion from a PNG image to a PMF file")
{

    //    val args by option(help = "").split(" " )
    val pfm_output:String by argument(
        "--output_pfm",
        help="name of the output file PFM (specify .pfm format) to create"
    )

    val a:Float by option(
        "-a",
        help="clamp value (float)"
    ).float().default(1f)

    val gamma:Float by option(
        "--gamma",
        help="gamma value of the screen"
    ).float().default(1f)

    val png_output:String by argument(
        "--output_png",
        help = "name of the input file PNG (specify .png format)"
    )


    override fun run()
    {

        val parameters: Parameters = Parameters()

        try {

            parameters.parse_command_line(
                listOf<String>(
                    pfm_output,
                    "$a",
                    "$gamma",
                    png_output)
            )
        } catch (e:RuntimeException)
        {
            println("Error:$e")
            return
        }


        try {
            val inp_stream: FileInputStream = FileInputStream(parameters.input_pfm_file_name)
            val img: HdrImage? = pfm_from_png(
                stream=inp_stream,
                path = parameters.output_png_filename,
                return_img = true,
                parameters=parameters
            )
            println("File ${parameters.output_png_filename} has been read from the disk")


        } catch (e2: FileNotFoundException)
        {
            println("Impossible to open file ${parameters.output_png_filename}")
            println("Error: $e2")
            return
        }



    }


}
