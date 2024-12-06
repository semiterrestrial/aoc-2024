import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val lines = Files.lines(Paths.get("day-4-input.txt")).toList()
    val input = lines.map { line -> line.toCharArray() }.toTypedArray()
    assert(lines.size > 0)

    val directions = arrayOf(
        0 to -1,
        0 to 1,
        1 to 0,
        -1 to 0,
        1 to -1,
        -1 to -1,
        1 to 1,
        -1 to 1
    )

    val rangeNames = arrayOf("up", "down", "right", "left", "up right", "up left", "down right", "down left")

    fun findWord(word: String, input: Array<CharArray>): Int {
        val wordFirstChar = word[0]
        var matches = 0
        input.forEachIndexed { yIndex, letters ->
            letters.forEachIndexed { xIndex, char ->
                if (char == wordFirstChar) {

                    //find in all directions
                    directions.forEachIndexed { dirIndex, direction ->
                        val chars = mutableListOf<Char>()
                        var currentX = xIndex
                        var currentY = yIndex
                        dir@ for (i in 0..word.length - 1) {
                            chars.addLast(input[currentY][currentX])
                            currentX += direction.first
                            currentY += direction.second
                            if (currentX < 0 || currentY < 0 || currentX >= letters.size || currentY >= input.size)
                                break@dir
                        }
                        if (chars.joinToString("") == word) {
                            matches++
                            println("Found match at line $yIndex, $xIndex using ${rangeNames[dirIndex]}")
                        }
                    }

                }
            }
        }
        return matches
    }


    val result = findWord("XMAS", input)
    println("Result 1: $result")

    fun findCross(input: Array<CharArray>): Int {

        val movementDeltas = arrayOf(
            -1 to -1, //up left
            1 to -1,  //up right
            1 to 1, //down right
            -1 to 1
        ) //down left

        var matches = 0
        input.forEachIndexed { yIndex, letters ->
            letters.forEachIndexed { xIndex, char ->
                if (char == 'A') {
                    val topLeft = xIndex + movementDeltas[0].first to yIndex + movementDeltas[0].second
                    val topRight = xIndex + movementDeltas[1].first to yIndex + movementDeltas[1].second
                    val bottomRight = xIndex + movementDeltas[2].first to yIndex + movementDeltas[2].second
                    val bottomLeft = xIndex + movementDeltas[3].first to yIndex + movementDeltas[3].second
                    if (topLeft.first < 0 ||
                        bottomLeft.first < 0 ||
                        topLeft.second < 0 ||
                        topRight.second < 0 ||
                        topRight.first >= letters.size ||
                        bottomRight.first >= letters.size ||
                        bottomLeft.second >= input.size ||
                        bottomRight.second >= input.size
                    )
                        Unit
                    else {
                        val tl = input[topLeft.second][topLeft.first]
                        val tr = input[topRight.second][topRight.first]
                        val bl = input[bottomLeft.second][bottomLeft.first]
                        val br = input[bottomRight.second][bottomRight.first]
                        if (((tl == 'M' && br == 'S') || (tl == 'S' && br == 'M')) && ((bl == 'M' && tr == 'S') || (bl == 'S' && tr == 'M')))
                            matches++
                    }
                }
            }
        }
        return matches
    }

    val result2 = findCross(input)
    println("Result 2: $result2")

}