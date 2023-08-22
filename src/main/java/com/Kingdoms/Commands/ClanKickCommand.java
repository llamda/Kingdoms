package com.Kingdoms.Commands;

import com.Kingdoms.Teams.ClanPlayer;

/**
 * Kicks a player from the user's Clan
 */
public class ClanKickCommand extends Command {

	public ClanKickCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (argc != 2) {
			msg(USAGE + CLAN_KICK);
			return;
		}

		if (clan == null) {
			msg(ERR + NEED_TEAM);
			return;
		}

		if (!rank.hasPermission("KICK")) {
			msg(ERR + NO_PERMISSION);
			return;
		}

		ClanPlayer kickedPlayer = null;

		/* Attempt to find member to kick */
		for (ClanPlayer member : clan.getMembers()) {

			if (member.getName().equals(args[1])) {
				kickedPlayer = member;
			}

		}

		if (kickedPlayer == null) {
			msg(ERR + PLAYER_NOT_FOUND);
			return;
		}

		if (rank.getRankNumber() > kickedPlayer.getRank().getRankNumber()) {
			msg (ERR + NO_PERMISSION);
			return;
		}

		/* Kick player */
		clan.removeMember(kickedPlayer);

		if (isOnline(kickedPlayer.getUuid())) {
			kickedPlayer.sendMessage(ERR + "You have been kicked from the team.");
			kickedPlayer.setClan(null);
		}

		clan.sendMessage(ERR_DARK + kickedPlayer.getName() + ERR + " has been kicked from the team.");
	}

}
