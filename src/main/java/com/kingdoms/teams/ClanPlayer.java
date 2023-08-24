package com.kingdoms.teams;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.kingdoms.AreaChunk;
import com.kingdoms.Kingdoms;

public class ClanPlayer {

	private File file;
	private FileConfiguration playerConfig;

	private String name;
	private UUID uuid;

	private Clan clan;
	private Clan invite;

	/**
	 * Load user ClanPlayer file if it exists, else create it
	 * @param uuid The UUID to load
	 */
	public ClanPlayer(UUID uuid) {

		setFile(new File(Kingdoms.CONFIG + "Players/" + uuid.toString() + ".yml"));
		setPlayerConfig(YamlConfiguration.loadConfiguration(getFile()));

		OfflinePlayer player = Kingdoms.instance.getServer().getOfflinePlayer(uuid);

		setName(player.getName());
		setUuid(uuid);

		if (getFile().exists()) {
			loadData();
			saveData();
		}	else {
			// loadDefaults(uuid);
			saveData();
		}
	}

	public void loadData() {
		setClan(Clans.getClan(getUuid()));
	}

	public void saveData() {

		getPlayerConfig().set("Name", getName());
		getPlayerConfig().set("UUID", getUuid().toString());

		try {
			getPlayerConfig().save(getFile());
		} catch (IOException e) {
			Kingdoms.instance.getLogger().log(Level.WARNING, "Failed to save " + file.getPath(), e);
		}
	}

	public ClanRank getRank() {
		return Clans.getClanRank(getUuid());
	}

	public Kingdom getKingdom() {

		if (clan == null) return null;

		return (clan.getKingdom() == null) ? null : clan.getKingdom();

	}

	public Player getPlayer() {
		return Kingdoms.instance.getServer().getPlayer(getUuid());
	}

	public AreaChunk getChunk() {
		return new AreaChunk(getPlayer().getLocation().getChunk());
	}

	/*  Send ClanPlayer messages to player or console if offline */
	public void sendMessage(TextComponent message) {
		if (null != getPlayer()) {
			getPlayer().sendMessage(message);
		}	else {
			Kingdoms.instance.getServer().getConsoleSender().sendMessage(message);
		}
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public FileConfiguration getPlayerConfig() {
		return playerConfig;
	}

	public void setPlayerConfig(FileConfiguration playerConfig) {
		this.playerConfig = playerConfig;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Clan getClan() {
		return clan;
	}

	public void setClan(Clan clan) {
		this.clan = clan;
	}

	public Clan getInvite() {
		return invite;
	}

	public void setInvite(Clan invite) {
		this.invite = invite;
	}
}
