public class Registradores {
    private short[] r;

    public Registradores(){
        r = new short[8];
    }

    public short get(int indice) {
        return r[indice & 0x7];
    }

    public void set(int indice, short value) {
        r[indice & 0x7] = value;
    }
}