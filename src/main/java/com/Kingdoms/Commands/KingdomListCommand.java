package com.Kingdoms.Commands;

import java.util.Collection;

import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.Kingdom;
import com.Kingdoms.Teams.Kingdoms;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public class KingdomListCommand extends Command {

	public KingdomListCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		Collection<Kingdom> kingdoms = Kingdoms.getKingdoms().values();

		TextComponent.Builder message = Component.text()
				.append(Component.text("Showing All Kingdoms ", INFO_DARK))
				.append(wrap('(', "Total: " + kingdoms.size(), ')', INFO_DARK, INFO))
				.appendNewline();

		for (Kingdom k : kingdoms) {
			int opc = k.getOnlineMembers().size();
			int tpc = k.getMembers().size();
			message.append(Component.text(k.getName() + " [" + opc + "/" + tpc + "]", k.getColor()));
		}

		player.sendMessage(message.build());
	}

}
