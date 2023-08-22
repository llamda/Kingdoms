package com.Kingdoms.Commands;

import com.Kingdoms.Teams.Clan;
import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.Clans;

public class KingdomKickCommand extends Command {

	public KingdomKickCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (argc != 2) {
			msg(USAGE + KINGDOM_KICK);
			return;
		}

		if (kingdom == null) {
			msg(ERR + NEED_KINGDOM);
			return;
		}

		if (!rank.hasPermission("KICK") || !clan.isKingdomLeader()) {
			msg(ERR + NO_PERMISSION);
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
			msg(ERR + TEAM_NOT_FOUND);
			return;
		}

		if (target.getKingdom() != kingdom) {
			msg (ERR + TEAM_NOT_FOUND);
			return;
		}

		kingdom.sendMessage(ERR_DARK + target.getName() + ERR + " has been kicked from the kingdom.");
		kingdom.removeMember(target);
	}

}
