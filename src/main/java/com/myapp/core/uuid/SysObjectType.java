package com.myapp.core.uuid;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class SysObjectType implements Serializable {
	private static final long serialVersionUID = -65534L;
	private transient String strType;
	private transient int intType;
	private static Map<String, SysObjectType> types = new HashMap();

	private SysObjectType(int type) {
		if (type == 0) {
			throw new IllegalArgumentException("zero BOSObjectType.");
		}
		this.intType = type;
		this.strType = null;
	}

	public SysObjectType(String type) {
		if (type == null) {
			throw new IllegalArgumentException("null BOSObjectType.");
		}
		type = type.trim();
		if (type.length() == 4) {
			this.intType = 0;
			this.strType = type.intern();
		} else {
			assert (type.length() == 8);
			long parsedLong = 0L;
			try {
				parsedLong = Long.parseLong(type, 16);

				if (parsedLong > 2147483647L) {
					parsedLong = -2147483648L + (parsedLong - 2147483647L - 1L);
				}
			} catch (NumberFormatException nfe) {
			}

			this.intType = ((int) parsedLong);
			if (parsedLong == 0L) {
				this.strType = type;
			} else
				this.strType = null;
		}
	}

	public static SysObjectType create(String type) {
		SysObjectType val = (SysObjectType) types.get(type);
		if (val != null) {
			return val;
		}
		synchronized (types) {
			val = (SysObjectType) types.get(type);
			if (val == null) {
				val = new SysObjectType(type);
				types.put(type, val);
			}
		}
		return val;
	}

	public String toString() {
		if (this.strType != null) {
			assert (this.intType == 0);
			return this.strType;
		}

		assert (this.intType != 0);
		return UuidUtils.toHexString(this.intType, 8).toUpperCase();
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if ((obj != null) && ((obj instanceof SysObjectType))) {
			if (this.strType != null) {
				return this.strType.equalsIgnoreCase(((SysObjectType) obj).strType);
			}
			return this.intType == ((SysObjectType) obj).intType;
		}
		return false;
	}

	public int hashCode() {
		if (this.strType != null) {
			return this.strType.hashCode();
		}
		return this.intType;
	}

	public void write(DataOutput out) throws IOException {
		out.writeInt(this.intType);
		if (this.intType == 0) {
			out.writeUTF(this.strType);
		}
	}

	public static SysObjectType read(DataInput in) throws IOException {
		int intType = in.readInt();
		if (intType != 0) {
			return create(new SysObjectType(intType).toString());
		}

		String strType = in.readUTF();
		return create(strType);
	}


	public int toInteger() {
		return this.intType;
	}
}
