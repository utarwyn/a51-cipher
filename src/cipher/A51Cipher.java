package cipher;

import file.FileHandler;
import file.StreamMode;
import util.BinaryUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class A51Cipher extends StreamCipher {

	public A51Cipher(A51KeyStream keyStream) {
		super(keyStream);
	}

	@Override
	public String crypt(String message) {
		StringBuilder crypt = new StringBuilder();

		char[] fluxBitChar = message.toCharArray();
		for (int i = 0; i < fluxBitChar.length; i++) {
			crypt.append(Integer.parseInt(String.valueOf(fluxBitChar[i]))
							^ (this.keyStream.getKeyAt(i % 228).get(0) ? 1 : 0));
		}
		return crypt.toString();
	}

	@Override
	public void crypt(FileHandler inputFile, FileHandler outputFile) {
		try {
			inputFile.open(StreamMode.INPUT);
			outputFile.open(StreamMode.OUTPUT);

			String buffer = new String(inputFile.readAllBytes());
			buffer = BinaryUtil.stringToBinary(buffer);

			String encrypted = BinaryUtil.binaryToString(this.crypt(buffer));
			byte[] bytes = encrypted.getBytes();

			outputFile.writeBytes(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputFile.close();
				outputFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
