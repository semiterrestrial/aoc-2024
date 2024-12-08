import Direction.*

fun main() {
    val input = getInputAsCharArrays("day-4-input.txt")
    val bounds = input.getBounds()

    val directions = arrayOf(N, S, E, W, NE, NW, SE, SW)

    fun findWord(word: String, input: Array<CharArray>): Int {
        val firstChar = word[0]
        var matches = 0
        input.forEachIndexed { yIndex, letters ->
            letters.mapIndexed { xIndex, char ->
                if (char == firstChar) {
                    //find in all directions
                    directions.forEach { direction ->
                        val chars = mutableListOf<Char>()
                        var currentX = xIndex
                        var currentY = yIndex
                        for (i in word.indices) {
                            chars.addLast(input[currentY][currentX])
                            currentX += direction.delta.first
                            currentY += direction.delta.second
                            if (!positionIsWithinBounds(currentY to currentX, bounds))
                                break
                        }
                        if (chars.joinToString("") == word) matches++
                    }
                }
            }
        }
        return matches
    }

    val result1 = findWord("XMAS", input)

    fun findMASCross(input: Array<CharArray>): Int {
        val lettersToMatch = listOf('M', 'S')
        return input.mapIndexed { yIndex, letters ->
            letters.mapIndexed { xIndex, char ->
                if (char == 'A') {
                    val topLeft = xIndex + NW.delta.first to yIndex + NW.delta.second
                    val topRight = xIndex + NE.delta.first to yIndex + NE.delta.second
                    val bottomRight = xIndex + SE.delta.first to yIndex + SE.delta.second
                    val bottomLeft = xIndex + SW.delta.first to yIndex + SW.delta.second
                    if (positionsAreWithinBounds(listOf(topLeft, topRight, bottomRight, bottomLeft), bounds)) {
                        val tl = input[topLeft.second][topLeft.first]
                        val tr = input[topRight.second][topRight.first]
                        val bl = input[bottomLeft.second][bottomLeft.first]
                        val br = input[bottomRight.second][bottomRight.first]
                        (listOf(tl, br).containsAll(lettersToMatch) && (listOf(bl, tr).containsAll(lettersToMatch)))
                    } else false
                } else
                    false
            }
        }.flatten().count { it }
    }

    val result2 = findMASCross(input)
    printResults(result1, result2)

}