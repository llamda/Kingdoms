package com.Kingdoms.Commands;

import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;

import com.Kingdoms.Teams.Clan;
import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.Clans;

public class ClanListCommand extends Command {

	/**
	 * Lists all Clans on the server
	 */
	public ClanListCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		Set<UUID> clans = Clans.getClans().keySet();

		String message = INFO_DARK + "Showing All Teams (" + INFO + "Total: " + clans.size() + INFO_DARK + ")\n";


		for (UUID uniqueId : clans) {

			// TODO show pages for more than 10 teams sorted by creation date

			Clan clan = Clans.getClanById(uniqueId);

			ChatColor c = clan.getColor();
			String tag = (clan.getTag() == null) ? "" : clan.getTag();


			/* Online and Total Player Count */
			int opc = clan.getOnlineMembers().size();
			int tpc = clan.getMembers().size();


			/* Team */
			message += c + "[" + tag + "] " + WHITE + clan.getName() + c + " [" + opc + "/" + tpc + "]\n";
		}
		msg(message);
	}

}
