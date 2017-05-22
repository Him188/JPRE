package net.mamoe.jpre;

import net.mamoe.jpre.exception.PluginException;
import net.mamoe.jpre.network.MPQClient;
import net.mamoe.jpre.network.packet.ServerStaticCommandPacket;
import net.mamoe.jpre.plugin.Plugin;
import net.mamoe.jpre.plugin.PluginManager;
import net.mamoe.jpre.scheduler.Scheduler;
import net.mamoe.jpre.utils.CommandResults;

import java.io.File;

/**
 * 框架. JPRE 允许多个MPQ框架连接, 每次连接都会创建本类, 断开连接后 {@link #shutdown(boolean)}
 *
 * @author Him188 @ JPRE Project */
@SuppressWarnings("WeakerAccess")
public final class Frame {
    @Override
    public String toString() {
        return "Frame(client=" + client.toString() + ")";
    }

    private final JPREMain jpre;
    private MPQClient client;

    public void setClient(MPQClient client) {
        if (this.client != null) {
            return;
        }
        this.client = client;
    }

    public MPQClient getClient() {
        return client;
    }

    public JPREMain getJPREMain() {
        return jpre;
    }

    public Frame(JPREMain jpre) {
        this.jpre = jpre;
        init(JPREMain.getDataFolder());// TODO: 2017/5/11 Frame 区分数据目录

        scheduler = new Scheduler(this);
        pluginManager = new PluginManager(this);

        pluginManager.loadPlugins();
    }

    private PluginManager pluginManager;
    private Scheduler scheduler;

    public PluginManager getPluginManager() {
        return pluginManager;
    }


    private String dataFolder;

    public String getDataFolder() {
        return dataFolder;
    }

    private boolean shutdown;

    public void connect() {

    }

    public void shutdown(boolean shutdown) {
        this.shutdown = shutdown;
        scheduler.shutdown();
        pluginManager.disablePlugins();
    }

    public boolean isShutdown() {
        return shutdown;
    }

    /**
     * 初始化插件环境
     *
     * @param dataFolder 配置目录
     */
    @SuppressWarnings({"SameParameterValue", "ResultOfMethodCallIgnored"})
    public void init(String dataFolder) {
        System.out.println("System data folder is: " + dataFolder);
        this.dataFolder = dataFolder;
        new File(dataFolder + "/plugins/").mkdir();
    }

    public String[] getLoadedPluginList() {
        String[] names = new String[pluginManager.getPlugins().size()];
        int i = 0;
        for (Plugin plugin : pluginManager.getPlugins()) {
            names[i++] = plugin.getName();
        }
        return names;
    }

    // TODO: 2017/5/22 确认是否需要删除
    //加载一个插件:
    public boolean loadPluginDescription(String fileName) {
        try {
            return pluginManager.loadPluginDescription(fileName) != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean loadPlugin(String fileName) throws PluginException {
        return pluginManager.loadPlugin(fileName);
    }

    public boolean enablePlugin(String name) {
        try {
            Plugin plugin = pluginManager.getPlugin(name);
            if (plugin == null) {
                return false;
            }
            plugin.enable();
            System.out.println("[Plugin] " + plugin.getName() + " enabled!");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean disablePlugin(String name) {
        try {
            Plugin plugin = pluginManager.getPlugin(name);
            if (plugin == null) {
                return false;
            }
            plugin.disable();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPluginEnabled(String name) {
        Plugin plugin = pluginManager.getPlugin(name);
        return plugin != null && plugin.isEnabled();
    }


    public Scheduler getScheduler() {
        return scheduler;
    }



	/* COMMAND SENDER */

    private CommandResults results = new CommandResults();

    public byte runCommand(CommandId id, Object... args) throws InterruptedException {
        byte i = results.requestId();
        this.getClient().sendPacket(new ServerStaticCommandPacket(i, id, args));
        return i;
    }

    public void runVoidCommand(CommandId id, Object... args) throws InterruptedException {
        this.getClient().sendPacket(new ServerStaticCommandPacket((byte) 0, id, args));
    }

    public void setResult(byte id, Object result) {
        this.results.setResult(id, result);
    }
    
	/* MPQ API */

    // TODO: 2017/5/17  package-private api,

    /**
     * 将群名片加入高速缓存当中
     *
     * @param group 群号
     * @param QQ    QQ
     * @param card  群名片
     */
    public void cacheNameCard(long group, long QQ, String card) {
        try {
            runVoidCommand(CommandId.CACHE_NAME_CARD, 0L, group, QQ, card);
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * 根据图片 GUID 取得图片下载连接 失败返回空
     *
     * @param guid GUID
     * @return 图片下载链接
     */
    public String guidGetPicLink(String guid) {
        try {
            byte id = runCommand(CommandId.GUID_GET_PIC_LINK, 0L, guid);
            return results.stringResult(id);
        } catch (InterruptedException e) {
            return null;
        }
    }

    /**
     * 发送封包
     *
     * @param data 封包数据
     * @return unknown
     */
    public int send(String data) {
        try {
            byte id = runCommand(CommandId.SEND, 0L, data);
            return results.intResult(id);
        } catch (InterruptedException e) {
            return 0;
        }
    }

    /**
     * 在框架记录页输出一行信息
     *
     * @param content 输出的内容
     * @return unknown
     */
    public int output(String content) {
        try {
            byte id = runCommand(CommandId.OUTPUT, 0L, content);
            return results.intResult(id);
        } catch (InterruptedException e) {
            return 0;
        }
    }

    /**
     * 取得本插件(JPRE)启用状态
     *
     * @return 本插件(JPRE)启用状态
     */
    public boolean isEnabled() {
        try {
            byte id = runCommand(CommandId.IS_ENABLE, 0L);
            return results.booleanResult(id);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 登录一个现存的 QQ
     *
     * @param QQ QQ
     * @return 是否成功
     */
    public boolean login(long QQ) {
        try {
            byte id = runCommand(CommandId.LOGIN, 0L, QQ);
            return results.booleanResult(id);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 让指定 QQ 下线
     *
     * @param QQ QQ
     */
    public void logout(long QQ) {
        try {
            runVoidCommand(CommandId.LOGOUT, 0L, QQ);
        } catch (InterruptedException ignored) {

        }
    }

    /**
     * Tean 加密算法
     *
     * @param content 内容
     * @param key     key
     * @return 加密结果
     */
    public String teaEncode(String content, String key) {
        try {
            byte id = runCommand(CommandId.TEA_ENCODE, 0L, content, key);
            return results.stringResult(id);
        } catch (InterruptedException e) {
            return null;
        }
    }

    /**
     * Tean 解密算法
     *
     * @param content 内容
     * @param key     key
     * @return 解密结果
     */
    public String teaDecode(String content, String key) {
        try {
            byte id = runCommand(CommandId.TEA_DECODE, 0L, content, key);
            return results.stringResult(id);
        } catch (InterruptedException e) {
            return null;
        }
    }

    /**
     * 取用户名
     *
     * @param QQ QQ
     * @return 用户名
     */
    public String getNick(long QQ) {
        try {
            byte id = runCommand(CommandId.GET_NICK, 0L, QQ);
            return results.stringResult(id);
        } catch (InterruptedException e) {
            return null;
        }
    }

    /**
     * 获取 QQ 等级
     *
     * @param QQ QQ
     * @return QQ 等级
     */
    public String getQQLevel(long QQ) {
        try {
            byte id = runCommand(CommandId.GET_QQ_LEVEL, 0L, QQ);
            return results.stringResult(id);
        } catch (InterruptedException e) {
            return null;
        }
    }

    /**
     * 获取 GID
     *
     * @param groupNumber 群号
     * @return GID
     */
    public String getGId(long groupNumber) {
        try {
            byte id = runCommand(CommandId.GN_GET_GID, 0L, groupNumber);
            return results.stringResult(id);
        } catch (InterruptedException e) {
            return null;
        }
    }

    /**
     * 获取群号
     *
     * @param gid GID
     * @return 群号
     */
    public long getGroupNumber(String gid) {
        try {
            byte id = runCommand(CommandId.GID_GET_GN, 0L, gid);
            return results.longResult(id);
        } catch (InterruptedException e) {
            return 0;
        }
    }

    /**
     * 取框架版本号(发布时间戳)
     *
     * @return 框架版本号(发布时间戳)
     */
    public int getVersion() {
        try {
            byte id = runCommand(CommandId.GET_VERSION, 0L);
            return results.intResult(id);
        } catch (InterruptedException e) {
            return 0;
        }
    }

    /**
     * 取框架版本名
     *
     * @return 框架版本名
     */
    public String getVersionName() {
        try {
            byte id = runCommand(CommandId.GET_VERSION_NAME, 0L);
            return results.stringResult(id);
        } catch (InterruptedException e) {
            return null;
        }
    }

    /**
     * 取当前框架内部时间戳, 周期性与服务器时间同步
     *
     * @return 当前框架内部时间戳, 周期性与服务器时间同步
     */
    public int getTimeStamp() {
        try {
            byte id = runCommand(CommandId.GET_TIME_STAMP, 0L);
            return results.intResult(id);
        } catch (InterruptedException e) {
            return 0;
        }
    }

    /**
     * 取得框架输出列表内所有信息
     * 包可能过长, 导致接受慢
     *
     * @return 框架输出列表内所有信息
     */
    //LONG PACKET WARNING!!!
    public String getLog() {
        try {
            byte id = runCommand(CommandId.GET_LOG, 0L);
            return results.stringResult(id);
        } catch (InterruptedException e) {
            return null;
        }
    }

    /**
     * 取得框架内随机一个在线且可以使用的QQ
     *
     * @return 框架内随机一个在线且可以使用的QQ
     */
    public String getRandomOnlineQQ() {
        try {
            byte id = runCommand(CommandId.GET_RANDOM_ONLINE_QQ, 0L);
            return results.stringResult(id);
        } catch (InterruptedException e) {
            return null;
        }
    }

    /**
     * 往帐号列表添加一个 QQ. 当该 QQ 已存在时则覆盖密码
     *
     * @param QQ        QQ
     * @param password  密码
     * @param autoLogin 运行框架时是否自动登录该Q.若添加后需要登录该 QQ 则需要通过 Api_Login 操作
     * @return 是否成功
     */
    public boolean addQQ(long QQ, String password, boolean autoLogin) {
        try {
            byte id = runCommand(CommandId.ADD_QQ, 0L, QQ, password, autoLogin);
            return results.booleanResult(id);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 设置在线状态和附加信息
     *
     * @param QQ              QQ
     * @param status          状态.
     * @param additionMessage 附加消息. 最大 255 字节
     * @return 是否成功
     */
    public boolean setOnlineStatus(long QQ, OnlineStatus status, String additionMessage) {
        try {
            byte id = runCommand(CommandId.SET_OL_STATUS, 0L, QQ, status.getId(), additionMessage);
            return results.booleanResult(id);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 取得机器码
     *
     * @return 机器码
     */
    public String getMachineCode() {
        try {
            byte id = runCommand(CommandId.GET_MC, 0L);
            return results.stringResult(id);
        } catch (InterruptedException e) {
            return null;
        }
    }

    /**
     * 取得框架所在目录
     *
     * @return 框架所在目录
     */
    public String getRunPath() {
        try {
            byte id = runCommand(CommandId.GET_RUN_PATH, 0L);
            return results.stringResult(id);
        } catch (InterruptedException e) {
            return null;
        }
    }

    /**
     * 取得当前框架内在线可用的QQ列表
     *
     * @return 当前框架内在线可用的QQ列表
     */
    public String getOnlineQQList() {
        try {
            byte id = runCommand(CommandId.GET_ONLINE_QQ_LIST, 0L);
            return results.stringResult(id);
        } catch (InterruptedException e) {
            return null;
        }
    }

    /**
     * 取得框架内所有QQ列表。包括未登录以及登录失败的QQ
     *
     * @return 框架内所有QQ列表
     */
    public String getFrameQQList() {
        try {
            byte id = runCommand(CommandId.GET_QQ_LIST, 0L);
            return results.stringResult(id);
        } catch (InterruptedException e) {
            return null;
        }
    }

    /**
     * 取得框架内设置的信息发送前缀
     *
     * @return 框架内设置的信息发送前缀
     */
    public String getPrefix() {
        try {
            byte id = runCommand(CommandId.GET_PREFIX, 0L);
            return results.stringResult(id);
        } catch (InterruptedException e) {
            return null;
        }
    }
}
