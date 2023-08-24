package com.Kingdoms.Commands;

import com.Kingdoms.Teams.ClanPlayer;

public class ClanRankRenameCommand extends Command {

	/**
	 * Rename the given rank on the user's Clan.
	 */
	public ClanRankRenameCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (argc < 3) {
			usage(CLAN_RANK_RENAME);
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

		/* Get rank number */
		int renamedRankNumber;
		try {
			renamedRankNumber = Integer.parseInt(args[1]);
		} catch(NumberFormatException e) {
			usage(CLAN_RANK_RENAME);
			return;
		}

		if (renamedRankNumber < 1 || renamedRankNumber > clan.getRanks().size()) {
			error(RANK_NOT_FOUND);
			return;
		}

		/* Get new rank name */
		StringBuilder newName = new StringBuilder(args[2]);
		for (int i = 3; i < argc; i++) {
			newName.append(" ").append(args[i]);
		}

		clan.getClanRankByNumber(renamedRankNumber).setTitle(newName.toString());
		clan.saveData();

		success("Rank renamed.");
	}

}
