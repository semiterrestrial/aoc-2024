import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class UtilsKtTest {

    @Test
    fun getPositionAndValueInDirection() {
        val input = getInputAsCharArrays("testdata.txt")
        val start = 4 to 4
        val startValue = input[start.second][start.first]
        assertEquals('0', startValue)
        assertEquals('1', input.getPositionAndValueInDirection(start, Direction.N )?.second)
        assertEquals('2', input.getPositionAndValueInDirection(start, Direction.NE)?.second)
        assertEquals('3', input.getPositionAndValueInDirection(start, Direction.E)?.second)
        assertEquals('4', input.getPositionAndValueInDirection(start, Direction.SE)?.second)
        assertEquals('5', input.getPositionAndValueInDirection(start, Direction.S)?.second)
        assertEquals('6', input.getPositionAndValueInDirection(start, Direction.SW)?.second)
        assertEquals('7', input.getPositionAndValueInDirection(start, Direction.W)?.second)
        assertEquals('8', input.getPositionAndValueInDirection(start, Direction.NW)?.second)
    }


}