package com.kingdoms.commands;

import com.kingdoms.teams.ClanPlayer;
import net.kyori.adventure.text.Component;

public class KingdomLeaveCommand extends Command {

	/**
	 * Leave a Kingdom
	 * @param clanPlayer user
	 */
	public KingdomLeaveCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (kingdom == null) {
			error(NEED_KINGDOM);
			return;
		}

		if (clan.isKingdomLeader()) {
			error(MUST_APPOINT_NEW_KINGDOM);
			return;
		}

		if (!rank.hasPermission("KINGDOM")) {
			error(NO_PERMISSION);
			return;
		}

		kingdom.sendPrefixedMessage(Component.text(clan.getName(), ERR_DARK)
				.append(Component.text(" left the Kingdom.", ERR)));

		kingdom.removeMember(clan);
	}

}
