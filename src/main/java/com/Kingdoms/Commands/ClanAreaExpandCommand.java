package com.Kingdoms.Commands;

import java.util.List;

import com.Kingdoms.Area;
import com.Kingdoms.Areas;
import com.Kingdoms.Transactions;
import com.Kingdoms.Teams.ClanPlayer;

public class ClanAreaExpandCommand extends Command {

	/** 
	 * Expands an existing team area to cover a new chunk
	 */
	public ClanAreaExpandCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);
		
		if (clan == null) {
			msg(ERR + NEED_TEAM);
			return;
		}
		
		if (!rank.hasPermission("AREA")) {
			msg(ERR + NO_PERMISSION);
			return;
		}
		
		/* Check for existing area in chunk */
		Area area = Areas.getChunkOwner(chunk);
		if (area != null) {
			msg(ERR + "Chunk already claimed by " + area.getAreaName());
			return;
		}
		
		/* Confirm attachment to nearby area */
		List<Area> adjacentAreas = Areas.getAdjacentAreas(chunk);
		if (adjacentAreas.size() == 0) {
			msg(ERR + "No adjacent areas.");
			return;
		}
		
		List<Area> nearbyAreas = Areas.getNearbyAreas(chunk);
		if (nearbyAreas.size() > 1) {
			msg(ERR + "Can not connect multiple different areas.");
			return;
		}
		
		Area expandedArea = adjacentAreas.get(0);
		
		if (!clan.getAreas().contains(expandedArea.getUuid())) {
			msg(ERR + "Your team does not own that area.");
			return;
		}
		
		if (expandedArea.getAreaChunks().size() >= expandedArea.getMaxAreaSize()) {
			msg(ERR + "Your area is already at the maximum size.");
			return;
		}
		
		Transactions transactions = new Transactions(player);
		
		if (!transactions.canAfford(Transactions.AREA_EXPAND_PRICE)) {
			msg(ERR + "You need " + Transactions.AREA_EXPAND_PRICE + " " + Transactions.CURRENCY.toString().toLowerCase() + "(s) to do that.");
			return;
		}
		
		/* Create Area */
		transactions.pay(Transactions.AREA_EXPAND_PRICE);
		
	
		expandedArea.getAreaChunks().add(chunk);
		expandedArea.saveData();
		
		msg(SUCCESS + "Added chunk.");
	}
}
