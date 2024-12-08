fun main() {
    val lines = getInputAsList("day-1-input.txt")
    val (firstList, secondList) = lines.map {  s ->
        s.split("   ").let {
            it[0].toInt() to it[1].toInt()
        }
    }.unzip()
    val firstSorted = firstList.sorted()
    val secondSorted = secondList.sorted()
    val differences = firstSorted.zip(secondSorted).sumOf { (first, second) -> first.coerceAtLeast(second) - first.coerceAtMost(second) }
    val numberOfMatches = firstSorted.map { firstVal -> secondSorted.count { it == firstVal } }
    val differencesByMatches =firstSorted.zip(numberOfMatches).sumOf { (first, second) -> first * second }
    printResults(differences, differencesByMatches)
}