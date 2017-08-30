package PrintersDevil

import spock.lang.Specification

class PageSpec extends Specification {

    private final static int PAGE_WIDTH = 42

    def 'Text should be displayed in a page'() {

        given: '2 lines are wider than page length'
        Environment env = new Environment()
        env.setPapersize(8, PAGE_WIDTH)
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

        Page page = new Page(env)
        page.display(formattor.takeSnapshot())
   }
}