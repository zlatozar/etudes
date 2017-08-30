package PrintersDevil

import spock.lang.Specification

class TextLineSpec extends Specification {

    private final static int PAGE_WIDTH = 42

    def 'Print the line as it is just fit it in the line dimension'() {

        given: 'Some pre-formatted material'
        Environment env = new Environment()
        env.setPapersize(72, PAGE_WIDTH)

        TextLine textLine = new TextLine(env)

        when: 'Try to store it in a page as it is'
        String unfilledLine = 'This text   will be taken exactly  as'

        then: 'Text should be the same'
        assert  textLine.unfilled(unfilledLine)[0] == 'This text   will be taken exactly  as\n'

        and: 'Margin should matter'
        env.setMargin(3, PAGE_WIDTH)

        assert textLine.unfilled(unfilledLine)[0] == '   This text   will be taken exactly  as\n'
    }

    def 'Unfill more than one line'() {

        given: '2 lines are wider than page length'
        Environment env = new Environment()
        env.setPapersize(72, PAGE_WIDTH)

        TextLine textLine = new TextLine(env)

        when: 'Try to store it in a page as it is'
        String unfilledLine1 = 'This text   will be taken exactly  as (extra extra)'
        String unfilledLine2 = '   seen and it   better  not  run'
        String[] lines = [unfilledLine1, unfilledLine2]

        then: 'Text should be the same and no rest symbols'
        StringBuilder sb = new StringBuilder()
        for (String line : lines) {
            sb.append(textLine.unfilled(line)[0])
        }

        assert sb.toString() ==
                'This text   will be taken exactly  as (ext\n' +
                '   seen and it   better  not  run\n'
    }

    def 'Print the line as try to fill it'() {

        given: 'Lines are wider than page length and could contain a lot of gaps'
        Environment env = new Environment()
        env.setPapersize(72, PAGE_WIDTH)

        TextLine textLine = new TextLine(env)

        String filled = '     In the fill  mode,  spaces    still have  no  effect,'

        when: 'Try to store it in a page as fill and tight as possible'

        then: 'It should be divided and words should have one space'
        String filledResult =
                'In the fill mode, spaces still have no \n' +
                'effect, '

        assert textLine.filled(filled, 0)[0] == filledResult
    }

    def 'Print the line as try to fill it and align it to right'() {

        given: 'Lines are wider than page length and could contain a lot of gaps'
        Environment env = new Environment()
        env.setPapersize(72, PAGE_WIDTH)

        TextLine textLine = new TextLine(env)

        String justified = '     In the fill  mode,  spaces    still have  no  effect,'

        when: 'Try to store it in a page'

        then: "It should be divided but can't be verified because of randomness"
        println textLine.justify(justified, '', 0)[0]
    }

}