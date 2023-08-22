package com.Kingdoms;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.Clans;

public class AreaCommands {

	private final static ChatColor err = ChatColor.RED;
	// private final static ChatColor warn = ChatColor.YELLOW;
	private final static ChatColor success = ChatColor.GREEN;
	// private final static ChatColor info = ChatColor.GRAY;
	// private final static ChatColor white = ChatColor.WHITE;

	private static final int BASE_CREATE_COST = 5;
	private static final int EXPAND_COST = 2;



	public AreaCommands(ClanPlayer clanPlayer, String[] args) {

		if (args.length < 2) {
			// TODO: show help
			return;
		}


		switch(args[1].toUpperCase()) {

		case"CREATE":		areaCreate(clanPlayer, args);							break;
		case"EXPAND":		areaExpand(clanPlayer, clanPlayer.getChunk());			break;
		case"SETCENTER":
		case"SHOW":
		case"PRODUCTION":

		default:
			break;
		}
	}

	public static void areaCreate(ClanPlayer clanPlayer, String[] args) {

		if (args.length < 3) {
			clanPlayer.sendMessage(err + "Usage: /team area create <area name>");
			return;
		}

		if (clanPlayer.getClan() == null) {
			clanPlayer.sendMessage(err + "You must be in a team to create an area.");
			return;
		}

		if (!Clans.getClanRank(clanPlayer.getUuid()).hasPermission("AREA")) {
			clanPlayer.sendMessage(err + "You do not have permission to edit areas.");
			return;
		}


		List<Area> adjacentAreas = Areas.getNearbyAreas(clanPlayer.getChunk());

		if (adjacentAreas.size() != 0) {
			clanPlayer.sendMessage(err + "Can not create an area directly beside another.");
			return;
		}

		// Price scales with number of currently owned areas.	BASE * (area count +1)^2
		int a = clanPlayer.getClan().getAreas().size() + 1;
		int cost = BASE_CREATE_COST * (a * a);

		if (!KingdomsUtils.payItem(clanPlayer.getPlayer(), Material.GOLD_BLOCK, cost)) {
			return;
		}

		String areaName = args[2];
		for (int i = 3; i < args.length; i++)
			areaName += " " + args[i];


		Area area = new Area(clanPlayer.getChunk(), areaName);
		Areas.getAreas().put(area.getUuid(), area);

		clanPlayer.getClan().getAreas().add(area.getUuid());
		clanPlayer.getClan().saveData();

		clanPlayer.sendMessage(success + "Created Area!");
	}

	public static boolean areaExpand(ClanPlayer clanPlayer, AreaChunk chunk) {

		if (clanPlayer.getClan() == null) {
			clanPlayer.sendMessage(err + "You must be in a team to expand area.");
			return false;
		}

		if (!Clans.getClanRank(clanPlayer.getUuid()).hasPermission("AREA")) {
			clanPlayer.sendMessage(err + "You do not have permission to edit areas.");
			return false;
		}

		Area area = Areas.getChunkOwner(chunk);

		if (area != null) {
			clanPlayer.sendMessage(err + "Chunk already claimed by " + area.getAreaName());
			return false;
		}

		List<Area> adjacentAreas = Areas.getNearbyAreas(chunk);

		if (adjacentAreas.size() == 0) {
			clanPlayer.sendMessage(err + "No adjacent areas.");
			return false;
		}

		if (adjacentAreas.size() > 1) {
			clanPlayer.sendMessage(err + "Can not connect multiple different areas.");
			return false;
		}

		Area expandedArea = adjacentAreas.get(0);

		if (!clanPlayer.getClan().getAreas().contains(expandedArea.getUuid())) {
			clanPlayer.sendMessage(err + "Your team does not own that area.");
			return false;
		}

		if (!KingdomsUtils.payItem(clanPlayer.getPlayer(), Material.GOLD_BLOCK, EXPAND_COST)) {
			return false;
		}

		adjacentAreas.get(0).getAreaChunks().add(chunk);
		adjacentAreas.get(0).saveData();

		clanPlayer.sendMessage(success + "Added chunk.");
		return true;
	}

	public static void areaSetCenter(ClanPlayer clanPlayer, String[] args) {

	}

	public static void areaShow(ClanPlayer clanPlayer, String[] args) {

	}

	public static void areaProduction(ClanPlayer clanPlayer, String[] args) {

	}
}
