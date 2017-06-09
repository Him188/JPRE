package net.mamoe.jpre.network.packet;


import net.mamoe.jpre.binary.BinaryStream;
import net.mamoe.jpre.network.MPQClient;
import net.mamoe.jpre.utils.Utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/**
 * 网络包基类
 * 所有网络包都必须继承本类
 * <p>
 * <h3>包中必须包含的内容</h3>
 * <li><strong>公共字节常量 NETWORK_ID</strong></li>
 * 该常量用于 注册包({@link #registerPacket(Class)}), 获取包网络 ID({@link #getNetworkId(Class)})
 * 如果包中没有该常量, 注册时会失败并抛出异常. {@link IllegalArgumentException}
 * <p>
 * <li><strong></strong></li>
 *
 * @author Him188 @ JPRE Project
 */
@SuppressWarnings("WeakerAccess")
abstract public class Packet extends BinaryStream {
	private boolean encoded;

	public boolean isEncoded() {
		return encoded;
	}

	public boolean setEncoded(boolean encoded) {
		boolean original = this.encoded;
		this.encoded = encoded;
		return original;
	}


	/**
	 * 编码包
	 * 仅该包发送给MPQ时被调用
	 */
	abstract public void encode();

	/**
	 * 解码包
	 * 仅从MPQ接收到包后调用
	 */
	abstract public void decode();

	/**
	 * 获取包的网络 ID.
	 * 推荐包中有一个公开的常量 byte NETWORK_ID
	 * 并让本方法返回常量的值.
	 *
	 * @return 网络 ID
	 */
	abstract public byte getNetworkId();

	/**
	 * 所有包都必须有的常量
	 * 若该包是由客户端发送的包, 该包必须有一个无参数的构造器(否则构造失败并抛出异常), 该常量用于服务器接收到数据包后识别并构造实例(否则接受失败, 无事件)
	 * 若该包是发送给客户端的包, 该常量用于MPQ识别.
	 */
	public static final byte NETWORK_ID = -1;

	private MPQClient client;

	/**
	 * 获取这个包的归属客户端(MPQ).
	 *
	 * @return 客户端
	 */
	public final MPQClient getClient() {
		return client;
	}

	/**
	 * 设置这个包的归属客户端
	 * 仅在用于实现特殊功能时使用. JPRE网络接收包后就会调用本方法设置客户端, 直接使用 {@link #getClient()} 获取即可
	 *
	 * @param client 客户端
	 */
	public final void setClient(MPQClient client) {
		this.client = client;
	}

	/**
	 * 获取包的网络 ID
	 *
	 * @param packet 包
	 *
	 * @return 包的网络 ID
	 *
	 * @throws NoSuchFieldException   当该包中不存在常量 {@code NETWORK_ID} 时产生
	 * @throws IllegalAccessException 当该包中的常量 {@code NETWORK_ID} 不可访问(private, protected 修饰)时产生
	 * @see #getNetworkId(Class)  实际上会调用该方法并传参 {@code packet.getClass()}
	 */
	public static byte getNetworkId(Packet packet) throws NoSuchFieldException, IllegalAccessException {
		return getNetworkId(packet.getClass());
	}

	public static byte getNetworkId(Class<?> packet) throws NoSuchFieldException, IllegalAccessException {
		Field field = packet.getField("NETWORK_ID");
		//field.setAccessible(true); //throw an IllegalAccessException instead when the field is not accessible
		return (byte) field.get(null);
	}

	static {
		PACKETS = new Class<?>[32];
		PACKET_IDS = new byte[32];
		PACKETS_COUNT = 0;

		try {
			for (Class<?> aClass : new Class<?>[]{
					ClientPingPacket.class,
					ClientCommandResultPacket.class,
					ClientGetPluginListPacket.class,
					ClientGetPluginInformationPacket.class,
                    ClientStaticCommandResultPacket.class,
					ClientEventPacket.class,

					ServerPongPacket.class,
					ServerEventResultPacket.class,
					ServerInvalidEventPacket.class,
                    ServerCommandPacket.class,
                    ServerGetPluginInformationResultPacket.class,
                    ServerInvalidEventPacket.class,
                    ServerInvalidIdPacket.class,
                    ServerStaticCommandPacket.class
            }) {
				//noinspection unchecked
				registerPacket((Class<? extends Packet>) aClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
        }
    }

	/**
	 * 已注册的包列表. 键与 {@link #PACKET_IDS} 对应
	 */
	private static final Class<?>[] PACKETS;

	/**
	 * 已注册的包ID列表. 键与 {@link #PACKETS} 对应
	 */
	private static final byte[] PACKET_IDS;

	/**
	 * 已注册的包数量
	 */
	private static int PACKETS_COUNT;

	/**
	 * 注册一个包.
	 * 关于包中必须包含的内容, 请查看 {@link Packet} 的类说明
	 *
	 * @param clazz 包类
	 *
	 * @throws NoSuchFieldException     包类中不存在常量 {@code NETWORK_ID} 时产生
	 * @throws IllegalAccessException   包类中的常量 {@code NETWORK_ID} 不可访问时产生
	 * @throws IllegalArgumentException {@code clazz} == Packet.class 或 {@code clazz} 是抽象类时产生
	 */
	public static void registerPacket(Class<? extends Packet> clazz) throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
		if (clazz == Packet.class) {
			throw new IllegalArgumentException("class cannot be Packet.class");
		} else if (Modifier.isAbstract(clazz.getModifiers())) {
			throw new IllegalArgumentException("cannot register a abstract class");
		}

		PACKET_IDS[PACKETS_COUNT] = (byte) clazz.getField("NETWORK_ID").get(null);
		PACKETS[PACKETS_COUNT++] = clazz;
	}

	/**
	 * 获取已注册的包
	 *
	 * @return 包
	 */
	public static Class<?>[] getPackets() {
		return PACKETS;
	}

	/**
	 * 获取已注册的包 ID
	 *
	 * @return 包 ID
	 */
	public static byte[] getPacketIds() {
		return PACKET_IDS;
	}

	/**
	 * 获取已注册的包数量
	 *
	 * @return 包数量
	 */
	public static int getPacketsCount() {
		return PACKETS_COUNT;
	}

	/**
	 * 由包网络 ID (NETWORK_ID) 寻找包类并调用无参数构造器构造包
	 *
	 * @param id 包网络 ID
	 *
	 * @return 包. 失败时 null
	 */
	public static Packet matchPacket(byte id) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		for (int i = 0; i < PACKET_IDS.length; i++) {
			if (PACKET_IDS[i] == id) {
				Constructor<?> constructor = PACKETS[i].getConstructor();
				return (Packet) constructor.newInstance();
			}
		}
		return null;
	}

    /**
     * 返回当前类的类名, 当前类和父类(不包括 Object)的成员变量信息
     * 例如, GroupMessageEvent(group=xxx,qq=xxx,message=xxx,cancelled=xxx,intercepted=xxx)
     *
     * @return {@code 类名(子类成员变量=值,父类成员变量=值)}
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(getClass().getSimpleName() + "(");
        Class<?> clazz = getClass();
        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                try {
	                result.append(field.getName()).append("=").append(Utils.toString(field.get(this))).append(",");
                } catch (IllegalAccessException ignored) {
                }
            }
            clazz = clazz.getSuperclass();
        }

        result.deleteCharAt(result.length() - 1);
        result.append(")");
        return result.toString();
    }
}
