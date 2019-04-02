package cipher;

import file.FileHandler;

public abstract class StreamCipher {

	protected KeyStream keyStream;

	StreamCipher(KeyStream keyStream) {
		this.keyStream = keyStream;
	}

	public void initialize() {
		this.keyStream.initialize();
	}

	public KeyStream getKeyStream() {
		return keyStream;
	}

	public abstract String crypt(String message);

	public abstract void crypt(FileHandler inputFile, FileHandler outputFile);

	@Override
	public String toString() {
		return "StreamCipher{" +
				"keyStream=" + keyStream +
				'}';
	}

}
