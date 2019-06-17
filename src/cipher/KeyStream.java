package cipher;

import java.util.BitSet;

public abstract class KeyStream {

    BitSet[] keyStream;

    public abstract void initialize();

    public BitSet[] getKey() {
        return keyStream;
    }

    BitSet getKeyAt(int index) {
        return keyStream[index];
    }

}
