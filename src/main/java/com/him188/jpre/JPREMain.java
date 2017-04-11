package com.him188.jpre;

import com.him188.jpre.plugin.Plugin;
import org.apache.commons.cli.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static com.him188.jpre.Utils.md5Encode;

/**
 * Java 插件加载器主类
 * <p>
 * 如果你想开发插件. 请查看 {@link Plugin}
 *
 * @author Him188
 * @see PluginManager 真正的插件管理器
 */
public final class JPREMain {
	@SuppressWarnings({"OctalInteger", "WeakerAccess"})
	public static final int DEFAULT_PORT = 420;

	public static void main(String[] args) throws ParseException, UnsupportedEncodingException, NoSuchAlgorithmException {
		printAbout();

		CommandLineParser parser = new DefaultParser();
		Options options = new Options();
		options.addOption("h", "help", false, "Print the usage information");
		options.addOption("p", "port", true, "Set the server port number");
		options.addOption("pwd", "password", true, "Set the password that client must verify after connecting to server");

		CommandLine commandLine = parser.parse(options, args);

		if (commandLine.hasOption("h")) {
			System.out.println("Help Message");
			System.exit(0);
		}

		int port = DEFAULT_PORT;
		if (commandLine.hasOption("p")) {
			try {
				port = Integer.parseInt(commandLine.getOptionValue("p"));
			} catch (NumberFormatException e) {
				System.out.println("The port you set is invalid. It must be a number in 1~65535");
				System.exit(0);
			}
		}
		System.out.println("Server port: " + port);

		Frame frame = new Frame();

		if (commandLine.hasOption("pwd")) {
			frame.password = md5Encode(commandLine.getOptionValue("pwd").trim());
		}
		frame.password = frame.password == null ? "" : frame.password;

		frame.startServer(port);
		System.out.println("Server startup done!");
	}

	@SuppressWarnings("WeakerAccess")
	public static final String VERSION_TYPE = "Pre";
	@SuppressWarnings("WeakerAccess")
	public static final String VERSION = "1.1.0";

	private static void printAbout() {
		System.out.println("MPQ JavaPluginRuntimeEnvironment");
		System.out.println("Version: " + VERSION_TYPE + ", v" + VERSION);
		System.out.println("Author: Him188 & LamGC");
		System.out.println("GitHub: https://github.com/Him188/CQ-JPRE\n");
	}
}
