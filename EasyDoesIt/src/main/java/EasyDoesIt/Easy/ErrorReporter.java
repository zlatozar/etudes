package EasyDoesIt.Easy;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ErrorReporter {

    int numErrors;

    ErrorReporter() {
        numErrors = 0;
    }

    public void reportError(String message, String tokenName, SourcePosition pos) {

        System.out.print("ERROR: ");

        for (int p = 0; p < message.length(); p++) {

            if (message.charAt(p) == '%') {
                System.out.print(tokenName);

            } else {
                System.out.print(message.charAt(p));
            }
        }

        System.out.println(" " + pos.start + ".." + pos.finish);
        numErrors++;
    }

    public void reportRestriction(String message) {
        System.out.println("RESTRICTION: " + message);
    }
}
