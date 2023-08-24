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
			usage(CLAN_RANK_CREATE);
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

		StringBuilder rankName = new StringBuilder(args[1]);
		for (int i = 2; i < args.length; i++) {
			rankName.append(" ").append(args[i]);
		}

		if (clan.getRankByName(rankName.toString()) != null) {
			error(RANK_EXISTS);
			return;
		}

		int rank = clan.getRanks().size() + 1;

		clan.getRanks().add(new ClanRank(rank, rankName.toString()));
		clan.saveData();

		success("Rank created!");
	}

}
