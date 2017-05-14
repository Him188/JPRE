package net.mamoe.jpre.plugin;

import net.mamoe.jpre.config.Config;
import net.mamoe.jpre.config.YamlConfig;

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
@SuppressWarnings({"ResultOfMethodCallIgnored", "WeakerAccess", "unused", "UnusedReturnValue"})
public abstract class JavaPlugin extends PluginDescription implements Plugin {
    private PluginManager owner;
    private boolean enabled;

    public PluginManager getPluginManager() {
        return owner;
    }

    @Override
    public final void setPluginManager(PluginManager owner) {
        if (this.owner != null) {
            throw new RuntimeException("There is already a plugin manager exists");
        }

        this.owner = owner;
    }

    public JavaPlugin() {
        super(null, null, null, 0, null, null, null);
    }

    /**
     * 保存资源文件
     *
     * @param resourceFile 资源文件名 (jar包 resources 目录下的文件名 (若 resources 目录搜索不到文件, 也会从 jar 包根目录搜索))
     * @param savingFile   保存到插件配置目录 ({@link #getDataFolder()}) 的文件名
     * @param forceReplace 是否强制保存 (配置已存在也进行保存)
     * @return 是否成功
     */
    public boolean saveResource(String resourceFile, String savingFile, boolean forceReplace) {
        try {
            File file = new File(getDataFolder() + File.separator + savingFile);

            if (file.exists()) {
                if (forceReplace) {
                    file.delete();
                } else return false;
            }

            InputStream resource = getPluginManager().getResourceFile(new JarFile(getFileName()), resourceFile);
            if (resource == null) {
                return false;
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
    public File getDataFolder() {
        return new File(getPluginManager().getPluginDataFolder() + File.separator + getName());
    }


	/* Config */

    private Config config;

    /**
     * 获取 Config (配置项)
     *
     * @return Config (配置项)
     */
    public Config getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }

    /**
     * 保存 Config (配置项)
     */
    public void saveConfig() {
        if (config == null) {
            return;
        }
        config.save();
    }

    /**
     * 重新读取 {@code config.yml}
     */
    public void reloadConfig() {
        File file = new File(getDataFolder() + File.separator + "config.yml");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (this.config == null) {
            this.config = new YamlConfig(file);
        }

        this.config.reload();
    }

    /**
     * 保存资源文件中的 {@code config.yml} 到 {@code dataFolder}
     */
    public void saveDefaultConfig() {
        if (this.config != null) {
            return;
        }

        saveResource("config.yml");
    }
}