package com.kingdoms;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import com.kingdoms.teams.Clan;
import com.kingdoms.teams.Clans;

public class Areas {

	private static final HashMap<UUID, Area> areas = new HashMap<>();
	private static final HashMap<AreaChunk, Set<BlockState>> chunkDamage = new HashMap<>();
	private static final HashSet<Block> liquids = new HashSet<>();


	public Areas() {
		reload();
	}

	public void reload() {
		File[] clanFiles = new File(Kingdoms.CONFIG + "Areas").listFiles();

		if (clanFiles == null) {
			return;
		}

		for (File file : clanFiles) {
			Area area = new Area(file);
			areas.put(area.getUuid(), area);
		}
	}




	public static Area getChunkOwner(Block block) {
		return getChunkOwner(block.getChunk());
	}

	public static Area getChunkOwner(Chunk chunk) {
		return getChunkOwner(new AreaChunk(chunk));
	}

	public static Area getChunkOwner(AreaChunk chunk) {

		for (UUID uuid : getAreas().keySet()) {
			Area area = areas.get(uuid);

			if (area.getAreaChunks().contains(chunk)) {
				return area;
			}
		}
		return null;
	}

	public static Clan getClanOwner(Area area) {

		for (Clan clan : Clans.getClans().values()) {
			if (clan.getAreas().contains(area.getUuid()))
				return clan;
		}
		return null;
	}

	/**
	 * Returns a list of areas the chunk is near (3x3)
	 * @param chunk chunk to check
	 * @return list of areas connected
	 */
	public static List<Area> getNearbyAreas(AreaChunk chunk) {

		List<Area> nearbyAreas = new ArrayList<>();

		AreaChunk[] chunks = {
				chunk.getRelative(-1, 1),
				chunk.getRelative(0, 1),
				chunk.getRelative(1, 1),
				chunk.getRelative(-1, 0),
				chunk.getRelative(0, 0),
				chunk.getRelative(1, 0),
				chunk.getRelative(-1, -1),
				chunk.getRelative(0, -1),
				chunk.getRelative(1, -1)
		};

		for (AreaChunk relative : chunks) {

			Area area;

			if ((area = getChunkOwner(relative)) != null && !nearbyAreas.contains(area))
				nearbyAreas.add(area);
		}

		return nearbyAreas;
	}

	/**
	 * Returns a list of areas the chunk is connected directly to. (North, South, East, West)
	 * @param chunk chunk to check
	 * @return list of areas connected
	 */
	public static List<Area> getAdjacentAreas(AreaChunk chunk) {

		List<Area> adjacentAreas = new ArrayList<>();

		AreaChunk[] chunks = {
				chunk.getRelative(0, 1),
				chunk.getRelative(1, 0),
				chunk.getRelative(0, -1),
				chunk.getRelative(-1, 0),
		};

		for (AreaChunk relative : chunks) {

			Area area;

			if ((area = getChunkOwner(relative)) != null && !adjacentAreas.contains(area))
				adjacentAreas.add(area);
		}

		return adjacentAreas;
	}

	/**
	 * Get an area by name
	 * @param name name to check (ignores case)
	 * @return Area or null
	 */
	public static Area getAreaByName(String name) {
		for (Area area : getAreas().values()) {
			if (area.getAreaName().equalsIgnoreCase(name)) {
				return area;
			}
		}
		return null;
	}

	public static HashMap<UUID, Area> getAreas() {
		return areas;
	}

	public static HashMap<AreaChunk, Set<BlockState>> getChunkDamage() {
		return chunkDamage;
	}

	public static HashSet<Block> getLiquids() {
		return liquids;
	}

}
