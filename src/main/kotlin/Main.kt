package org.example

import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main(argv:Array<String>) {
    val parameters:Parameters = Parameters()

    try {
        parameters.parse_command_line(argv)
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