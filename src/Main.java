public class Main {

    public static void main(String[] args) {
        int p = PrimeUtil.generateRandomPrimeNumber();
        int g = PrimeUtil.generateRandomNumber();

        Person bob = new Person(p, g);
        Person alice = new Person(p, g);

        bob.generatePublicKey();
        alice.generatePublicKey();

        bob.computerSymetricKey(alice.getPublicKey());
        alice.computerSymetricKey(bob.getPublicKey());

        System.out.println(bob.getSymetricKey());
        System.out.println(alice.getSymetricKey());
    }

}
