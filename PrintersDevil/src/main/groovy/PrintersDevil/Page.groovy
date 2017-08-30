package PrintersDevil

/**
 * Used for illustration of the formatted text
 */
class Page {

    private static final String LEFT = '|'
    private static final String RIGHT = '|' + Constants.LINE_SEP
    private static final String TOP_BOTTOM = '-'

    private final Environment env

    Page(Environment env) {
        this.env = env
    }

    /**
     * Content is bunch of lines that are already
     * processed and ready to be passed to the printer
     */
    void display(String content) {

        StringBuilder pageContent = new StringBuilder()
        String[] lines = content.split(Constants.LINE_SEP)

        int paperHeightBorder = env.getPapersizeHeight() + 3
        int paperWideBorder = env.getPapersizeWidth() + 3

        // top border
        for (int i = 0; i < paperWideBorder - 1; i++) {
            pageContent.append(TOP_BOTTOM)
        }

        pageContent.append(TOP_BOTTOM)
        pageContent.append(Constants.LINE_SEP)

        for (String line : lines) {
            pageContent.append(LEFT)
            pageContent.append(line)

            int fillRestWidth = paperWideBorder - line.size() - 2
            for (int i = 0; i < fillRestWidth; i++) {
                pageContent.append(Constants.WORDS_SEP)
            }

            pageContent.append(RIGHT)
        }

        int fillRestHeight = paperHeightBorder - lines.size() - 2
        for (int i = 0; i < fillRestHeight; i++) {
            pageContent.append(LEFT)

            for (int j = 0; j < paperWideBorder - 2; j++) {
                pageContent.append(Constants.WORDS_SEP)
            }
            pageContent.append(RIGHT)
        }


        for (int i = 0; i < paperWideBorder; i++) {
            pageContent.append(TOP_BOTTOM)
        }

        println(pageContent)
    }

}
