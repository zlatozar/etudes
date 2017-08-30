package PrintersDevil

class Environment {

    private int $papersise_HEIGHT = 40
    private int $papersise_WIDTH  = 72

    private String $mode          = Constants.FILL_mode

    private int $paragraph_INDENT = 3

    private int $margin_LEFT      = 0
    private int $margin_RIGHT     = 72

    private int $linespacing_GAP  = 1

    private int $heading_DEPTH    = 0
    private String $heading_PLACE = ''  // left, right or center
    private int $heading_POSITION = 0

    private int $number_N         = 0
    private int $footnote_DEPTH   = 0

    // real:fake
    private Map<String, String> $alias_MAP = [:]

    int getPapersizeHeight() {
        return $papersise_HEIGHT
    }

    int getPapersizeWidth() {
        return $papersise_WIDTH
    }

    void setPapersize(int height, int width) {
        this.$papersise_HEIGHT = height
        this.$papersise_WIDTH = width

        this.$margin_RIGHT = width
    }

    String getParagraphMode() {
        return $mode
    }

    void setParagraphMode(String mode) {
        this.$mode = mode
    }

    int getParagraphIndent() {
        return $paragraph_INDENT + $margin_LEFT
    }

    /**
     * Note that line separator could have alias
     */
    String getLineSeparator() {
        StringBuilder sep = new StringBuilder()
        for (int i = 0; i < $linespacing_GAP; i++) {
            sep.append($alias_MAP.containsKey(Constants.LINE_SEP) ? $alias_MAP.get(Constants.LINE_SEP) : Constants.LINE_SEP)
        }

        return sep.toString()
    }

    void setParagraph(int indent) {
        this.$paragraph_INDENT = indent
    }

    int getMarginLeft() {
        return $margin_LEFT
    }

    int getMarginRight() {
        return $margin_RIGHT
    }

    void setMargin(int left, int right) {
        this.$margin_LEFT = left
        this.$margin_RIGHT = right
    }

    int getLinespacingGap() {
        return $linespacing_GAP
    }

    void setLinespacingGap(int linespacingGap) {
        this.$linespacing_GAP = linespacingGap
    }

    int getHeadingDepth() {
        return $heading_DEPTH
    }

    String getHeadingPlace() {
        return $heading_PLACE
    }

    int getHeadingPosition() {
        return $heading_POSITION
    }

    boolean hasHeading() {
        return $heading_DEPTH > 0
    }

    void setHeading(int depth, String place, int position) {
        this.$heading_DEPTH = depth
        this.$heading_PLACE = place
        this.$heading_POSITION = position
    }

    int getPageNumber() {
        return $number_N
    }

    void setPageNumber(int n) {
        this.$number_N = n
    }

    void incrementPageNumber() {
        this.$number_N++
    }

    int getFootnoteDepth() {
        return $footnote_DEPTH
    }

    void setFootnoteDepth(int depth) {
        this.$footnote_DEPTH = depth
    }

    boolean inFootnote() {
        return $footnote_DEPTH > 0
    }

    Map<String, String> getAliases() {
        return $alias_MAP
    }

    void setAlias(String real, String fake) {
        this.$alias_MAP[real] = fake
    }

    void resetAliases() {
        this.$alias_MAP = [:]
    }

    String getAliasFor(String key) {
        this.$alias_MAP[key]
    }

    int getTextWidth() {
        int rightOutdent = getPapersizeWidth() - getMarginRight()
        return getPapersizeWidth() - getMarginLeft() - rightOutdent
    }

    @Override
    String toString() {
        return "Environment {\n" +
                "     ?papersize ${getPapersizeHeight()} ${getPapersizeWidth()}\n" +
                "     ?mode ${getParagraphMode()}\n" +
                "     ?paragraph ${getParagraphIndent()}\n" +
                "     ?margin ${getMarginLeft()}, ${getMarginRight()}\n" +
                "     ?linespacing ${getLinespacingGap()}\n" +
                "     ?heading ${getHeadingDepth()} ${getHeadingPlace()} ${getHeadingPosition()}\n" +
                "     ?number ${getPageNumber()}\n" +
                "     ?footnote ${getFootnoteDepth()}\n" +
                "     ?alias ${getAliases()}\n" +
                "--------Meta---------\n" +
                "     text start position: ${getMarginLeft()}\n" +
                "     real page width: ${getTextWidth()}\n" +
                "}\n"
    }
}
