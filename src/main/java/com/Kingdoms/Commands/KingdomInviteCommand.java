package com.Kingdoms.Commands;

import org.bukkit.ChatColor;

import com.Kingdoms.Teams.Clan;
import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.Clans;

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
			clanPlayer.sendMessage(USAGE + KINGDOM_INVITE);
			return;
		}

		if (clan == null) {
			clanPlayer.sendMessage(ERR + NEED_TEAM);
			return;
		}

		if (!rank.hasPermission("KINGDOM")) {
			clanPlayer.sendMessage(ERR + NO_PERMISSION);
			return;
		}

		/* Check for Clan with given args as tag or name */
		Clan target;
		String check = args[1];
		for (int i = 2; i < args.length; i++)
			check += " " + args[i];

		if (Clans.tagExists(check)) {
			target = Clans.getClanByTag(check);
		}

		else if (Clans.clanExists(check)) {
			target = Clans.getClanByName(check);
		}

		else {
			clanPlayer.sendMessage(ERR + TEAM_NOT_FOUND);
			return;
		}

		/* Check if team can be invited */
		if (target.getKingdom() != null) {
			clanPlayer.sendMessage(ERR + TEAM_HAS_KINGDOM);
			return;
		}

		if (target.getKingdomInvite() == clan.getUuid()) {
			clanPlayer.sendMessage(ERR + ALREADY_INVITED_KINGDOM);
			return;
		}

		/* Create new Kingdom */
		if (kingdom == null) {
			if (target == clanPlayer.getClan()) {
				clanPlayer.sendMessage(ERR + CANNOT_INVITE_SELF);
				return;
			}
			clan.sendExactMessage(ChatColor.AQUA + " * " + target.getColor() + target.getName() + ChatColor.AQUA + " has been invited to your kingdom.");
			target.sendExactMessage(ChatColor.AQUA + " * " + clanPlayer.getClan().getColor() + clanPlayer.getClan().getName() + ChatColor.AQUA + " has invited you to join their kingdom.\nTo accept do /kingdom accept");
			target.setKingdomInvite(clanPlayer.getClan().getUuid());
		}

		/* Invite to current Kingdom */
		else {
			if (!clan.isKingdomLeader()) {
				clanPlayer.sendMessage(ERR + NO_PERMISSION);
				return;
			}

			kingdom.sendExactMessage(ChatColor.AQUA + " * " + target.getColor() + target.getName() + ChatColor.AQUA + " has been invited to the Kingdom.");
			target.sendExactMessage(ChatColor.AQUA + " * " + clanPlayer.getKingdom().getColor() + clanPlayer.getKingdom().getName() + ChatColor.AQUA + " has invited you to join their Kingdom.\nTo accept do /kingdom accept");
			target.setKingdomInvite(clanPlayer.getClan().getUuid());
		}
	}
}
