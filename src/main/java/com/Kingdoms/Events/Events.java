package com.Kingdoms.Events;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

import com.Kingdoms.Area;
import com.Kingdoms.AreaChunk;
import com.Kingdoms.Areas;
import com.Kingdoms.Teams.ClanPlayer;

public class Events {

	/**
	 * Checks if the ClanPlayer can edit a certain block
	 * @param clanPlayer the player
	 * @param block the block
	 * @return true if chunk is unclaimed or part of team's owned areas.
	 */
	public static boolean canBuild(ClanPlayer clanPlayer, Block block) {

		AreaChunk chunk = new AreaChunk(block.getChunk());
		Area area = Areas.getChunkOwner(chunk);

		if (area == null)
			return true;

		if (clanPlayer.getClan() == null || !clanPlayer.getClan().getAreas().contains(area.getUuid()))
			return false;

		return true;
	}


	/**
	 * Checks if the player can edit a certain area
	 * @param clanPlayer the player
	 * @param area the area
	 * @return true if area is owned by player's team
	 */
	public static boolean canBuild(ClanPlayer clanPlayer, Area area) {

		if (area == null) {
			return true;
		}

		// TODO: let kingdom members build (if they want)
		if (clanPlayer.getClan() == null || !clanPlayer.getClan().getAreas().contains(area.getUuid())) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if the player can edit a certain block inside area area (Such as blocks they placed themselves in an unowned area)
	 * @param clanPlayer the player
	 * @param area the area
	 * @param block the block to check
	 * @return true if area is owned by player's team
	 */
	/*
	 * lag..
	public static boolean canBuild(ClanPlayer clanPlayer, Area area, Block block) {

		AreaChunk chunk = new AreaChunk(block.getChunk());
		Set<BlockState> damage;
		if ((damage = Areas.getChunkDamage().get(chunk)) != null) {
			for (BlockState state : damage) {
				if (state.getLocation().equals(block.getLocation())) {
					return true;
				}
			}

		}
		return canBuild(clanPlayer, area);
	}
	*/


	public static boolean isArea(Location location) {

		AreaChunk chunk = new AreaChunk(location.getChunk());
		for (Area area : Areas.getAreas().values()) {
			if (area.getAreaChunks().contains(chunk))
				return true;
		}
		return false;
	}

	public static void saveState(BlockState state) {

		AreaChunk chunk = new AreaChunk(state.getChunk());

		Set<BlockState> currentChunkStates = Areas.getChunkDamage().get(chunk);

		if (currentChunkStates == null) {
			currentChunkStates = new HashSet<BlockState>();
		}

		else {
			for (BlockState blockstate : currentChunkStates) {
				if (blockstate.getLocation().equals(state.getLocation()))
					return;
			}
		}

		currentChunkStates.add(state);
		Areas.getChunkDamage().put(chunk, currentChunkStates);
	}

	public static void sendAreaChangeMessage(Player player, Chunk from, Chunk to) {

		if (from.equals(to)) {
			return;
		}

		Area fromArea = Areas.getChunkOwner(from);
		Area toArea = Areas.getChunkOwner(to);

		if (toArea == fromArea) {
			return;
		}

		if (fromArea != null) {
			player.sendMessage(ChatColor.RED + " * " + ChatColor.DARK_RED + "Leaving " + ChatColor.RED + fromArea.getAreaName());
		}

		if (toArea != null) {
			player.sendMessage(ChatColor.GREEN + " * " + ChatColor.DARK_GREEN + "Entering " + ChatColor.GREEN + toArea.getAreaName());
		}
	}

	public static void restoreChunk(Chunk chunk) {
		restoreChunk(new AreaChunk(chunk));
	}

	public static void restoreChunk(AreaChunk chunk) {

		if (Areas.getChunkDamage().keySet().contains(chunk)) {
		//	System.out.println("restoring damaged chunk.");
			Set<BlockState> states = Areas.getChunkDamage().get(chunk);
			for (BlockState state : states) {

				state.getBlock().breakNaturally();
				state.update(true);
			}

			Areas.getChunkDamage().remove(chunk);
		}
	}

	public static void restoreAllChunks() {
		for (Set<BlockState> states : Areas.getChunkDamage().values()) {
			for (BlockState state : states) {

				state.getBlock().breakNaturally();
				state.update(true);
			}
		}

		Areas.getChunkDamage().clear();
	}

}
