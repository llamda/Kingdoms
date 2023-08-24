package com.Kingdoms.Commands;

import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.ClanRank;

public class ClanRankMassMoveCommand extends Command {

	/**
	 * Move all players inside a rank to a different rank on user's Clan.
	 */
	public ClanRankMassMoveCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (argc != 3) {
			usage(CLAN_RMM);
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


		/* Rank from */
		int rankNumberFrom;
		try {
			rankNumberFrom = Integer.parseInt(args[1]);
		} catch(NumberFormatException e) {
			usage(CLAN_RMM);
			return;
		}

		if (rankNumberFrom < 1 || rankNumberFrom > clan.getRanks().size()) {
			usage(CLAN_RMM);
			return;
		}


		/* Rank to */
		int rankNumberTo;
		try {
			rankNumberTo = Integer.parseInt(args[2]);
		} catch(NumberFormatException e) {
			usage(CLAN_RMM);
			return;
		}

		if (rankNumberTo < 1 || rankNumberTo > clan.getRanks().size()) {
			error("Rank does not exist.");
			return;
		}

		ClanRank rankFrom = clan.getClanRankByNumber(rankNumberFrom);
		ClanRank rankTo = clan.getClanRankByNumber(rankNumberTo);


		/* Avoid changing own rank */
		if (rankFrom.getPlayers().contains(clanPlayer.getUuid())) {
			error(NO_PERMISSION);
			return;
		}

		rankTo.getPlayers().addAll(rankFrom.getPlayers());
		rankFrom.getPlayers().clear();
		clanPlayer.getClan().saveData();

		success("Ranks changed.");

		// TODO add promotion/demotion messages (?)
	}

}
