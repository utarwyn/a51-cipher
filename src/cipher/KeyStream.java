package cipher;

import java.util.BitSet;

public abstract class KeyStream {

	protected BitSet[] keyStream;

	public abstract void initialize();

	public BitSet[] getKey() {
		return keyStream;
	}

	public BitSet getKeyAt(int index) {
		return keyStream[index];
	}

}
