import cipher.A51Cipher;
import cipher.A51KeyStream;
import file.FileHandler;
import util.BinaryUtil;

public class A51CipherTest {

    private static final byte[] KEY = new byte[]{0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 1};

    private static final byte[] FRAME_COUNTER = new byte[]{0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0};

    private A51CipherTest(String filename) {
        // Initialize the A5/1 stream cipher
        A51Cipher cipher = new A51Cipher(new A51KeyStream(KEY, FRAME_COUNTER));
        cipher.initialize();

        // Display the cipher information
        System.out.println("----[ Cipher information ]----");
        System.out.println(cipher);
        System.out.println();

        // Encrypt a string and display results in the console.
        System.out.println("----[ Test with a message ]----");

        String message = "This is an incredible test with some special characters é à ! ? ^";
        String encrypted = cipher.encrypt(BinaryUtil.stringToBinary(message));
        String decrypted = BinaryUtil.binaryToString(cipher.encrypt(encrypted));

        System.out.println("The original message is: '" + message + "'");
        System.out.println("The encrypted message is: '" + BinaryUtil.binaryToString(encrypted) + "'");
        System.out.println("The decrypted message is: '" + decrypted + "'");
        System.out.println("Is equal to the original message? " + (message.equals(decrypted)));
        System.out.println();

        // Encrypt an external file if a file name has been passed through program arguments
        if (filename != null) {
            FileHandler original = new FileHandler(filename);
            FileHandler encryptedFile = new FileHandler(original.getPathWithoutExtension() + ".encrypted" + original.getExtension());
            FileHandler decryptedFile = new FileHandler(original.getPathWithoutExtension() + ".decrypted" + original.getExtension());

            cipher.encrypt(original, encryptedFile);
            cipher.encrypt(encryptedFile, decryptedFile);

            System.out.println("----[ File encryption ]----");
            System.out.println("Original file: " + original.getPath());
            System.out.println("Encrypted file: " + encryptedFile.getPath());
            System.out.println("Decrypted file: " + decryptedFile.getPath());
        }
    }

    public static void main(String[] args) {
        new A51CipherTest(args.length > 0 ? args[0] : null);
    }

}
