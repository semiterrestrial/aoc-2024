import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class UtilsKtTest {

    @Test
    fun getValueInDirection() {
        val input = getInputAsCharArrays("testdata.txt")
        val start = 4 to 4
        val startValue = input[start.second][start.first]
        assertEquals('0', startValue)
        assertEquals('1', getValueInDirection(start, Direction.N, input)?.second)
        assertEquals('2', getValueInDirection(start, Direction.NE, input)?.second)
        assertEquals('3', getValueInDirection(start, Direction.E, input)?.second)
        assertEquals('4', getValueInDirection(start, Direction.SE, input)?.second)
        assertEquals('5', getValueInDirection(start, Direction.S, input)?.second)
        assertEquals('6', getValueInDirection(start, Direction.SW, input)?.second)
        assertEquals('7', getValueInDirection(start, Direction.W, input)?.second)
        assertEquals('8', getValueInDirection(start, Direction.NW, input)?.second)
    }


}