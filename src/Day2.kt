import java.nio.file.Files
import java.nio.file.Paths


enum class State { UNSET, INCREASING, DECREASING, SAME, OUT_OF_RANGE }

fun main() {

    fun stateOfPair(previousNumber: Int, nextNumber: Int): State {
        val isSame = previousNumber == nextNumber
        if (isSame) {
            return State.SAME
        }
        val isIncreasing = nextNumber in previousNumber..previousNumber + 3
        val isDecreasing = nextNumber in previousNumber - 3..previousNumber
        if (!isIncreasing && !isDecreasing) {
            return State.OUT_OF_RANGE
        }
        val nextState = if (isIncreasing) State.INCREASING else State.DECREASING
        return nextState
    }

    val lines = Files.lines(Paths.get("day-2-input.txt")).toList()
    assert(lines.size > 0)


    //count safe lines for task 1
    fun isLineSafe(digits: List<Int>): Boolean {

        val states = digits.windowed(2).map { sublist ->
            stateOfPair(sublist[0], sublist[1])
        }
        val allIncreasing = states.filter { it != State.UNSET }.all { it == State.INCREASING }
        val allDecreasing = states.filter { it != State.UNSET }.all { it == State.DECREASING }
        return allDecreasing || allIncreasing
    }

    val safeLines = lines.map { line ->
        isLineSafe(line.split(" ").map(String::toInt))
    }
    val safeLevelCount = safeLines.count { it }
    println("result 1: $safeLevelCount")

    val unsafeLines = lines.filter { !isLineSafe(it.split(" ").map { it.toInt() }) }

    val canBeMadeSafe = unsafeLines.map { line ->
        line.split(" ").map { it.toInt() }
    }.map { digits ->
        digits.mapIndexed { index, ints ->
            isLineSafe(digits.filterIndexed { i, _ -> i != index })
        }
    }.count { it.contains(true) }
    println("result 2: $canBeMadeSafe")
}

