package Compiler;

import Generator.Generator;
import Parser.*;
import Scanner.ForthScanner;
import Tokens.Token;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Compiler {

  public void compile(String fileName) throws Exception {
    ForthScanner scanner = new ForthScanner(fileName);
    scanner.tokenize();

    List<Token> tokens = scanner.getTokens();

    Parser parser = new Parser(tokens);
    parser.parse();

    Generator generator = new Generator(tokens);
    generator.generate();

    String mainObjectName = "main.o";
    String printObjectName = "print.o";

    List<String> asMain = Arrays.asList("as", "-o", mainObjectName, generator.getGeneratedFile());

    List<String> asPrint = Arrays.asList("as", "-o", printObjectName, "print.s");
    List<String> linker =
        Arrays.asList(
            "ld",
            "-o",
            fileName,
            mainObjectName,
            printObjectName,
            "-lc",
            "-dynamic-linker",
            "/lib64/ld-linux-x86-64.so.2");

    // Run macro on target
    ProcessBuilder mainAsProcess = new ProcessBuilder(asMain);
    ProcessBuilder printAsProcess = new ProcessBuilder(asPrint);
    ProcessBuilder linkerProcess = new ProcessBuilder(linker);
//    mainAsProcess.directory(new File(System.getProperty("user.dir")));
    mainAsProcess.redirectErrorStream(true);

//    printAsProcess.directory(new File(System.getProperty("user.dir")));
    printAsProcess.redirectErrorStream(true);

//    linkerProcess.directory(new File(System.getProperty("user.dir")));
    linkerProcess.redirectErrorStream(true);

    try {
      // Start the process
      Process processMain = mainAsProcess.start();
      Process processPrint= printAsProcess.start();
      Process processLinker= linkerProcess.start();

      int exitCodeMain = processMain.waitFor();
      int exitCodePrint = processPrint.waitFor();
      int exitCodeLinker = processLinker.waitFor();
      System.out.println("Process exited with code Main " + exitCodeMain);
      System.out.println("Process exited with code Print " + exitCodePrint);
      System.out.println("Process exited with code Linker " + exitCodeLinker);
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
