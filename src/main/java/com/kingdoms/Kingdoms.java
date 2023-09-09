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
		pm.registerEvents(new AsyncChat(), this);
		pm.registerEvents(new PlayerJoin(), this);
		pm.registerEvents(new PlayerQuit(), this);
		pm.registerEvents(new BlockDamage(), this);
		pm.registerEvents(new BlockPlace(), this);
		pm.registerEvents(new BlockBreak(), this);
		pm.registerEvents(new PlayerBucketEmpty(), this);
		pm.registerEvents(new BlockFromTo(), this);
		pm.registerEvents(new PlayerInteract(), this);
		pm.registerEvents(new PlayerMove(), this);
		pm.registerEvents(new PlayerTeleport(), this);
		pm.registerEvents(new BlockBurn(), this);
		pm.registerEvents(new ChunkUnload(), this);
		pm.registerEvents(new CreatureSpawn(), this);
		pm.registerEvents(new EntityTarget(), this);

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
