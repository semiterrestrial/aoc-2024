import java.nio.file.Files
import java.nio.file.Paths
import kotlin.time.measureTime

data class Equation(val result: Long, val inputs: List<Long>)
enum class Operator { PLUS, MULTIPLY, CONCAT }

fun List<Operator>.generateCombinations(n: Int): List<List<Operator>> {
    if (n == 0) return listOf(emptyList())
    val smallerCombinations = this.generateCombinations(n - 1)
    return smallerCombinations.flatMap { combination ->
        this.map { combination + it }
    }
}

fun calculate(
    equations: List<Equation>,
    operators: List<Operator>
): Long {
    return equations.parallelStream().filter { equation ->
        val inputs = equation.inputs
        val combinations = operators.generateCombinations(inputs.size - 1)
        combinations.map { combination ->
            val calculated = inputs.foldIndexed(0L) { index, acc, n ->
                if (index == 0) n
                else when (combination[index - 1]) {
                    Operator.PLUS -> acc + n
                    Operator.MULTIPLY -> acc * n
                    else -> ("$acc$n").toLong() //concatenate
                }
            }
            calculated == equation.result
        }.any { it }
    }.toList().sumOf { it.result }
}

fun main() {
    val time = measureTime {
        val lines = Files.lines(Paths.get("day-7-input.txt")).toList()
        val eqRegex = """(\d+):(.*)""".toRegex()
        val equations = lines.map { line ->
            val (result, inputs) = eqRegex.matchEntire(line)!!.destructured
            Equation(result.toLong(), inputs.trim().split(" ").map { it.toLong() })
        }
        val result1 = calculate(equations, listOf(Operator.PLUS, Operator.MULTIPLY))
        println("Result 1: $result1")

        val result2 = calculate(equations, listOf(Operator.PLUS, Operator.MULTIPLY, Operator.CONCAT))
        println("Result 2 $result2")
    }
    println("Time spent: $time seconds")
}