package org.example

import kotlin.math.abs

class Day1: AdventDay("/day1") {
    override fun printOutput() {
        val input = input().lines()
        val inputLeft = input //
                .map { line -> line.replace("\\s+\\d+".toRegex(), "") } //
                .map { str -> str.toInt() }.toList() //
                .sorted()
        val inputRight = input //
                .map { line -> line.replace("\\d+\\s+".toRegex(), "") } //
                .map { str -> str.toInt() }.toList() //
                .sorted()
        var distanceCounter = 0
        for (i in inputLeft.indices) {
            val difference = inputLeft[i] - inputRight[i]
            distanceCounter += abs(difference)
        }
        println(distanceCounter)
        println(inputLeft.stream().map { num -> num * inputRight.count{ it == num } }.toList().sum())
    }
}