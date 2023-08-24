package com.kingdoms.commands;

import com.kingdoms.Kingdoms;
import com.kingdoms.teams.ClanPlayer;
import net.kyori.adventure.text.Component;

public class ClanDisbandCommand extends Command {

	public ClanDisbandCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (clan == null) {
			error(NEED_TEAM);
			return;
		}

		if (!rank.hasPermission("DISBAND")) {
			error(NO_PERMISSION);
			return;
		}

		if (kingdom != null && kingdom.getLeader() == clan && kingdom.getMemberClans().size() > 1) {
			error(IN_KINGDOM);
			return;
		}

		clan.disband();

		Kingdoms.instance.getServer().broadcast(Component.text(" * ", ERR)
				.append(Component.text(clan.getName(), ERR_DARK))
				.append(Component.text(" has been disbanded.", ERR)));
	}

}
