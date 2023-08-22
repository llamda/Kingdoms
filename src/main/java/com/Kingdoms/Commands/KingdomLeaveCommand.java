package com.Kingdoms.Commands;

import com.Kingdoms.Teams.ClanPlayer;

public class KingdomLeaveCommand extends Command {

	/**
	 * Leave a Kingdom
	 * @param clanPlayer user
	 */
	public KingdomLeaveCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (kingdom == null) {
			clanPlayer.sendMessage(NEED_KINGDOM);
			return;
		}

		if (clan.isKingdomLeader()) {
			clanPlayer.sendMessage(ERR + MUST_APPOINT_NEW_KINGDOM);
			return;
		}

		if (!rank.hasPermission("KINGDOM")) {
			clanPlayer.sendMessage(ERR + NO_PERMISSION);
			return;
		}

		kingdom.sendMessage(ERR_DARK + clan.getName() + ERR + " left the Kingdom.");
		kingdom.removeMember(clan);
	}

}
