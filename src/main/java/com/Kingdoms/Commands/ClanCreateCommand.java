package com.Kingdoms.Commands;

import com.Kingdoms.Teams.Clan;
import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.Clans;
import com.Kingdoms.Teams.Kingdoms;


public class ClanCreateCommand extends Command {

	/**
	 * Creates a new Clan
	 */
	public ClanCreateCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (argc < 2) {
			usage(CLAN_CREATE);
			return;
		}

		if (clan != null) {
			error(IN_TEAM);
			return;
		}

		StringBuilder name = new StringBuilder(args[1]);
		for (int i = 2; i < argc; i++) {
			name.append(" ").append(args[i]);
		}

		if (Clans.clanExists(name.toString())) {
			error(TEAM_EXISTS);
			return;
		}

		if (Kingdoms.nameExists(name.toString())) {
			error(KINGDOM_EXISTS);
			return;
		}

		new Clan(clanPlayer, name.toString());
		success("Team Created \"" + name + "\"");
	}

}
