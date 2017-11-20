package EasyDoesIt.demo;

import EasyDoesIt.Easy.Compiler;

import java.io.BufferedReader;
import java.io.FileReader;

class Main {

    private static final String DEMO_EASY_SOURCE = "src/main/resources/example.esy";

    public static void main(String[] args) {

        boolean compiledOK;

        String sourceName;

        if (args.length != 1) {
            System.out.println("\nUsage: ec <filename.esy>\n");

            System.out.println("Example program from the book will be used: \n");

            displayDemoSource();
            sourceName = DEMO_EASY_SOURCE;

            System.out.println("\n\n");

        } else {
            sourceName = args[0];
        }

        compiledOK = Compiler.compileProgram(sourceName, false, false);

        System.out.println("\nIs compilation pass? " + compiledOK + "\n\n");
    }

    // Helper function

    private static void displayDemoSource() {

        try (BufferedReader br = new BufferedReader(new FileReader(DEMO_EASY_SOURCE))) {

            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
