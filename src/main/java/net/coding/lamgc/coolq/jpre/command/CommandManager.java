package net.coding.lamgc.coolq.jpre.command;

import net.coding.lamgc.coolq.jpre.JPREMain;

import java.util.List;
import java.util.Vector;

/**
 * @author Him188
 */
public class CommandManager {
	protected static final List<Command> commands = new Vector<>();

	public static void addCommand(Command command) {
		System.out.println("Command sent: " + command.toString());
		commands.add(command);
	}

	@SuppressWarnings("unchecked")
	public static <T> T runCommand(Command command, Class<T> resultType) {
		String string = runCommand(command).toString();
		if (resultType == int.class || resultType == Integer.class) {
			return (T) new Integer(string);
		} else if (resultType == long.class || resultType == Long.class) {
			return (T) new Long(string);
		} else if (resultType == String.class) {
			return (T) string;
		} else if (resultType == boolean.class || resultType == Boolean.class) {
			if (string.equals("1")) {
				return (T) Boolean.TRUE;
			} else if (string.equals("0")) {
				return (T) Boolean.FALSE;
			}

			return (T) Boolean.valueOf(string);
		} else if (resultType == byte.class || resultType == Byte.class) {
			try {
				return (T) new Byte(string.getBytes()[0]);
			} catch (ArrayIndexOutOfBoundsException e) {
				return (T) new Byte((byte) 0);
			}
		} else if (resultType == short.class || resultType == Short.class) {
			return (T) new Short(string);
		}

		return (T) string;
	}

	/**
	 * 执行指令, 并获取返回值
	 */
	public static Object runCommand(Command command) {
		addCommand(command);
		synchronized (CommandManager.class) {//使正在等待返回值时, 指令不传达
			return JPREMain.getCommandResult();
		}
	}

	/**
	 * 会阻塞线程直到有指令可取出
	 */
	@SuppressWarnings("StatementWithEmptyBody")
	public static Command getCommand() {
		System.out.println("Getting command");
		while (commands.size() == 0) ;
		return commands.remove(0);
	}
}
