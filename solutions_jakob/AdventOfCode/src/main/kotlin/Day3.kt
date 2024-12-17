package org.example

class Day3(private val exercise:Int): AdventDay("/day3") {

    override fun printOutput() {
        var input = input()
        if(exercise == 2) {
            input = input.replace("don't\\(\\).*?(do\\(\\)|$)".toRegex(RegexOption.DOT_MATCHES_ALL) , "")
        }
        val regexResult ="mul\\(\\d{1,3},\\d{1,3}\\)".toRegex().findAll(input).map { value -> value.value }.toList()
        println(regexResult.sumOf { res -> multiply(res) })
    }

    private fun multiply(input:String): Int {
        val leftNum = input.replace("mul(", "").replace(",.*".toRegex(), "").toInt()
        val rightNum = input.replace("mul\\(.*,".toRegex(), "").replace(")","").toInt()
        return leftNum * rightNum
    }
}