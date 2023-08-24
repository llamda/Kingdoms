package com.Kingdoms.Commands;

import com.Kingdoms.Teams.ClanPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public class ClanHelpCommand extends Command {

	public static final int PAGES = Page.values().length;
	public final static String CLAN_HELP = "/team help [1-" + PAGES + "]";

	/**
	 * Shows help pages to user
	 */
	public ClanHelpCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		Page page;

		/* Give page 1 unless page number is given */
		int n;
		if (argc < 2) {
			page = Page.GENERAL;
			n = 1;
		}
		else {

			try {
				n = Integer.parseInt(args[1]);
			} catch(NumberFormatException e) {
				n = 1;
			}

			if (n < 1 || n > PAGES) {
				n = 1;
			}
			page = Page.values()[n - 1];
		}

		TextComponent.Builder info = Component.text()
				.append(Component.text("Kingdoms Help [" + n + "/" + PAGES + "]", INFO_DARK))
				.append(Component.text("\n  " + switch (page) {
			case GENERAL ->
					CLAN_HELP + "\n  " +
					CLAN_CREATE + "\n  " +
					CLAN_TAG + "\n  " +
					CLAN_COLOR + "\n  " +
					CLAN_INVITE + "\n  " +
					CLAN_INFO + "\n  " +
					CLAN_LIST + "\n  " +
					CLAN_CHAT;
			case DELETE ->
					CLAN_DISBAND + "\n  " +
					CLAN_KICK + "\n  " +
					CLAN_LEAVE;
			case RANKS ->
					CLAN_RANK_CREATE + "\n  " +
							CLAN_RANK_DELETE + "\n  " +
							CLAN_RANK_RENAME + "\n  " +
							CLAN_RANK_SET + "\n  " +
							CLAN_RANK_PERM + "\n  " +
							CLAN_RANK_PERMS + "\n  " +
							CLAN_RANK_INFO + "\n  " +
					CLAN_RMM;
			case AREA ->
					CLAN_AREA_CREATE + "\n  " +
							CLAN_AREA_EXPAND + "\n  " +
							CLAN_AREA_INFO + "\n  " +
							CLAN_AREA_MAP + "\n  " +
							CLAN_AREA_UPGRADE + "\n  " +
							CLAN_AREA_UPGRADES;
			case KINGDOM ->
					KINGDOM_INVITE + "\n  " +
					KINGDOM_ACCEPT + "\n  " +
					KINGDOM_RENAME + "\n  " +
					KINGDOM_LEAVE + "\n  " +
					KINGDOM_KICK + "\n  " +
					KINGDOM_INFO + "\n  " +
					KINGDOM_LIST + "\n  " +
					KINGDOM_CHAT;
		}, INFO));

		player.sendMessage(info.build());
	}

	private enum Page {
		GENERAL,
		DELETE,
		RANKS,
		AREA,
		KINGDOM
	}
}
