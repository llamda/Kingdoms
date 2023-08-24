package com.kingdoms;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.kingdoms.commands.Commands;
import com.kingdoms.teams.ClanPlayer;
import org.jetbrains.annotations.NotNull;

public class CommandListener implements CommandExecutor {

	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String commandLabel, String[] args) {

		String command = cmd.getName().toLowerCase();

		if (!(sender instanceof Player player)) {
			sender.sendMessage("You must be a player to do that.");
			return true;
		}

		ClanPlayer clanPlayer = Connections.getClanPlayer(player.getUniqueId());

		switch (command) {
			case "team" -> Commands.newClanCommand(clanPlayer, args);
			case "t" -> Commands.newClanChatCommand(clanPlayer, args);
			case "kingdom" -> Commands.newKingdomCommand(clanPlayer, args);
			case "k" -> Commands.newKingdomChatCommand(clanPlayer, args);
			default -> {
				return false;
			}
		}

		return true;
	}
}