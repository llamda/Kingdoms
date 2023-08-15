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
			msg(USAGE + CLAN_RMM);
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
		
		
		/* Rank from */
		int rankNumberFrom = 0;
		try {
			rankNumberFrom = Integer.valueOf(args[1]);
		} catch(NumberFormatException e) {
			msg(USAGE + CLAN_RMM);
			return;
		}
		
		if (rankNumberFrom < 1 || rankNumberFrom > clan.getRanks().size()) {
			msg(USAGE + CLAN_RMM);
			return;
		}
		
		
		/* Rank to */
		int rankNumberTo = 0;
		try {
			rankNumberTo = Integer.valueOf(args[2]);
		} catch(NumberFormatException e) {
			msg(USAGE + CLAN_RMM);
			return;
		}
		
		if (rankNumberTo < 1 || rankNumberTo > clan.getRanks().size()) {
			msg(ERR + "Rank does not exist.");
			return;
		}
		
		ClanRank rankFrom = clan.getClanRankByNumber(rankNumberFrom);
		ClanRank rankTo = clan.getClanRankByNumber(rankNumberTo);
		
		
		/* Avoid changing own rank */
		if (rankFrom.getPlayers().contains(clanPlayer.getUuid())) {
			msg(ERR + NO_PERMISSION);
			return;
		}
		
		rankTo.getPlayers().addAll(rankFrom.getPlayers());
		rankFrom.getPlayers().clear();
		clanPlayer.getClan().saveData();
		
		msg(SUCCESS + "Ranks changed.");
		
		// TODO add promotion/demotion messages (?)
	}

}
