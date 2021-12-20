fun main() {
	val filetext = "ygdoohadaipe\n" +
		            "piedpiperepo\n" +
		            "rnfleleogprm\n" +
		            "pgiappcddeio\n" +
		            "ieoeskiratnn\n" +
		            "nrncoemaaecs\n" +
		            "obantrdgurei\n" +
		            "crkythnoqpse\n" +
		            "cedeesinrasu\n" +
		            "hankpilkanpr\n" +
		            "idrneolireun\n" +
		            "oardghpnrdsd\n" +
		            "allerednicse"
	val words = arrayOf(
		"donkey",
		"princess",
		"fiona",
		"lord",
		"farquaad",
		"monsieur",
		"hood"
	)
	println(solve(filetext, { /* strikethrough */ it + 0x336.toChar() }, *words))
}

fun solve(text: String, highlight: (String) -> String, vararg words: String): String {
	val board = text.replace("[ \\t]".toRegex(), "").split("\n".toRegex()).map { it.toCharArray() }
	val struckBoard = text.replace("[ \\t]".toRegex(), "").lowercase().split("\n".toRegex())
		.map { it.toCharArray().map { x -> x.toString() }.toMutableList() }.toMutableList()
	val maxLength = words.maxOf { it.length }

	for (x in board.indices) {
		for (y in board[x].indices) {
			if (board[x][y].isWhitespace()) continue

			for (i in -1..1) {
				for (j in -1..1) {
					if (i == 0 && j == 0) continue
					var nx = x
					var ny = y
					var length = 0
					var word = ""
					try {
						while (length++ <= maxLength) {
							word += board[nx][ny]

							if (word in words) {
								var oldx = nx
								var oldy = ny
								for (a in word.indices) {
									if (struckBoard[oldx][oldy].length == 1) {
										struckBoard[oldx][oldy] = highlight(struckBoard[oldx][oldy])
									}
									oldx -= i
									oldy -= j
								}
							}

							nx += i
							ny += j
						}
					} catch (e: IndexOutOfBoundsException) { /* ignored */ }
				}
			}
		}
	}

	return struckBoard.joinToString("\n") { it.joinToString("") }
}