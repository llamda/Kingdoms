package com.kingdoms.commands;

import com.kingdoms.teams.ClanPlayer;
import com.kingdoms.teams.ClanRank;
import net.kyori.adventure.text.Component;

public class ClanRankSetCommand extends Command {

	/**
	 * Set a player on the user's team to a specific rank.
	 */
	public ClanRankSetCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (argc < 3) {
			usage(CLAN_RANK_SET);
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


		/* Attempt to find member to promote */
		ClanPlayer promoted = null;

		for (ClanPlayer member : clan.getMembers()) {

			if (member.getName().equals(args[1])) {
				promoted = member;
			}

		}

		if (promoted == null) {
			error(PLAYER_NOT_FOUND);
			return;
		}

		/* Get rank */
		StringBuilder rankName = new StringBuilder(args[2]);
		for (int i = 3; i < argc; i++) {
			rankName.append(" ").append(args[i]);
		}

		ClanRank newRank = clan.getRankByName(rankName.toString());

		if (newRank == null) {
			error(RANK_NOT_FOUND);
			return;
		}

		if (newRank.getRankNumber() < rank.getRankNumber()) {
			error(NO_PERMISSION);
			return;
		}

		if (promoted.getUuid().equals(clanPlayer.getUuid())) {
			error(NO_PERMISSION);
			return;
		}



		ClanRank oldRank = promoted.getRank();

		oldRank.getPlayers().remove(promoted.getUuid());
		newRank.getPlayers().add(promoted.getUuid());
		clan.saveData();

		/* Promotion / Demotion message */
		boolean wasPromoted = oldRank.getRankNumber() > newRank.getRankNumber();

		if (wasPromoted) {
			clan.sendPrefixedMessage(Component.text(promoted.getName() + " has been promoted to " + newRank.getTitle(), SUCCESS));
		}

		else {
			clan.sendPrefixedMessage(Component.text(promoted.getName() + " has been demoted to " + newRank.getTitle(), ERR));
		}
	}

}
