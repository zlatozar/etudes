package PrintersDevil

class Constants {

    // here is all 15th commands
    final static String $PAPERSIZE   = '?papersize'
    final static String $MODE        = '?mode'
    final static String $PARAGRAPH   = '?paragraph'
    final static String $MARGIN      = '?margin'
    final static String $LINESPACING = '?linespacing'
    final static String $SPACE       = '?space'
    final static String $BLANK       = '?blank'
    final static String $CENTER      = '?center'
    final static String $PAGE        = '?page'
    final static String $TESTPAGE    = '?testpage'
    final static String $HEADING     = '?heading'
    final static String $NUMBER      = '?number'
    final static String $BREAK       = '?break'
    final static String $NOTBREAK    = '?notbreak'      // used internally
    final static String $FOOTNOTE    = '?footnote'
    final static String $ALIAS       = '?alias'

    final static Set<String> ALL_COMMANDS = [$PAPERSIZE, $MODE, $PARAGRAPH, $MARGIN, $LINESPACING, $SPACE, $BLANK, $CENTER, $PAGE,
                                $TESTPAGE, $HEADING, $NUMBER, $BREAK, $FOOTNOTE, $ALIAS]

    final static Set<String> BREAK_COMMANDS = [$PAPERSIZE, $MODE, $MARGIN, $LINESPACING, $SPACE, $PAGE, $TESTPAGE, $BREAK]

    final static String WORDS_SEP = ' '
    final static String LINE_SEP = System.getProperty("line.separator")

    // Assume that syntax and punctuation is correct
    // final static Set<String> SENTENCE_END = ['.', '?', '!', '.)', '?)', '!)', '."', '?"', '!"', '.")', '?")', '!")', ':']

    final static String UNFILLED_mode = 'unfilled'  // line as is
    final static String FILL_mode     = 'fill'      // as tightly as possible (default)
    final static String JUSTIFY_mode  = 'justify'   // add extra blanks to aline last word to right margin

    final static Set<String> POSSIBLE_MODES = [UNFILLED_mode, FILL_mode, JUSTIFY_mode]
}
