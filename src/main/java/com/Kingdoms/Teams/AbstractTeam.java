package com.Kingdoms.Teams;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.Kingdoms.Connections;
import com.Kingdoms.Kingdoms;

public abstract class AbstractTeam {

	private UUID uuid;
	private String name;


	/* YAML Configuration file */
	private File file;
	private FileConfiguration teamConfig;



	/* Load all data from file */
	public abstract void loadFileData();


	/* Get the file folder */
	public abstract String getFileFolder();


	/* Get all members in group */
	public abstract List<ClanPlayer> getMembers();


	/* Get format for messages sent to this Team */
	public abstract String getMessageFormat(String message);



	/* Load new Configuration file */
	public void loadNew() {

		/* New UUID */
		if (uuid == null) {
			uuid = UUID.randomUUID();
		}

		/* Kingdoms/Folder/UUID.yml */
		file = new File(Kingdoms.CONFIG + getFileFolder() + "/" + uuid.toString() + ".yml");
		teamConfig = YamlConfiguration.loadConfiguration(file);

	}



	/* Load existing Configuration file */
	public void loadFile(File file) {

		this.file = file;
		teamConfig = YamlConfiguration.loadConfiguration(file);

		loadFileData();
	}



	/* Save data to file */
	public void saveData() {
		try {
			teamConfig.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	/* Get all online members in group */
	public List<ClanPlayer> getOnlineMembers() {

		List<ClanPlayer> online = new ArrayList<ClanPlayer>();

		for (ClanPlayer player : getMembers()) {

			if (Connections.getClanPlayers().containsKey(player.getUuid())) {
				online.add(player);
			}
		}

		return online;
	}



	/* Send message with special formatting to all members in group */
	public void sendMessage(String message) {

		String format = getMessageFormat(message);
		sendExactMessage(format);

	}



	/* Send exact message to all members in group without any formatting */
	public void sendExactMessage(String message) {

		for (ClanPlayer player : getOnlineMembers()) {
			player.sendMessage(message);
		}

	}



	/* Getters and Setters */
	public UUID getUuid() {
		return uuid;
	}



	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public File getFile() {
		return file;
	}



	public void setFile(File file) {
		this.file = file;
	}



	public FileConfiguration getTeamConfig() {
		return teamConfig;
	}



	public void setTeamConfig(FileConfiguration teamConfig) {
		this.teamConfig = teamConfig;
	}

}
