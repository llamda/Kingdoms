package com.Kingdoms.Commands;

import com.Kingdoms.KingdomsUtils;
import com.Kingdoms.Teams.ClanPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

import java.util.List;
import java.util.Random;

public class ClanColorCommand extends Command {

	/**
	 * Sets the Clan color
	 */
	public ClanColorCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (argc != 2) {
			usage(CLAN_COLOR);
			return;
		}

		if (args[1].equalsIgnoreCase("list")) {
			player.sendMessage(colors());
			return;
		}

		if (clan == null) {
			error(NEED_TEAM);
			return;
		}

		TextColor color = KingdomsUtils.stringToTextColor(args[1]);

		if (color == null) {
			error(UNKNOWN_COLOR);
			player.sendMessage(colors());
			return;
		}

		if (!rank.hasPermission("COLOR")) {
			error(NO_PERMISSION);
			return;
		}

		clan.setColor(color);
		clan.saveData();

		player.sendMessage(Component.text("Changed color to ", SUCCESS)
				.append(Component.text(args[1], color)));
	}

	public static TextComponent colors() {
		TextComponent.Builder colors = Component.text()
			.append(Component.text("Preset colors: ", NamedTextColor.GRAY));
		List.of(
				NamedTextColor.AQUA,
				NamedTextColor.BLACK,
				NamedTextColor.BLUE,
				NamedTextColor.DARK_AQUA,
				NamedTextColor.DARK_BLUE,
				NamedTextColor.DARK_GRAY,
				NamedTextColor.DARK_GREEN,
				NamedTextColor.DARK_PURPLE,
				NamedTextColor.DARK_RED,
				NamedTextColor.GOLD,
				NamedTextColor.GRAY,
				NamedTextColor.GREEN,
				NamedTextColor.LIGHT_PURPLE,
				NamedTextColor.RED,
				NamedTextColor.WHITE,
				NamedTextColor.YELLOW
		).forEach(c -> {
			String str = c.toString().toLowerCase();
			colors.append(Component.text(str, c).clickEvent(ClickEvent.suggestCommand("/team color " + str)))
					.append(Component.text(", ", NamedTextColor.GRAY));
		});

		colors.appendNewline().appendNewline().append(Component.text("Hex colors: ", NamedTextColor.GRAY));
		for (int i = 0; i < 12; i++) {
			colors.append(randomColor());
			colors.append(Component.text(", ", NamedTextColor.GRAY));
		}

		return colors.build();
	}

	private static TextComponent randomColor() {
		byte[] rgb = new byte[3];
		new Random().nextBytes(rgb);
		TextColor c = TextColor.color(rgb[0], rgb[1], rgb[2]);
		String hex = c.asHexString();

		return Component.text(hex, c).hoverEvent(Component.text(c.asHexString(), c))
				.clickEvent(ClickEvent.suggestCommand("/team color " + c.asHexString()));
	}

}
