package PrintersDevil

/**
 * Detects commands and pass them to {@link Formattor}
 */
class InterpretLine {

    private final Environment env
    private final InterpretCmd interpret

    InterpretLine(Environment env) {
        this.env = env
        this.interpret = new InterpretCmd(env)
    }

    String process(String line) {

        if (!line.size()) {
            return ''
        }

        String[] possibleCmdParams = line.split(Constants.WORDS_SEP)
        String possibleCmd = possibleCmdParams[0]

        if (Constants.ALL_COMMANDS.contains(possibleCmd)) {

            return interpretCmd(possibleCmdParams).join(Constants.WORDS_SEP)
        }

        return line
    }

    // Helper functions

    private String[] interpretCmd(String[] command) {

        String commandKeyWord = command[0]

        String[] resultingCommand

        switch (commandKeyWord) {

            case Constants.$PAPERSIZE:
                resultingCommand = interpret.$PAPERSIZE(command)
                break

            case Constants.$MODE:
                resultingCommand = interpret.$MODE(command)
                break

            case Constants.$PARAGRAPH:
                resultingCommand = interpret.$PARAGRAPH(command)
                break

            case Constants.$MARGIN:
                resultingCommand = interpret.$MARGIN(command)
                break

            case Constants.$LINESPACING:
                resultingCommand = interpret.$LINESPACING(command)
                break

            case Constants.$SPACE:
                resultingCommand = interpret.$SPACE(command)
                break

            case Constants.$BLANK:
                resultingCommand = interpret.$BLANK(command)
                break

            case Constants.$CENTER:
                resultingCommand = interpret.$CENTER(command)
                break

            case Constants.$PAGE:
                resultingCommand = interpret.$PAGE(command)
                break

            case Constants.$TESTPAGE:
                resultingCommand = interpret.$TESTPAGE(command)
                break

            case Constants.$HEADING:
                resultingCommand = interpret.$HEADING(command)
                break

            case Constants.$NUMBER:
                resultingCommand = interpret.$NUMBER(command)
                break

            case Constants.$BREAK:
                resultingCommand = interpret.$BREAK(command)
                break

            case Constants.$FOOTNOTE:
                resultingCommand = interpret.$FOOTNOTE(command)
                break

            case Constants.$ALIAS:
                resultingCommand = interpret.$ALIAS(command)
                break

            // Indicates logical error
            default:
                throw new IllegalArgumentException("There is no defined interpretation for command: '$commandKeyWord'")
        }

        return resultingCommand
    }

}
