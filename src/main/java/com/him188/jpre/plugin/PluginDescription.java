package com.him188.jpre.plugin;

import java.util.jar.JarFile;

/**
 * 由 Gson 读取
 *
 * @author Him188
 * @see PluginManager#getDescription(JarFile)
 */
public class PluginDescription {
	public String name;
	public String main;
	public String version;
	public int api;
	public String author;
	public String description;
	public String config; //may be null

	private String fileName;

	public PluginDescription(String name, String main, String version, int api, String author, String description, String fileName, String config) {
		setValues(name, main, version, api, author, description, fileName, config);
	}

	public void setValues(String name, String main, String version, int api, String author, String description, String fileName, String config) {
		this.name = name;
		this.main = main;
		this.version = version;
		this.api = api;
		this.author = author;
		this.description = description == null ? "" : description;
		this.fileName = fileName;
		this.config = config;
	}

	public void setValues(PluginDescription description) {
		setValues(description.name, description.main, description.version, description.api, description.author, description.description, description.fileName, description.config);
	}

	@Override
	public String toString() {
		return "{[PluginDescription" + "@" + Integer.toHexString(hashCode()) + "] name:" + this.name + ", main:" + this.main + ", version:" + this.version + ", api:" + this.api + ", author:" + this.author + ", description:" + this.description + ", file:" + this.fileName + ", config:" + this.config + "}";
	}

	public boolean isEmpty() {
		return this.name != null && !this.name.isEmpty()
				&& this.main != null && !this.main.isEmpty()
				&& this.version != null && !this.version.isEmpty()
				&& this.api != 0
				&& this.author != null && !this.author.isEmpty()
				&& this.description != null
				&& this.fileName != null && !this.fileName.isEmpty();
	}

	public String getName() {
		return name;
	}

	public PluginDescription setName(String name) {
		this.name = name;
		return this;
	}

	public String getMainClass() {
		return main;
	}

	public PluginDescription setMainClass(String mainClass) {
		this.main = mainClass;
		return this;
	}

	public String getVersion() {
		return version;
	}

	public PluginDescription setVersion(String version) {
		this.version = version;
		return this;
	}

	public int getAPIVersion() {
		return api;
	}

	public PluginDescription setAPIVersion(int APIVersion) {
		this.api = APIVersion;
		return this;
	}

	public String getAuthor() {
		return author;
	}

	public PluginDescription setAuthor(String author) {
		this.author = author;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public PluginDescription setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getFileName() {
		return this.fileName;
	}

	public PluginDescription setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}
}
