package Tokens;

import java.util.HashMap;

public class Token {
    private final Tokens token;
    private final String literal;
    private static final HashMap<String, Tokens> wordToTokenMap = initializeMap();

    private static HashMap<String, Tokens> initializeMap() {
        HashMap<String, Tokens> mp = new HashMap<>();
        mp.put("swap", Tokens.SWAP);
        mp.put("negate", Tokens.NEGATE);
        mp.put(".s", Tokens.PRINT);
        return mp;
    }

    public Token(Tokens t, String l) {
        token = t;
        literal = l;
    }

    public Tokens getToken() {
        return this.token;
    }

    public String getLiteral() {
        return this.literal;
    }

    public boolean isNumber() {
        return this.token == Tokens.INTEGER;
    }

    public boolean isDot() {
        return this.token == Tokens.DOT;
    }

    public boolean isIllegal() {
        return this.token == Tokens.ILLEGAL;
    }

    public static Tokens getPredeclaredToken(String str) {
        if (wordToTokenMap.containsKey(str)) {
            return wordToTokenMap.get(str);
        }
        return Tokens.IDENT;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token=" + this.token +
                ", literal='" + this.literal + '\'' +
                '}';
    }
}
