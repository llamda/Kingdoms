package com.kingdoms.commands;

import com.kingdoms.teams.ClanPlayer;
import com.kingdoms.teams.ClanRank;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.UUID;

public class ClanRankInfoCommand extends Command {

	/**
	 * Show user info on the given rank number
	 */
	public ClanRankInfoCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (argc < 2) {
			error(CLAN_RANK_INFO);
			return;
		}

		if (clan == null) {
			error(NEED_TEAM);
			return;
		}

		StringBuilder rankName = new StringBuilder(args[1]);
		for (int i = 2; i < argc; i++) {
			rankName.append(" ").append(args[i]);
		}

		ClanRank info = clan.getRankByName(rankName.toString());

		if (info == null) {
			error(RANK_NOT_FOUND);
			return;
		}

		TextComponent.Builder message = Component.text();
		TextColor c = clan.getColor();


		/* Info header */
		message.append(Component.text("--- ", c))
				.append(Component.text(info.getTitle()))
				.append(Component.text(" ---", c));

		/* Permissions */
		message.appendNewline().append(Component.text("Permissions:", c));

		Style permission = Style.style(INFO_DARK, TextDecoration.STRIKETHROUGH);

		for (String p : ClanRank.permissionList) {
			message.appendSpace()
					.append(Component.text(p.toLowerCase(), info.hasPermission(p) ? Style.empty() : permission));
		}

		/* Players */
		message.appendNewline().append(Component.text("Players:", c));

		for (UUID uniqueId : info.getPlayers()) {
			message.appendSpace().append(Component.text(getName(uniqueId), isOnline(uniqueId) ? WHITE : INFO));
		}

		player.sendMessage(message.build());
	}

}
