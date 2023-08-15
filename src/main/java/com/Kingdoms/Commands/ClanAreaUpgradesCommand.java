package com.Kingdoms.Commands;

import com.Kingdoms.AreaUpgrade;
import com.Kingdoms.Teams.ClanPlayer;


public class ClanAreaUpgradesCommand extends Command {

	public ClanAreaUpgradesCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);
		
		String message = INFO_DARK + "\n["+ INFO + "upgrade" + INFO_DARK + "] (" + INFO + "price in gold blocks" + INFO_DARK + ")";
		for (AreaUpgrade upgrade : AreaUpgrade.values()) {
			message += INFO_DARK + "\n["+ INFO + upgrade.name().toLowerCase() + INFO_DARK + "] (" + INFO + upgrade.getCost() + INFO_DARK + ") " + WHITE + upgrade.getInfo();
		}
		msg(message);
	}

}
