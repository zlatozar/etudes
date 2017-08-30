package PrintersDevil

import spock.lang.Specification

class FormatterSpec extends Specification {

    def 'Immediately commands processing'() {

        given: 'Formattor instance'
        Environment env = new Environment()
        Formattor formattor = new Formattor(null, null, env)

        when: 'Start processing given line'
        String line = '?space 10'

        then: 'I should discover immediate commands'
        formattor.writeInPage(line)
    }

    def 'Command combination'() {

        given: 'Formattor and environment'
        Environment env = new Environment()
        Formattor formattor = new Formattor(null, null, env)

        when: 'Command combination is given formattor should react'
        String paperSize = '?papersize 5 42'
        String mode = '?mode fill'
        String line1= 'In the fill mode,  spaces    still have no effect,'
        String line2 = 'but now the words are all run close up and the right margin is'
        String line3 = 'raggedy.'

        String newPage = '?page'
        String[] pageContent = [paperSize, mode, line1, line2, line3, newPage]

        then: 'Page should be formatter like this'
        for(String line : pageContent) {
            formattor.startLineByLine(line)
        }

        println formattor.takeSnapshot()
    }

    def 'Unfill more than one line'() {

        given: 'Formattor and environment'
        Environment env = new Environment()
        Formattor formattor = new Formattor(null, null, env)

        when: 'Command combination is given formattor should react'
        String paperSize = '?papersize 40 42'
        String mode = '?mode unfilled'
        String line1= 'This text   will be taken exactly  as'
        String line2 = '   seen and it   better  not  run'
        String line3 = 'past      column    40.'

        String[] pageContent = [paperSize, mode, line1, line2, line3]

        then: 'Page should be formatter like this'
        for(String line : pageContent) {
            formattor.startLineByLine(line)
        }

        assert formattor.takeSnapshot() ==
                'This text   will be taken exactly  as\n' +
                '   seen and it   better  not  run\n' +
                'past      column    40.\n'
    }

    def 'Unfill more than one line with a given margin'() {

        given: 'Formattor and environment'
        Environment env = new Environment()
        Formattor formattor = new Formattor(null, null, env)

        when: 'Command combination is given formattor should react'
        String paperSize = '?papersize 40 42'
        String mode = '?mode unfilled'
        String margin = '?margin 3 43'
        String line1 = 'This text   will be taken exactly  as'
        String line2 = '   seen and it   better  not  run'
        String line3 = 'past      column    40.'

        String[] pageContent = [paperSize, mode, margin, line1, line2, line3]

        then: 'Page should be formatter like this'
        for (String line : pageContent) {
            formattor.startLineByLine(line)
        }

        assert formattor.takeSnapshot() ==
                '   This text   will be taken exactly  as\n' +
                '      seen and it   better  not  run\n' +
                '   past      column    40.\n'
    }


    def 'Fill more than one line'() {

        given: '2 lines are wider than page length'
        Environment env = new Environment()
        env.setPapersize(40, 42)
        env.setParagraphMode(Constants.FILL_mode)

        Formattor formattor = new Formattor(null, null, env)

        when: 'Try to store it in a page as fill it'

        String line1 = 'In the fill mode,  spaces    still have no effect,'
        String line2 = 'but now the words are all run close up and the right margin is'
        String line3 = 'raggedy.'
        String line4 = 'Research suggests that the ragged right  edge'
        String line5 = 'may improve reading speed.'
        String line6 = 'Notice also the paragraph break caused by ?mode.'

        String[] lines = [line1, line2, line3, line4, line5, line6]

        then: 'Text should be the filled'
        for (String line : lines) {
            formattor.startLineByLine(line)
        }

        assert formattor.takeSnapshot() ==
                '   In the fill mode, spaces still have no\n' +
                'effect, but now the words are all run \n' +
                'close up and the right margin is raggedy.\n' +
                'Research suggests that the ragged right \n' +
                'edge may improve reading speed. Notice \n' +
                'also the paragraph break caused by ?mode.'

    }

    def 'Fill more than one line with a given margin'() {

        given: '2 lines are wider than page length'
        Environment env = new Environment()
        env.setPapersize(40, 42)
        env.setParagraphMode(Constants.FILL_mode)
        env.setMargin(3, 42)

        Formattor formattor = new Formattor(null, null, env)

        when: 'Try to store it in a page as fill it'

        String line1 = 'In the fill mode,  spaces    still have no effect,'
        String line2 = 'but now the words are all run close up and the right margin is'
        String line3 = 'raggedy.'
        String line4 = 'Research suggests that the ragged right  edge'
        String line5 = 'may improve reading speed.'
        String line6 = 'Notice also the paragraph break caused by ?mode.'

        String[] lines = [line1, line2, line3, line4, line5, line6]

        then: 'Text should be the filled'
        for (String line : lines) {
            formattor.startLineByLine(line)
        }

        assert formattor.takeSnapshot() ==
                '      In the fill mode, spaces still \n' +
                '   have no effect, but now the words \n' +
                '   are all run close up and the right \n' +
                '   margin is raggedy. Research suggests\n' +
                '   that the ragged right edge may \n' +
                '   improve reading speed. Notice also \n' +
                '   the paragraph break caused by ?mode.'
    }


    def 'Justify more than one line'() {

        given: '2 lines are wider than page length'
        Environment env = new Environment()
        env.setPapersize(40, 42)
        env.setParagraphMode(Constants.JUSTIFY_mode)

        Formattor formattor = new Formattor(null, null, env)

        when: 'Try to store it in a page as justify it'

        String line1 = 'This sample section of text will be set justified. Notice that'
        String line2 = '   the way    spaces are left  has  no effect'
        String line3 =  'on     the output.'
        String line4 = 'Only word separation is caused by spaces.'
        String line5 = 'Thus, it is a good idea to start each source text sentence on a'
        String line6 = 'new line to make editing easier.'

        String[] lines = [line1, line2, line3, line4, line5, line6]

        then: 'Text should be the filled'
        for (String line : lines) {
            formattor.startLineByLine(line)
        }

        println formattor.takeSnapshot()
    }


}
