package com.Kingdoms.Commands;

import org.bukkit.ChatColor;

import com.Kingdoms.Teams.Clan;
import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.Kingdom;
import com.Kingdoms.Teams.Kingdoms;

public class KingdomInfoCommand extends Command {

	public KingdomInfoCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		Kingdom target = null;
		if (argc > 1) {

			String words = args[1];
			for (int i = 2; i < args.length; i++) {
				words += " " + args[i];
			}
			target = Kingdoms.getKingdomByName(words);
		} else {
			target = kingdom;
		}

		if (target == null) {
			clanPlayer.sendMessage(ERR + KINGDOM_NOT_FOUND);
			return;
		}

		String side = target.getColor() + "═══" + ChatColor.RESET;
		String info = side + "   " + target.getName() + "   " + side;
		for (Clan clan : target.getMemberClans()) {
			ChatColor color = clan.getColor();
			String tag = (clan.getTag() == null) ? "" : clan.getTag();

			int onlinePlayers = clan.getOnlineMembers().size();
			int totalPlayers = clan.getMembers().size();

			info += "\n   " + color + tag + " " + ChatColor.WHITE + clan.getName() + color + " [" + onlinePlayers + "/" + totalPlayers + "]";
		}
		clanPlayer.sendMessage(info);
	}

}
