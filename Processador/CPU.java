public class CPU {
    private Lib lib;
    private Registradores regs;

    private short PC;
    private short IR;
    private boolean running;

    public CPU(Lib lib) {
        this.lib = lib;
        this.regs = new Registradores();
        this.PC = 1;
        this.running = true;
    }

    public void run() {
        while (running) {
            fetch();
            Instrucao instrucao = decode();
            if(instrucao.formato == 0) {
                executeR(instrucao.opcode, instrucao.rd, instrucao.rs, instrucao.rt);
            } else {
                executeI(instrucao.opcode, instrucao.reg, instrucao.imm);
            }
        }
        regs.print();
        System.out.println("Programa finalizado.");
    }

    private void fetch() {
        IR = lib.memory_read(PC);
        PC++;
    }

    private Instrucao decode() {
        Instrucao instrucao = new Instrucao();
        instrucao.formato = lib.extract_bits(IR, 15, 1);
        
        if (instrucao.formato == 0) {
            instrucao.opcode = lib.extract_bits(IR, 9, 6);
            instrucao.rd = lib.extract_bits(IR, 6, 3);
            instrucao.rs = lib.extract_bits(IR, 3, 3);
            instrucao.rt = lib.extract_bits(IR, 0, 3);
        } else {
            instrucao.opcode = lib.extract_bits(IR, 13, 2);
            instrucao.reg = lib.extract_bits(IR, 10, 3);
            instrucao.imm = lib.extract_bits(IR, 0, 10);
        }
        return instrucao;
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
                if(regs.get(0) == 0) running = false;
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
