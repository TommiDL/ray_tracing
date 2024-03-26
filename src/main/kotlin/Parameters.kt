package org.example

data class Parameters(
        var input_pfm_file_name:String="",
        var factor:Float=0.2f,
        var gamma:Float=1.0f,
        var output_png_filename:String=""
    )
{
    fun parse_command_line(argv:Array<String>)
    {
        if(argv.size!=4)
        {
            // digli quali arg vuoi
            //throw runtimeerror

            throw RuntimeException("Usage: main.py INPUT_PFM_FILE FACTOR GAMMA OUTPUT_PNG_FILE")
        }

        this.input_pfm_file_name = argv.get(0) as String

        try {
            this.factor=argv.get(1).toFloat()
        } catch (e:NumberFormatException)
        {
            throw RuntimeException("Invalid factor ('${argv[1]}'), it must be a floating-point number.")
        }

        try {
            this.gamma = argv.get(2).toFloat()
        }catch (e:NumberFormatException)
        {
            throw RuntimeException("Invalid gamma ('${argv[2]}'), it must be a floating-point number.")
        }

        this.output_png_filename=argv[3] as String

    }
}
