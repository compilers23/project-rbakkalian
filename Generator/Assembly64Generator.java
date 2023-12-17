package Generator;


public class Assembly64Generator {
    private static Assembly64Generator Instance = null;
    private int count = 0;
    public String RAX = "%rax";
    public String RBX = "%rbx";
    public String RCX = "%rcx";
    public String RDX = "%rdx";
    public String RSI = "%rsi";
    public String RDI = "%rdi";
    public String RBP = "%rbp";
    public String RSP = "%rsp";
    public String R8 = "%r8";
    public String R9 = "%r9";
    public String R10 = "%r10";
    public String R11 = "%r11";
    public String R12 = "%r12";
    public String R13 = "%r13";
    public String R14 = "%r14";
    public String R15 = "%r15";

    private Assembly64Generator() {
    }

    public static Assembly64Generator getInstance() {
        if (Assembly64Generator.Instance == null) {
            Assembly64Generator.Instance = new Assembly64Generator();
        }
        return Assembly64Generator.Instance;
    }

    public String getUniqueLabel() {
        this.count++;
        return "l" + this.count;
    }

    public String pushQ(String reg) {
        return "pushq " + reg + "\n";
    }

    public String pushQNum(String number) {
        return "pushq " + "$" + number + "\n";
    }

    public String popQ(String reg) {
        return "popq " + reg + "\n";
    }

    public String negq(String reg) {
        return "negq " + reg + "\n";
    }

    public String addQ(String reg, String aReg) {
        return "addq " + reg + ", " + aReg + "\n";
    }

    public String subq(String reg, String aReg) {
        return "subq " + reg + ", " + aReg + "\n";
    }

    public String xorq(String reg, String aReg) {
        return "xorq " + reg + ", " + aReg + "\n";
    }

    public String mulq(String reg, String aReg) {
        return "imulq " + reg + ", " + aReg + "\n";
    }

    public String movq(String reg, String aReg) {
        return "movq " + reg + ", " + aReg + "\n";
    }

    public String movqNum(String num, String aReg) {
        return "movq $" + num + ", " + aReg + "\n";
    }

    public String printLn() {
        return "call println\n";
    }

    public String section(String sectionType) {
        return ".section " + sectionType + "\n";
    }

    public String global(String methodName) {
        return ".global " + methodName + "\n";
    }

    public String label(String label) {
        return label + ":\n";
    }

    public String syscall() {
        return "syscall";
    }
}
