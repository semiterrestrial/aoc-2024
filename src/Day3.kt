fun main() {
    val input = getInputAsString("day-3-input.txt")
    val mulRegex = """mul\((\d{1,3}),(\d{1,3})\)|(do\(\))|(don't\(\))""".toRegex()

    val matches = mulRegex.findAll(input)
    val count = matches.toList().size
    println("Matches: $count")
    var shouldParse = true
    val multiplicatives = matches
        .map { it.groupValues }
        .sumOf { group ->
            println(group[0])
            if(group[0] != "do()" && group[0] != "don't()" && shouldParse) {
                val first = group[1].toInt()
                val second = group[2].toInt()
                println("multiply $first $second")
                group[1].toInt() * group[2].toInt()
            } else {
                shouldParse = group[0] == "do()"
                0
            }
        }
    println("Part 1: $multiplicatives")
}