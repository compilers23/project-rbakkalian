package Generator;

import Tokens.Token;

import java.io.*;
import java.util.List;

public class Generator {
    private String generatedFile = "gen_temp_main.s";
    private List<Token> tokens;
    private BufferedWriter bufferedWriter;
    private final Assembly64Generator AG = Assembly64Generator.getInstance();

    public Generator(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public String getGeneratedFile() {
        return generatedFile;
    }

    public void setGeneratedFile(String generatedFile) {
        if(!generatedFile.endsWith(".s")){
            return;
        }
        this.generatedFile = generatedFile;
    }

    public void generate() throws Exception {
        FileWriter fileReader = new FileWriter(this.generatedFile);
        this.bufferedWriter = new BufferedWriter(fileReader);
        this.generateFileStart();

        for (Token token : this.tokens) {
            switch (token.getToken()) {
                case INTEGER -> this.generatePushInt(token);
                case PRINT -> this.generatePrintTop();
                case ADD, MUL, SUB -> this.generateOperation(token);
                case NEGATE -> this.generateNegate();
                case DOT -> this.generateDropPrint();
                case SWAP -> this.generateSwap();
                default -> throw new Exception("Generate is not supported on this literal " + token.getLiteral());
            }

        }

        this.generateFileEnd();
        this.bufferedWriter.close();
    }

    private void generateFileStart() throws IOException {
        String startLabel = "_start";
        this.bufferedWriter.write(AG.section(".text"));
        this.bufferedWriter.write(AG.global(startLabel));
        this.bufferedWriter.write(AG.label(startLabel));
    }

    private void generateFileEnd() throws IOException {
        this.bufferedWriter.write(AG.movqNum("60", AG.RAX));
        this.bufferedWriter.write(AG.xorq(AG.RDI, AG.RDI));
        this.bufferedWriter.write(AG.syscall());
    }

    private void generatePrintTop() throws IOException {
        this.bufferedWriter.write(AG.popQ(AG.RSI));
        this.bufferedWriter.write(AG.movq(AG.RSI, AG.R15)); // callee save
        this.bufferedWriter.write(AG.printLn());
        this.bufferedWriter.write(AG.pushQ(AG.R15));
    }

    private void generateDropPrint() throws IOException {
        this.bufferedWriter.write(AG.popQ(AG.RSI));
        this.bufferedWriter.write(AG.printLn());
    }

    private void generatePushInt(Token token) throws IOException {
        this.bufferedWriter.write(AG.pushQNum(token.getLiteral()));
    }

    private void generateOperation(Token token) throws Exception {
        this.bufferedWriter.write(AG.popQ(AG.RAX));
        this.bufferedWriter.write(AG.popQ(AG.RBX));

        switch (token.getToken()) {
            case ADD -> this.bufferedWriter.write(AG.addQ(AG.RAX, AG.RBX));
            case SUB -> this.bufferedWriter.write(AG.subq(AG.RAX, AG.RBX));
            case MUL -> this.bufferedWriter.write(AG.mulq(AG.RAX, AG.RBX));
            default -> throw new Exception("Operation not supported");
        }

        this.bufferedWriter.write(AG.pushQ(AG.RBX));
    }

    private void generateNegate() throws IOException {
        this.bufferedWriter.write(AG.popQ(AG.RAX));
        this.bufferedWriter.write(AG.negq(AG.RAX));
        this.bufferedWriter.write(AG.pushQ(AG.RAX));
    }

    private void generateSwap() throws IOException {
        this.bufferedWriter.write(AG.popQ(AG.RAX));
        this.bufferedWriter.write(AG.popQ(AG.RBX));
        this.bufferedWriter.write(AG.pushQ(AG.RAX));
        this.bufferedWriter.write(AG.pushQ(AG.RBX));
    }
}
