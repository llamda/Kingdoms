package com.Kingdoms.Commands;

import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.Kingdom;
import com.Kingdoms.Teams.Kingdoms;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

import java.util.Optional;

public class KingdomInfoCommand extends Command {

	public KingdomInfoCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		Kingdom target;
		if (argc > 1) {

			StringBuilder words = new StringBuilder(args[1]);
			for (int i = 2; i < args.length; i++) {
				words.append(" ").append(args[i]);
			}
			target = Kingdoms.getKingdomByName(words.toString());
		} else {
			target = kingdom;
		}

		if (target == null) {
			error(KINGDOM_NOT_FOUND);
			return;
		}

		TextComponent side = Component.text("\u2550\u2550", target.getColor());
		TextComponent.Builder info = Component.text()
				.append(side)
				.append(Component.text("   " + target.getName() + "   "))
				.append(side);

		target.getMemberClans().forEach(clan -> {
			TextColor color = clan.getColor();
			String tag = Optional.ofNullable(clan.getTag()).orElse("");
			int online = clan.getOnlineMembers().size();
			int total = clan.getMembers().size();

			info.appendNewline()
					.append(Component.text("    "))
					.append(Component.text(tag, color))
					.appendSpace()
					.append(Component.text(clan.getName()))
					.append(Component.text(" [" + online + "/" + total + "]", color));
		});

		player.sendMessage(info.build());
	}

}
