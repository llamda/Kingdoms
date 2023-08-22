package com.Kingdoms;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

	private final int maxAreaSize;

	public Config() {
		Kingdoms.instance.saveDefaultConfig();
		FileConfiguration config = Kingdoms.instance.getConfig();
		this.maxAreaSize = config.getInt("maxAreaSize", 36);
	}

	public int getMaxAreaSize() {
		return maxAreaSize;
	}
}
