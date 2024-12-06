import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val lines = Files.lines(Paths.get("day-1-input.txt")).toList()
    val numbers = """(\d+)\s+(\d+)""".toRegex()  //regex for the two numbers
    val accumulator = mutableListOf<Int>() to mutableListOf<Int>() //accumulator is just two lists
    lines.fold(accumulator) { acc, s ->
        numbers.find(s)?.let {
            acc.first.add(it.groups[1]!!.value.toInt())
            acc.second.add(it.groups[2]!!.value.toInt())
        }
        acc
    }.toList().map { it.sort() }
    val differences = accumulator.first.zip(accumulator.second).sumOf { (first, second) -> first.coerceAtLeast(second) - first.coerceAtMost(second) }
    val numberOfMatches = accumulator.first.map { firstVal -> accumulator.second.count { it == firstVal } }
    val differencesByMatches = accumulator.first.zip(numberOfMatches).sumOf { (first, second) -> first * second }
    println("Part 1 = $differences, Part 2 = $differencesByMatches")
}