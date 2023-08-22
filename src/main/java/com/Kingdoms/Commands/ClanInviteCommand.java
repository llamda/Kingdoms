package com.Kingdoms.Commands;

import com.Kingdoms.Connections;
import com.Kingdoms.Teams.ClanPlayer;

public class ClanInviteCommand extends Command {

	/**
	 * Invites a new player to the user's team.
	 */
	public ClanInviteCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (argc != 2) {
			msg(USAGE + CLAN_INVITE);
			return;
		}

		if (clan == null) {
			msg(ERR + NEED_TEAM);
			return;
		}

		if (!rank.hasPermission("INVITE")) {
			msg(ERR + NO_PERMISSION);
			return;
		}

		if (!isOnline(args[1])) {
			msg(ERR + PLAYER_NOT_FOUND);
			return;
		}

		ClanPlayer invited = Connections.getClanPlayer(getUniqueId(args[1]));
		if (invited.getClan() != null) {
			msg(ERR + PLAYER_HAS_TEAM);
			return;
		}

		if (invited.getInvite() == clan) {
			msg(ERR + ALREADY_INVITED);
			return;
		}

		/* Set Player as Invited*/
		invited.setInvite(clan);
		invited.sendMessage(SUCCESS + "You have been invited to " + clan.getColor() + clan.getName());
		invited.sendMessage(SUCCESS + "Type \"" + CLAN_ACCEPT + "\" to join!");

		clan.sendMessage(name + SUCCESS + " invited " + SUCCESS_DARK + invited.getName());
	}

}
