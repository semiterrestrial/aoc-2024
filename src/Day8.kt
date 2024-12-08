import kotlin.time.measureTime

data class Antenna(val char: Char, val position: Pair<Int, Int>)
data class AntiNode(val position: Pair<Int, Int>)

fun main() {
    fun findAntiNodes(
        antennae: Map<Char, List<Antenna>>,
        bounds: Pair<Int, Int>,
        withHarmonics: Boolean = false
    ): List<AntiNode> {
        return buildList {
            antennae.entries.forEach { entry ->
                val positions = entry.value
                positions.forEach { antenna ->
                    positions.minusElement(antenna).forEach { other ->
                        val diff =
                            other.position.first - antenna.position.first to other.position.second - antenna.position.second
                        var nextPosition: Pair<Int, Int>? = other.position
                        do {
                            val antinodePos = nextPosition!!.first + diff.first to nextPosition.second + diff.second
                            if (antinodePos.first > -1 && antinodePos.first < bounds.first && antinodePos.second > -1 && antinodePos.second < bounds.second) {
                                add(AntiNode(antinodePos))
                                nextPosition = antinodePos
                            } else {
                                nextPosition = null
                            }
                        } while (withHarmonics && nextPosition != null)
                        //if harmonics, other is also an antinode
                        if (withHarmonics) {
                            add(AntiNode(other.position))
                        }
                    }
                }
            }
        }
    }

    val input = getInputAsCharArrays("day-8-input.txt")
    val bounds = input.size to input[0].size

    val antennaeMap = input.mapIndexed { yIndex, chars ->
        chars.mapIndexed { xIndex, char ->
            if (char != '.') Antenna(char, Pair(yIndex, xIndex)) else null
        }.mapNotNull { it }
    }.flatten().groupBy { it.char }
    println(
        "Time taken:  ${
            measureTime {
                println("Result 1:${findAntiNodes(antennaeMap, bounds).distinct().count()}")
                println("Result 2 ${findAntiNodes(antennaeMap, bounds, true).distinct().count()}")
            }
        }")

}