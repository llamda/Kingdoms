package com.Kingdoms.Commands;

import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.ClanRank;

public class ClanRankCreateCommand extends Command {


	/**
	 * Create a new rank for the user's Clan
	 */
	public ClanRankCreateCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (argc < 2) {
			msg(USAGE + CLAN_RCREATE);
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

		String rankName = args[1];
		for (int i = 2; i < args.length; i++) {
			rankName += " " + args[i];
		}

		if (clan.getRankByName(rankName) != null) {
			msg(ERR + RANK_EXISTS);
			return;
		}

		int rank = clan.getRanks().size() + 1;

		clan.getRanks().add(new ClanRank(rank, rankName));
		clan.saveData();

		msg(SUCCESS + "Rank created!");
	}

}
