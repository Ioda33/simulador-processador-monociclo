public class Main {
    public static void main(String[] args) {
        Lib lib = new Lib();
        lib.load_binary("program.bin");

        CPU cpu = new CPU(lib);
        cpu.run();
    }
}