package com.Kingdoms.Commands;

import com.Kingdoms.Teams.ClanPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class KingdomChatCommand extends Command {

	public KingdomChatCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (kingdom == null) {
			error(NEED_KINGDOM);
			return;
		}

		if (argc == 0) {
			usage(KINGDOM_CHAT);
			return;
		}

		/* Show coords on @loc message */
		StringBuilder words = new StringBuilder();
		if (args.length == 1 && args[0].equalsIgnoreCase("@LOC")) {
			words = new StringBuilder(" I am at X:" + (int) loc.getX() + " Y:" + (int) loc.getY() + " Z:" + (int) loc.getZ());
		}

		// Otherwise send combined message
		else {
			for (String arg : args) {
				words.append(" ").append(arg);
			}
		}

		kingdom.sendPrefixedMessage(Component.text(name + ":", NamedTextColor.DARK_AQUA)
				.append(Component.text(words.toString(), NamedTextColor.AQUA)));
	}

}
