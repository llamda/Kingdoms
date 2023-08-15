package com.Kingdoms.Commands;

import org.bukkit.ChatColor;

import com.Kingdoms.Teams.ClanPlayer;

public class ClanChatCommand extends Command {

	/** 
	 * Send a chat message to all players on only the user's Clan.
	 */
	public ClanChatCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);
		
		if (clan == null) {
			msg(ERR + NEED_TEAM);
			return;
		}
		
		if (argc == 0) {
			msg(USAGE + CLAN_CHAT);
			return;
		}
		
		/* Show coords on @loc message */
		String words = "";
		if (args.length == 1 && args[0].equalsIgnoreCase("@LOC")) {
			words = " I am at X:" + (int) loc.getX() + " Y:" + (int) loc.getY() + " Z:" + (int) loc.getZ();
		}	
		
		/* Otherwise show written message */
		else {
			for(int i = 0; i < argc; i++) {
				words += " " + args[i];
			}
		}	
		
		String message = name + ":" + ChatColor.GREEN + words;
		clan.sendMessage(message);
	}
}
