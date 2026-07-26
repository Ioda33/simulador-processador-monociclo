import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Lib {
    public static final int tmem = 65536;
    private short[] memory;

    public Lib() {
        memory = new short[tmem];
    }

    public short extract_bits (short value, int bstart, int blength)
    {
        short mask = (short)((1 << blength) - 1);
        return (short)((value >> bstart) & mask);
    }

    public void memory_write (short addr, short value)
    {
        memory[addr & 0xFFFF] = value;
    }

    public short memory_read(short addr)
    {
        return memory[addr & 0xFFFF];
    }

    void load_binary (String binary_name)
    {
        try {
            FileInputStream fileInputStream = new FileInputStream(binary_name);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);

            long tamanhoArquivo = fileInputStream.getChannel().size();

            int numShorts = (int) (tamanhoArquivo / 2);

            for (int i = 0; i < numShorts; i++) {
                int low = dataInputStream.readByte() & 0x000000FF;
                int high = dataInputStream.readByte() & 0x000000FF;
                int value = (low | (high << 8)) & 0x0000FFFF;

                this.memory_write((short)i, (short)value);
            }

            dataInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}