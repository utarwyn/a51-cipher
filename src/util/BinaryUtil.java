package util;

import java.util.BitSet;

public class BinaryUtil {

	private BinaryUtil() {

	}

	public static BitSet[] bytesToBitSets(byte[] values) {
		BitSet[] bitSet = new BitSet[values.length];
		for (int i = 0; i < values.length; i++) {
			bitSet[i] = getBitSet(values[i] == 1);
		}
		return bitSet;
	}

	public static byte[] bitSetsToBytes(BitSet[] bitSets) {
		byte[] bytes = new byte[bitSets.length];
		for (int i = 0; i < bitSets.length; i++) {
			bytes[i] = (byte) (bitSets[i].get(0) ? 1 : 0);
		}
		return bytes;
	}

	public static BitSet getBitSet(boolean value) {
		BitSet bitSet = new BitSet(1);
		bitSet.set(0, value);
		return bitSet;
	}

	public static String bitSetArrayToString(BitSet[] arr) {
		StringBuilder sb = new StringBuilder();
		for (BitSet bitSet : arr) {
			sb.append(bitSet.get(0) ? 1 : 0);
		}
		return sb.toString();
	}

	public static String stringToBinary(String message) {
		StringBuilder result = new StringBuilder();
		for (char c : message.toCharArray()) {
			result.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
		}
		return result.toString();
	}

	public static String binaryToString(String binary) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < binary.length(); i += 8) {
			String temp = binary.substring(i, i + 8);
			int number = Integer.parseInt(temp, 2);
			result.append((char) number);
		}
		return result.toString();
	}

}
