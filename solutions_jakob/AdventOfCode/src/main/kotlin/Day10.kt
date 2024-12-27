package org.example

class Day10 : AdventDay("/day10") {

    private val input = ArrayList(getInputArrayList().map { line -> ArrayList(line.map { item -> item.digitToInt() }) })

    override fun printOutput() {
        var counter = 0;
        input.forEachIndexed{x, line -> line.forEachIndexed {y, item -> if(item == 0) {
            val ret = getAllTrailsForHead(x,y)
            counter += ret.sumOf { line -> line.filter { item -> item != null && item == 9 }.size }
        } }}
        println("ex1 $counter")
        val pathCount = input.mapIndexed{x, line -> line.mapIndexed{y, item -> if(item == 0){ countTrails(x,y)} else 0 }.sum()}.sum()
        println("ex2 $pathCount")
    }

   private fun countTrails(startX: Int, startY: Int): Int{
       val height = input[startX][startY]
       if(height == 9) return 1
       val surroundingTrails = getSurroundingElementsCoordinates(startX,startY)
           .filter { p -> input[p.first][p.second] == height + 1}
       return surroundingTrails.sumOf {p -> countTrails(p.first,p.second)}
   }

    private fun getAllTrailsForHead(inputX:Int,inputY:Int): ArrayList<ArrayList<Int?>> {
        val returnList: ArrayList<ArrayList<Int?>> =
            ArrayList(input.map { line -> ArrayList(List(line.size) { null }) })
        returnList[inputX][inputY] = 0
        for (n in 1..9) {
            ascendOneStep(n, returnList)
        }
        return returnList
    }

    private fun ascendOneStep(n: Int, returnList: ArrayList<ArrayList<Int?>>) {
        input.forEachIndexed { x, line ->
            line.forEachIndexed { y, item ->
                if (item == n && getSurroundingElements(x, y, returnList).contains(item - 1)) {
                    returnList[x][y] = item
                }
            }
        }
    }

    private fun getSurroundingElements(x: Int, y: Int, list: ArrayList<ArrayList<Int?>>): List<Int> {
        val top = safeAccess(x - 1, y, list)
        val left = safeAccess(x, y - 1, list)
        val right = safeAccess(x, y + 1, list)
        val bottom = safeAccess(x + 1, y, list)
        return listOfNotNull(top, left, right, bottom)
    }

    private fun getSurroundingElementsCoordinates(x: Int, y: Int): List<Pair<Int,Int>> {
        val top = if(safeAccess(x - 1, y, input) != null) Pair(x-1,y) else null
        val left = if(safeAccess(x, y - 1, input) != null) Pair(x,y -1 ) else null
        val right = if(safeAccess(x, y + 1, input) != null) Pair(x,y + 1 ) else null
        val bottom = if(safeAccess(x + 1, y, input) != null) Pair(x + 1,y) else null
        return listOfNotNull(top, left, right, bottom)
    }

    private fun safeAccess(x: Int, y: Int, list: ArrayList<ArrayList<Int?>>): Int? {
        if (x >= 0 && x < list.size && y >= 0 && y < list[x].size) {
            return list[x][y]
        }
        return null
    }

}