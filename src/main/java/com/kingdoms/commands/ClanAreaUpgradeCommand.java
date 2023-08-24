package com.kingdoms.commands;

import com.kingdoms.Area;
import com.kingdoms.AreaUpgrade;
import com.kingdoms.Areas;
import com.kingdoms.teams.ClanPlayer;
import com.kingdoms.Transactions;

public class ClanAreaUpgradeCommand extends Command {

	public ClanAreaUpgradeCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (argc != 3) {
			usage(CLAN_AREA_UPGRADE);
			return;
		}

		String upgradeString = args[2].toUpperCase();
		AreaUpgrade upgrade = null;
		for (AreaUpgrade u : AreaUpgrade.values()) {
			if (u.toString().equals(upgradeString)) {
				upgrade = u;
			}
		}

		if (upgrade == null) {
			error(UNKNOWN_UPGRADE);
			return;
		}

		Area area  = Areas.getChunkOwner(chunk);

		if (area == null) {
			error(MUST_BE_INSIDE_AREA);
			return;
		}

		if (clan == null) {
			error(NEED_TEAM);
			return;
		}

		if (!clan.getAreas().contains(area.getUuid())) {
			error(NO_PERMISSION);
			return;
		}

		if (!rank.hasPermission("AREA")) {
			error(NO_PERMISSION);
			return;
		}

		if (area.hasAreaUpgrade(upgrade)) {
			error(ALREADY_UPGRADED);
			return;
		}

		Transactions transactions = new Transactions(player);
		if (transactions.canNotAfford(upgrade.getCost())) {
			error(transactions.canNotAffordString(upgrade.getCost()));
			return;
		}

		transactions.pay(upgrade.getCost());
		area.addUpgrade(upgrade);

		clan.sendPrefixedString(area.getAreaName() + " now has the \"" + upgrade.toString().toLowerCase() + "\" upgrade.");
	}

}
