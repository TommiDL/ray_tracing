# LITracer
This is a very simple [Ray-Tracer](https://developer.nvidia.com/discover/ray-tracing#:~:text=Ray%20tracing%20is%20a%20rendering,%2C%20shadows%2C%20and%20indirect%20lighting.) written in [Kotlin](https://en.wikipedia.org/wiki/Kotlin_(programming_language)) language 

## Installation

## Usage
The program was made using Gradle and can perform two tasks:

 - demo  
    `./gradlew run --args="demo <camera_type> <camera_rotation_angle>"`
     
   Create a png image and a pfm file of 11 Spheres in a 3D space
   
 - pfm2png 
    `./gradlew run --args="pfm2png <input_PFM_file>.pfm <clamp value (float)> <gamma value of the screen (float)> <output_png_file>.png"`
   
   Execute conversion from a PFM file to a PNG image

### Examples


## History

See the file [CHANGELOG.md](https://github.com/TommiDL/LITracer/blob/master/CHANGELOG.md)

## License
The code is released under the Apache License version 2.0. See the file [LICENSE.md](https://github.com/TommiDL/LITracer/blob/master/LICENSE)

## Authors: 
[Tommaso Di Luciano](https://github.com/TommiDL),
[Maria Laura Ilisco](https://github.com/marialaurailisco),
[Ludovico Morabito](https://github.com/Ludovico-Morabito).
