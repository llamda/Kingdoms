package com.Kingdoms.Commands;

import com.Kingdoms.Teams.ClanPlayer;

public class ClanLeaveCommand extends Command {

	/**
	 * Removes user from Clan
	 */
	public ClanLeaveCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);
	
		if (clan== null) {
			msg(ERR + NEED_TEAM);
			return;
		}
		
		if (rank.getRankNumber() == 1 && rank.getPlayers().size() < 2) {
			msg(ERR + MUST_APPOINT_NEW);
			msg (USAGE + CLAN_RSET);
			return;
		}
		
		// TODO fix ranks
		clan.removeMember(clanPlayer);
		msg(SUCCESS + "You left the team.");
		
		clan.sendMessage(ERR_DARK + name + ERR + " left the team.");
	}

}
