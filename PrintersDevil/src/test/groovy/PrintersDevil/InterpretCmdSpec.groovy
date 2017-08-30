package PrintersDevil

import spock.lang.Specification
import spock.lang.Title

@Title('Checks how well interpretation works')
class InterpretCmdSpec extends Specification {

    private Environment env = new Environment()
    private InterpretLine interpretLine = new InterpretLine(env)

    def '?papersize [height width] (default 40 72) (break: TRUE)'() {

        given: 'Correct ?papersize command'
        String papersise = '?papersize 50 80 4'

        when: 'It is interpreted'
        env.getPapersizeHeight() == 40
        env.getPapersizeWidth() == 72
        interpretLine.process(papersise)

        then: 'Parameters should be set'
        assert env.getPapersizeHeight() == 50
        assert env.getPapersizeWidth() == 80
    }

    def 'Check ?mode [unfilled|fill|justify]'() {

        given: 'Correct ?mode command'
        String mode = '?mode justify'

        when: 'It is interpreted'
        interpretLine.process(mode)

        then: 'Parameters should be set'
        assert env.getParagraphMode() == 'justify'
    }

    def '?paragraph [indent gap] (default 3 0) (break: FALSE)'() {

        given: 'Correct ?paragraph command'
        String paragraph = '?paragraph 4 2'

        when: 'It is interpreted'
        interpretLine.process(paragraph)

        then: 'Parameters should be set'
        assert env.getParagraphIndent() == 4
    }

    def '?margin [left right] (break: TRUE)'() {

        given: 'Correct ?margin command'
        String margin = '?margin 10 20'

        when: 'It is interpreted'
        interpretLine.process(margin)

        then: '$margin should be set'
        assert env.getMarginLeft() == 10
        assert env.getMarginRight() == 20
    }

    def '?linespacing [gap] (default 1) (break: TRUE)'() {

        given: 'Correct ?linespacing command'
        String linespacing = '?linespacing 10 20'

        when: 'It is interpreted'
        interpretLine.process(linespacing)

        then: 'Parameters should be set'
        assert env.getLinespacingGap() == 10
    }

    def '?space [n] (default 0) (break: TRUE)'() {

        given: 'Correct ?space command'
        String space = '?space 10'

        when: 'It is interpreted'
        interpretLine.process(space)

        then: 'Parameters should be set'
    }

    def '?blank [n] (break: FALSE)'() {

        given: 'Correct ?blank command'
        String blank = '?blank 10'

        when: 'It is interpreted'
        interpretLine.process(blank)

        then: 'Parameters should be set'
    }

    def '?center (break: FALSE)'() {

        given: 'Correct ?center command'
        String center = '?center 10'

        when: 'It is interpreted'
        interpretLine.process(center)

        then: 'Parameters should be set'
    }

    def '?page  (break: TRUE)'() {

        given: 'Correct ?page command'
        String page = '?page 10'

        when: 'It is interpreted'
        interpretLine.process(page)

        then: 'Parameters should be set'
    }

    def '?testpage [n] (break: TRUE)'() {

        given: 'Correct ?testpage command'
        String testpage = '?testpage 10'

        when: 'It is interpreted'
        interpretLine.process(testpage)

        then: 'Parameters should be set'
    }

    def '?heading [depth place position] (break: FALSE)'() {

        given: 'Correct ?heading command'
        String heading = '?heading 10 center 4 additional'

        when: 'It is interpreted'
        interpretLine.process(heading)

        then: 'Parameters should be set'
        assert env.getHeadingDepth() == 10
        assert env.getHeadingPlace() == 'center'
        assert env.getHeadingPosition() == 4
    }

    def '?number [n] (break: FALSE)'() {

        given: 'Correct ?number command'
        String number = '?number 10'

        when: 'It is interpreted'
        interpretLine.process(number)

        then: 'Parameters should be set'
        assert env.getPageNumber() == 10
    }

    def '?break (break: TRUE)'() {

        given: 'Correct ?break command'
        String breakCMD = '?break 10'

        when: 'It is interpreted'
        interpretLine.process(breakCMD)

        then: 'Parameters should be set'
    }

    def '?footnote [depth] (break: FALSE)'() {

        given: 'Correct ?footnote command'
        String footnote = '?footnote 10'

        when: 'It is interpreted'
        interpretLine.process(footnote)

        then: 'Parameters should be set'
        assert env.getFootnoteDepth() == 10
    }

    def '?alias [fake real] (break: FALSE)'() {

        given: 'Correct ?alias command'
        String alias = '?alias fake real'

        when: 'It is interpreted'
        interpretLine.process(alias)

        then: 'Parameters should be set'
        assert env.getAliasFor('real') == 'fake'
    }
}