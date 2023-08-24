package com.Kingdoms.Commands;

import com.Kingdoms.Area;
import com.Kingdoms.Areas;
import com.Kingdoms.Teams.Clan;
import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.ClanRank;
import com.Kingdoms.Teams.Clans;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

import java.util.List;
import java.util.UUID;

public class ClanInfoCommand extends Command {

	/**
	 * Shows info about user's clan if no arguments, otherwise attempt to show info about the given team.
	 */
	public ClanInfoCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		Clan target;

		/* If no args give own team data */
		if (argc < 2) {

			if (clan == null) {
				error(NEED_TEAM);
				usage(CLAN_INFO);
				return;
			}
			target = clan;
		}

		/* Otherwise search for team */
		else {

			StringBuilder check = new StringBuilder(args[1]);

			for (int i = 2; i < argc; i++) {
				check.append(" ").append(args[i]);
			}

			if (Clans.tagExists(check.toString())) {
				target = Clans.getClanByTag(check.toString());
			}

			else if (Clans.clanExists(check.toString())) {
				target = Clans.getClanByName(check.toString());
			}

			else {
				error("Could not find team.");
				return;
			}
		}

		if (target != null) {
			showInfo(target);
		}
	}

	private void showInfo(Clan clan) {

		TextColor c = clan.getColor();

		String tag;
		if ((tag = clan.getTag()) == null) {
			tag = "";
		}

		/* Online and Total Player Counts */
		int opc = clan.getOnlineMembers().size();
		int tpc = clan.getMembers().size();

		/* General Info */
		TextComponent.Builder info = Component.text()
				.append(wrap('[', tag, ']', c))
				.appendSpace()
				.append(Component.text(clan.getName()))
				.appendSpace()
				.append(wrap('[', opc + "/" + tpc, ']', c))
				.appendNewline();

		/* Area Info */
		List<UUID> areaList = clan.getAreas();
		if (!areaList.isEmpty()) {
			boolean showCoords = clanPlayer.getClan() == clan && rank.hasPermission("AREA_INFO");
			info.append(Component.text("Areas:", c));

			for (UUID uuid : areaList) {
				Area area = Areas.getAreas().get(uuid);
				String coords = (showCoords) ? area.getFormattedAreaCoordinates() : area.getHiddenAreaCoordinates();

				info.appendNewline()
						.append(Component.text("  "))
						.append(wrap('[', area.getAreaName(), ']', c))
						.appendSpace()
						.append(wrap('(', coords, ')', c));

			}
		}

		/* Rank Info */
		for (int i = 1, n = clan.getRanks().size(); i <= n; i++) {
			ClanRank rank = clan.getClanRankByNumber(i);
			info.appendNewline().append(Component.text(i + ". " + rank.getTitle() + ":", c));

			var players = rank.getPlayers();
			if (players.isEmpty()) continue;

			info.appendNewline();
			rank.getPlayers().forEach(id -> info
					.appendSpace()
					.append(Component.text(" " + getName(id), isOnline(id) ? WHITE : INFO)));
		}

		player.sendMessage(info.build());
	}

}
