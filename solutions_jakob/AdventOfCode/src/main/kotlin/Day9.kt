package org.example

class Day9 : AdventDay("/day9") {

    override fun printOutput() {
        val extended = extendFileSystem()
        while (extended.contains(null)) {
            collapse(extended)
        }
        println(extended.mapIndexed { i, item -> item!! * i }.sum())
        val blockMap = extendFileSystemEx2()
        for (lastFile in ArrayList(blockMap).reversed().filterIsInstance<File>()) {
            val largeEnoughBlocks =
                blockMap.filterIsInstance<EmptySpace>().filter { sp -> sp.length >= lastFile.length }
            if (largeEnoughBlocks.isNotEmpty()) {
                switchSpaceWithFile(largeEnoughBlocks.first() as EmptySpace, lastFile, blockMap)
                mergeEmpties(blockMap)
            }
        }
        val returnList =
            blockMap.flatMap { bl -> if (bl is File) List(bl.length) { bl.id } else List(bl.length) { null } }
        println(returnList.mapIndexed { i, item -> if (item != null) item * i else 0 }.sum())

    }


    private fun extendFileSystem(): ArrayList<Long?> {
        val extended = ArrayList<Long?>()
        input().toCharArray().forEachIndexed { index, c -> //
            if (index % 2 == 0) {
                extended.addAll(List(c.digitToInt()) { index.toLong() / 2 })
            } else {
                extended.addAll(List(c.digitToInt()) { null })
            }
        }
        return extended
    }

    private fun extendFileSystemEx2(): ArrayList<Block> {
        val extended = ArrayList<Block>()
        input().toCharArray().forEachIndexed { index, c -> //
            if (index % 2 == 0) {
                extended.add(File(c.digitToInt(), index.toLong() / 2))
            } else {
                extended.add(EmptySpace(c.digitToInt()))
            }
        }
        return extended
    }

    private fun switchSpaceWithFile(empty: EmptySpace, file: File, list: ArrayList<Block>) {
        val fileIndex = list.indexOf(file)
        val emptyIndex = list.indexOf(empty)
        val fileLength = file.length
        val emptyLength = empty.length
        if (emptyIndex > fileIndex) return;
        list[emptyIndex] = file
        list[fileIndex] = empty
        list[fileIndex].length = file.length
        if (emptyLength > fileLength) {
            list.add(emptyIndex + 1, EmptySpace(emptyLength - fileLength))
        }
    }

    private fun mergeEmpties(list: ArrayList<Block>) {
        var i = 0;
        while (i + 1 < list.size) {
            if (list[i] is EmptySpace && list[i + 1] is EmptySpace) {
                list[i + 1].length += list[i].length
                list.removeAt(i)
            } else {
                i++
            }
        }
    }

    private fun collapse(lst: ArrayList<Long?>) {
        val last = lst.removeLast()
        if (last != null) {
            lst[lst.indexOf(null)] = last
        }
    }

    open class Block(open var length: Int)
    class EmptySpace(override var length: Int) : Block(length)
    class File(override var length: Int, val id: Long) : Block(length)

}