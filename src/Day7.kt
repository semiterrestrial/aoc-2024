import java.nio.file.Files
import java.nio.file.Paths
import kotlin.time.measureTime


data class Equation(val result: Long, val inputs: List<Int>)
enum class Operator { PLUS, MULTIPLY, CONCAT }

fun main() {
    val time = measureTime {
    val lines = Files.lines(Paths.get("day-7-input.txt")).toList()
    val eqRegex = """(\d+):(.*)""".toRegex()
    val equations = lines.map { line ->
        val (result, inputs) = eqRegex.matchEntire(line)!!.destructured
        Equation(result.toLong(), inputs.trim().split(" ").map { it.toInt() })
    }

    fun <T : Enum<T>> List<T>.generateCombinations(n: Int): List<List<T>> {
        if (n == 0) return listOf(emptyList())
        val smallerCombinations = this.generateCombinations(n - 1)
        return smallerCombinations.flatMap { combination ->
            this.map { value -> combination + value }
        }
    }

    val operators = listOf(Operator.PLUS, Operator.MULTIPLY)
    val result1 = equations.filter { equation ->
        val inputs = equation.inputs
        val combinations = operators.generateCombinations(inputs.size - 1)
        //println("There are ${combinations.size} combination(s)")
        combinations.map { combination ->
            //println("Testing $equation : ${combination}")
            val calculated = inputs.foldIndexed(0L) { index, acc, n ->
                if (index == 0 || combination[index-1] == Operator.PLUS) acc + n else acc * n
            }
            //println("Combination is ${calculated == equation.result}")
            calculated == equation.result
        }.any{it}

    }
    println("Result 1: ${result1.sumOf { it.result  }}" )

    val part2operators = listOf(Operator.PLUS, Operator.MULTIPLY, Operator.CONCAT)
    val result2 = equations.filter { equation ->
        val inputs = equation.inputs
        val combinations = part2operators.generateCombinations(inputs.size - 1)
        //println("There are ${combinations.size} combination(s)")
        combinations.map { combination ->
            //println("Testing $equation : ${combination}")
            val calculated = inputs.foldIndexed(0L) { index, acc, n ->
                if(index == 0){

                    acc + n
                } else {
                    val currentOperator = combination[index-1]
                    if (currentOperator == Operator.PLUS)
                        acc + n
                    else if(combination[index-1] == Operator.MULTIPLY)
                        acc * n
                    else //operation is CONCAT so we must remove the previous number as it will be concatenated
                         ("$acc${inputs[index]}").toLong()
                }
            }
            //println("Combination is $calculated which is ${calculated == equation.result}")
            calculated == equation.result
        }.any{it}
    }
    println("Result 2 ${result2.sumOf { it.result  }}" )
    }
    println("Time spent: ${time} seconds")
}


