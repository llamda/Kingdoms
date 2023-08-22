package com.Kingdoms.Commands;

import org.bukkit.ChatColor;

import com.Kingdoms.Teams.ClanPlayer;

public class KingdomChatCommand extends Command {

	public KingdomChatCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (kingdom == null) {
			clanPlayer.sendMessage(ERR + NEED_KINGDOM);
			return;
		}

		if (argc == 0) {
			clanPlayer.sendMessage(USAGE + KINGDOM_CHAT);
			return;
		}

		/* Show coords on @loc message */
		String words = "";
		if (args.length == 1 && args[0].equalsIgnoreCase("@LOC")) {
			words = " I am at X:" + (int) loc.getX() + " Y:" + (int) loc.getY() + " Z:" + (int) loc.getZ();
		}

		// Otherwise send combined message
		else {
			for (int i = 0; i < args.length; i++) {
				words += " " + args[i];
			}
		}

		String message = name + ":" + ChatColor.AQUA + words;
		kingdom.sendMessage(message);
	}

}
