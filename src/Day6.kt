import kotlin.time.measureTime

fun main(args: Array<String>) {

    val input = getInputAsCharArrays("day-6-input.txt")
    var startYIndex: Int = -1;
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
    val traveledPathLength =
        inputCopy1.map { chars: CharArray -> chars.count { it == 'X' } }.sum() + 1 //include starting position
    println("Result 1: $traveledPathLength")
    val simulationTime = measureTime {
        var simulations = 0
        var loopCount = 0
        for (i in 0..input.size - 1) {
            for (j in 0..input[i].size - 1) {

                val inputCopy: Array<CharArray> = input.map { it.copyOf() }.toTypedArray()
                if (inputCopy[j][i] == '#' || inputCopy[j][i] == '^')
                    continue
                inputCopy[j][i] = '#'
                val loops = simulateGuardRun(indexOfGuard, startYIndex, movementDirections, inputCopy)
                if (loops)
                    loopCount++
            }
        }
        println("Result 2: $loopCount")
    }
    println("Simulation ran in $simulationTime sec")
}

private fun simulateGuardRun(
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
        val step = getValueInDirection(currentPosition, currentMovement, theRoom)
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