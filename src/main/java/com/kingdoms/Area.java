package com.kingdoms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Area {

	private UUID uuid;
	private String areaName;

	private File file;
	private FileConfiguration areaConfig;

	private String worldName;
	private AreaChunk centerChunk;

	private final HashSet<AreaChunk> areaChunks = new HashSet<>();
	private final HashSet<AreaUpgrade> upgrades = new HashSet<>();
	private final int maxAreaSize = Kingdoms.settings.getMaxAreaSize();

	// TODO: productions

	public Area(AreaChunk chunk, String areaName) {

		setUuid(UUID.randomUUID());
		setFile(new File(Kingdoms.CONFIG + "Areas/" + uuid.toString() + ".yml"));
		setAreaConfig(YamlConfiguration.loadConfiguration(getFile()));

		setAreaName(areaName);
		setWorldName(chunk.getWorldName());
		setCenterChunk(chunk);
		getAreaChunks().add(chunk);

		saveData();
	}

	public Area(File file) {

		setFile(file);
		setAreaConfig(YamlConfiguration.loadConfiguration(getFile()));

		setUuid(UUID.fromString(Objects.requireNonNull(getAreaConfig().getString("UUID"))));
		setAreaName(getAreaConfig().getString("AreaName"));
		setWorldName(getAreaConfig().getString("WorldName"));

		setCenterChunk(AreaChunk.fromString(getWorldName(), Objects.requireNonNull(getAreaConfig().getString("Center"))));

		List<String> chunkData = getAreaConfig().getStringList("AreaChunks");
		for (String chunk : chunkData) {
			getAreaChunks().add(AreaChunk.fromString(getWorldName(), chunk));
		}

		for (String upgrade : getAreaConfig().getStringList("Upgrades")) {
			getUpgrades().add(AreaUpgrade.valueOf(upgrade));
		}
	}

	public void saveData() {
		getAreaConfig().set("UUID", getUuid().toString());
		getAreaConfig().set("AreaName", getAreaName());
		getAreaConfig().set("WorldName", getWorldName());
		getAreaConfig().set("Center", getCenterChunk().toString());

		List<String> chunkData = new ArrayList<>();
		for (AreaChunk chunk : getAreaChunks()) {
			chunkData.add(chunk.toString());
		}
		getAreaConfig().set("AreaChunks", chunkData);

		List<String> upgrades = new ArrayList<>();
		for (AreaUpgrade upgrade : this.upgrades) {
			upgrades.add(upgrade.toString());
		}
		getAreaConfig().set("Upgrades", upgrades);

		try {
			getAreaConfig().save(getFile());
		} catch (IOException e) {
			Kingdoms.instance.getLogger().log(Level.WARNING, "Failed to save " + file.getPath(), e);
		}
	}

	public Location getAreaCenterLocation() {
		Chunk chunk = getCenterChunk().getChunk();
		return chunk.getBlock(8, 60, 8).getLocation();
	}

	public String getFormattedAreaCoordinates() {
		Location center = getAreaCenterLocation();
		return "X:" + center.getBlockX() + ", Z:" + center.getBlockZ();
	}

	public static final String HIDDEN_COORDINATES = "X:?, Z:?";

	public boolean hasAreaUpgrade(AreaUpgrade upgrade) {
		return getUpgrades().contains(upgrade);
	}

	public void addUpgrade(AreaUpgrade upgrade) {
		getUpgrades().add(upgrade);
		saveData();
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public FileConfiguration getAreaConfig() {
		return areaConfig;
	}

	public void setAreaConfig(FileConfiguration areaConfig) {
		this.areaConfig = areaConfig;
	}

	public String getWorldName() {
		return worldName;
	}

	public void setWorldName(String worldName) {
		this.worldName = worldName;
	}

	public AreaChunk getCenterChunk() {
		return centerChunk;
	}

	public void setCenterChunk(AreaChunk centerChunk) {
		this.centerChunk = centerChunk;
	}

	public HashSet<AreaChunk> getAreaChunks() {
		return areaChunks;
	}

	public HashSet<AreaUpgrade> getUpgrades() {
		return upgrades;
	}

	public int getMaxAreaSize() {
		return maxAreaSize;
	}

}

