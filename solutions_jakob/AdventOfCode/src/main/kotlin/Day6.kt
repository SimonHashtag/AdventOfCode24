package org.example

class Day6 : AdventDay("/day6") {

    private var direction = Direction.UP
    private lateinit var map: ArrayList<ArrayList<Char>>
    private var checkMapWithXs = false
    private lateinit var coolerMap: ArrayList<ArrayList<Char>>
    private var finished = false;
    private lateinit var ogCoords:Pair<Int,Int>


    override fun printOutput() {
        resetMap()
        ogCoords = findGuardCoordinates()
        while(!finished){
            move()
        }
        coolerMap = ArrayList(map.map { item -> ArrayList(item)})
        coolerMap[ogCoords.first][ogCoords.second] = '^'
        checkMapWithXs = true;
        println(map.sumOf{line -> line.count{item -> item == 'X'}})
        var obstacleCounter = 0
        for(x in 0 until map.size){
            print("line $x of ${map.size} ")
            for(y in 0 until map[x].size){
                print("|")
                if(checkNewObstacleLoop(x,y)){
                    obstacleCounter ++;
                }
            }
            println()
        }
        println(obstacleCounter)
    }

    enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private fun hitObstacle(){
        direction = when(direction) {
            Direction.UP -> Direction.RIGHT
            Direction.DOWN -> Direction.LEFT
            Direction.LEFT -> Direction.UP
            Direction.RIGHT -> Direction.DOWN
        }
    }

    private fun checkNewObstacleLoop(x: Int, y: Int): Boolean {

        resetMap()
        if(map[x][y] == '^' ||map[x][y] == '#' || map[x][y] == '.'){
            return false;
        }
        val positionLog = arrayListOf(Pair(findGuardCoordinates(), direction))
        map[x][y] = 'O'
        while(!finished){
            move()
            if(finished){
                return false;
            }
            val guardCoords = findGuardCoordinates()
            if(positionLog.contains(Pair(guardCoords, direction))){
                return true;
            }
            positionLog.add(Pair(guardCoords, direction))

        }
        return false;
    }

    private fun move(){
        val coordinates = findGuardCoordinates()
        val oldX = coordinates.first
        val oldY = coordinates.second
        if(oldX == 0 || oldX == map.size-1 || oldY == 0 || oldY == map[0].size -1){
            finished = true
            map[oldX][oldY] = 'X'
            return;
        }
        var newX = oldX
        var newY = oldY
        when(direction){
            Direction.UP -> newX -= 1
            Direction.DOWN -> newX += 1
            Direction.LEFT -> newY -= 1
            Direction.RIGHT -> newY += 1
        }
        if(map[newX][newY] == '#' || map[newX][newY] == 'O'){
            hitObstacle()
            return
        }
        map[oldX][oldY] = 'X'
        map[newX][newY] = '^'
    }

    private fun findGuardCoordinates():Pair<Int, Int> {
        val line = map.first { line -> line.contains('^') }
        val x = map.indexOf(line)
        val y = line.indexOf('^')
        return Pair(x,y)
    }

    private fun resetMap(){
        if(!checkMapWithXs){
            map = ArrayList(input().lines().map { line ->ArrayList(line.toCharArray().toList()) })
        }else {
            map = ArrayList(coolerMap.map { item -> ArrayList(item)})
        }
        direction = Direction.UP
        finished = false;
    }

}