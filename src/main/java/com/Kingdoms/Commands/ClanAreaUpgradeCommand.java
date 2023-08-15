package com.Kingdoms.Commands;

import com.Kingdoms.Area;
import com.Kingdoms.AreaUpgrade;
import com.Kingdoms.Areas;
import com.Kingdoms.Transactions;
import com.Kingdoms.Teams.ClanPlayer;

public class ClanAreaUpgradeCommand extends Command {

	public ClanAreaUpgradeCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);
		
		if (argc != 3) {
			msg(USAGE + CLAN_AUPGRADE);
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
			msg(ERR + UNKNOWN_UPGRADE);
			return;
		}
		
		Area area  = Areas.getChunkOwner(chunk);
		
		if (area == null) {
			msg(ERR + MUST_BE_INSIDE_AREA);
			return;
		}
		
		if (clan == null) {
			msg(ERR + NEED_TEAM);
			return;
		}
		
		if (!clan.getAreas().contains(area.getUuid())) {
			msg(ERR + NO_PERMISSION);
			return;
		}
		
		if (!rank.hasPermission("AREA")) {
			msg(ERR + NO_PERMISSION);
			return;
		}
		
		if (area.hasAreaUpgrade(upgrade)) {
			msg(ERR + ALREADY_UPGRADED);
			return;
		}
		
		Transactions transactions = new Transactions(player);
		if (!transactions.canAfford(upgrade.getCost())) {
			msg(ERR + transactions.canNotAffordString(upgrade.getCost()));
			return;
		}
		
		transactions.pay(upgrade.getCost());
		area.addUpgrade(upgrade);
		
		clan.sendMessage(area.getAreaName() + " now has the \"" + upgrade.toString().toLowerCase() + "\" upgrade.");
	}

}
