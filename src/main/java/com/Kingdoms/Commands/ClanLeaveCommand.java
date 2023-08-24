package com.Kingdoms.Commands;

import com.Kingdoms.Teams.ClanPlayer;
import net.kyori.adventure.text.Component;

public class ClanLeaveCommand extends Command {

	/**
	 * Removes user from Clan
	 */
	public ClanLeaveCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (clan== null) {
			error(NEED_TEAM);
			return;
		}

		if (rank.getRankNumber() == 1 && rank.getPlayers().size() < 2) {
			error(MUST_APPOINT_NEW);
			usage(CLAN_RANK_SET);
			return;
		}

		// TODO fix ranks
		clan.removeMember(clanPlayer);
		success("You left the team.");

		clan.sendPrefixedMessage(Component.text(name, ERR_DARK)
				.append(Component.text(" left the team.", ERR)));
	}

}
