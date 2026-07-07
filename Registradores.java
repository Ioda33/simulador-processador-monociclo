public class Registradores {
    public class Registradores {
    private short[] r;
    public Registradores(){
        r = new short[8];
    }

    public short get(int indice) {
        return r[indice & 0x7];
        //precisamos fazer uma limitação, mostrar que dá erro, tanto aqui quanto na parte da memória
    }

    public void set(int indice, short value) {
        r[indice & 0x7] = value;
    }
}
}
