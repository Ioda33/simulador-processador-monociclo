public class CPU {

    private Lib lib;
    private Registradores regs;

    private short PC;
    private short IR;
    private boolean running;

    public CPU(Lib lib) {
        this.lib = lib;
        this.regs = new Registradores();
        this.PC = 1; // conforme ISA
        this.running = true;
    }

    public void run() {
        while (running) {
            fetch();
            decode();
        }
        System.out.println("Programa finalizado.");
        regs.print();
    }

    private void fetch() {
        IR = lib.memory_read(PC);
        PC++;
    }

    private void decode() {
        int formato = lib.extract_bits(IR, 15, 1);
        if (formato == 0) {
            int opcode = lib.extract_bits(IR, 9, 6);
            int rd = lib.extract_bits(IR, 6, 3);
            int rs = lib.extract_bits(IR, 3, 3);
            int rt = lib.extract_bits(IR, 0, 3);
            
            executeR(opcode, rd, rs, rt);
        } else {
            int opcode = lib.extract_bits(IR, 13, 2);
            int reg = lib.extract_bits(IR, 10, 3);
            short imm = lib.extract_bits(IR, 0, 10);

            executeI(opcode, reg, imm);
        }
    }

    private void executeR(int opcode, int rd, int rs, int rt) {
        switch (opcode) {
            case Opcodes.ADD:
                regs.set(rd, (short)(regs.get(rs) + regs.get(rt)));
                break;
            case Opcodes.SUB:
                regs.set(rd, (short)(regs.get(rs) - regs.get(rt)));
                break;
            case Opcodes.MUL:
                regs.set(rd, (short)(regs.get(rs) * regs.get(rt)));
                break;
            case Opcodes.DIV:
                if (regs.get(rt) != 0) {
                    regs.set(rd, (short)(regs.get(rs) / regs.get(rt)));
                } else {
                    System.out.println("Falha de execução: Divisão por zero.");
                    running = false;
                }
                break;
            case Opcodes.CMP_EQUAL:
                regs.set(rd, (short)(regs.get(rs) == regs.get(rt) ? 1 : 0));
                break;
            case Opcodes.CMP_NEQ:
                regs.set(rd, (short)(regs.get(rs) != regs.get(rt) ? 1 : 0));
                break;
            case Opcodes.LOAD:
                regs.set(rd, lib.memory_read(regs.get(rs)));
                break;
            case Opcodes.STORE:
                lib.memory_write(regs.get(rs), regs.get(rt));
                break;
            case Opcodes.SYSCALL:
                if (regs.get(0) == 0) running = false;
                break;
            default:
                System.out.println("Opcode R invalido: " + opcode);
                running = false;
        }
    }

    private void executeI(int opcode, int reg, short imm) {
        switch (opcode) {
            case Opcodes.MOV:
                regs.set(reg, imm);
                break;
            case Opcodes.JUMP:
                PC = imm;
                break;
            case Opcodes.JUMP_COND:
                if (regs.get(reg) == 1) PC = imm;
                break;
            default:
                System.out.println("Opcode I invalido: " + opcode);
                running = false;
        }
    }
}
