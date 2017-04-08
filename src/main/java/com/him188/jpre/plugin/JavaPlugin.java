package com.him188.jpre.plugin;

import com.him188.jpre.JPREMain;
import com.him188.jpre.PluginManager;
import com.him188.jpre.command.PluginMPQCaller;
import com.him188.jpre.log.logger.Logger;
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
@SuppressWarnings("ResultOfMethodCallIgnored")
public class JavaPlugin extends PluginDescription implements Plugin {
	private final Logger logger;
	private boolean enabled;
	private int authCode;
	private PluginMPQCaller cq;

	public PluginMPQCaller getCq() {
		return cq;
	}

	public JavaPlugin() {
		super(null, null, null, 0, null, null, null, null);

		logger = new PluginLogger();
		new File(getDataFolder() + File.pathSeparator).mkdir();
		cq = new PluginMPQCaller(this);
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
			InputStream resource = PluginManager.getResourceFile(new JarFile(getFileName()), resourceFile);
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
	public PluginDescription getPluginDescription() {
		return this;
	}

	@Override
	public void setPluginDescription(PluginDescription d) {
		setValues(d);
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
	public void initialize(int authCode) {
		this.authCode = authCode;
	}

	@Override
	public int getAuthCode() {
		return authCode;
	}

	@Override
	public File getDataFolder() {
		return new File(JPREMain.getDataFolder() + "/plugins/" + getName());
	}
}