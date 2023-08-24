package com.Kingdoms.Commands;

import com.Kingdoms.Teams.ClanPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class ClanChatCommand extends Command {

	/**
	 * Send a chat message to all players on only the user's Clan.
	 */
	public ClanChatCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (clan == null) {
			error(NEED_TEAM);
			return;
		}

		if (argc == 0) {
			usage(CLAN_CHAT);
			return;
		}

		/* Show coords on @loc message */
		StringBuilder words = new StringBuilder();
		if (args.length == 1 && args[0].equalsIgnoreCase("@LOC")) {
			words = new StringBuilder(" I am at X:" + (int) loc.getX() + " Y:" + (int) loc.getY() + " Z:" + (int) loc.getZ());
		}

		/* Otherwise show written message */
		else {
			for(int i = 0; i < argc; i++) {
				words.append(" ").append(args[i]);
			}
		}

		clan.sendPrefixedMessage(Component.text(name + ":", NamedTextColor.DARK_GREEN)
				.append(Component.text(words.toString(), NamedTextColor.GREEN)));
	}
}
