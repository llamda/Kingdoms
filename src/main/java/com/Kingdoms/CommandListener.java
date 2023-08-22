package com.Kingdoms;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Kingdoms.Commands.Commands;
import com.Kingdoms.Teams.ClanPlayer;

public class CommandListener implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		String command = cmd.getName().toLowerCase();

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to do that.");
			return true;
		}

		Player player = (Player) sender;
		ClanPlayer clanPlayer = Connections.getClanPlayer(player.getUniqueId());

		switch(command) {
			case"team": 	Commands.newClanCommand(clanPlayer, args); 			break;
			case"t":		Commands.newClanChatCommand(clanPlayer, args);		break;
			case"kingdom": 	Commands.newKingdomCommand(clanPlayer, args); 		break;
			case"k":		Commands.newKingdomChatCommand(clanPlayer, args);	break;
			default:	return false;
		}

		return true;
	}
}