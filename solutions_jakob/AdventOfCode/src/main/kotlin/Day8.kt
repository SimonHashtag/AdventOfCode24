package org.example

class Day8: AdventDay("/day8") {
    private val inputMap = getInputArrayList()
    private var antennaLocations = HashMap<Char, ArrayList<Pair<Int,Int>>>()

    override fun printOutput() {
        inputMap.forEachIndexed{ i, line -> line.forEachIndexed{ j, char -> if(char!= '.') addToAntennaLocations(i,j,char) }}
        antennaLocations.values.forEach{list -> addAllAntinodesForChar(list)}
        println(inputMap.sumOf { line -> line.count { item -> item == '#' } })
        antennaLocations.values.forEach{list -> addAllAntinodesForCharEx2(list)}
        println(inputMap.sumOf { line -> line.count { item -> item == '#' } })
    }

    private fun addToAntennaLocations(x:Int, y:Int, value:Char){
        if(!antennaLocations.keys.contains(value)){
            antennaLocations[value] = arrayListOf( Pair(x,y))
            return;
        }
        antennaLocations[value]?.add(Pair(x,y))
    }

    private fun <T> safeArrayListAdd(list:ArrayList<ArrayList<T>>, x:Int, y:Int, ch:T){
        if(x < 0 || x >= list.size || y < 0 || y >= list[x].size) return;
        list[x][y] = ch
    }

    private fun addAllAntinodesForChar(coords: List<Pair<Int,Int>>){
        for(coordA in coords){
            for(coordB in coords){
                if(coordA != coordB) {
                    val newX = 2 * coordA.first - coordB.first
                    val newY = 2 * coordA.second - coordB.second
                    safeArrayListAdd(inputMap, newX, newY, '#')
                }
            }
        }
    }

    private fun addAllAntinodesForCharEx2(coords: List<Pair<Int,Int>>){
        for(coordA in coords){
            for(coordB in coords){
                if(coordA != coordB){
                    val distX = coordA.first - coordB.first
                    val distY = coordA.second - coordB.second
                    addAntinodesForCoordinates(distY, distX, coordA, coordB)
                }}
        }
    }

    private fun addAntinodesForCoordinates(distY: Int, distX: Int, coordA: Pair<Int, Int>, coordB: Pair<Int, Int>) {
        val gcd = greatestCommonDivisor(distY, distX)
        for (i in 0..maxOf(inputMap.size, inputMap[0].size)) {
            var newCoordX = coordA.first - i * distX / gcd
            var newCoordY = coordA.second - i * distY / gcd
            safeArrayListAdd(inputMap, newCoordX, newCoordY, '#')
            newCoordX = coordB.first + i * distX
            newCoordY = coordB.second + i * distY
            safeArrayListAdd(inputMap, newCoordX, newCoordY, '#')
        }
    }

    private fun greatestCommonDivisor(a:Int, b:Int): Int{
        return if (b == 0) a;
        else (greatestCommonDivisor (b, a % b));
    }

}