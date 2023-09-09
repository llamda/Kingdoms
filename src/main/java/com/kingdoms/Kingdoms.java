package com.kingdoms;

import com.kingdoms.events.AsyncChat;
import com.kingdoms.events.BlockBreak;
import com.kingdoms.events.BlockBurn;
import com.kingdoms.events.BlockDamage;
import com.kingdoms.events.BlockFromTo;
import com.kingdoms.events.BlockPlace;
import com.kingdoms.events.ChunkUnload;
import com.kingdoms.events.CreatureSpawn;
import com.kingdoms.events.EntityTarget;
import com.kingdoms.events.Events;
import com.kingdoms.events.PlayerBucketEmpty;
import com.kingdoms.events.PlayerInteract;
import com.kingdoms.events.PlayerJoin;
import com.kingdoms.events.PlayerMove;
import com.kingdoms.events.PlayerQuit;
import com.kingdoms.events.PlayerTeleport;
import com.kingdoms.teams.Clans;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.stream.Stream;

public class Kingdoms extends JavaPlugin {

	public static Kingdoms instance;
	public static final String CONFIG = "plugins/Kingdoms/";
	public static Config settings;


	public void onEnable() {
		instance = this;
		settings = new Config();

		/* Load data */
		new Areas();
		new Clans();
		new com.kingdoms.teams.Kingdoms();

		/* Reload any online players */
		Connections.reload();

		/* Register Events */
		PluginManager pm = getServer().getPluginManager();
		Stream.of(
				new AsyncChat(),
				new PlayerJoin(),
				new PlayerQuit(),
				new BlockDamage(),
				new BlockPlace(),
				new BlockBreak(),
				new PlayerBucketEmpty(),
				new BlockFromTo(),
				new PlayerInteract(),
				new PlayerMove(),
				new PlayerTeleport(),
				new BlockBurn(),
				new ChunkUnload(),
				new CreatureSpawn(),
				new EntityTarget()
		).forEach(listener -> pm.registerEvents(listener, Kingdoms.instance));

		/* Register Commands */
		CommandListener commandListener = new CommandListener();
		Stream.of("team", "t", "kingdom", "k")
				.map(this::getCommand)
				.peek(Objects::requireNonNull)
				.forEach(cmd -> cmd.setExecutor(commandListener));
	}

	public void onDisable() {
		Events.restoreAllChunks();
	}

}
