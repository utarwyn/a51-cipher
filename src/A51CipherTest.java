import cipher.A51Cipher;
import cipher.A51KeyStream;
import file.FileHandler;
import util.BinaryUtil;

public class A51CipherTest {

	private static final byte[] KEY = new byte[]{0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 1};

	private static final byte[] FRAME_COUNTER = new byte[]{0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0};

	private A51CipherTest(String filename) {
		FileHandler original = new FileHandler(filename);
		FileHandler encrypted = new FileHandler(filename + ".encrypted");
		FileHandler decrypted = new FileHandler(filename + ".decrypted");
		A51Cipher cipher = new A51Cipher(new A51KeyStream(KEY, FRAME_COUNTER));

    	cipher.initialize();
    	cipher.crypt(original, encrypted);
    	cipher.crypt(encrypted, decrypted);

		System.out.println("----[ TEST AVEC UN MESSAGE ]----");
		String message = "Ceci est un test éà!!!??";
		System.out.println("message = " + message);
        String binary = BinaryUtil.stringToBinary(message);
        String crypt = cipher.crypt(binary);
        System.out.println(BinaryUtil.binaryToString(crypt));
        System.out.println(BinaryUtil.binaryToString(cipher.crypt(crypt)));

		System.out.println();
		System.out.println(cipher);
    }

    public static void main(String[] args) {
		validate(args);
        new A51CipherTest(args[0]);
    }

	private static void validate(String[] args) {
		if (args.length == 0) {
			System.err.println("Pas assez d'arguments!");
			System.err.println("Format: [chemin vers le fichier]");
			System.exit(-1);
		}
	}

}
