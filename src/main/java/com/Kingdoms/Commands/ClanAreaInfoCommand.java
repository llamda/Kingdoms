package com.Kingdoms.Commands;

import com.Kingdoms.Area;
import com.Kingdoms.AreaUpgrade;
import com.Kingdoms.Areas;
import com.Kingdoms.Teams.Clan;
import com.Kingdoms.Teams.ClanPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.Optional;

public class ClanAreaInfoCommand extends Command {

	public ClanAreaInfoCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		Area area;
		if (argc < 3) {
			area = Areas.getChunkOwner(chunk);
		} else {
			StringBuilder areaName = new StringBuilder(args[2]);
			for (int i = 3; i < args.length; i++) {
				areaName.append(" ").append(args[i]);
			}
			area = Areas.getAreaByName(areaName.toString());
		}

		if (area == null) {
			error(AREA_NOT_FOUND);
			return;
		}

		if (clan == null || !clan.isAreaOwner(area.getUuid()) || !rank.hasPermission("AREA_INFO")) {
			error(NO_PERMISSION);
			return;
		}

		String coords = area.getFormattedAreaCoordinates();

		TextColor c = Optional.ofNullable(Areas.getClanOwner(area)).map(Clan::getColor).orElse(NamedTextColor.GRAY);
		TextComponent.Builder message = Component.text()
				.append(wrap('[', area.getAreaName(), ']', c))
				.appendSpace()
				.append(wrap('(', coords, ')', c))
				.appendNewline()
				.append(Component.text("  Total Chunks: (", c))
				.append(Component.text(area.getAreaChunks().size()))
				.append(Component.text("/", c))
				.append(Component.text(area.getMaxAreaSize()))
				.append(Component.text(')', c))
				.appendNewline()
				.append(Component.text("  Active Upgrades:", c));

		Style unowned = Style.style(NamedTextColor.DARK_GRAY, TextDecoration.STRIKETHROUGH);
		for (AreaUpgrade upgrade : AreaUpgrade.values()) {
			message.appendSpace().append(Component.text(
					upgrade.toString().toLowerCase(),
					area.hasAreaUpgrade(upgrade) ? Style.empty() : unowned));
		}

		player.sendMessage(message.build());
	}

}
