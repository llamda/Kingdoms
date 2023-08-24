package com.kingdoms.commands;

import com.kingdoms.teams.Clan;
import com.kingdoms.teams.ClanPlayer;
import com.kingdoms.teams.Clans;
import net.kyori.adventure.text.Component;

public class KingdomKickCommand extends Command {

	public KingdomKickCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (argc != 2) {
			usage(KINGDOM_KICK);
			return;
		}

		if (kingdom == null) {
			error(NEED_KINGDOM);
			return;
		}

		if (!rank.hasPermission("KICK") || !clan.isKingdomLeader()) {
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

		if (target.getKingdom() != kingdom) {
			error(TEAM_NOT_FOUND);
			return;
		}

		if (target.isKingdomLeader()) {
			error(MUST_APPOINT_NEW_KINGDOM);
			return;
		}

		kingdom.sendPrefixedMessage(Component.text(target.getName(), ERR_DARK)
				.append(Component.text(" has been kicked from the Kingdom.", ERR)));

		kingdom.removeMember(target);
	}

}
