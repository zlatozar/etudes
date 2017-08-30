package PrintersDevil.demo

import PrintersDevil.Environment
import PrintersDevil.Formattor

class Main {

    private static final String DEMO_FILE_NAME = 'src/main/resources/example-page.txt'

    public static void main(String[] args) {

        Environment env = new Environment()
        Formattor formattor = new Formattor(env)

        new File(DEMO_FILE_NAME).eachLine({
            line ->
                formattor.startLineByLine(line)
        })
    }

}
