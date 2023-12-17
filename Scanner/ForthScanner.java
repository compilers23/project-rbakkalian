package Scanner;

import Tokens.Token;
import Tokens.Tokens;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ForthScanner {
    private final String fileName;
    private final List<Token> tokens = new ArrayList<>();
    private String ct;
    private Scanner scanner;

    public ForthScanner(String f) {
        this.fileName = f;
    }

    public void tokenize() throws FileNotFoundException {
        FileReader fileReader = new FileReader(this.fileName);

        // Creating a Scanner object with custom delimiters
        // Cause the language is pretty simple
        Scanner scanner = new Scanner(fileReader);

        // Setting delimiters as spaces and newline
        scanner.useDelimiter("[\\s\\n]+");

        this.scanner = scanner;
        this.startScanning();
    }

    private void startScanning() {
        while (this.scanner.hasNext()) {
            this.ct = this.scanner.next();

            if (this.ct.length() == 1) {
                Tokens t = this.scanSingularTokens();
                if (t != null) {
                    this.tokens.add(new Token(t, this.ct));
                    continue;
                }
            }


            // scanning number
            try {
                Integer.parseInt(this.ct);
                this.tokens.add(new Token(Tokens.INTEGER, this.ct));
                continue;
            } catch (NumberFormatException ignored) {
            }

            Tokens token = Token.getPredeclaredToken(this.ct);
            if (token != Tokens.IDENT) {
                this.tokens.add(new Token(token, this.ct));
                continue;
            }
            this.tokens.add(new Token(Tokens.ILLEGAL, this.ct));
        }
    }

    private Tokens scanSingularTokens() {
        switch (this.ct) {
            case "+":
                return Tokens.ADD;
            case "-":
                return Tokens.SUB;
            case "*":
                return Tokens.MUL;
            case ".":
                return Tokens.DOT;
            default:
                if (Character.isDigit(this.ct.charAt(0))) {
                    return Tokens.INTEGER;
                }
        }
        return null;
    }


    public List<Token> getTokens() {
        return tokens;
    }
}
