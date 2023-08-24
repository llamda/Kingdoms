package com.kingdoms.commands;

import com.kingdoms.teams.Clan;
import com.kingdoms.teams.ClanPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class ClanAcceptCommand extends Command {

	/**
	 * Accepts a Clan invite
	 */
	public ClanAcceptCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (clanPlayer.getInvite() == null) {
			error(NO_INVITE);
			return;
		}

		if (!clanPlayer.getInvite().exists()) {
			error(TEAM_NOT_FOUND);
			clanPlayer.setInvite(null);
			return;
		}

		Clan invitation = clanPlayer.getInvite();

		clanPlayer.setClan(invitation);
		clanPlayer.setInvite(null);
		invitation.addMember(clanPlayer);

		invitation.sendPrefixedMessage(Component.text(name, NamedTextColor.DARK_GREEN)
				.append(Component.text(" joined the team.", SUCCESS)));
	}

}
