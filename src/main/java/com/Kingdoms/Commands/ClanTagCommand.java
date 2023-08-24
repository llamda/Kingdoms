package com.Kingdoms.Commands;

import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.Clans;
import net.kyori.adventure.text.Component;

public class ClanTagCommand extends Command {

	/**
	 * Sets the clan tag
	 */
	public ClanTagCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (argc != 2) {
			usage(CLAN_TAG);
			return;
		}

		if (clanPlayer.getClan() == null) {
			error(NEED_TEAM);
			return;
		}

		if (args[1].length() > 10) {
			error(TAG_CHAR_LIMIT);
			return;
		}

		if (!rank.hasPermission("TAG")) {
			error(NO_PERMISSION);
			return;
		}

		if (Clans.tagExists(args[1])) {
			error(TAG_EXISTS);
			return;
		}

		clan.setTag(args[1]);
		clan.saveData();

		player.sendMessage(Component.text("Changed tag to ", SUCCESS)
				.append(wrap('[', args[1], ']', clan.getColor())));
	}
}
