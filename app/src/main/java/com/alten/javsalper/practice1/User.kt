package com.alten.javsalper.practice1

import java.io.Serializable
data class User (
    val email:String,
    val password:String,
    val username: String,
    val photo:String
):Serializable