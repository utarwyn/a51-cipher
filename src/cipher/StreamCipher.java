package cipher;

import file.FileHandler;

public abstract class StreamCipher {

    KeyStream keyStream;

    StreamCipher(KeyStream keyStream) {
        this.keyStream = keyStream;
    }

    public void initialize() {
        this.keyStream.initialize();
    }

    public KeyStream getKeyStream() {
        return keyStream;
    }

    public abstract String encrypt(String message);

    public abstract void encrypt(FileHandler inputFile, FileHandler outputFile);

    @Override
    public String toString() {
        return "StreamCipher{" +
                "keyStream=" + keyStream +
                '}';
    }

}
