package com.kingdoms.commands;

import com.kingdoms.teams.Clan;
import com.kingdoms.teams.ClanPlayer;
import com.kingdoms.teams.Clans;
import com.kingdoms.teams.Kingdom;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class KingdomAcceptCommand extends Command {

	/**
	 * Accept an invitation to a Kingdom
	 * @param clanPlayer user who will accept
	 */
	public KingdomAcceptCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (clan == null) {
			error(NEED_TEAM);
			return;
		}

		if (!rank.hasPermission("KINGDOM")) {
			error(NO_PERMISSION);
			return;
		}

		if (clan.getKingdomInvite() == null) {
			error(NO_INVITE);
			return;
		}

		Clan kingdomLeader = Clans.getClans().get(clan.getKingdomInvite());
		if (kingdomLeader == null) {
			error(TEAM_NOT_FOUND);
			clan.setKingdomInvite(null);
			return;
		}

		/* Create new Kingdom */
		if (kingdomLeader.getKingdom() == null) {
			new Kingdom(kingdomLeader, clan);
		}

		/* Join current Kingdom */
		else {
			kingdomLeader.getKingdom().addClanMember(clan);
			kingdomLeader.getKingdom().saveData();
		}

		TextComponent message = Component.text(" * ", NamedTextColor.AQUA)
				.append(Component.text(clan.getName(), clan.getColor()))
				.append(Component.text(" has joined the kingdom.", NamedTextColor.AQUA));

		kingdomLeader.getKingdom().sendExactMessage(message);
		clan.setKingdomInvite(null);
	}

}
