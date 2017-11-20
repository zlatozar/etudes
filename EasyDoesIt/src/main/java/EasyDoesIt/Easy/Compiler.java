package EasyDoesIt.Easy;

import EasyDoesIt.Easy.AbstractSyntaxTrees.Program;
import EasyDoesIt.Easy.ContextualAnalizer.Checker;
import EasyDoesIt.Easy.SyntacticAnalizer.Parser;
import EasyDoesIt.Easy.SyntacticAnalizer.Scanner;
import EasyDoesIt.Easy.SyntacticAnalizer.SourceFile;

/**
 * The main driver class for the Easy compiler.
 */
public class Compiler {

    // Easy Abstract Machine
    private static String objectName = "obj.eam";

    private static Scanner scanner;
    private static Parser parser;
    private static Checker checker;

    private static ErrorReporter reporter;

    // The AST representing the source program
    private static Program theAST;

    public static boolean compileProgram(String sourceName, boolean showingAST, boolean showingTable) {

        System.out.println("\n********** Easy Compiler (Java Version 0.1) **********\n");

        System.out.print("Syntactic Analysis ... ");
        SourceFile source = new SourceFile(sourceName);

        if (source == null) {
            System.out.println("Can't access source file " + sourceName);
            System.exit(1);
        }

        scanner = new Scanner(source);
        reporter = new ErrorReporter();
        parser = new Parser(scanner, reporter);
        checker = new Checker(reporter);

        theAST = parser.parseProgram();                     // 1st pass

        if (reporter.numErrors == 0) {
            System.out.println("pass");

            System.out.print("Contextual Analysis ... ");

            checker.check(theAST);                          // 2nd pass

            if (reporter.numErrors == 0) {                  // 3rd pass
                System.out.println("pass");

                System.out.println("Code Generation ...(not implemented yet)");
                // ...
            }
        }

        boolean successful = (reporter.numErrors == 0);

        if (!successful) {
            System.out.println("Compilation was unsuccessful.");
        }

        return successful;
    }
}

