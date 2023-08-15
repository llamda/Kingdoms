package com.Kingdoms.Commands;

import com.Kingdoms.Teams.ClanPlayer;

public class ClanRankRenameCommand extends Command {

	/**
	 * Rename the given rank on the user's Clan.
	 */
	public ClanRankRenameCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);
		
		if (argc < 3) {
			msg(USAGE + CLAN_RRENAME);
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
		
		/* Get rank number */
		int renamedRankNumber;
		try {
			renamedRankNumber = Integer.valueOf(args[1]);
		} catch(NumberFormatException e) {
			msg(USAGE + CLAN_RRENAME);
			return;
		}
		
		if (renamedRankNumber < 1 || renamedRankNumber > clan.getRanks().size()) {
			msg(ERR + RANK_NOT_FOUND);
			return;
		}
		
		/* Get new rank name */
		String newName = args[2];
		for (int i = 3; i < argc; i++) {
			newName += " " + args[i];
		}
	
		clan.getClanRankByNumber(renamedRankNumber).setTitle(newName);
		clan.saveData();
		
		msg(SUCCESS + "Rank renamed.");
	}

}
