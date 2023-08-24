package com.kingdoms.commands;

import com.kingdoms.teams.ClanPlayer;
import com.kingdoms.teams.ClanRank;

public class ClanRankPermissionCommand extends Command {

	/**
	 * Set permission on the given rank
	 */
	public ClanRankPermissionCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);


		if (argc != 4) {
			usage(CLAN_RANK_PERM);
			return;
		}

		if (clan == null) {
			error(NEED_TEAM);
			return;
		}

		if (!rank.hasPermission("RANKEDIT")) {
			error(NO_PERMISSION);
			return;
		}


		/* Get rank */
		int rankNumber;
		try {
			rankNumber = Integer.parseInt(args[1]);
		} catch(NumberFormatException e) {
			usage(CLAN_RANK_PERM);
			return;
		}

		if (rankNumber < 1 || rankNumber > clan.getRanks().size()) {
			error(RANK_NOT_FOUND);
			return;
		}

		if (rankNumber == 1) {
			error(NO_PERMISSION);
			return;
		}

		ClanRank changed = clan.getClanRankByNumber(rankNumber);


		/* Get permission */
		String permission = args[2].toUpperCase();

		if (!ClanRank.isValidPermission(permission)) {
			error(PERM_NOT_FOUND);
			return;
		}


		/* Get new value */
		String stringValue = args[3].toUpperCase();
		if (!stringValue.equals("TRUE") && !stringValue.equals("FALSE")) {
			error(NON_BOOLEAN);
			return;
		}

		boolean value = Boolean.parseBoolean(stringValue);

		if (value) {
			changed.getPermissions().add(permission);
		}

		else {
			changed.getPermissions().remove(permission);
		}

		clan.saveData();

		success("Permissions updated.");
	}

}
