package PrintersDevil

/**
 * This is the heart of the project. All line transformations
 * are made here. Important thing is that if line can't fit it
 * should be appended(considered) when new line comes.
 */
class TextLine {

    private final Environment env

    TextLine(Environment env) {
        this.env = env
    }

    String[] unfilled(String rawLine, int spaceLeft = 0) {

        StringBuilder allLines = new StringBuilder()

        for (int j = 0; j < env.getMarginLeft(); j++) {
            allLines.append(Constants.WORDS_SEP)
        }

        allLines.append(fitOrIgnore(rawLine, env.getTextWidth()))

        allLines.append(env.getLineSeparator())
        return [allLines.toString(), 0]
    }

    /**
     * Get chunk as big as page width, ignore rest
     */
    private static String fitOrIgnore(String line, int width) {

        if (line.length() == 0) {
            return ''
        }

        if (line.length() <= width) {
            return line
        }

        return line.substring(0, width)
    }

    /**
     * Start adding word by word and when capacity of the line
     * finish add line separator and continue.
     */
    String[] filled(String rawLine, int inheritedSpaces) {

        StringBuilder allLines = new StringBuilder()

        int currentLineLength = inheritedSpaces
        String[] words = rawLine.split(Constants.WORDS_SEP)

        words.each {

            if (it.size() == 0) {
                return
            }

            if (env.getTextWidth() >= currentLineLength + it.size()) {
                allLines.append(it)
                currentLineLength = currentLineLength + it.length()

            } else {
                // start new line
                allLines.append(env.getLineSeparator())
                currentLineLength = 0

                // add margin
                for (int j = 0; j < env.getMarginLeft(); j++) {
                    allLines.append(Constants.WORDS_SEP)
                    currentLineLength++
                }

                allLines.append(it)
                currentLineLength = currentLineLength + it.length()
            }

            if (currentLineLength + 1 < env.getTextWidth()) {
                allLines.append(Constants.WORDS_SEP)
                currentLineLength++
            }
        }

        return [allLines.toString(), currentLineLength]
    }

    /**
     * To the available gaps should be added redundant gaps.
     *
     * For every line take word gaps. Add to them redundant spaces in random way
     * and start rebuild the line.
     */
    String[] justify(String rawLines, String inheritedLine, int paragraphIndent) {

        String fullRawLines = inheritedLine.size() > 0? inheritedLine + Constants.WORDS_SEP + rawLines : rawLines
        int indent = paragraphIndent > 0 ? paragraphIndent : 0

        String filled = filled(fullRawLines, indent)[0]

        StringBuilder allLines = new StringBuilder()

        String[] lines = filled.split(Constants.LINE_SEP)

        for (int i = 0; i < lines.length - 1; i++) {
            allLines.append(fitAndJustify(lines[i], indent))
            allLines.append(env.getLineSeparator())
        }

        return [allLines.toString(), lines[lines.length - 1]]
    }

    private String fitAndJustify(String rawLine, int indent = 0) {

        StringBuilder allLines = new StringBuilder()

        for (int i = 0; i < env.getMarginLeft(); i++) {
            allLines.append(Constants.WORDS_SEP)
        }

        // line could end with space
        int availableBlanks = env.getTextWidth() - calculateFilledLength(rawLine) - indent

        if (availableBlanks == 0) {
            allLines = new StringBuilder()
            allLines.append(rawLine)

        } else {

            List<String> words = extractWords(rawLine)

            if (words.size() == 1) {
                allLines.append(words[0])
                return allLines
            }

            int availableGaps = words.size() - 1

            List<Integer> lengthOfGaps = []
            for (int j = 0; j < availableGaps; j++) {
                lengthOfGaps.add(1)
            }

            for (int k = 0; k < availableBlanks; k++) {
                int index = k % lengthOfGaps.size()
                lengthOfGaps[index] = lengthOfGaps[index] + 1
            }

            randomizeList(lengthOfGaps)

            for (int f = 0; f < words.size(); f++) {

                allLines.append(words[f])

                for (int h = 0; h < lengthOfGaps[f]; h++) {
                    allLines.append(Constants.WORDS_SEP)
                }

            }
        }

        return allLines.toString()
    }

    // Helper functions

    private static List<String> extractWords(String allWords) {

        List<String> words = []

        String[] split = allWords.split(Constants.WORDS_SEP)
        for (String word : split) {
            if (word.length() > 0) {
                words.add(word)
            }
        }

        return words
    }

    private static int calculateFilledLength(String allWords) {

        int lineLength = 0
        int numberOfWords = 0

        String[] split = allWords.split(Constants.WORDS_SEP)

        for (String word : split) {
            if (word.size() > 0) {
                lineLength += word.size()
                numberOfWords++
            }
        }

        return lineLength + (numberOfWords - 1)
    }

    private static void randomizeList(List<Integer> list) {
        Collections.shuffle(list)
    }
}
