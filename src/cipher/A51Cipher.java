package cipher;

import file.FileHandler;
import util.BinaryUtil;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class A51Cipher extends StreamCipher {

	public A51Cipher(A51KeyStream keyStream) {
		super(keyStream);
	}

	@Override
	public String encrypt(String message) {
		StringBuilder encrypted = new StringBuilder();
		char[] fluxBitChar = message.toCharArray();

		for (int i = 0; i < fluxBitChar.length; i++) {
			encrypted.append(Integer.parseInt(String.valueOf(fluxBitChar[i]))
							^ (this.keyStream.getKeyAt(i % 228).get(0) ? 1 : 0));
		}

		return encrypted.toString();
	}

	@Override
	public void encrypt(FileHandler inputFile, FileHandler outputFile) {
		try {
			if (inputFile.exists()) {
				outputFile.openOutputStream();

				// Read the input file and transform it to binary data
				String buffer = new String(inputFile.readAllBytes());
				buffer = BinaryUtil.stringToBinary(buffer);

				// Encrypt the data and write it in the output file
				String encrypted = BinaryUtil.binaryToString(this.encrypt(buffer));
				outputFile.writeBytes(encrypted.getBytes());
			} else {
				throw new NoSuchFileException(inputFile.getOriginalFilename());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputFile.closeOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
