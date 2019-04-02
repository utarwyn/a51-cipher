import java.util.Random;

public class PrimeUtil {

    public static final int[] primeNumbers = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199};

    private static final int MAX = 1000;

    public static int generateRandomNumber() {
        return generateRandomNumber(MAX);
    }

    public static int generateRandomNumber(int range) {
        return new Random().nextInt(range);
    }

    public static int generateRandomPrimeNumber() {
        return primeNumbers[generateRandomNumber(primeNumbers.length)];
    }

}
