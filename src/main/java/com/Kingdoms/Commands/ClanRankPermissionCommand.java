package com.Kingdoms.Commands;

import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.ClanRank;

public class ClanRankPermissionCommand extends Command {

	/**
	 * Set permission on the given rank
	 */
	public ClanRankPermissionCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);
		
		
		if (argc != 4) {
			msg(USAGE + CLAN_RPERM);
			return;
		}
		
		if (clan == null) {
			msg(ERR + NEED_TEAM);
			return;
		}
		
		if (!rank.hasPermission("RANKEDIT")) {
			msg(ERR + NO_PERMISSION);
			return;
		}
		
		
		/* Get rank */
		int rankNumber;
		try {
			rankNumber = Integer.valueOf(args[1]);
		} catch(NumberFormatException e) {
			msg(USAGE + CLAN_RPERM);
			return;
		}
		
		if (rankNumber < 1 || rankNumber > clan.getRanks().size()) {
			msg(ERR + RANK_NOT_FOUND);
			return;
		}
		
		if (rankNumber == 1) {
			msg(ERR + NO_PERMISSION);
			return;
		}
		
		ClanRank changed = clan.getClanRankByNumber(rankNumber);
		
		
		/* Get permission */
		String permission = args[2].toUpperCase();
		
		if (!ClanRank.isValidPermission(permission)) {
			msg(ERR + PERM_NOT_FOUND);
			return;
		}
		
		
		/* Get new value */
		String stringValue = args[3].toUpperCase();
		if (!stringValue.equals("TRUE") && !stringValue.equals("FALSE")) {
			msg(ERR + NON_BOOLEAN);
			return;
		}
		
		boolean value = Boolean.valueOf(stringValue);
		
		if (value) {
			changed.getPermissions().add(permission);
		}
		
		else {
			changed.getPermissions().remove(permission);
		}
		
		clan.saveData();
		
		msg(SUCCESS + "Permissions updated.");
	}

}
