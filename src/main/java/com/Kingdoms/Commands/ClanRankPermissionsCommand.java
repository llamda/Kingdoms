package com.Kingdoms.Commands;

import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.ClanRank;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public class ClanRankPermissionsCommand extends Command {

	/**
	 * Message user all valid rank permissions
	 */
	public ClanRankPermissionsCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		TextComponent.Builder permissions = Component.text().append(Component.text("Valid Permissions:\n", INFO_DARK));
		boolean first = false;

		for (String permission : ClanRank.permissionList) {

			if (first) {
				permissions.append(Component.text(", ", INFO_DARK));
			}

			first = true;
			permissions.append(Component.text(" " + permission.toLowerCase(), INFO));
		}

		player.sendMessage(permissions.build());
	}

}
