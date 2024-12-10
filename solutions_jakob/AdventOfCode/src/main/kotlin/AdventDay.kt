package org.example

import java.io.File

abstract class AdventDay(val inputPath: String){

    abstract fun printOutput();
    fun input(): String {
        return  this.javaClass.getResource(inputPath).readText()
    }
}