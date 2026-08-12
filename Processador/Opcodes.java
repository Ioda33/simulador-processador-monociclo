public class Opcodes {

    // Formato R
    public static final int ADD = 0;
    public static final int SUB = 1;
    public static final int MUL = 2;
    public static final int DIV  = 3;
    public static final int CMP_EQUAL = 4;
    public static final int CMP_NEQ  = 5;
    public static final int LOAD = 15;
    public static final int STORE = 16;
    public static final int SYSCALL = 63;

    // Formato I
    public static final int JUMP = 0;
    public static final int JUMP_COND = 1;
    public static final int MOV = 3;
}
