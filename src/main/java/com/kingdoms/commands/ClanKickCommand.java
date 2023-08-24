package com.kingdoms.commands;

import com.kingdoms.teams.ClanPlayer;
import net.kyori.adventure.text.Component;

/**
 * Kicks a player from the user's Clan
 */
public class ClanKickCommand extends Command {

	public ClanKickCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (argc != 2) {
			usage(CLAN_KICK);
			return;
		}

		if (clan == null) {
			error(NEED_TEAM);
			return;
		}

		if (!rank.hasPermission("KICK")) {
			error(NO_PERMISSION);
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
			error(PLAYER_NOT_FOUND);
			return;
		}

		if (rank.getRankNumber() > kickedPlayer.getRank().getRankNumber()) {
			error(NO_PERMISSION);
			return;
		}

		/* Kick player */
		clan.removeMember(kickedPlayer);

		if (isOnline(kickedPlayer.getUuid())) {
			kickedPlayer.sendMessage(Component.text( "You have been kicked from the team.", ERR));
			kickedPlayer.setClan(null);
		}

		clan.sendPrefixedMessage(Component.text(kickedPlayer.getName(), ERR_DARK)
				.append(Component.text(" has been kicked from the team.", ERR)));
	}

}
