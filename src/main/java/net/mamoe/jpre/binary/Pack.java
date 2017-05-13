package net.mamoe.jpre.binary;

import net.mamoe.jpre.exception.binary.BinaryException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.mamoe.jpre.binary.Binary.*;

/**
 * @author Him188
 */
@SuppressWarnings("WeakerAccess")
public class Pack {
    protected byte[] data;
    protected int location;

    public Pack(Pack unpack) {
        this(unpack.getAll());
    }

    public Pack() {
        this(new byte[]{});
    }

    public Pack(byte[] data) {
        setData(data);
    }

    @Override
    public String toString() {
        return "Pack(data=(" + data.length + ")" + Arrays.toString(data) + ",location=" + location + ")";
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getAll() {
        return data.clone();
    }

    public void clear() {
        this.data = new byte[0];

    }


	/* Putter */

    public void putInt(int value) {
        putBytes(toBytes(value));
    }

    public void putLong(long value) {
        putBytes(toBytes(value));

    }

    public void putShort(short value) {
        putBytes(toBytes(value));

    }

    public void putBoolean(boolean value) {
        putBytes(toBytes(value));

    }

    public void putFloat(float value) {
        putBytes(toBytes(value));

    }

    public void putDouble(double value) {
        putBytes(toBytes(value));

    }

    public void putByte(byte value) {
        if (this.data.length <= this.location + 1) {
            byte[] newArray = new byte[this.data.length + 1];
            System.arraycopy(this.data, 0, newArray, 0, this.data.length);
            this.data = newArray;
        }
        this.data[location++] = value;
    }

    public void putString(String value) {
        if (value == null) {
            putInt(0);
            return;
        }

        try {
            putInt(value.getBytes("GBK").length);
            putBytes(value.getBytes("GBK"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putBytes(byte[] value) {
        for (byte aValue : value) {
            putByte(aValue);
        }
    }

    public void putBytes(Byte[] value) {
        for (Byte aValue : value) {
            if (aValue == null) {
                putByte((byte) 0);
                continue;
            }
            putByte(aValue);
        }
    }

    public void putRaw(Object... values) {
        for (Object value : values) {
            if (value.getClass().equals(int.class) || value.getClass().equals(Integer.class)) {
                putInt((Integer) value);
            } else if (value.getClass().equals(byte.class) || value.getClass().equals(Byte.class)) {
                putByte((Byte) value);
            } else if (value.getClass().equals(long.class) || value.getClass().equals(Long.class)) {
                putLong((Long) value);
            } else if (value.getClass().equals(String.class)) {
                putString((String) value);
            } else if (value.getClass().equals(boolean.class) || value.getClass().equals(Boolean.class)) {
                putBoolean((Boolean) value);
            } else if (value.getClass().equals(double.class) || value.getClass().equals(Double.class)) {
                putDouble((Double) value);
            } else if (value.getClass().equals(short.class) || value.getClass().equals(Short.class)) {
                putShort((Short) value);
            } else if (value.getClass().equals(float.class) || value.getClass().equals(Float.class)) {
                putFloat((Float) value);
            } else if (value.getClass().equals(byte[].class)) {
                putBytes((byte[]) value);
            } else if (value.getClass().equals(Byte[].class)) {
                putBytes((Byte[]) value);
            } else {
                throw new IllegalArgumentException("[Pack] putRaw: wrong type of values");
            }
        }

    }

    public void putRawWithType(Object... values) {
        for (Object value : values) {
            if (value.getClass().equals(int.class) || value.getClass().equals(Integer.class)) {
                putByte((byte) 0);
                putInt((Integer) value);
            } else if (value.getClass().equals(byte.class) || value.getClass().equals(Byte.class)) {
                putByte((byte) 1);
                putByte((Byte) value);
            } else if (value.getClass().equals(long.class) || value.getClass().equals(Long.class)) {
                putByte((byte) 2);
                putLong((Long) value);
            } else if (value.getClass().equals(String.class)) {
                putByte((byte) 3);
                putString((String) value);
            } else if (value.getClass().equals(boolean.class) || value.getClass().equals(Boolean.class)) {
                putByte((byte) 4);
                putBoolean((Boolean) value);
            } else if (value.getClass().equals(double.class) || value.getClass().equals(Double.class)) {
                putByte((byte) 5);
                putDouble((Double) value);
            } else if (value.getClass().equals(short.class) || value.getClass().equals(Short.class)) {
                putByte((byte) 6);
                putShort((Short) value);
            } else if (value.getClass().equals(float.class) || value.getClass().equals(Float.class)) {
                putByte((byte) 7);
                putFloat((Float) value);
            } else if (value.getClass().equals(byte[].class)) {
                putByte((byte) 8);
                putInt(((byte[]) value).length);
                putBytes((byte[]) value);
            } else if (value.getClass().equals(Byte[].class)) {
                putByte((byte) 9);
                putInt(((Byte[]) value).length);
                putBytes((Byte[]) value);
            } else {
                throw new IllegalArgumentException("[Pack] putRaw: wrong type of values");
            }
        }

    }

    public <T> void putList(List<T> list, Class<T> valueClass) {
        putString(valueClass.getName());
        putInt(list.size());
        list.forEach(this::putRawWithType);
    }


    /* Getter */

    public int getLastLength() {
        return data.length - location;
    }


    public byte[] getBytes(int length) {
        try {
            byte[] result = arrayGetCenter(this.data, location, length);
            location += length;
            return result;
        } catch (BinaryException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    private static byte[] arrayGetCenter(byte[] array, int location, int length) throws BinaryException {
        if (length <= 0) {
            return new byte[0];
        }

        byte[] result;

        try {
            result = new byte[length];
            System.arraycopy(array, location, result, 0, length);
        } catch (Exception e) {
            length = Binary.toInt(Binary.realReverse(Binary.toBytes(length)));

            try {
                result = new byte[length];
                System.arraycopy(array, location, result, 0, length);
            } catch (Exception e2) {
                throw new BinaryException("array=(" + array.length + ")" + Arrays.toString(array) + ",location=" + location + ",length=" + length, e2);
            }
        }

        return result;
    }

    public byte[] getLast() {
        return getBytes(getLastLength());
    }

    public byte getByte() {
        return getBytes(1)[0];
    }

    public int getInt() {
        return toInt(realReverse(getBytes(4)));
        //return (int) getLong();
    }

    public long getLong() {
        return toLong(getBytes(8));
    }

    public short getShort() {
        return toShort(getBytes(2));
    }

    public String getString() {
        try {
            return new String(getBytes(getInt()), "GBK");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public boolean getBoolean() {
        return toBoolean(getBytes(1));
    }

    public double getDouble() {
        return toDouble(getBytes(8));
    }

    public float getFloat() {
        return toFloat(getBytes(4));
    }


    public Object getRaw() {
        switch (getByte()) {
            case 0:
                return getInt();
            case 1:
                return getByte();
            case 2:
                return getLong();
            case 3:
                return getString();
            case 4:
                return getBoolean();
            case 5:
                return getDouble();
            case 6:
                return getShort();
            case 7:
                return getFloat();
            case 8:
            case 9:
                return getBytes(getInt());
            default:
                throw new IllegalArgumentException("wrong type of values");
        }
    }

    public List<?> getList() {
        return getList(Object.class);
    }

    @SuppressWarnings({"unchecked", "unused"})
    public <T> List<T> getList(Class<T> valueType) {
        Class<?> clazz;
        try {
            clazz = Class.forName(getString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        List<T> result = new ArrayList<>();
        for (int i = 0; i < getInt(); i++) {
            result.add((T) clazz.cast(getRaw()));
        }

        return result;
    }

}
