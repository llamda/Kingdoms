package com.Kingdoms.Commands;

import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.Clans;

public class ClanTagCommand extends Command {

	/**
	 * Sets the clan tag
	 */
	public ClanTagCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (argc != 2) {
			msg(USAGE + CLAN_TAG);
			return;
		}

		if (clanPlayer.getClan() == null) {
			msg(ERR + NEED_TEAM);
			return;
		}

		if (args[1].length() > 10) {
			msg(ERR + TAG_CHAR_LIMIT);
			return;
		}

		if (!rank.hasPermission("TAG")) {
			msg(ERR + NO_PERMISSION);
			return;
		}

		if (Clans.tagExists(args[1])) {
			msg(ERR + TAG_EXISTS);
			return;
		}

		clan.setTag(args[1]);
		clan.saveData();

		msg(SUCCESS + "Changed tag to " + clan.getColor() + "[" + args[1] + "]");
	}

}
