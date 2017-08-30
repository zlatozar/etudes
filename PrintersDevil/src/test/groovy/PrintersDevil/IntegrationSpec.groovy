package PrintersDevil

import spock.lang.Specification

class IntegrationSpec extends Specification {

    private static final String COMMANDS_FILE_NAME = 'src/test/resources/example-page.txt'

    def 'Command combination'() {

        given: 'Formattor and environment'
        Environment env = new Environment()
        Formattor formattor = new Formattor(null, null, env)

        when: 'Command combination is given formattor should react'
        new File(COMMANDS_FILE_NAME).eachLine({
            line ->
                formattor.startLineByLine(line)
        })


        then: 'Page should be formatter like this'
        println "Done"
    }

}