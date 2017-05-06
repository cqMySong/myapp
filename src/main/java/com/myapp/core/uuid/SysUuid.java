package com.myapp.core.uuid;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;

public final class SysUuid implements Serializable {
	private static final long serialVersionUID = -65533L;
	private transient Uuid uuid;
	private transient SysObjectType type;
	private transient byte encoding;
	private static final byte MIXED_ENCODING = 0;
	private static final byte BASE64_ENCODING = 1;
	private static final byte BASE64_SHORT_ENCODING = 2;
	private static final int COMPATIBLE_MIXED_ENCODING_LENGTH = 40;
	private static final int MIXED_ENCODING_LENGTH = 44;
	private static final int BASE64_ENCODING_LENGTH = 28;
	private static final int BASE64_SHORT_ENCODING_LENGTH = 16;

	public static synchronized SysUuid create(SysObjectType type) {
		assert (type != null);
		return new SysUuid(Uuid.create(), type, (byte) 1);
	}

	public static SysUuid create(String typeString) {
		if (typeString == null)
			throw new UuidException("Invalid typeString.");
		assert ((typeString.length() == 8) || (typeString.length() == 4));

		return create(SysObjectType.create(typeString));
	}

	private SysUuid(Uuid uuid, SysObjectType type, byte encoding) {
		if (type == null) {
			throw new UuidException("Invalid BOSObjectType.");
		}

		if (type.toInteger() == 0)
			this.encoding = 0;
		else {
			this.encoding = encoding;
		}
		this.uuid = uuid;
		this.type = type;
	}

	public SysObjectType getType() {
		return this.type;
	}

	public static SysUuid read(String id) throws UuidException {
		if ((id == null) || (id.length() == 0)) {
			throw new IllegalArgumentException("id is null or length is 0. '"
					+ id + "'");
		}

		if ((id.length() == 44) || (id.length() == 40)) {
			Uuid uuid = Uuid.read(id.substring(0, 36));
			String type = id.substring(36).intern();
			return new SysUuid(uuid, SysObjectType.create(type), (byte) 0);
		}
		if (id.length() == 28) {
			if (id.charAt(id.length() - 1) != '=') {
				throw new IllegalArgumentException(
						"the argument 'id' is invalid. '" + id + "'");
			}

			byte[] array = Base64Encoder.base64ToByteArray(id);
			DataInput in = new DataInputStream(new ByteArrayInputStream(array));
			try {
				Uuid uuid = Uuid.read(in);
				SysObjectType type = SysObjectType.read(in);
				return new SysUuid(uuid, type, (byte) 1);
			} catch (IOException ioe) {
				throw new UuidException(ioe);
			}
		}
		if (id.length() == 16) {
			byte[] array = Base64Encoder.base64ToByteArray(id);
			DataInput in = new DataInputStream(new ByteArrayInputStream(array));
			try {
				Uuid uuid = Uuid.read2(in);
				SysObjectType type = SysObjectType.read(in);
				return new SysUuid(uuid, type, (byte) 2);
			} catch (IOException ioe) {
				throw new UuidException(ioe);
			}

		}

		throw new IllegalArgumentException("The argument 'id' is invalid. '"
				+ id + "'");
	}

	public String toString() {
		if (this.encoding == 1) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(28);
			DataOutput out = new DataOutputStream(baos);
			try {
				this.uuid.write(out);
				this.type.write(out);
			} catch (IOException ioe) {
				throw new UuidException(ioe);
			}
			return Base64Encoder.byteArrayToBase64(baos.toByteArray());
		}
		if (this.encoding == 2) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(16);
			DataOutput out = new DataOutputStream(baos);
			try {
				this.uuid.write2(out);
				this.type.write(out);
			} catch (IOException ioe) {
				throw new UuidException(ioe);
			}
			return Base64Encoder.byteArrayToBase64(baos.toByteArray());
		}
		if (this.encoding == 0) {
			return this.uuid.toString() + this.type.toString();
		}
		throw new AssertionError("unhandled encoding.");
	}

	public static boolean isValidLength(String bosuuid) {
		if ((bosuuid == null) || (bosuuid.length() == 0)) {
			return false;
		}
		if ((bosuuid.length() != 44) && (bosuuid.length() != 40)
				&& (bosuuid.length() != 28) && (bosuuid.length() != 16)) {
			return false;
		}
		return true;
	}

	public static boolean isValidLength(String bosuuid, boolean trim) {
		if ((bosuuid == null) || (bosuuid.length() == 0)) {
			return false;
		}
		String id = null;
		if (trim)
			id = bosuuid.trim();
		else {
			id = bosuuid;
		}
		return isValidLength(id);
	}

	public static boolean isValid(String bosuuid, boolean trim) {
		if (!isValidLength(bosuuid, trim))
			return false;
		try {
			read(bosuuid.trim());
		} catch (UuidException e) {
			return false;
		} catch (IllegalArgumentException e1) {
			return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static SysObjectType getBOSObjectType(String bosuuid,
			boolean validate) {
		if ((validate) && (!isValid(bosuuid, true))) {
			return null;
		}

		return read(bosuuid.trim()).getType();
	}

	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof SysUuid))) {
			return (this.uuid.equals(((SysUuid) obj).uuid))
					&& (this.type.equals(((SysUuid) obj).type));
		}

		return false;
	}

	public int hashCode() {
		return this.uuid.hashCode();
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		this.uuid.write(s);
		this.type.write(s);
		s.writeByte(this.encoding);
	}

	private void readObject(ObjectInputStream s) throws IOException,
			ClassNotFoundException {
		this.uuid = Uuid.read(s);
		this.type = SysObjectType.read(s);
		this.encoding = s.readByte();
	}

	public void write(DataOutput out) throws IOException {
		this.uuid.write(out);
		this.type.write(out);
		out.writeByte(this.encoding);
	}

	public static SysUuid read(DataInput in) throws IOException {
		return new SysUuid(Uuid.read(in), SysObjectType.read(in), in.readByte());
	}
}
