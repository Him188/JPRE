package net.coding.lamgc.coolq.jpre.command;

import net.coding.lamgc.coolq.jpre.JPREMain;
import net.coding.lamgc.coolq.jpre.exception.CommandException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Him188
 */
public final class Command {
	private final CommandId commandId;
	private final String code;
	private final boolean needResult;

	public Command(CommandId commandId, boolean needResult, Object... args) {
		this.commandId = commandId;
		this.needResult = needResult;
		this.code = writeData(args);
	}

	private static String getUTF8String(String xml) {
		try {
			return URLEncoder.encode(new String(xml.getBytes("UTF-8")), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return xml;
		}
	}

	public boolean isNeedResult() {
		return needResult;
	}

	public CommandId getCommandId() {
		return commandId;
	}

	public String getCode() {
		return code;
	}

	private boolean classEquals(Class<?> a, Class<?> b) {
		if (a == b) {
			return true;
		} else if (a == int.class && b == Integer.class) {
			return true;
		} else if (b == int.class && a == Integer.class) {
			return true;
		} else if (a == long.class && b == Long.class) {
			return true;
		} else if (b == long.class && a == Long.class) {
			return true;
		} else if (a == boolean.class && b == Boolean.class) {
			return true;
		} else if (b == boolean.class && a == Boolean.class) {
			return true;
		} //may be faster


		/*
		try {
			Field method = a.getField("TYPE");
			if (b == method.get(null)) {
				return true;
			}

			method = b.getField("TYPE");
			if (a == method.get(null)) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}*/
		return false;
	}

	private String writeData(Object... args) {
		if (getCommandId() == null) {
			return null;
		}

		String data = String.valueOf(getCommandId().getId()) + "||" + (needResult ? 1 : 0);
		Class[] classes = getCommandId().getArgs();
		for (int i = 0; i < classes.length; i++) {
			if (classEquals(args[i].getClass(), classes[i])) {
				data += "||" + getUTF8String(args[i].toString());
			} else {
				JPREMain.getLogger().exception(new CommandException("指令参数类型错误. ID 为 " + getCommandId().getId() + " 的指令的第 " + i + " 个参数应为 " + classes[i] + ", 提供的为 " + args[i].getClass()));
			}
		}

		return data;
	}

	@Override
	public String toString() {
		return getCode();
	}
}
