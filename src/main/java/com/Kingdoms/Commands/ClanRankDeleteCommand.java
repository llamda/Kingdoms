package com.Kingdoms.Commands;

import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.ClanRank;

public class ClanRankDeleteCommand extends Command {

	/**
	 * Delete a rank on the user's Clan. Rank must not have any players.
	 */
	public ClanRankDeleteCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (argc < 2) {
			usage(CLAN_RANK_DELETE);
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

		ClanRank deleted = clan.getRankByName(rankName.toString());

		if (deleted == null) {
			error(RANK_NOT_FOUND);
			return;
		}

		if (!deleted.getPlayers().isEmpty()) {
			error(NON_EMPTY_RANK);
			return;
		}

		/* Delete rank */
		clan.getTeamConfig().set("Ranks", null);
		clan.getRanks().remove(deleted);



		/* Update existing rank numbers */
		int rankNumber = deleted.getRankNumber();

		for (ClanRank clanRank : clan.getRanks()) {

			if (clanRank.getRankNumber() > rankNumber) {
				clanRank.setRankNumber(clanRank.getRankNumber() - 1);
			}
		}

		clan.saveData();

		success("Rank deleted.");
	}

}
