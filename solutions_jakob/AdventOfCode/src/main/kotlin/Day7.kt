package org.example

class Day7: AdventDay("/day7") {
    private var correctEquationCounter: Long = 0

    override fun printOutput() {
        val input = input().lines()
        val leftSides = input.map { str -> str.replace(":.*".toRegex(), "").toLong() }
        val rightSides = input //
            .map { str -> str.replace(".*:\\s".toRegex(), "") } //
            .map { right ->
                right.split("\\s".toRegex()) //
                    .map { str -> str.toLong() }
            }
        var pairs = ArrayList<Triple<Long, List<Long>, Int>>()
        leftSides.forEachIndexed { i, left -> pairs.add(Triple(left, rightSides[i], i)) }
        val unsolvedPairs = ArrayList(pairs)
        for(exercise in 1..2) {
            for (pair in pairs) {
                if (solveLineRecursively(pair.first, pair.second, exercise)) {
                    correctEquationCounter += pair.first
                    unsolvedPairs.removeAll { p -> p.third == pair.third }
                }
            }
            pairs = ArrayList(unsolvedPairs)
            println("The solution of exercise $exercise is $correctEquationCounter")
        }
    }
    private fun solveLineRecursively(target:Long, nums:List<Long>, exercise:Int): Boolean{
        val first = nums[0]
        if(nums.size == 1 ) return first == target
        val template = ArrayList(nums.subList(1, nums.size));
        val mult = template.mapIndexed{i,v -> if (i == 0) first * v else v };
        val add = template.mapIndexed{i,v -> if (i == 0) first + v else v};
        if(exercise == 1){
            return (solveLineRecursively(target, mult, exercise) || solveLineRecursively(target, add, exercise))
        }
        val concat = template.mapIndexed{i,v -> if (i == 0) "$first$v".toLong() else v};
        return (solveLineRecursively(target, mult, exercise) //
                || solveLineRecursively(target, add, exercise) //
                || solveLineRecursively(target, concat, exercise))
    }
}