package com.Kingdoms;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Kingdoms.Teams.Clan;
import com.Kingdoms.Teams.Clans;

public class KingdomsUtils {
	
	/**
	 * Show correct sides for each chunk in area
	 * @param area area to show
	 */
	
	private static final int PARTICLE_DIST = 40 * 40;
	// private static final int PARTICLE_DIST = 10 * 10;
	
	public static void startTimer() {
		
		/*
		Kingdoms.instance.getServer().getScheduler().runTaskTimer(Kingdoms.instance, new Runnable() {
			public void run() {
				showAreas1();
			}
		}, 0L, 3L);
		*/
	}
	
	
	/*
	public static void showAreas() {
		
		for (Player player : Kingdoms.instance.getServer().getOnlinePlayers()) {
			
			if (player.getInventory().getItemInMainHand().getType() != Material.GOLD_BLOCK) {
				continue;
			}
			
			ClanPlayer clanPlayer = Connections.getClanPlayer(player.getUniqueId());
			Area area = Areas.getChunkOwner(clanPlayer.getChunk());
			
			int dist = 2;
			for (int x = -dist; x <= dist; x++) {
				for (int z = -dist; z <= dist; z++) {
					
				}
			}
			
			
			// Area area = Areas.getChunkOwner(chunk);
		}
	}
	*/
	
	
	public static void showAreas1() {
		
		for (Clan clan : Clans.getClans().values()) {
		
			//ParticleColor color = new ParticleColor(clan.getColor());
			DustOptions options = new DustOptions(chatColorToColor(clan.getColor()), 1);
			
			
			for (UUID uuid : clan.getAreas()) {
				Area area = Areas.getAreas().get(uuid);
				
				for (AreaChunk areaChunk : area.getAreaChunks()) {
					
					int x = areaChunk.getX();
					int z = areaChunk.getZ();
					
					boolean north = false, south = false, west = false, east = false;
					
					if (!area.hasChunk(x, z - 1))	north = true;
					if (!area.hasChunk(x, z + 1))	south = true;
					if (!area.hasChunk(x - 1, z))	west = true;
					if (!area.hasChunk(x + 1, z))	east = true;
					
					if (!north && !south && !west && !east)
						continue;
					
					showChunk(areaChunk.getChunk(), options, north, south, west, east);
				}
			}
			
		}
		
		/*
		for (Player player : Kingdoms.instance.getServer().getOnlinePlayers()) {
			
			ClanPlayer clanPlayer = Connections.getClanPlayer(player.getUniqueId());
			
			for (AreaChunk areaChunk : PlayerListener.getPlayerChunks().get(player.getUniqueId())) {
				
				Area area = Areas.getChunkOwner(areaChunk);

				
				if (area == null)
					continue;
				
				int x = areaChunk.getX();
				int z = areaChunk.getZ();
				
				boolean north = false, south = false, west = false, east = false;
				
				if (!area.hasChunk(x, z - 1))	north = true;
				if (!area.hasChunk(x, z + 1))	south = true;
				if (!area.hasChunk(x - 1, z))	west = true;
				if (!area.hasChunk(x + 1, z))	east = true;
				
				
				ParticleColor color;
				if (clanPlayer.getClan() == null || !clanPlayer.getClan().getAreas().contains(area.getUuid())) {
					color = new ParticleColor(1, 0, 0);
				}	else {
					color = new ParticleColor(0.001, 1, 0);
				}
				
				
				
				
				showChunk(player, areaChunk.getChunk(), color, north, south, west, east);	
			}
		}
		*/
	}
	
	
	
	
	public static Chunk getRelativeChunk(Chunk chunk, int x,  int z) {		
		return chunk.getWorld().getChunkAt(chunk.getX() + (x * 16), chunk.getZ() + (z * 16));
	}
	
	/*
	public static void showArea(Area area) {
		for (AreaChunk areaChunk : area.getAreaChunks()) {
			
			int x = areaChunk.getX();
			int z = areaChunk.getZ();
			
			boolean north = false, south = false, west = false, east = false;
			
			if (!area.hasChunk(x, z - 1))	north = true;
			if (!area.hasChunk(x, z + 1))	south = true;
			if (!area.hasChunk(x - 1, z))	west = true;
			if (!area.hasChunk(x + 1, z))	east = true;
			
			if (!north && !south && !west && !east)
				return;
			
			
			showChunk(areaChunk.getChunk(), north, south, west, east);	
		}
	}
	
	public static void showChunk(Chunk chunk) {
		showChunk(chunk, true, true, true, true);
	}
	*/
	
	public static void showChunk(Chunk chunk, DustOptions dustOptions, boolean north, boolean south, boolean west, boolean east) {	
		
		for (int i = 0; i < 16; i++) {	
			if (north)	spawnParticle(chunk.getBlock(i, 0, 0), dustOptions);
			if (south)	spawnParticle(chunk.getBlock(i, 0, 15), dustOptions);
			if (west)	spawnParticle(chunk.getBlock(0, 0, i),dustOptions);
			if (east)	spawnParticle(chunk.getBlock(15, 0, i), dustOptions);	
		}	
	}
	
	public static void spawnParticle(Block block, DustOptions dustOptions) {
		
		double y = block.getWorld().getHighestBlockYAt(block.getX(), block.getZ()) + 0.5;
		double x = block.getX() + 0.5;
		double z = block.getZ() + 0.5;
				
		for (Player player : Kingdoms.instance.getServer().getWorld(block.getWorld().getName()).getPlayers()) {
			
			if (player.getLocation().distanceSquared(new Location(block.getWorld(), x, y, z)) > PARTICLE_DIST)
				continue;
			
			//player.spawnParticle(Particle.REDSTONE, x, y, z, 0, color.getR(), color.getG(), color.getB(), 1, null);
			player.spawnParticle(Particle.REDSTONE, x, y, z, 1, dustOptions);
		}
		
		/*
		player.spawnParticle(Particle.REDSTONE,
			x, y, z,	// Location
			0,			// Count (must be 0 to color?)
			0.00001,	// Red
			1,			// Green
			0,			// Blue
			1,			//Extra (must be 1 to color?)
			null);		// data has to be null?
			*/
	}
	
	/**
	 * Removes the given amount of items with type 
	 * @param player player to remove items from
	 * @param type Material of the item to remove
	 * @param count amount to remove
	 * @return false if items are not in the player's inventory
	 */
	public static boolean payItem(Player player, Material type, int count) {
		
		if (player.getGameMode() == GameMode.CREATIVE)
			return true;
		
		
		if (!player.getInventory().containsAtLeast(new ItemStack(type, 1), count)) {
			player.sendMessage(ChatColor.RED + "You need " + count + " " + type.toString() + " to do that.");
			return false;
		}
		
		int removed = 0;
		for (ItemStack item : player.getInventory().getContents()) {
			
			if (item == null)
				continue;
			
			if (item.getType() == type && !item.hasItemMeta()) {
				
				if (item.getAmount() <= count) {
					removed += item.getAmount();
					item.setAmount(0);
				}
				
				if (item.getAmount() > count) {
					item.setAmount(item.getAmount() - count);
					break;
				}
				
				if (removed == count)
					break;	
			}	
		}
		return true;
	}
	
	
	public static String chatColorToString(ChatColor color) {
		switch (color) {
		case AQUA: 			return "AQUA";
		case BLACK:			return "BLACK";	
		case BLUE:			return "BLUE";
		case BOLD:			return "BOLD";
		case DARK_AQUA:		return "DARK_AQUA";			
		case DARK_BLUE:		return "DARK_BLUE";
		case DARK_GRAY:		return "DARK_GRAY";
		case DARK_GREEN:	return "DARK_GREEN";
		case DARK_PURPLE:	return "DARK_PURPLE";
		case DARK_RED:		return "DARK_RED";
		case GOLD:			return "GOLD";
		case GRAY:			return "GRAY";
		case GREEN:			return "GREEN";
		case ITALIC:		return "ITALIC";
		case LIGHT_PURPLE:	return "LIGHT_PURPLE";
		case MAGIC:			return "MAGIC";
		case RED:			return "RED";
		case RESET:			return "RESET";
		case STRIKETHROUGH:	return "STRIKETHROUGH";
		case UNDERLINE:		return "UNDERLINE";
		case WHITE:			return "WHITE";
		case YELLOW:		return "YELLOW";
		}
		return null;	
	}

	public static ChatColor stringToChatColor(String string, boolean onlyColors) {
		
		if (onlyColors) {
			switch(string.toUpperCase()) {
			case "AQUA": 			return ChatColor.AQUA;
			case "BLACK":			return ChatColor.BLACK;	
			case "BLUE":			return ChatColor.BLUE;
			case "BOLD":			return ChatColor.BOLD;
			case "DARK_AQUA":		return ChatColor.DARK_AQUA;			
			case "DARK_BLUE":		return ChatColor.DARK_BLUE;
			case "DARK_GRAY":		return ChatColor.DARK_GRAY;
			case "DARK_GREEN":		return ChatColor.DARK_GREEN;
			case "DARK_PURPLE":		return ChatColor.DARK_PURPLE;
			case "DARK_RED":		return ChatColor.DARK_RED;
			case "GOLD":			return ChatColor.GOLD;
			case "GRAY":			return ChatColor.GRAY;
			case "GREEN":			return ChatColor.GREEN;
			case "LIGHT_PURPLE":	return ChatColor.LIGHT_PURPLE;
			case "RED":				return ChatColor.RED;
			case "WHITE":			return ChatColor.WHITE;
			case "YELLOW":			return ChatColor.YELLOW;
			}
		}	else {
			switch(string.toUpperCase()) {
			case "AQUA": 			return ChatColor.AQUA;
			case "BLACK":			return ChatColor.BLACK;	
			case "BLUE":			return ChatColor.BLUE;
			case "BOLD":			return ChatColor.BOLD;
			case "DARK_AQUA":		return ChatColor.DARK_AQUA;			
			case "DARK_BLUE":		return ChatColor.DARK_BLUE;
			case "DARK_GRAY":		return ChatColor.DARK_GRAY;
			case "DARK_GREEN":		return ChatColor.DARK_GREEN;
			case "DARK_PURPLE":		return ChatColor.DARK_PURPLE;
			case "DARK_RED":		return ChatColor.DARK_RED;
			case "GOLD":			return ChatColor.GOLD;
			case "GRAY":			return ChatColor.GRAY;
			case "GREEN":			return ChatColor.GREEN;
			case "ITALIC":			return ChatColor.ITALIC;
			case "LIGHT_PURPLE":	return ChatColor.LIGHT_PURPLE;
			case "MAGIC":			return ChatColor.MAGIC;
			case "RED":				return ChatColor.RED;
			case "RESET":			return ChatColor.RESET;
			case "STRIKETHROUGH":	return ChatColor.STRIKETHROUGH;
			case "UNDERLINE":		return ChatColor.UNDERLINE;
			case "WHITE":			return ChatColor.WHITE;
			case "YELLOW":			return ChatColor.YELLOW;
			}
		}
		return null;
	}
	
	private static Color chatColorToColor(ChatColor color) {
		  switch (color) {
	          case AQUA:
	              return Color.AQUA;
	          case BLACK:
	              return Color.BLACK;
	          case BLUE:
	              return Color.BLUE;
	          case DARK_AQUA:
	              return Color.BLUE;
	          case DARK_BLUE:
	              return Color.BLUE;
	          case DARK_GRAY:
	              return Color.GRAY;
	          case DARK_GREEN:
	              return Color.GREEN;
	          case DARK_PURPLE:
	              return Color.PURPLE;
	          case DARK_RED:
	              return Color.RED;
	          case GOLD:
	              return Color.YELLOW;
	          case GRAY:
	              return Color.GRAY;
	          case GREEN:
	              return Color.GREEN;
	          case LIGHT_PURPLE:
	              return Color.PURPLE;
	          case RED:
	              return Color.RED;
	          case WHITE:
	              return Color.WHITE;
	          case YELLOW:
	              return Color.YELLOW;
	          default:
	        	  return null;
		  }
	}

}








