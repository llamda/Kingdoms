package com.Kingdoms.Commands;

import org.bukkit.ChatColor;

import com.Kingdoms.KingdomsUtils;
import com.Kingdoms.Teams.ClanPlayer;

public class ClanColorCommand extends Command {
	
	/**
	 * Sets the Clan color
	 */
	public ClanColorCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);
		
		if (argc != 2) {
			msg(USAGE + CLAN_COLOR);
			return;
		}
		
		if (args[1].toLowerCase().equals("list")) {
			msg(INFO + COLORS);
			return;
		}
		
		if (clan == null) {
			msg(ERR + NEED_TEAM);
			return;
		}
		
		ChatColor color = KingdomsUtils.stringToChatColor(args[1], true);
		
		if (color == null) {
			msg(ERR + UNKNOWN_COLOR + COLORS);
			return;
		}
		
		if (!rank.hasPermission("COLOR")) {
			msg(ERR + NO_PERMISSION);
			return;
		}
		
		clan.setColor(color);
		clan.saveData();
		
		msg(SUCCESS + "Changed color to " + color + color.name().toLowerCase());	
	}

}
