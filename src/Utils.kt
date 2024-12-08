import java.nio.file.Files
import java.nio.file.Paths

//null if out of bounds
fun getValueInDirection(
    currentLocation: Pair<Int, Int>, direction: Direction, data: Array<CharArray>
): Pair<Pair<Int, Int>, Char>? {
    val x = currentLocation.first
    val y = currentLocation.second
    val next = x + direction.delta.first to y + direction.delta.second
    //bounds check
    if (next.first < 0 || next.first >= data.size || next.second < 0 || next.second >= data.size) return null
    return next to data[next.second][next.first]
}

enum class Direction(val delta: Pair<Int, Int>) {

    N(0 to -1), NE(1 to -1), E(1 to 0), SE(1 to 1), S(0 to 1), SW(-1 to 1), W(-1 to 0), NW(-1 to -1)
}

fun getInputAsCharArrays(inputPath: String): Array<CharArray> {
    val lines = Files.lines(Paths.get(inputPath)).toList()
    val input = lines.map { line -> line.toCharArray() }.toTypedArray()
    assert(lines.size > 0)
    return input
}

fun positionIsWithinBounds(position: Pair<Int, Int>, bounds: Pair<Int,Int>): Boolean {
    return position.first > -1 && position.first < bounds.first && position.second > -1 && position.second < bounds.second
}

fun positionsAreWithinBounds(positions: List<Pair<Int, Int>>, bounds: Pair<Int,Int>): Boolean {
    return positions.all{ positionIsWithinBounds(it, bounds)}
}

fun printResults(vararg results:Int) {
    for(i in results.indices){
        println("Result $i: ${results[i]}")
    }
}