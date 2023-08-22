package com.Kingdoms.Commands;

import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.ClanRank;

public class ClanRankPermissionsCommand extends Command {

	/**
	 * Message user all valid rank permissions
	 */
	public ClanRankPermissionsCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		String permissions = INFO_DARK + "Valid Permissions:\n";
		boolean first = false;

		for (String permission : ClanRank.permissionList) {

			if (first) {
				permissions += INFO_DARK + ",";
			}

			first = true;
			permissions += INFO + " " + permission.toLowerCase();
		}

		msg(permissions);
	}

}
