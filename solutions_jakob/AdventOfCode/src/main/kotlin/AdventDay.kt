package org.example

import java.io.File

abstract class AdventDay(val inputPath: String){

    abstract fun printOutput();
    fun input(): List<String> {
        return  this.javaClass.getResource(inputPath).readText().lines()
    }
}