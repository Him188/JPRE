import net.mamoe.jpre.JPREMain;
import net.mamoe.jpre.plugin.Plugin;
import net.mamoe.jpre.plugin.PluginDescription;
import net.mamoe.jpre.plugin.PluginManager;
import net.mamoe.jpre.scheduler.PluginTask;
import net.mamoe.jpre.scheduler.Scheduler;
import net.mamoe.jpre.scheduler.TaskHandler;
import net.mamoe.jpre.scheduler.TaskInfo;

import java.io.File;

/**
 * @author Him188 @ JPRE Project
 */
@TaskInfo(delay = 1000, period = 1000)
class TestTask extends PluginTask {
	public TestTask(Plugin owner) {
		super(owner);
	}

	@Override
	public void onRun() {
		System.out.println("通过注解设置属性, 每秒执行一次的 task1");
	}
}

class TestTask2 extends PluginTask {
	public TestTask2(Plugin owner) {
		super(owner, 1000);
	}

	@Override
	public void onRun() {
		System.out.println("没有注解, 通过创建task时参数设置属性的 task2");
	}
}


class TestTasks {
	@TaskInfo(delay = 3000)
	public void methodTask1() {
		System.out.println("延迟3秒的方法 task");
	}

	@TaskInfo(delay = 5000, period = 1000)
	public void methodTask2() {
		System.out.println("延迟5秒然后循环执行的方法 task2");
	}
}


class TestScheduler {
	public static void main(String... args) throws Exception { //works well
		JPREMain.main(args);
		Scheduler scheduler = JPREMain.getInstance().getScheduler();

		Plugin fuckingPlugin = new Plugin() {
			@Override
			public PluginDescription getPluginDescription() {
				return null;
			}

			@Override
			public void setPluginDescription(PluginDescription description) {

			}

			@Override
			public void onLoad() {

			}

			@Override
			public void onEnable() {

			}

			@Override
			public void onDisable() {

			}

			@Override
			public void enable() {

			}

			@Override
			public void disable() {

			}

			@Override
			public boolean isEnabled() {
				return false;
			}

			@Override
			public boolean isDisabled() {
				return false;
			}

			@Override
			public String getName() {
				return "haha";
			}

			@Override
			public int getAPIVersion() {
				return 0;
			}

			@Override
			public String getFileName() {
				return null;
			}

			@Override
			public String getMainClass() {
				return null;
			}

			@Override
			public File getDataFolder() {
				return null;
			}

			@Override
			public void setPluginManager(PluginManager pluginManager) {

			}
		};

		System.out.println(scheduler.addTask(new TestTask(fuckingPlugin)).getWorker().getWorkerId());

		System.out.println(scheduler.addTask(new TestTask2(fuckingPlugin)).getWorker().getWorkerId());

		for (TaskHandler handler : scheduler.addMethodTasks(fuckingPlugin, new TestTasks())) {
			System.out.println(handler.getWorker().getWorkerId());
		}

		System.out.println(scheduler.addTask(fuckingPlugin, () -> System.out.println("hahahaha")).getWorker().getWorkerId());
	}
}
