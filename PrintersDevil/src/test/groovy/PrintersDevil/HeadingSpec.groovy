package PrintersDevil

import spock.lang.Specification

class HeadingSpec extends Specification {

    private final Environment env = new Environment()

    def 'Check how heading looks like left aligned'() {

        given: 'Environment'

        env.setPageNumber(5)
        env.setHeading(5, 'left', 3)

        when: 'Page is constructed'

        then: 'Heading should be on the left'
        def heading = new Heading(env)
        assert heading.get() ==
                '\n' +
                '\n' +
                'page: 5\n' +
                '\n' +
                '\n'
    }

    def 'Check how heading looks like right aligned'() {

        given: 'Environment'

        env.setPageNumber(5)
        env.setHeading(5, 'right', 3)

        when: 'Page is constructed'
        env.setPapersize(42, 10)

        then: 'Heading should be on the right'
        def heading = new Heading(env)
        assert heading.get() ==
                '\n' +
                '\n' +
                '   page: 5\n' +
                '\n' +
                '\n'
    }

    def 'Check how heading looks like centered'() {

        given: 'Environment'

        env.setPageNumber(5)
        env.setHeading(5, 'center', 3)

        when: 'Page is constructed'
        env.setPapersize(42, 20)

        then: 'Heading should be in the center'
        def heading = new Heading(env)
        assert heading.get() ==
                '\n' +
                '\n' +
                '       page: 5\n' +
                '\n' +
                '\n'
    }

}