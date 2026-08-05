public class Registradores {
    private static final int NUM_REGISTRADORES = 8;

    private short[] r;

    public Registradores(){
        r = new short[8];
    }

    public short get(int indice) {
        if (indice < 0 || indice >= NUM_REGISTRADORES) {
            System.out.println("Erro: tentativa de acesso ao registrador " + indice + " (intervalo válido: 0 a " + (NUM_REGISTRADORES - 1) + ").");
            System.exit(1);
        }
        return r[indice];
    }

    public void set(int indice, short value) {
        if (indice < 0 || indice >= NUM_REGISTRADORES) {
            System.out.println("Erro: tentativa de acesso ao registrador " + indice + " (intervalo válido: 0 a " + (NUM_REGISTRADORES - 1) + ").");
            System.exit(1);
        }
        r[indice] = value;
    }

    public void print() {
        for(int i = 0; i < NUM_REGISTRADORES;i++) {
            System.out.println("r" + i + ": " + r[i]);
        }
    }
}
