package org.example

import kotlin.math.abs

class Day4(): AdventDay("/day4") {

    override fun printOutput() {
        val matrix = input().lines()
        var checkMatrix = matrix;
        var count = 0
        for(i in 0 until 4) {
            checkMatrix = turnMatrix(checkMatrix)
            count += checkMatrix.sumOf { line -> checkHorizontal(line) }
            count += makeDiagonalMatrix(checkMatrix).sumOf { line -> checkHorizontal(line) }
        }
        println("Ex1: $count")
        count = 0
        val charListMatrix = matrix.map{ line -> line.toCharArray().toList()}
        for(i in matrix.indices){
            for(j in matrix[0].indices){
                if(checkOutPiece(charListMatrix,i,j)) {
                    count ++
                }
            }
        }
        println("Ex2: $count")
    }

    private fun turnMatrix(matrix: List<String>) : List<String> {
        val newMatrix = Array(matrix[0].toCharArray().size){Array(matrix.size) {'.'} }
        for(i in matrix.indices){
            for(j in matrix[0].indices) {
                newMatrix[j][i] = matrix[i][j];
            }
        }
        return newMatrix.map { chars -> chars.joinToString ( "" ).reversed() };
    }

    private fun makeDiagonalMatrix(matrix: List<String>) : List<String> {
        val rows = matrix.size;
        val cols = matrix[0].toCharArray().size
        val outputList = ArrayList<ArrayList<Char>>(rows + cols)
        for (i in 0 until rows){
            for (j in 0 until cols) {
                val char = matrix[i][j]
                if(i + j !in outputList.indices){
                    outputList.add(i + j, ArrayList<Char>())
                }
                outputList[i + j].add(char)
            }
        }
        return outputList.map { chars -> chars.joinToString ( "")  };
    }

    private fun checkHorizontal(inputStr: String): Int{
        return("XMAS".toRegex().findAll(inputStr).count())
    }

    private fun checkOutPiece(matrix: List<List<Char>>, rowStart: Int, colStart: Int): Boolean {
        if(rowStart + 3 >  matrix.size || colStart + 3 > matrix[0].size){
            return false;
        }
        val checkMatrix =  matrix.subList(rowStart, rowStart + 3).map { row ->
            row.subList(colStart, colStart + 3).joinToString( "")
        }
        val check1 = makeDiagonalMatrix(checkMatrix)
        val check2 = makeDiagonalMatrix(turnMatrix(checkMatrix))
        val regex = "MAS|SAM".toRegex()
        return (regex.matches(check1[2]) && regex.matches(check2[2]))
    }
}