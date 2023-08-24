package com.kingdoms.commands;

import java.util.List;

import com.kingdoms.Area;
import com.kingdoms.Areas;
import com.kingdoms.Transactions;
import com.kingdoms.teams.ClanPlayer;

public class ClanAreaCreateCommand extends Command {

	/**
	 * Creates a team area in chunk the user is standing in
	 */
	public ClanAreaCreateCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (argc < 3) {
			usage(CLAN_AREA_CREATE);
			return;
		}

		if (clan == null) {
			error(NEED_TEAM);
			return;
		}

		if (!rank.hasPermission("AREA")) {
			error(NO_PERMISSION);
			return;
		}


		if (!clan.getAreas().isEmpty()) {
			error("You can not make any more areas.");
			return;
		}

		List<Area> adjacentAreas = Areas.getNearbyAreas(chunk);

		if (!adjacentAreas.isEmpty()) {
			error(NEARBY_AREA);
			return;
		}

		Transactions transactions = new Transactions(player);

		if (transactions.canNotAfford(Transactions.AREA_CREATE_PRICE)) {
			error("You need " + Transactions.AREA_CREATE_PRICE + " " + Transactions.CURRENCY.toString().toLowerCase() + "(s) to do that.");
			return;
		}

		StringBuilder areaName = new StringBuilder(args[2]);
		for (int i = 3; i < argc; i++) {
			areaName.append(" ").append(args[i]);
		}

		if (Areas.getAreaByName(areaName.toString()) != null) {
			error(AREA_EXISTS);
			return;
		}


		/* Create Area */
		transactions.pay(Transactions.AREA_CREATE_PRICE);

		Area area = new Area(chunk, areaName.toString());
		Areas.getAreas().put(area.getUuid(), area);

		clan.getAreas().add(area.getUuid());
		clan.saveData();

		success("Created Area!");
	}

}
