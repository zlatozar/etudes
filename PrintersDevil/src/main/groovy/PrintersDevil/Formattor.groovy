package PrintersDevil

/**
 * Main logic is defined here. Here is how it works:
 *
 * For some 'long lived' print parameters it use environment. Basically this commands
 * are related to how line(paragraph) looks like. Rest command required immediate
 * reaction so actions is taken here.
 */
class Formattor {

    private final Environment env

    private final InterpretLine interpretLine
    private final TextLine text
    private final Heading heading
    private final Page page

    private StringBuilder sbParagraph = new StringBuilder()
    private StringBuilder sbFootnote = new StringBuilder()

    // state variables
    private boolean firstLineInParagraph = true
    private boolean nextInCenter = false

    private int inheritedSpaces = 0
    private String inheritedLine = ''

    Formattor(Environment env) {

        this.env = env
        this.interpretLine = new InterpretLine(env)
        this.text = new TextLine(env)
        this.heading = new Heading(env)
        this.page = new Page(env)
    }

    void startLineByLine(String line) {

        int lineNumber = 1
        try {
            writeInPage(interpretLine.process(line))
            lineNumber++

        } catch (Exception e) {
            println("ERROR: line: $lineNumber, ${e.getMessage()}")
            e.printStackTrace()
        }
    }

    protected void writeInPage(String line) {

        // [commandName, N]
        String[] words = line.split(Constants.WORDS_SEP)
        StringBuilder buffer = env.inFootnote() ? sbFootnote : sbParagraph

        if (line == Constants.$BREAK) {
            breakParagraph(buffer)

        } else if (line.startsWith(Constants.$SPACE)) {
            processSpace(buffer, words[1])

        } else if (line.startsWith(Constants.$BLANK)) {
            processBlank(buffer, words[1])

        } else if (line.startsWith(Constants.$CENTER)) {
            breakParagraph(buffer)
            nextInCenter = true

        } else if (line.startsWith(Constants.$PAGE)) {
            breakParagraph(buffer)
            flushContent()
            startNewPage()

        } else if (line.startsWith(Constants.$TESTPAGE)) {
            breakParagraph(buffer)
            processTestpage(words[1])

        } else if (line.startsWith(Constants.$NOTBREAK)) {
            // NOP

        } else {
            processTextLine(buffer, line)
        }
    }

    /**
     * Doesn't include 'inheritedLine'
     */
    protected String takeSnapshot() {
        StringBuilder pageContent = new StringBuilder()

        pageContent.append(heading.get())
        pageContent.append(sbParagraph)
        pageContent.append(sbFootnote)

        return pageContent.toString()
    }

    // Helper functions

    /**
     * How line will be placed in page is defined in paragraph mode and page dimension
     *
     * NOTE: before text aligning all symbols from the ALIAS command should be replaced,
     * except line separator. This is not implemented!
     */
    private void processTextLine(StringBuilder buffer, String line) {

        if (nextInCenter) {
            processCenter(buffer, line)
            nextInCenter = false

        } else {

            switch (env.getParagraphMode()) {

                case Constants.FILL_mode:
                    indentIfNeeded(buffer)
                    writeText(buffer, extractInheritedSpaces(text.filled(line, inheritedSpaces)))
                    break

                case Constants.JUSTIFY_mode:
                    indentIfNeeded(buffer)
                    writeText(buffer, extractInheritedLines(text.justify(line, inheritedLine, inheritedSpaces)))
                    inheritedSpaces = 0
                    break

                default:
                    writeText(buffer, extractInheritedSpaces(text.unfilled(line, 0)))
            }
        }
    }

    // Helper function

    /**
     * It means to flush state variables and add line separator
     */
    private void breakParagraph(StringBuilder buffer) {

        // not first line in a page
        if (buffer.size() != 0) {
            if (inheritedLine.size() > 0) {
                buffer.append(inheritedLine)
                inheritedLine = ''
            }
            buffer.append(Constants.LINE_SEP)
        }

        firstLineInParagraph = true
        inheritedSpaces = 0
    }

    /**
     * Take the center of the line and text and make calculations.
     */
    private void processCenter(StringBuilder buffer, String line) {
        int pageCenter = env.getTextWidth().intdiv(2)
        int titleCenter = line.length().intdiv(2)

        for (int i = 0; i < pageCenter - titleCenter; i++) {
            buffer.append(Constants.WORDS_SEP)
        }

        buffer.append(Constants.LINE_SEP)
    }

    private void processSpace(StringBuilder buffer, String n) {

        int sepNumber = n.toInteger()

        if (sepNumber > 0) {

            for (int i = 0; i < sepNumber; i++) {

                if (canAddLine(env.getLinespacingGap())) {
                    buffer.append(env.getLineSeparator())

                } else {
                    flushContent()
                }
            }
        }
    }

    private void processBlank(StringBuilder buffer, String n) {

        int spaceNumber = n.toInteger()

        if (spaceNumber > 0) {

            for (int i = 0; i < spaceNumber; i++) {

                if (canAddLine()) {
                    buffer.append(Constants.LINE_SEP)

                } else {
                    flushContent()
                }
            }
        }
    }

    private void processTestpage(String n) {

        int numNewLines = n.toInteger()

        if (numNewLines <= (env.getPapersizeHeight() - currentPageLineNumbers())) {
            flushContent()
            startNewPage()
        }

        // ignore
    }

    private void indentIfNeeded(StringBuilder buffer) {

        if (firstLineInParagraph) {
            for (int i = 0; i < env.getParagraphIndent(); i++) {
                buffer.append(Constants.WORDS_SEP)
                inheritedSpaces++
            }
            firstLineInParagraph = false
        }
    }

    /**
     * Write lines in buffers until they could be flushed in page
     * with a given dimension the start new page.
     */
    private void writeText(StringBuilder buffer, String text) {
        String[] lines = text.split(Constants.LINE_SEP)
        int numLines = lines.size()

        if (canAddLine(numLines)) {
            buffer.append(text)

        } else {

            // one by one
            for (int i = 0; i < numLines; i++) {
                if (!canAddLine()) {
                    flushContent()
                }
                buffer.append(lines[i])
            }
        }
    }

    private boolean canAddLine(int n = 1) {
        return env.getPapersizeHeight() >= currentPageLineNumbers() + n
    }

    private void flushContent() {
        StringBuilder pageContent = new StringBuilder()

        pageContent.append(heading.get())
        pageContent.append(sbParagraph)
        pageContent.append(sbFootnote)

        page.display(pageContent.toString())

        startNewPage()
    }

    private String extractInheritedSpaces(String[] processedLine) {
        inheritedSpaces = processedLine[1].toInteger()
        return processedLine[0]
    }

    private String extractInheritedLines(String[] processedLine) {
        inheritedLine = processedLine[1]
        return processedLine[0]
    }

    private void startNewPage() {
        sbParagraph = new StringBuilder()
        sbFootnote = new StringBuilder()

        env.incrementPageNumber()
        inheritedSpaces = 0
        firstLineInParagraph = true
    }

    private int sbSize(StringBuilder stringBuilder) {
        return stringBuilder.toString().split(Constants.LINE_SEP).size()
    }

    private int currentPageLineNumbers() {
        int baseBuffers = heading.getSize() + sbSize(sbParagraph) + sbSize(sbFootnote)

        return inheritedLine.size() > 0 ? baseBuffers + 1 : baseBuffers
    }
}
