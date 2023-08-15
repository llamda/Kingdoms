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
			msg(USAGE + CLAN_CREATE);
			return;
		}
		
		if (clan != null) {
			msg(ERR + IN_TEAM);
			return;
		}
		
		String name = args[1];
		for (int i = 2; i < argc; i++) {
			name += " " + args[i];
		}
		
		if (Clans.clanExists(name)) {
			msg(ERR + TEAM_EXISTS);
			return;
		}
		
		if (Kingdoms.nameExists(name)) {
			msg(ERR + KINGDOM_EXISTS);
			return;
		}
		
		new Clan(clanPlayer, name);
		msg(SUCCESS + "Team Created \"" + name + "\"");
	}

}
