import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class UtilsKtTest {

    @Test
    fun getPositionAndValueInDirection() {
        val input = getInputAsCharArrays("testdata.txt")
        val start = 4 to 4
        val startValue = input[start.second][start.first]
        assertEquals('0', startValue)
        assertEquals('1', getPositionAndValueInDirection(start, Direction.N, input)?.second)
        assertEquals('2', getPositionAndValueInDirection(start, Direction.NE, input)?.second)
        assertEquals('3', getPositionAndValueInDirection(start, Direction.E, input)?.second)
        assertEquals('4', getPositionAndValueInDirection(start, Direction.SE, input)?.second)
        assertEquals('5', getPositionAndValueInDirection(start, Direction.S, input)?.second)
        assertEquals('6', getPositionAndValueInDirection(start, Direction.SW, input)?.second)
        assertEquals('7', getPositionAndValueInDirection(start, Direction.W, input)?.second)
        assertEquals('8', getPositionAndValueInDirection(start, Direction.NW, input)?.second)
    }


}