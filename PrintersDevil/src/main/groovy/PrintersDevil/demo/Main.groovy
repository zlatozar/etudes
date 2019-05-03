package PrintersDevil.demo

import PrintersDevil.Environment
import PrintersDevil.Formatter

class Main {

    private static final String DEMO_FILE_NAME = 'src/main/resources/example-page.txt'

    static void main(String[] args) {

        Environment env = new Environment()
        Formatter formatter = new Formatter(env)

        new File(DEMO_FILE_NAME).eachLine({
            line ->
                formatter.startLineByLine(line)
        })
    }

}
