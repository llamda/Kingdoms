package com.Kingdoms.Teams;

import com.Kingdoms.Connections;
import com.Kingdoms.Kingdoms;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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


	public abstract TextComponent getMessagePrefix();

	public abstract TextColor messageColor();

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

		List<ClanPlayer> online = new ArrayList<>();

		for (ClanPlayer player : getMembers()) {

			if (Connections.getClanPlayers().containsKey(player.getUuid())) {
				online.add(player);
			}
		}

		return online;
	}


	public void sendPrefixedMessage(TextComponent message) {
		sendExactMessage(getMessagePrefix().append(message));
	}

	public void sendPrefixedString(String message) {
		sendExactMessage(getMessagePrefix().append(Component.text(message, messageColor())));
	}

	/* Send exact message to all members in group without any formatting */
	public void sendExactMessage(TextComponent message) {
		getOnlineMembers().forEach(p -> p.getPlayer().sendMessage(message));
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


	public FileConfiguration getTeamConfig() {
		return teamConfig;
	}


}
