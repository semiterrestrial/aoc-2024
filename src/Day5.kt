fun main() {
    val lines = getInputAsList("day-5-input.txt")
    val rules = mutableListOf<Pair<Int, Int>>()
    val updates = mutableListOf<List<Int>>()

    lines.forEach {
        if (it.contains("|")) {
            val rule = it.split("|").map { it.toInt() }
            rules += Pair(rule[0], rule[1])
        } else if (it.contains(",")) {
            updates += it.split(",").map { it.toInt() }
        }
    }
    val (validUpdates, invalidUpdates) = updates.partition {  update ->
        rules.all { rule ->
            val first = update.indexOf(rule.first)
            val second = update.indexOf(rule.second)
            if (first > -1 && second > -1) {
                second > first
            } else {
                true
            }

        }
    }
    val result1 = validUpdates.sumOf { it[it.size / 2] }

    val comparator = Comparator<Int> { a, b ->
        //a is smaller than b if both are in rules and is in the first position
        if(rules.find { it.first == a && it.second == b} !=null) -1 else 1
    }

    val invalidSorted = invalidUpdates.map{it.sortedWith(comparator)}
    val result2 = invalidSorted.sumOf { it[it.size / 2] }
    printResults(result1, result2)
}