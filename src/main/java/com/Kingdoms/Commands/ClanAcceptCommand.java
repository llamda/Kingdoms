package com.Kingdoms.Commands;

import com.Kingdoms.Teams.Clan;
import com.Kingdoms.Teams.ClanPlayer;

public class ClanAcceptCommand extends Command {

	/**
	 * Accepts a Clan invite
	 */
	public ClanAcceptCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (clanPlayer.getInvite() == null) {
			msg(ERR + NO_INVITE);
			return;
		}

		if (!clanPlayer.getInvite().exists()) {
			msg(ERR + TEAM_NOT_FOUND);
			clanPlayer.setInvite(null);
			return;
		}

		Clan invitation = clanPlayer.getInvite();

		clanPlayer.setClan(invitation);
		clanPlayer.setInvite(null);
		invitation.addMember(clanPlayer);

		invitation.sendMessage(name + SUCCESS + " joined the team.");
	}

}
