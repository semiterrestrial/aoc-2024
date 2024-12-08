import kotlin.time.measureTime

fun main() {

    fun simulateGuardRun(
        indexOfGuard: Int,
        startYIndex: Int,
        movementDirections: Array<Direction>,
        theRoom: Array<CharArray>
    ): Boolean {
        var currentStep = 0
        var currentMovementIndex = 0
        var currentPosition = indexOfGuard to startYIndex
        var currentMovement = movementDirections[currentMovementIndex]
        do {
            ++currentStep
            //naive check, if the whole room size is covered, we must be looping somehow
            if (currentStep > theRoom.size * theRoom[0].size)
                return true
            val step = theRoom.getPositionAndValueInDirection(currentPosition, currentMovement)
            step?.let { movement ->
                if (movement.second == '#') { //obstacle hit, turn right
                    currentMovementIndex++
                    currentMovementIndex %= 4
                    currentMovement = movementDirections[currentMovementIndex]
                    //println("Obstacle hit at ${movement.first}, changing direction to $currentMovement")

                } else {
                    theRoom[movement.first.second][movement.first.first] = 'X'
                    currentPosition = movement.first
                }
            }

        } while (step != null)
        return false
    }

    val input = getInputAsCharArrays("day-6-input.txt")
    var startYIndex: Int = -1
    val lineWithGuard = input.filterIndexed { index: Int, chars: CharArray ->
        if (chars.contains('^')) {
            startYIndex = index
            true
        } else false
    }
    assert(lineWithGuard.size == 1)
    val indexOfGuard = lineWithGuard[0].indexOf('^')
    val movementDirections = arrayOf(Direction.N, Direction.E, Direction.S, Direction.W)
    val inputCopy1: Array<CharArray> = input.map { it.copyOf() }.toTypedArray()

    simulateGuardRun(indexOfGuard, startYIndex, movementDirections, inputCopy1)
    val result1 = inputCopy1.sumOf { chars: CharArray -> chars.count { it == 'X' } } + 1 //include starting position

    val simulationTime = measureTime {
        var result2 = 0
        for (i in 0..input.size - 1) {
            for (j in 0..input[i].size - 1) {

                val inputCopy: Array<CharArray> = input.map { it.copyOf() }.toTypedArray()
                if (inputCopy[j][i] == '#' || inputCopy[j][i] == '^')
                    continue
                inputCopy[j][i] = '#'
                val loops = simulateGuardRun(indexOfGuard, startYIndex, movementDirections, inputCopy)
                if (loops)
                    result2++
            }
        }
        printResults(result1, result2)
    }
    println("Simulation ran in $simulationTime")
}