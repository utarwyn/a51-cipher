import java.nio.charset.StandardCharsets;
import java.util.BitSet;

public class CryptoA51 {

    private BitSet[] key;
    private BitSet[] frameCounter;
    private BitSet[] xReg;
    private BitSet[] yReg;
    private BitSet[] zReg;
    private BitSet[] keyStream;

    private static final byte[] XREG_TAPS = {18, 17, 16, 13};
    private static final byte[] YREG_TAPS = {21, 20};
    private static final byte[] ZREG_TAPS = {22, 21, 20, 7};

    public CryptoA51() {
        this.frameCounter = new BitSet[22];
        this.key = this.bytesToBitSets(new byte[]{0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 1});
        this.frameCounter = this.bytesToBitSets(new byte[]{0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0});
        this.init();
        this.generateKeyStream();
        String crypt = this.cryptFlux(this.stringToBinary("Ceci est un test"));
        System.out.println(this.stringToBinary("Ceci est un test"));
        System.out.println((crypt));
        System.out.println((this.cryptFlux(crypt)));
    }

    public String stringToBinary(String message) {
        String result = "";
        for (byte b : message.getBytes(StandardCharsets.UTF_8)) {
            result += Integer.toBinaryString(b);
        }
        return result;
    }

    public String binaryToString(String binary) {
        String result = "";
        for (int i = 0; i < binary.length() - 8; i += 8) {
            String temp = binary.substring(i, i + 8);
            int number = Integer.parseInt(temp, 2);
            result += (char) number;
        }
        return result;
    }

    public void init() {
        this.initRegisters();
        this.mixKeyIntoRegisters();
        this.mixFrameCounterIntoRegisters();
        this.clock100Times();
    }

    public BitSet[] bytesToBitSets(byte[] values) {
        BitSet[] bitSet = new BitSet[values.length];
        for (int i = 0; i < values.length; i++) {
            bitSet[i] = this.getBitSet(values[i] == 1);
        }
        return bitSet;
    }

    public byte[] bitSetsToBytes(BitSet[] bitSets) {
        byte[] bytes = new byte[bitSets.length];
        for (int i = 0; i < bitSets.length; i++) {
            bytes[i] = (byte) (bitSets[i].get(0) ? 1 : 0);
        }
        return bytes;
    }

    public void initRegisters() {
        this.xReg = new BitSet[19];
        this.yReg = new BitSet[22];
        this.zReg = new BitSet[23];
        this.initRegister(this.xReg);
        this.initRegister(this.yReg);
        this.initRegister(this.zReg);
    }

    public void initRegister(BitSet[] reg) {
        for (int i = 0; i < reg.length; i++) {
            reg[i] = this.getBitSet(false);
        }
    }

    public BitSet getBitSet(boolean value) {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, value);
        return bitSet;
    }

    private void shiftRegister(BitSet[] reg, BitSet toInsert) {
        for (int i = reg.length - 1; i > 0; i--) {
            reg[i] = reg[i - 1];
        }
        reg[0] = toInsert;
    }

    public void mixKeyIntoRegisters() {
        for (int i = 0; i < this.key.length; i++) {
            this.shiftRegister(this.xReg, this.xor(this.getXorValueWithTaps(this.xReg, XREG_TAPS), this.key[i]));
            this.shiftRegister(this.yReg, this.xor(this.getXorValueWithTaps(this.yReg, YREG_TAPS), this.key[i]));
            this.shiftRegister(this.zReg, this.xor(this.getXorValueWithTaps(this.zReg, ZREG_TAPS), this.key[i]));
        }
    }

    public BitSet xor(BitSet bitSet1, BitSet bitSet2) {
        BitSet bitSet = (BitSet) bitSet1.clone();
        bitSet.xor(bitSet2);
        return bitSet;
    }

    public void mixFrameCounterIntoRegisters() {
        for (int i = 0; i < this.frameCounter.length; i++) {
            this.shiftRegister(this.xReg, this.xor(this.getXorValueWithTaps(this.xReg, XREG_TAPS), this.frameCounter[i]));
            this.shiftRegister(this.yReg, this.xor(this.getXorValueWithTaps(this.yReg, YREG_TAPS), this.frameCounter[i]));
            this.shiftRegister(this.zReg, this.xor(this.getXorValueWithTaps(this.zReg, ZREG_TAPS), this.frameCounter[i]));
        }
    }

    public BitSet getXorValueWithTaps(BitSet[] reg, byte[] taps) {
        BitSet result = (BitSet) reg[0].clone();
        for (byte tap : taps) {
            result.xor(reg[tap]);
        }
        return result;
    }

    public void clock100Times() {
        for (int i = 0; i < 100; i++) {
            this.clock1Time();
        }
    }

    public void clock1Time() {
        boolean majority = this.getMajority();
        if (this.xReg[8].get(0) == majority) {
            this.shiftRegister(this.xReg, this.getXorValueWithTaps(this.xReg, XREG_TAPS));
        }
        if (this.yReg[10].get(0) == majority) {
            this.shiftRegister(this.yReg, this.getXorValueWithTaps(this.yReg, YREG_TAPS));
        }
        if (this.zReg[10].get(0) == majority) {
            this.shiftRegister(this.zReg, this.getXorValueWithTaps(this.zReg, ZREG_TAPS));
        }
    }

    public boolean getMajority() {
        BitSet result = (BitSet) this.xReg[8].clone();
        result.xor(this.yReg[10]);
        result.xor(this.zReg[10]);
        return result.get(0);
    }

    public void generateKeyStream() {
        this.keyStream = new BitSet[228];
        for (int i = 0; i < this.keyStream.length; i++) {
            BitSet bitSet = (BitSet) this.xReg[this.xReg.length - 1].clone();
            bitSet.xor(this.yReg[this.yReg.length - 1]);
            bitSet.xor(this.zReg[this.zReg.length - 1]);
            this.keyStream[i] = bitSet;

            this.clock1Time();
        }
    }

    public String crypt(String message) {
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        byte[] encryptMessage = new byte[bytes.length];
        int keyStreamIndex = 0;
        for (int i = 0; i < bytes.length; i++) {
            byte value = (byte) (bytes[i] ^ (this.keyStream[keyStreamIndex].get(0) ? (byte) 1 : (byte) 0));
            encryptMessage[i] = value;
            keyStreamIndex++;
            if (keyStreamIndex == this.keyStream.length) {
                keyStreamIndex = 0;
            }
        }
        return new String(encryptMessage);
    }

    public String cryptFlux(String flux) {
        String crypt = "";

        char[] fluxBitChar = flux.toCharArray();
        for (int i = 0; i < fluxBitChar.length; i++) {
            crypt += Integer.parseInt(String.valueOf(fluxBitChar[i])) ^ (this.keyStream[i % 228].get(0) ? 1 : 0);
        }
        return crypt;
    }

    public void printReg(BitSet[] reg) {
        for (BitSet bitSet : reg) {
            System.out.print(bitSet.get(0) ? 1 : 0);
        }
        System.out.println();
    }

    public void debug() {
        System.out.print("KEY : ");
        this.printReg(this.key);
        this.printReg(xReg);
        this.printReg(yReg);
        this.printReg(zReg);
        System.out.println("KEYSTREAM : ");
        this.printReg(this.keyStream);
    }

    public static void main(String[] args) {
        new CryptoA51();
    }

}
