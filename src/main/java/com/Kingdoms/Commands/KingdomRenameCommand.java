package com.Kingdoms.Commands;

import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.Kingdom;
import com.Kingdoms.Teams.Kingdoms;

public class KingdomRenameCommand extends Command {

	public KingdomRenameCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);
		
		if (argc < 2) {
			clanPlayer.sendMessage(USAGE + KINGDOM_RENAME);
			return;
		}

		if (clanPlayer.getKingdom() == null) {
			clanPlayer.sendMessage(ERR + NEED_KINGDOM);
			return;
		}

		if (!rank.hasPermission("KINGDOM")) {
			clanPlayer.sendMessage(ERR + NO_PERMISSION);
			return;
		}

		if (!clan.isKingdomLeader()) {
			clanPlayer.sendMessage(ERR + NO_PERMISSION);
			return;
		}

		String name = args[1];
		for (int i = 2; i < args.length; i++) {
			name += " " + args[i];
		}

		if (Kingdoms.nameExists(name)) {
			clanPlayer.sendMessage(ERR + KINGDOM_EXISTS);
			return;
		}

		Kingdom kingdom = clanPlayer.getKingdom();
		kingdom.setName(name);
		kingdom.saveData();
		kingdom.sendExactMessage(SUCCESS + "Kingdom name changed to: \"" + kingdom.getColor() + name + SUCCESS + "\"");
	}

}
