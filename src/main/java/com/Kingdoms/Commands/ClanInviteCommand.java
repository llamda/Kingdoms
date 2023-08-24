package com.Kingdoms.Commands;

import com.Kingdoms.Connections;
import com.Kingdoms.Teams.ClanPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class ClanInviteCommand extends Command {

	/**
	 * Invites a new player to the user's team.
	 */
	public ClanInviteCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (argc != 2) {
			usage(CLAN_INVITE);
			return;
		}

		if (clan == null) {
			error(NEED_TEAM);
			return;
		}

		if (!rank.hasPermission("INVITE")) {
			error(NO_PERMISSION);
			return;
		}

		if (!isOnline(args[1])) {
			error(PLAYER_NOT_FOUND);
			return;
		}

		ClanPlayer invited = Connections.getClanPlayer(getUniqueId(args[1]));
		if (invited.getClan() != null) {
			error(PLAYER_HAS_TEAM);
			return;
		}

		if (invited.getInvite() == clan) {
			error(ALREADY_INVITED);
			return;
		}

		/* Set Player as Invited */
		invited.setInvite(clan);

		invited.sendMessage(Component.text("You have been invited to ", SUCCESS)
				.append(Component.text(clan.getName(), clan.getColor()))
				.append(Component.text("\nType \"" + CLAN_ACCEPT + "\" to join!")));

		clan.sendPrefixedMessage(Component.text(name, NamedTextColor.DARK_GREEN)
				.append(Component.text(" invited", SUCCESS))
				.append(Component.text(" " + invited.getName(), SUCCESS_DARK)));
	}

}
