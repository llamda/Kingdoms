package com.Kingdoms.Commands;

import java.util.List;

import com.Kingdoms.Area;
import com.Kingdoms.Areas;
import com.Kingdoms.Transactions;
import com.Kingdoms.Teams.ClanPlayer;

public class ClanAreaCreateCommand extends Command {
	
	/**
	 * Creates a team area in chunk the user is standing in
	 */
	public ClanAreaCreateCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);
		
		if (argc < 3) {
			msg(USAGE + CLAN_ACREATE);
			return;
		}
		
		if (clan == null) {
			msg(ERR + NEED_TEAM);
			return;
		}
		
		if (!rank.hasPermission("AREA")) {
			msg(ERR + NO_PERMISSION);
			return;
		}
		
		
		if (clan.getAreas().size() >= 1) {
			msg(ERR + "You can not make any more areas.");
			return;
		}
		
		List<Area> adjacentAreas = Areas.getNearbyAreas(chunk);
		
		if (adjacentAreas.size() != 0) {
			msg(ERR + NEARBY_AREA);
			return;
		}
		
		Transactions transactions = new Transactions(player);
		
		if (!transactions.canAfford(Transactions.AREA_CREATE_PRICE)) {
			msg(ERR + "You need " + Transactions.AREA_CREATE_PRICE + " " + Transactions.CURRENCY.toString().toLowerCase() + "(s) to do that.");
			return;
		}
		
		String areaName = args[2];		
		for (int i = 3; i < argc; i++) {
			areaName += " " + args[i];
		}
		
		if (Areas.getAreaByName(areaName) != null) {
			msg(ERR + AREA_EXISTS);
			return;
		}
		
		
		/* Create Area */
		transactions.pay(Transactions.AREA_CREATE_PRICE);

		Area area = new Area(chunk, areaName);
		Areas.getAreas().put(area.getUuid(), area);
		
		clan.getAreas().add(area.getUuid());
		clan.saveData();
		
		msg(SUCCESS + "Created Area!");
	}

}
