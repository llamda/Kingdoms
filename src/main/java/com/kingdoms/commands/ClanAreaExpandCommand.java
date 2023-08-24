package com.kingdoms.commands;

import java.util.List;

import com.kingdoms.Area;
import com.kingdoms.Areas;
import com.kingdoms.Transactions;
import com.kingdoms.teams.ClanPlayer;

public class ClanAreaExpandCommand extends Command {

	/**
	 * Expands an existing team area to cover a new chunk
	 */
	public ClanAreaExpandCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (clan == null) {
			error(NEED_TEAM);
			return;
		}

		if (!rank.hasPermission("AREA")) {
			error(NO_PERMISSION);
			return;
		}

		/* Check for existing area in chunk */
		Area area = Areas.getChunkOwner(chunk);
		if (area != null) {
			error("Chunk already claimed by " + area.getAreaName());
			return;
		}

		/* Confirm attachment to nearby area */
		List<Area> adjacentAreas = Areas.getAdjacentAreas(chunk);
		if (adjacentAreas.isEmpty()) {
			error("No adjacent areas.");
			return;
		}

		List<Area> nearbyAreas = Areas.getNearbyAreas(chunk);
		if (nearbyAreas.size() > 1) {
			error("Can not connect multiple different areas.");
			return;
		}

		Area expandedArea = adjacentAreas.get(0);

		if (!clan.getAreas().contains(expandedArea.getUuid())) {
			error("Your team does not own that area.");
			return;
		}

		if (expandedArea.getAreaChunks().size() >= expandedArea.getMaxAreaSize()) {
			error("Your area is already at the maximum size.");
			return;
		}

		Transactions transactions = new Transactions(player);

		if (transactions.canNotAfford(Transactions.AREA_EXPAND_PRICE)) {
			error("You need " + Transactions.AREA_EXPAND_PRICE + " " + Transactions.CURRENCY.toString().toLowerCase() + "(s) to do that.");
			return;
		}

		/* Create Area */
		transactions.pay(Transactions.AREA_EXPAND_PRICE);


		expandedArea.getAreaChunks().add(chunk);
		expandedArea.saveData();

		success("Added chunk.");
	}
}
