package com.Kingdoms.Commands;

import java.util.UUID;

import org.bukkit.ChatColor;

import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.ClanRank;

public class ClanRankInfoCommand extends Command {

	/**
	 * Show user info on the given rank number
	 */
	public ClanRankInfoCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (argc < 2) {
			msg(ERR + CLAN_RINFO);
			return;
		}

		if (clan == null) {
			msg(ERR + NEED_TEAM);
			return;
		}

		String rankName = args[1];
		for (int i = 2; i < argc; i++) {
			rankName += " " + args[i];
		}

		ClanRank info = clan.getRankByName(rankName);

		if (info == null) {
			msg(ERR + RANK_NOT_FOUND);
			return;
		}

		ChatColor c = clan.getColor();


		/* Info header */
		msg(c + "--- " + WHITE + info.getTitle() + c + " ---");


		/* Permissions */
		String permissions = c + "Permissions: ";

		for (String p : ClanRank.permissionList) {

			if (info.hasPermission(p)) {
				permissions += WHITE;
			}

			else {
				permissions += INFO_DARK + "" + STRIKETHROUGH;
			}

			permissions += p.toLowerCase() + RESET + " ";
		}

		msg(permissions);


		/* Players */
		String playerList = c + "Players:";

		for (UUID uniqueId : info.getPlayers()) {

			if (isOnline(uniqueId)) {
				playerList += WHITE;
			}

			else {
				playerList+= INFO;
			}

			playerList += " " + getName(uniqueId);
		}

		msg(playerList);
	}

}
