fun main() {
    val input = getInputAsString("day-3-input.txt")
    val mulRegexPart1 = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()
    val mulRegexPart2 = """mul\((\d{1,3}),(\d{1,3})\)|(do\(\))|(don't\(\))""".toRegex()
    val matches = mulRegexPart1.findAll(input)

    val result1 = matches
        .map { it.groupValues }
        .sumOf { group ->
            group[1].toInt() * group[2].toInt()
        }

    var shouldParse = true
    val result2 = mulRegexPart2.findAll(input)
        .map { it.groupValues }
        .sumOf { group ->
            val wholeMatch = group[0]
            if (shouldParse && wholeMatch != "do()" && wholeMatch != "don't()") {
                group[1].toInt() * group[2].toInt()
            } else {
                shouldParse = wholeMatch == "do()"
                0
            }
        }
    printResults(result1, result2)
}