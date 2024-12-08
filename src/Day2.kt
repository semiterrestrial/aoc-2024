import State.*

enum class State { UNSET, INCREASING, DECREASING, SAME, OUT_OF_RANGE }

fun main() {

    fun stateOfPair(previousNumber: Int, nextNumber: Int): State {
        val isSame = previousNumber == nextNumber
        if (isSame) {
            return SAME
        }
        val isIncreasing = nextNumber in previousNumber..previousNumber + 3
        val isDecreasing = nextNumber in previousNumber - 3..previousNumber
        if (!isIncreasing && !isDecreasing) {
            return OUT_OF_RANGE
        }
        val nextState = if (isIncreasing) INCREASING else DECREASING
        return nextState
    }

    fun isLineSafe(digits: List<Int>): Boolean {
        val states = digits.windowed(2).map { sublist ->
            stateOfPair(sublist[0], sublist[1])
        }
        val allIncreasing = states.filter { it != UNSET }.all { it == INCREASING }
        val allDecreasing = states.filter { it != UNSET }.all { it == DECREASING }
        return allDecreasing || allIncreasing
    }

    val input = getInputAsList("day-2-input.txt")
    val safeLines = input.map { line ->
        isLineSafe(line.split(" ").map(String::toInt))
    }
    val safeLevelCount = safeLines.count { it }
    val unsafeLines = input.filter { !isLineSafe(it.split(" ").map { it.toInt() }) }
    val canBeMadeSafe = unsafeLines.map { line ->
        line.split(" ").map { it.toInt() }
    }.map { digits ->
        List(digits.size) { index ->
            isLineSafe(digits.filterIndexed { i, _ -> i != index })
        }
    }.count { it.contains(true) }
    printResults(safeLevelCount, safeLevelCount + canBeMadeSafe)
}

