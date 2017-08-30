package EasyDoesIt.Easy;

import EasyDoesIt.Easy.AbstractSyntaxTrees.Program;
import EasyDoesIt.Easy.SyntacticAnalizer.Parser;
import EasyDoesIt.Easy.SyntacticAnalizer.Scanner;
import EasyDoesIt.Easy.SyntacticAnalizer.SourceFile;

/**
 * The main driver class for the Easy compiler.
 */
public class Compiler {

    static String objectName = "obj.esy";

    private static Scanner scanner;
    private static Parser parser;
    private static ErrorReporter reporter;

    /**
     * The AST representing the source program.
     */
    private static Program theAST;

    static boolean compileProgram(String sourceName, String objectName, boolean showingAST, boolean showingTable) {

        System.out.println("********** Easy Compiler (Java Version 0.1) **********");

        System.out.println("Syntactic Analysis ...");
        SourceFile source = new SourceFile(sourceName);

        if (source == null) {
            System.out.println("Can't access source file " + sourceName);
            System.exit(1);
        }

        scanner = new Scanner(source);
        reporter = new ErrorReporter();
        parser = new Parser(scanner, reporter);

        theAST = parser.parseProgram();                     // 1st pass

        return reporter.numErrors == 0;
    }

    public static void main(String[] args) {
        boolean compiledOK;

        if (args.length != 1) {
            System.out.println("Usage: esy filename");
            System.exit(1);
        }

        String sourceName = args[0];
        compiledOK = compileProgram(sourceName, objectName, false, false);

        System.out.println("Is compilation pass? " + compiledOK);
    }
}

