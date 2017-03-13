package com.him188.jpre.plugin;

import com.him188.jpre.JPREMain;
import com.him188.jpre.log.logger.Logger;
import com.him188.jpre.CoolQCaller;
import com.him188.jpre.PluginManager;
import com.him188.jpre.log.logger.PluginLogger;

import java.io.*;
import java.util.jar.JarFile;

/**
 * 插件基础类.
 * 推荐插件主类继承此类, 原因请查看: {@link Plugin}
 * <p>
 * Javadoc 请查看: {@link Plugin}
 *
 * @author Him188
 */
abstract public class JavaPlugin extends CoolQCaller implements Plugin {
	private final Logger logger;
	public PluginDescription description;
	private boolean enabled;
	private int authCode;

	public JavaPlugin() {
		this(null);
	}

	public JavaPlugin(PluginDescription description) {
		this.description = description;
		logger = new PluginLogger();
		new File(getDataFolder() + "/").mkdir();
	}

	/**
	 * 保存资源文件
	 *
	 * @param resourceFile 资源文件名 (jar包 resources 目录下的文件名)
	 * @param savingFile   保存到插件配置目录({@link #getDataFolder()})的文件名
	 * @param forceReplace 是否强制保存 (配置已存在也进行保存)
	 *
	 * @return 是否成功
	 */
	public boolean saveResource(String resourceFile, String savingFile, boolean forceReplace) {
		try {
			InputStream resource = PluginManager.getResourceFile(new JarFile(description.getFileName()), resourceFile);
			if (resource == null) {
				return false;
			}

			File file = new File(getDataFolder() + "/" + savingFile);

			if (file.exists()) {
				if (forceReplace) {
					file.delete();
				} else return false;
			}
			file.createNewFile();

			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
			InputStreamReader in = new InputStreamReader(resource);

			int buf = in.read();
			while (buf != -1) {
				out.write(buf);
				buf = in.read();
			}

			in.close();
			out.flush();
			out.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public boolean saveResource(String resourceFile, String savingFile) {
		return saveResource(resourceFile, savingFile, false);
	}

	public boolean saveResource(String resourceFile) {
		return saveResource(resourceFile, false);
	}

	@SuppressWarnings("SameParameterValue")
	public boolean saveResource(String resourceFile, boolean forceReplace) {
		return saveResource(resourceFile, resourceFile, forceReplace);
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public PluginDescription getDescription() {
		return description;
	}

	@Override
	public void setDescription(PluginDescription d) {
		this.description = d;
	}

	@Override
	public int getApi() {
		return description.getAPIVersion();
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
	public boolean isEnabled() {
		return enabled;
	}

	private void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean isDisabled() {
		return !isEnabled();
	}

	@Override
	public void enable() {
		setEnabled(true);
		onEnable();
	}

	@Override
	public void disable() {
		setEnabled(false);
		onDisable();
	}

	@Override
	public int initialize(int authCode) {
		this.authCode = authCode;
		return 0;
	}

	@Override
	public int getAuthCode() {
		return authCode;
	}

	@Override
	public String getName() {
		return description.getName();
	}

	@Override
	public String getFileName() {
		return description.getFileName();
	}

	@Override
	public String getMainClass() {
		return description.getMainClass();
	}

	@Override
	public String getDataFolder() {
		return JPREMain.getDataFolder() + "/plugins/" + getName();
	}
}