package PrintersDevil

import spock.lang.Specification

class IntegrationSpec extends Specification {

    private static final String COMMANDS_FILE_NAME = 'src/test/resources/example-page.txt'

    def 'Command combination'() {

        given: 'Formatter and environment'
        Environment env = new Environment()
        Formatter formatter = new Formatter(env)

        when: 'Command combination is given formatter should react'
        new File(COMMANDS_FILE_NAME).eachLine({
            line ->
                formatter.startLineByLine(line)
        })

        then: 'Page should be formatter like this'
        println "Done"
    }

}