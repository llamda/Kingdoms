package com.Kingdoms.Commands;

import com.Kingdoms.AreaUpgrade;
import com.Kingdoms.Teams.ClanPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;


public class ClanAreaUpgradesCommand extends Command {

	public ClanAreaUpgradesCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		TextComponent.Builder message = Component.text()
				.appendNewline()
				.append(wrap('[', "upgrade", ']', INFO_DARK, INFO))
				.appendSpace()
				.append(wrap('(', "price in gold blocks", ')', INFO_DARK, INFO));

		for (AreaUpgrade upgrade : AreaUpgrade.values()) {
			message.appendNewline()
					.append(wrap('[', upgrade.name().toLowerCase(), ']', INFO_DARK, INFO))
					.appendSpace()
					.append(wrap('(', String.valueOf(upgrade.getCost()), ')', INFO_DARK, INFO))
					.appendSpace()
					.append(Component.text(upgrade.getInfo()));
		}

		player.sendMessage(message.build());
	}

}
