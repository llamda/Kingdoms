package com.Kingdoms.Commands;

import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.ClanRank;

public class ClanRankSetCommand extends Command {

	/**
	 * Set a player on the user's team to a specific rank.
	 */
	public ClanRankSetCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (argc < 3) {
			msg(USAGE + CLAN_RSET);
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


		/* Attempt to find member to promote */
		ClanPlayer promoted = null;

		for (ClanPlayer member : clan.getMembers()) {

			if (member.getName().equals(args[1])) {
				promoted = member;
			}

		}

		if (promoted == null) {
			msg(ERR + PLAYER_NOT_FOUND);
			return;
		}

		/* Get rank */
		String rankName = args[2];
		for (int i = 3; i < argc; i++) {
			rankName += " " + args[i];
		}

		ClanRank newRank = clan.getRankByName(rankName);

		if (newRank == null) {
			msg(ERR + RANK_NOT_FOUND);
			return;
		}

		if (newRank.getRankNumber() < rank.getRankNumber()) {
			msg(ERR + NO_PERMISSION);
			return;
		}

		if (promoted.getUuid().equals(clanPlayer.getUuid())) {
			msg(ERR + NO_PERMISSION);
			return;
		}



		ClanRank oldRank = promoted.getRank();

		oldRank.getPlayers().remove(promoted.getUuid());
		newRank.getPlayers().add(promoted.getUuid());
		clan.saveData();

		/* Promotion / Demotion message */
		boolean wasPromoted = (oldRank.getRankNumber() > newRank.getRankNumber()) ? true : false;

		if (wasPromoted) {
			clan.sendMessage(SUCCESS + promoted.getName() + " has been promoted to " + newRank.getTitle());
		}

		else {
			clan.sendMessage(ERR + promoted.getName() + " has been demoted to " + newRank.getTitle());
		}
	}

}
