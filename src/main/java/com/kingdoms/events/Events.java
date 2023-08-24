package com.kingdoms.events;

import com.kingdoms.Area;
import com.kingdoms.AreaChunk;
import com.kingdoms.Areas;
import com.kingdoms.teams.ClanPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Chunk;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Events {

	/**
	 * Checks if the player can edit a certain area
	 * @param clanPlayer the player
	 * @param area the area
	 * @return true if area is not owned by player's team
	 */
	public static boolean canNotBuild(ClanPlayer clanPlayer, Area area) {

		if (area == null) {
			return false;
		}

		// TODO: let kingdom members build (if they want)
		return clanPlayer.getClan() == null || !clanPlayer.getClan().getAreas().contains(area.getUuid());
	}

	public static void saveState(BlockState state) {

		AreaChunk chunk = new AreaChunk(state.getChunk());

		Set<BlockState> currentChunkStates = Areas.getChunkDamage().get(chunk);

		if (currentChunkStates == null) {
			currentChunkStates = new HashSet<>();
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
			player.sendMessage(Component.text(" * ", NamedTextColor.RED)
					.append(Component.text("Leaving ", NamedTextColor.DARK_RED))
					.append(Component.text(fromArea.getAreaName(), NamedTextColor.RED)));
		}

		if (toArea != null) {
			player.sendMessage(Component.text(" * ", NamedTextColor.GREEN)
					.append(Component.text("Entering ", NamedTextColor.DARK_GREEN))
					.append(Component.text(toArea.getAreaName(), NamedTextColor.GREEN)));
		}
	}

	public static void restoreChunk(Chunk chunk) {
		restoreChunk(new AreaChunk(chunk));
	}

	public static void restoreChunk(AreaChunk chunk) {

		if (Areas.getChunkDamage().containsKey(chunk)) {
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
