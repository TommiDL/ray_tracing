package org.example

class Material (
    val emitted_radiance:Pigment=UniformPigment(Color(255f,255f,255f)),
    val brdf: BRDF=DiffusiveBRDF(emitted_radiance))