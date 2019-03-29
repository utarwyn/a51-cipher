import java.util.Arrays;

public class CryptoA51 {

    private byte[] key;
    private byte[] frameNumber;
    private byte[] xReg;
    private byte[] yReg;
    private byte[] zReg;

    public CryptoA51() {
        this.key = new byte[64];
        this.frameNumber = new byte[22];
        this.xReg = new byte[19];
        this.yReg = new byte[22];
        this.zReg = new byte[23];
        this.initRegisters();
        this.mixKeyIntoRegisters();
        this.mixFrameNumberIntoRegisters();
    }

    public void initRegisters() {
        for (int i = 0; i < this.key.length; i++) {
            if (i < this.xReg.length) {
                this.xReg[i] = this.key[i];
            } else if (i < this.xReg.length + this.yReg.length) {
                this.yReg[i - this.xReg.length] = this.key[i];
            } else {
                this.zReg[i - this.xReg.length - this.yReg.length] = this.key[i];
            }
        }
    }

    public void mixKeyIntoRegisters() {
        for (int i = 0; i < this.key.length) {
            this.xReg[0] ^= this.key[i];
            this.yReg[0] ^= this.key[i];
            this.zReg[0] ^= this.key[i];
            this.shiftRegister(xReg);
            this.shiftRegister(yReg);
            this.shiftRegister(zReg);
        }
    }

    public void mixFrameNumberIntoRegisters() {
        for (int i = 0; i < this.frameNumber.length; i++) {
            this.xReg[0] ^= this.frameNumber[i];
            this.yReg[0] ^= this.frameNumber[i];
            this.zReg[0] ^= this.frameNumber[i];
            this.shiftRegister(xReg);
            this.shiftRegister(yReg);
            this.shiftRegister(zReg);
        }
    }

    public void encrypt() {
        for (int i = 0; i < 100; i++) {
            byte vote = (byte) (this.xReg[8] ^ this.yReg[10] ^ this.zReg[10]);
            if (this.xReg[8] == vote) {

            }
        }
    }

    private void shiftRegister(byte[] reg){
        byte last = reg[reg.length - 1];
        for(int i = reg.length - 1; i > 0; i--)
            reg[i] = reg[i - 1];
        reg[0] = last;
    }

    public void fill(byte[] reg, byte toFill) {
        for (int i = 0; i < reg.length; i++) {
            reg[i] = toFill;
        }
    }

    public void printReg() {
        System.out.println(Arrays.toString(this.xReg));
        System.out.println(Arrays.toString(this.yReg));
        System.out.println(Arrays.toString(this.zReg));
    }

    public void main(String[] args) {
        new CryptoA51();
    }

}
