package com.Kingdoms.Commands;

import com.Kingdoms.Teams.ClanPlayer;

public class ClanDisbandCommand extends Command {

	public ClanDisbandCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (clan == null) {
			msg(ERR + NEED_TEAM);
			return;
		}

		if (!rank.hasPermission("DISBAND")) {
			msg(ERR + NO_PERMISSION);
			return;
		}

		if (kingdom != null && kingdom.getLeader() == clan && kingdom.getMemberClans().size() > 1) {
			clanPlayer.sendMessage(ERR + IN_KINGDOM);
			return;
		}

		clan.disband();
		broadcast(ERR + " * " + ERR_DARK + clan.getName() + ERR + " has been disbanded.");
	}

}
