import net.coding.lamgc.coolq.jpre.JPREMain;

public class TestLoad {
	private static boolean running = true;

	private static String PLUGIN_NAME = "E:\\MEGA\\IDEAPojects\\CQ-JPRE\\out\\test\\CQ-JPRE\\plugins\\BlackList.jar";

	public static void main(String[] args) {
		JPREMain.init(9, "E:\\MEGA\\IDEAPojects\\CQ-JPRE\\out\\test\\CQ-JPRE");

		new Thread(() -> {
			while (running) {
				System.out.println(JPREMain.getLog());
			}
		}).start();

		new Thread(() -> {
			while (running) {
				System.out.println(JPREMain.getCommand());
				JPREMain.setCommandResult("1");
			}
		}).start();

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		JPREMain.setAuthCode(97);

		JPREMain.getLogger().info("test", "this is a message");
		JPREMain.getCaller().sendLike(1040400290L, 10);

		//System.out.println(JPREMain.loadPluginDescription(PLUGIN_NAME));
		//System.out.println(PluginManager.matchPluginDescription(PLUGIN_NAME).getMainClass());

		//JPREMain.loadPlugin(PLUGIN_NAME);
		//JPREMain.enablePlugin(PLUGIN_NAME);

		//GroupMessageEvent event = new GroupMessageEvent(GroupMessageEvent.TYPE_GROUP, 0, 0, 1040400290L, "", "!添加黑名单", 0);
		//System.out.println(JPREMain.callEvent(event));

		running = false;
		System.exit(0); //succeed
	}
}
