package PrintersDevil

import spock.lang.Specification
import spock.lang.Title

@Title('How lines are interpreted')
class InterpretLineSpec extends Specification {

    private static final COMMANDS_FILE_NAME = 'src/test/resources/commands.txt'
    private int breakCommands = 0

    def 'Discover commands'() {

        given: 'File that contains all commands'
        File commandsFile = new File(COMMANDS_FILE_NAME)
        assert commandsFile

        when: 'Reading file line by line (pass trimmed)'
        Environment env = new Environment()
        InterpretLine formattingCmd = new InterpretLine(env)

        commandsFile.eachLine({
            line ->
                String processed = ''
                try {
                    processed = formattingCmd.process(line)

                } catch (Exception _) {
                    println("Error: $line")
                }

                String[] command = processed.split(Constants.WORDS_SEP)
                if (command.size() > 0 && Constants.BREAK_COMMANDS.contains(command[0])) {
                    breakCommands++
                }
        })

        then: 'All immediate commands should be found'
        assert breakCommands == Constants.BREAK_COMMANDS.size()

        and: 'All commands should be included'
        assert Constants.ALL_COMMANDS.size() == 15
    }
}