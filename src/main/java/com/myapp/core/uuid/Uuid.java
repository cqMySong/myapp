package com.myapp.core.uuid;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.SecureRandom;

public class Uuid implements Serializable, Comparable {

	private static final long serialVersionUID = 7952004632150713063L;
	public static final long MAGIC_UUID = -64424513537L;
	public static final int LENGTH = 36;
	private transient long mostSigBits;
	private transient long leastSigBits;
	private static volatile SecureRandom numberGenerator = null;

	private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static synchronized Uuid create() throws UuidException {
		return randomUUID();
	}

	public byte[] toByteArray() {
		byte[] array = new byte[16];
		toBytes(this.mostSigBits, array, 0);
		toBytes(this.leastSigBits, array, 8);
		return array;
	}

	private void toBytes(long x, byte[] array, int startPos) {
		int bytePos = 8;
		while (true) {
			bytePos--;
			if (bytePos < 0)
				break;
			array[(startPos + bytePos)] = ((byte) (int) (x & 0xFF));
			x >>>= 8;
		}
	}

	private synchronized void writeObject(ObjectOutputStream s)
			throws IOException {
		s.defaultWriteObject();
		s.writeLong(this.mostSigBits);
		s.writeLong(this.leastSigBits);
	}

	private void readObject(ObjectInputStream s) throws IOException,
			ClassNotFoundException {
		s.defaultReadObject();
		this.mostSigBits = s.readLong();
		this.leastSigBits = s.readLong();
	}

	public void write(DataOutput out) throws IOException {
		out.writeLong(this.mostSigBits);
		out.writeLong(this.leastSigBits);
	}

	public void write2(DataOutput out) throws IOException {
		out.writeLong(this.leastSigBits);
	}

	public static Uuid read(DataInput in) throws IOException {
		long most = in.readLong();
		long least = in.readLong();
		return new Uuid(most, least);
	}

	public static Uuid read2(DataInput in) throws IOException {
		long least = in.readLong();
		return new Uuid(least);
	}

	public static Uuid read(String id) throws UuidException {
		return fromString(id);
	}

	private Uuid(byte[] data) {
		long msb = 0L;
		long lsb = 0L;
		assert (data.length == 16);
		for (int i = 0; i < 8; i++)
			msb = msb << 8 | data[i] & 0xFF;
		for (int i = 8; i < 16; i++)
			lsb = lsb << 8 | data[i] & 0xFF;
		this.mostSigBits = msb;
		this.leastSigBits = lsb;
	}

	public Uuid(long leastSigBits) {
		this.mostSigBits = -64424513537L;
		this.leastSigBits = leastSigBits;
	}

	private Uuid(long mostSigBits, long leastSigBits) {
		this.mostSigBits = mostSigBits;
		this.leastSigBits = leastSigBits;
	}

	public static Uuid randomUUID() {
		SecureRandom ng = numberGenerator;
		if (ng == null) {
			numberGenerator = ng = new SecureRandom();
		}

		byte[] randomBytes = new byte[16];
		ng.nextBytes(randomBytes);
		byte[] tmp33_30 = randomBytes;
		tmp33_30[6] = ((byte) (tmp33_30[6] & 0xF));
		byte[] tmp43_40 = randomBytes;
		tmp43_40[6] = ((byte) (tmp43_40[6] | 0x40));
		byte[] tmp53_50 = randomBytes;
		tmp53_50[8] = ((byte) (tmp53_50[8] & 0x3F));
		byte[] tmp63_60 = randomBytes;
		tmp63_60[8] = ((byte) (tmp63_60[8] | 0x80));
		return new Uuid(randomBytes);
	}

	public static Uuid fromString(String name) {
		String[] components = name.split("-");
		if (components.length != 5)
			throw new IllegalArgumentException("Invalid UUID string: " + name);
		for (int i = 0; i < 5; i++) {
			components[i] = ("0x" + components[i]);
		}
		long mostSigBits = Long.decode(components[0]).longValue();
		mostSigBits <<= 16;
		mostSigBits |= Long.decode(components[1]).longValue();
		mostSigBits <<= 16;
		mostSigBits |= Long.decode(components[2]).longValue();

		long leastSigBits = Long.decode(components[3]).longValue();
		leastSigBits <<= 48;
		leastSigBits |= Long.decode(components[4]).longValue();

		return new Uuid(mostSigBits, leastSigBits);
	}

	public long getLeastSignificantBits() {
		return this.leastSigBits;
	}

	public long getMostSignificantBits() {
		return this.mostSigBits;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(36);
		buf.append(toHexString(this.mostSigBits >>> 32, 8)).append("-");
		buf.append(toHexString(this.mostSigBits >>> 16, 4)).append("-");
		buf.append(toHexString(this.mostSigBits, 4)).append("-");
		buf.append(toHexString(this.leastSigBits >>> 48, 4)).append("-");
		buf.append(toHexString(this.leastSigBits, 12));
		return buf.toString();
	}

	private static String toHexString(long x, int chars) {
		char[] buf = new char[chars];
		int charPos = chars;
		while (true) {
			charPos--;
			if (charPos < 0)
				break;
			buf[charPos] = hexDigits[((int) (x & 0xF))];
			x >>>= 4;
		}
		return new String(buf);
	}

	public int hashCode() {
		int hashCode = (int) (this.mostSigBits >> 32 ^ this.mostSigBits
				^ this.leastSigBits >> 32 ^ this.leastSigBits);

		return hashCode;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Uuid))
			return false;
		Uuid id = (Uuid) obj;
		return (this.mostSigBits == id.mostSigBits)
				&& (this.leastSigBits == id.leastSigBits);
	}

	public int compareTo(Object val) {
		if ((val instanceof Uuid)) {
			Uuid uuid = (Uuid) val;

			return this.leastSigBits > uuid.leastSigBits ? 1
					: this.leastSigBits < uuid.leastSigBits ? -1
							: this.mostSigBits > uuid.mostSigBits ? 1
									: this.mostSigBits < uuid.mostSigBits ? -1
											: 0;
		}

		return -1;
	}
	
}
