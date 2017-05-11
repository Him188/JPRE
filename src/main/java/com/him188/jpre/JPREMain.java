package com.him188.jpre;

import com.him188.jpre.network.Network;
import com.him188.jpre.plugin.Plugin;
import com.him188.jpre.plugin.PluginManager;
import com.him188.jpre.scheduler.ServerScheduler;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

/**
 * JPRE 主类
 * <p>
 * 如果你想开发插件. 请查看 {@link Plugin}
 *
 * @author Him188
 * @see PluginManager 真正的插件管理器
 */
@SuppressWarnings("WeakerAccess")
public final class JPREMain {
    /* Instance */
    private static JPREMain instance;

    public static JPREMain getInstance() {
        return instance;
    }

    private JPREMain() {
    }

    @Override
    public String toString() {
        return "JPREMain";
    }

    /* Server Scheduler */
    private static ServerScheduler serverScheduler;

    /**
     * Server Scheduler 主要用于 {@link RobotQQ#addResult(Object)} 的超时处理
     * 插件请使用 {@link Frame#getScheduler()}
     *
     * @return Server sScheduler
     */
    static ServerScheduler getServerScheduler() {
        return serverScheduler;
    }

    /* Constants */
    public static final int DEFAULT_PORT = 420;
    public static final String VERSION_TYPE = "EAP";
    public static final String VERSION = "1.1.0";


    static {
        serverScheduler = new ServerScheduler();
    }

    public static void main(String[] args) throws Exception {
        printAbout();

        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption("h", "help", false, "Print the usage information");
        options.addOption("p", "port", true, "Set the server port number");

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

        System.out.println("Starting server...");
        int finalPort = port;
        new Thread(() -> {
            try {
                Network.start(instance = new JPREMain(), finalPort);
            } catch (InterruptedException e) {
                System.out.println("Starting server failed. Could not open port " + finalPort);
                System.exit(0);
            }
        }).start();
        System.out.println("JPRE server is listening port " + port);

        System.out.println("Server startup done!");
    }

    /**
     * 输出关于信息
     */
    private static void printAbout() {
        System.out.println("MPQ JavaPluginRuntimeEnvironment");
        System.out.println("Version: " + VERSION_TYPE + ", v" + VERSION);
        System.out.println("Author: Him188 & LamGC & Other fiends");
        System.out.println("GitHub: https://github.com/Him188/JPRE\n");
    }
}
