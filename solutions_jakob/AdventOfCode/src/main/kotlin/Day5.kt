package org.example

class Day5 : AdventDay("/day5") {

    override fun printOutput() {
        val input = input().lines();
        val rules = input.filter { x -> x.contains("|") } //
            .map { line ->
                line.split('|') //
                    .map { num -> num.toInt() }
            }
        val updates = input.filter { x -> x.contains(",") } //
            .map { line ->
                line.split(',') //
                    .map { num -> num.toInt() }
            }

        val results = findMiddleNumbersOfCorrectLines(updates, rules)
        println("Ex1: ${results.sum()}")
        val sortedUpdates = updates.map { upd -> makeCorrectOrder(upd, rules) }
        println("Ex2: ${findMiddleNumbersOfCorrectLines(sortedUpdates, rules).sum()}")
    }

    private fun findMiddleNumbersOfCorrectLines(updates: List<List<Int>>, rules: List<List<Int>>): List<Int> {
        return updates.map { line ->
            if (!rules.map { rule -> checkCorrectOrder(line, rule) }.contains(false)) {
                line[line.size / 2]
            } else {
                -1
            }
        }.filter { num -> num >= 0 }
    }

    private fun checkCorrectOrder(update: List<Int>, rule: List<Int>): Boolean {
        val first = rule[0]
        val second = rule[1]
        if (update.contains(first) && update.contains(second)) {
            return update.indexOf(first) < update.indexOf(second)
        }
        return true;
    }

    private fun makeCorrectOrder(update: List<Int>, rules: List<List<Int>>): List<Int> {
        val newList = ArrayList(update)
        if(! rules.map { rule -> checkCorrectOrder(newList, rule) }.contains(false)){
            return listOf(0)
        }
        while (rules.map { rule -> checkCorrectOrder(newList, rule) }.contains(false)) {
            for (rule in rules) {
                val first = rule[0]
                val second = rule[1]
                if (update.contains(first) && update.contains(second) && newList.indexOf(first) > newList.indexOf(second)) {
                    newList[newList.indexOf(first)] = second
                    newList[newList.indexOf(second)] = first

                }
            }
        }
        return newList;
    }

}