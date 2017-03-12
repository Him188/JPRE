package net.coding.lamgc.coolq.jpre.plugin;

import net.coding.lamgc.coolq.jpre.log.logger.Logger;

/**
 * 所有的插件主类都必须实现的接口.
 * 推荐插件主类继承 {@link JavaPlugin}, 该类已经封装了一些常用方法, 便于开发.
 *
 * @author Him188 (code)
 * @author LamGC (javadoc)
 */
public interface Plugin {
	/**
	 * 获取日志记录器
	 *
	 * @return 日志记录器
	 */
	Logger getLogger();

	/**
	 * 获取插件信息
	 *
	 * @return 插件信息
	 */
	PluginDescription getDescription();

	/**
	 * 设置插件信息
	 *
	 * @param description 插件信息
	 */
	void setDescription(PluginDescription description);

	/**
	 * 在酷Q启动时调用本方法. 即只会被调用 1 次
	 * 加载插件的过程都是单线程的, 请尽量不要在本方法中进行可能阻塞线程的操作.
	 * <p>
	 * 注意: 如果本方法调用时出现错误, 将直接停用插件
	 * 警告: 本方法调用时 AuthCode 并未接收. 请不要使用任何酷 Q接口
	 */
	void onLoad();

	/**
	 * 插件被启用时调用本方法.
	 * ,如果插件在被载入时启用 则在 {@link #onLoad} 方法执行完后本方法也会被调用一次
	 * <p>
	 * 注意: 如果本方法调用时出现错误, 将直接停用插件
	 */
	void onEnable();

	/**
	 * 插件被停用时调用本方法.
	 * 如果在插件被启用的情况下运行器插件被停用, 那么本方法也会被调用.
	 * <p>
	 * 本方法产生的错误会被忽略.
	 */
	void onDisable();

	/**
	 * 开启这个插件 (设置插件状态为启用), 并调用 {@link #onEnable()}
	 */
	void enable();

	/**
	 * 停用这个插件 (设置插件状态为停用), 销毁已注册的所有有事件监听器, 并调用 {@link #onDisable()}
	 */
	void disable();

	boolean isEnabled();

	boolean isDisabled();

	/**
	 * 获取插件的名字
	 *
	 * @return 插件名字
	 */
	String getName();

	/**
	 * 获取应用 AuthCode
	 *
	 * @return AuthCode
	 */
	int getAuthCode();

	/**
	 * 应用 AuthCode 接收
	 *
	 * @param authCode authCode
	 * @return 0
	 */
	int initialize(int authCode);

	/**
	 * 获取插件 API 版本
	 *
	 * @return API version
	 */
	int getApi();

	/**
	 * 获取插件文件名
	 *
	 * @return File name
	 */
	String getFileName();

	/**
	 * 获取主类全名
	 *
	 * @return Class name
	 */
	String getMainClass();

	/**
	 * 获取配置文件路径. (结尾不带 "/")
	 *
	 * @return 配置文件路径
	 */
	String getDataFolder();
}