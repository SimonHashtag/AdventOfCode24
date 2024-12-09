package org.example

import kotlin.math.abs

class Day2: AdventDay("/day2") {

    override fun printOutput() {
        val input = input().map { line -> line.split(' ').map {str -> str.toInt() } }.toList()
        println(input.count { report -> safetyReport(report) })
        println(input.count { report -> dampenedSafetyReport(report) })
    }

    private fun safetyReport(report : List<Int>): Boolean {
        if(report.sorted() == report){
            return safetyReportCorrectlySorted(report, {a,b -> b - a})
        }
        if(report.sortedDescending() == report){
            return safetyReportCorrectlySorted(report, {a,b -> a - b})
        }
        return false
    }

    private fun dampenedSafetyReport(report : List<Int>): Boolean {
        if (safetyReport(report)) return true
        for(i in report.indices){
            val listWithoutI = report.toMutableList()
            listWithoutI.removeAt(i)
            if(safetyReport(listWithoutI)){
                return true
            }
        }
        return false
    }

    private fun safetyReportCorrectlySorted(report : List<Int>, operation:((Int,Int) -> Int)): Boolean {
        for(i in report.dropLast(1).indices){
            if(operation(report[i], report[i+1]) > 3 || report[i] == report[i+1]) return false
        }
        return true
    }
}