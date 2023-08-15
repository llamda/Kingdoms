package com.Kingdoms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

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
	//private List<AreaChunk> areaChunks = new ArrayList<AreaChunk>();
	
	private HashSet<AreaChunk> areaChunks = new HashSet<AreaChunk>();
	
	
	private HashSet<AreaUpgrade> upgrades = new HashSet<AreaUpgrade>();
	
	private int maxAreaSize = 36;
	
	
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
		
		setUuid(UUID.fromString(getAreaConfig().getString("UUID")));
		setAreaName(getAreaConfig().getString("AreaName"));
		setWorldName(getAreaConfig().getString("WorldName"));
		
		setCenterChunk(AreaChunk.fromString(getWorldName(), getAreaConfig().getString("Center")));
		
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
		
		List<String> chunkData = new ArrayList<String>();
		for (AreaChunk chunk : getAreaChunks()) {
			chunkData.add(chunk.toString());
		}
		getAreaConfig().set("AreaChunks", chunkData);
		
		List<String> upgrades = new ArrayList<String>();
		for (AreaUpgrade upgrade : this.upgrades) {
			upgrades.add(upgrade.toString());
		}
		getAreaConfig().set("Upgrades", upgrades);
		
		try {
			getAreaConfig().save(getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks if the area contains a specific chunk
	 * @param x the chunk x
	 * @param z the chunk z
	 * @return result
	 */
	public boolean hasChunk(int x, int z) {
		for (AreaChunk areaChunk : getAreaChunks()) {
			if (areaChunk.getX() == x && areaChunk.getZ() == z) {
				return true;
			}
		}
		return false;
	}
	
	public Location getAreaCenterLocation() {
		
		Chunk chunk = getCenterChunk().getChunk();
		Location loc = chunk.getBlock(8, 60, 8).getLocation();
		
		return loc;
	}
	
	public String getFormattedAreaCoordinates() {
		Location center = getAreaCenterLocation();
		return "X:" + center.getBlockX() + ", Z:" + center.getBlockZ();
	}

	public String getHiddenAreaCoordinates() {
		return "X:?, Z:?";
	}
	
	
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

	/*
	public List<AreaChunk> getAreaChunks() {
		return areaChunks;
	}

	public void setAreaChunks(List<AreaChunk> areaChunks) {
		this.areaChunks = areaChunks;
	}
	*/
	public HashSet<AreaChunk> getAreaChunks() {
		return areaChunks;
	}
	
	public void setAreaChunks(HashSet<AreaChunk> areaChunks) {
		this.areaChunks = areaChunks;
	}
	
	public HashSet<AreaUpgrade> getUpgrades() {
		return upgrades;
	}

	public void setUpgrades(HashSet<AreaUpgrade> upgrades) {
		this.upgrades = upgrades;
	}

	public int getMaxAreaSize() {
		return maxAreaSize;
	}

	public void setMaxAreaSize(int maxAreaSize) {
		this.maxAreaSize = maxAreaSize;
	}
}
		
