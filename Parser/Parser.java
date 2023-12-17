package Parser;

import Tokens.Token;

import java.util.List;

public class Parser {
    private List<Token> tokens;

    public Parser(List<Token> t) {
        this.tokens = t;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void parse() throws ParserError {
        if (this.tokens == null) {
            throw new ParserError("No Tokens are available");
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Token t : this.tokens) {
            if (t.isIllegal()) {
                stringBuilder.append(t.getLiteral());
                stringBuilder.append(" ");
            }
        }

        if (stringBuilder.length() > 0) {
            throw new ParserError(stringBuilder.toString());
        }
    }
}
