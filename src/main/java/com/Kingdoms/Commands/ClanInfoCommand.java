package com.Kingdoms.Commands;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;

import com.Kingdoms.Area;
import com.Kingdoms.Areas;
import com.Kingdoms.Teams.Clan;
import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.ClanRank;
import com.Kingdoms.Teams.Clans;

public class ClanInfoCommand extends Command {

	/**
	 * Shows info about user's clan if no arguments, otherwise attempt to show info about the given team.
	 */
	public ClanInfoCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		Clan target;

		/* If no args give own team data */
		if (argc < 2) {

			if (clan == null) {
				msg(ERR + NEED_TEAM);
				msg(USAGE + CLAN_INFO);
				return;
			}
			target = clan;
		}

		/* Otherwise search for team */
		else {

			String check = args[1];

			for (int i = 2; i < argc; i++) {
				check += " " + args[i];
			}

			if (Clans.tagExists(check)) {
				target = Clans.getClanByTag(check);
			}

			else if (Clans.clanExists(check)) {
				target = Clans.getClanByName(check);
			}

			else {
				msg(ERR + "Could not find team.");
				return;
			}
		}

		showInfo(target);
	}

	private void showInfo(Clan clan) {

		ChatColor c = clan.getColor();

		String tag;
		if ((tag = clan.getTag()) == null) {
			tag = "";
		}

		/* Online and Total Player Counts */
		int opc = clan.getOnlineMembers().size();
		int tpc = clan.getMembers().size();

		/* General Info */
		msg(c + "[" + tag + "] " + WHITE + clan.getName() + c + " [" + opc + "/" + tpc + "]");

		/* Area Info */
		boolean showCoords = (clanPlayer.getClan() == clan && rank.hasPermission("AREA_INFO")) ? true : false;
		String areas = c + "Areas:\n";
		List<UUID> areaList = clan.getAreas();
		for (UUID uuid : areaList) {

			Area area = Areas.getAreas().get(uuid);
			String coords = (showCoords) ? area.getFormattedAreaCoordinates() : area.getHiddenAreaCoordinates();
			areas += c + "  [" + WHITE + area.getAreaName() + c + "] (" + WHITE + coords + c + ")\n";
		}

		if (areaList.size() > 0) {
			msg(areas);
		}

		/* Rank Info */
		for (int i = 1, n = clan.getRanks().size(); i <= n; i++) {

			ClanRank rank = clan.getClanRankByNumber(i);

			msg(c + "" + i + ". " + rank.getTitle() + ":");

			String rankMembers = " ";
			Set<UUID> players = rank.getPlayers();

			if (players.size() <= 0) {
				continue;
			}

			for (UUID uniqueId : players) {

				ChatColor isOnline = (isOnline(uniqueId)) ? ChatColor.WHITE : ChatColor.GRAY;
				rankMembers += " " + isOnline + getName(uniqueId);
			}

			msg(rankMembers);
		}
	}

}
