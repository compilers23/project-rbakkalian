import Compiler.Compiler;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Arguments number should be one");
            return;
        }

        Compiler compiler = new Compiler();
        compiler.compile(args[0]);
    }
}
