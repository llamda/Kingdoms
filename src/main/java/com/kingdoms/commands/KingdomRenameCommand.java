package com.kingdoms.commands;

import com.kingdoms.teams.ClanPlayer;
import com.kingdoms.teams.Kingdom;
import com.kingdoms.teams.Kingdoms;
import net.kyori.adventure.text.Component;

public class KingdomRenameCommand extends Command {

	public KingdomRenameCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (argc < 2) {
			usage(KINGDOM_RENAME);
			return;
		}

		if (clanPlayer.getKingdom() == null) {
			error(NEED_KINGDOM);
			return;
		}

		if (!rank.hasPermission("KINGDOM")) {
			error(NO_PERMISSION);
			return;
		}

		if (!clan.isKingdomLeader()) {
			error(NO_PERMISSION);
			return;
		}

		StringBuilder name = new StringBuilder(args[1]);
		for (int i = 2; i < args.length; i++) {
			name.append(" ").append(args[i]);
		}

		if (Kingdoms.nameExists(name.toString())) {
			error(KINGDOM_EXISTS);
			return;
		}

		Kingdom kingdom = clanPlayer.getKingdom();
		kingdom.setName(name.toString());
		kingdom.saveData();
		kingdom.sendExactMessage(Component.text("Kingdom name changed to: \"", SUCCESS)
				.append(Component.text(name.toString(), kingdom.getColor()))
				.append(Component.text("\"", SUCCESS)));
	}

}
