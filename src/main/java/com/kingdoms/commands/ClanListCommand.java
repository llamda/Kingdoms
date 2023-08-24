package com.kingdoms.commands;

import com.kingdoms.teams.Clan;
import com.kingdoms.teams.ClanPlayer;
import com.kingdoms.teams.Clans;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

import java.util.Set;
import java.util.UUID;

public class ClanListCommand extends Command {

	/**
	 * Lists all Clans on the server
	 */
	public ClanListCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		Set<UUID> clans = Clans.getClans().keySet();
		TextComponent.Builder message = Component.text()
				.append(Component.text("Showing All Teams ", INFO_DARK))
				.append(wrap('(', "Total: " + clans.size(), ')', INFO_DARK, INFO))
				.appendNewline();

		for (UUID uniqueId : clans) {
			// TODO show pages for more than 10 teams sorted by creation date

			Clan clan = Clans.getClanById(uniqueId);

			TextColor c = clan.getColor();
			String tag = (clan.getTag() == null) ? "" : clan.getTag();

			/* Online and Total Player Count */
			int opc = clan.getOnlineMembers().size();
			int tpc = clan.getMembers().size();


			/* Team */
			message.append(wrap('[', tag, ']', c))
					.appendSpace()
					.append(Component.text(clan.getName()))
					.append(Component.text(" [" + opc + "/" + tpc + "]\n", c));
		}

		player.sendMessage(message.build());
	}

}
