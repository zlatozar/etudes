package PrintersDevil

class Heading {

    private final Environment env

    Heading(Environment env) {
        this.env = env
    }

    String get() {

        final StringBuilder sbHeading = new StringBuilder()

        if (env.hasHeading()) {

            int headingLineNumb = 0
            for (int i = 0; i < env.getHeadingPosition() - 1; i++) {
                sbHeading.append(Constants.LINE_SEP)
                headingLineNumb++
            }

            String headingText = "page: ${env.getPageNumber()}"
            switch (env.getHeadingPlace()) {

                case 'left':
                    for (int i = 0; i < env.getMarginLeft(); i++) {
                        sbHeading.append(Constants.WORDS_SEP)
                    }
                    break

                case 'right':
                    int restBlanks = env.getTextWidth() - headingText.length()
                    for (int i = 0; i < restBlanks; i++) {
                        sbHeading.append(Constants.WORDS_SEP)
                    }
                    break

                default:
                    int pageCenter = env.getTextWidth().intdiv(2)
                    int titleCenter = headingText.length().intdiv(2)

                    for (int i = 0; i < pageCenter - titleCenter; i++) {
                        sbHeading.append(Constants.WORDS_SEP)
                    }
            }

            sbHeading.append(headingText)
            sbHeading.append(Constants.LINE_SEP)
            headingLineNumb++

            for (int i = headingLineNumb; i < env.getHeadingDepth(); i++) {
                sbHeading.append(Constants.LINE_SEP)
            }

            return sbHeading.toString()
        }

        return ''
    }

    int getSize() {
        return env.getHeadingDepth()
    }
}
