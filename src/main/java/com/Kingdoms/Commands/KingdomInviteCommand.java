package com.Kingdoms.Commands;

import com.Kingdoms.Teams.Clan;
import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.Clans;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class KingdomInviteCommand extends Command {


	/**
	 * Invites a new Clan to the user's Kingdom.
	 * @param clanPlayer user
	 * @param args name of Clan or Clan Tag to invite
	 */
	public KingdomInviteCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		/* Check if user can invite team */
		if (argc < 2) {
			usage(KINGDOM_INVITE);
			return;
		}

		if (clan == null) {
			error(NEED_TEAM);
			return;
		}

		if (!rank.hasPermission("KINGDOM")) {
			error(NO_PERMISSION);
			return;
		}

		/* Check for Clan with given args as tag or name */
		Clan target;
		StringBuilder check = new StringBuilder(args[1]);
		for (int i = 2; i < args.length; i++)
			check.append(" ").append(args[i]);

		if (Clans.tagExists(check.toString())) {
			target = Clans.getClanByTag(check.toString());
		}

		else if (Clans.clanExists(check.toString())) {
			target = Clans.getClanByName(check.toString());
		}

		else {
			error(TEAM_NOT_FOUND);
			return;
		}

		if (target == null) {
			error(TEAM_NOT_FOUND);
			return;
		}

		/* Check if team can be invited */
		if (target.getKingdom() != null) {
			error(TEAM_HAS_KINGDOM);
			return;
		}

		if (target.getKingdomInvite() == clan.getUuid()) {
			error(ALREADY_INVITED_KINGDOM);
			return;
		}

		/* Create new Kingdom */
		if (kingdom == null) {
			if (target == clanPlayer.getClan()) {
				error(CANNOT_INVITE_SELF);
				return;
			}
		}

		/* Invite to current Kingdom */
		else {
			if (!clan.isKingdomLeader()) {
				error(NO_PERMISSION);
				return;
			}

		}
		clan.sendExactMessage(invitation(target,
				" has been invited to your kingdom."));
		target.sendExactMessage(invitation(clanPlayer.getClan(),
				" has invited you to join their kingdom.\nTo accept do /kingdom accept"));
		target.setKingdomInvite(clanPlayer.getClan().getUuid());
	}

	private static TextComponent invitation(Clan clan, String message) {
		return Component.text(" * ", NamedTextColor.AQUA)
				.append(Component.text(clan.getName(), clan.getColor()))
				.append(Component.text(message, NamedTextColor.AQUA));
	}
}
