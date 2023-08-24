package com.Kingdoms;

import com.Kingdoms.Events.AsyncChat;
import com.Kingdoms.Events.BlockBreak;
import com.Kingdoms.Events.BlockBurn;
import com.Kingdoms.Events.BlockDamage;
import com.Kingdoms.Events.BlockFromTo;
import com.Kingdoms.Events.BlockPlace;
import com.Kingdoms.Events.ChunkUnload;
import com.Kingdoms.Events.CreatureSpawn;
import com.Kingdoms.Events.Events;
import com.Kingdoms.Events.PlayerBucketEmpty;
import com.Kingdoms.Events.PlayerInteract;
import com.Kingdoms.Events.PlayerJoin;
import com.Kingdoms.Events.PlayerMove;
import com.Kingdoms.Events.PlayerQuit;
import com.Kingdoms.Events.PlayerTeleport;
import com.Kingdoms.Teams.Clans;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

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
		new com.Kingdoms.Teams.Kingdoms();

		/* Reload any online players */
		Connections.reload();

		/* Register Events */
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new AsyncChat(), this);
		pm.registerEvents(new PlayerJoin(), this);
		pm.registerEvents(new PlayerQuit(), this);
		pm.registerEvents(new BlockDamage(), this);
		// pm.registerEvents(new EntityExplode(), this);
		pm.registerEvents(new BlockPlace(), this);
		pm.registerEvents(new BlockBreak(), this);
		// pm.registerEvents(new InventoryOpen(), this);
		pm.registerEvents(new PlayerBucketEmpty(), this);
		pm.registerEvents(new BlockFromTo(), this);
		pm.registerEvents(new PlayerInteract(), this);
		pm.registerEvents(new PlayerMove(), this);
		pm.registerEvents(new PlayerTeleport(), this);
		pm.registerEvents(new BlockBurn(), this);
		pm.registerEvents(new ChunkUnload(), this);
		pm.registerEvents(new CreatureSpawn(), this);

		/* Register Commands */
		CommandListener commandListener = new CommandListener();
		getCommand("team").setExecutor(commandListener);
		getCommand("t").setExecutor(commandListener);
		getCommand("kingdom").setExecutor(commandListener);
		getCommand("k").setExecutor(commandListener);

	}

	public void onDisable() {
		Events.restoreAllChunks();
	}

}
