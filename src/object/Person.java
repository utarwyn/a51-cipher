package object;

import util.PrimeUtil;

public class Person {

    private int privateKey;
    private int publicKey;
    private int symmetricKey;
    private int p;
    private int g;

    public Person(int p, int g) {
        this.p = p;
        this.g = g;
        this.privateKey = PrimeUtil.generateRandomNumber();
    }

    public int getSymetricKey() {
        return this.symmetricKey;
    }

    public int getPublicKey() {
        return publicKey;
    }

    public void generatePublicKey() {
        this.publicKey = (int) Math.pow(this.g, this.privateKey) % this.p;
    }

    public void computerSymetricKey(int otherPersonPublicKey) {
        this.symmetricKey = (int) Math.pow(otherPersonPublicKey, this.privateKey) % this.p;
    }

}
