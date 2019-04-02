package cipher;

import util.BinaryUtil;

import java.util.BitSet;

public class A51KeyStream extends KeyStream {

	private BitSet[] key;
	private BitSet[] frameCounter;
	private BitSet[] xReg;
	private BitSet[] yReg;
	private BitSet[] zReg;

	private static final byte[] XREG_TAPS = {18, 17, 16, 13};
	private static final byte[] YREG_TAPS = {21, 20};
	private static final byte[] ZREG_TAPS = {22, 21, 20, 7};

	public A51KeyStream(byte[] key, byte[] frameCounter) {
		this.frameCounter = new BitSet[22];
		this.key = BinaryUtil.bytesToBitSets(key);
		this.frameCounter = BinaryUtil.bytesToBitSets(frameCounter);
	}

	@Override
	public void initialize() {
		this.initRegisters();
		this.mixKeyIntoRegisters();
		this.mixFrameCounterIntoRegisters();
		this.clock100Times();
		this.generate();
	}

	private void initRegisters() {
		this.xReg = new BitSet[19];
		this.yReg = new BitSet[22];
		this.zReg = new BitSet[23];
		this.initRegister(this.xReg);
		this.initRegister(this.yReg);
		this.initRegister(this.zReg);
	}

	private void initRegister(BitSet[] reg) {
		for (int i = 0; i < reg.length; i++) {
			reg[i] = BinaryUtil.getBitSet(false);
		}
	}

	private void shiftRegister(BitSet[] reg, BitSet toInsert) {
		for (int i = reg.length - 1; i > 0; i--) {
			reg[i] = reg[i - 1];
		}
		reg[0] = toInsert;
	}

	private void mixKeyIntoRegisters() {
		for (BitSet aKey : this.key) {
			this.shiftRegister(this.xReg, this.xor(this.getXorValueWithTaps(this.xReg, XREG_TAPS), aKey));
			this.shiftRegister(this.yReg, this.xor(this.getXorValueWithTaps(this.yReg, YREG_TAPS), aKey));
			this.shiftRegister(this.zReg, this.xor(this.getXorValueWithTaps(this.zReg, ZREG_TAPS), aKey));
		}
	}

	private BitSet xor(BitSet bitSet1, BitSet bitSet2) {
		BitSet bitSet = (BitSet) bitSet1.clone();
		bitSet.xor(bitSet2);
		return bitSet;
	}

	private void mixFrameCounterIntoRegisters() {
		for (BitSet aFrameCounter : this.frameCounter) {
			this.shiftRegister(this.xReg, this.xor(this.getXorValueWithTaps(this.xReg, XREG_TAPS), aFrameCounter));
			this.shiftRegister(this.yReg, this.xor(this.getXorValueWithTaps(this.yReg, YREG_TAPS), aFrameCounter));
			this.shiftRegister(this.zReg, this.xor(this.getXorValueWithTaps(this.zReg, ZREG_TAPS), aFrameCounter));
		}
	}

	private BitSet getXorValueWithTaps(BitSet[] reg, byte[] taps) {
		BitSet result = (BitSet) reg[0].clone();
		for (byte tap : taps) {
			result.xor(reg[tap]);
		}
		return result;
	}

	private void clock100Times() {
		for (int i = 0; i < 100; i++) {
			this.clock1Time();
		}
	}

	private void clock1Time() {
		boolean majority = this.getMajority();
		if (this.xReg[8].get(0) == majority) {
			this.shiftRegister(this.xReg, this.getXorValueWithTaps(this.xReg, XREG_TAPS));
		}
		if (this.yReg[10].get(0) == majority) {
			this.shiftRegister(this.yReg, this.getXorValueWithTaps(this.yReg, YREG_TAPS));
		}
		if (this.zReg[10].get(0) == majority) {
			this.shiftRegister(this.zReg, this.getXorValueWithTaps(this.zReg, ZREG_TAPS));
		}
	}

	private boolean getMajority() {
		BitSet result = (BitSet) this.xReg[8].clone();
		result.xor(this.yReg[10]);
		result.xor(this.zReg[10]);
		return result.get(0);
	}

	private void generate() {
		this.keyStream = new BitSet[228];
		for (int i = 0; i < this.keyStream.length; i++) {
			BitSet bitSet = (BitSet) this.xReg[this.xReg.length - 1].clone();
			bitSet.xor(this.yReg[this.yReg.length - 1]);
			bitSet.xor(this.zReg[this.zReg.length - 1]);
			this.keyStream[i] = bitSet;

			this.clock1Time();
		}
	}

	@Override
	public String toString() {
		return "cipher.A51KeyStream{" +
				"key=" + BinaryUtil.bitSetArrayToString(key) +
				", frameCounter=" + BinaryUtil.bitSetArrayToString(frameCounter) +
				", xReg=" + BinaryUtil.bitSetArrayToString(xReg) +
				", yReg=" + BinaryUtil.bitSetArrayToString(yReg) +
				", zReg=" + BinaryUtil.bitSetArrayToString(zReg) +
				", keyStream=" + BinaryUtil.bitSetArrayToString(keyStream) +
				'}';
	}

}
