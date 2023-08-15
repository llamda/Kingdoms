package com.Kingdoms.Commands;

import org.bukkit.ChatColor;

import com.Kingdoms.Teams.Clan;
import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.Clans;
import com.Kingdoms.Teams.Kingdom;

public class KingdomAcceptCommand extends Command {

	/**
	 * Accept an invite to a Kingdom
	 * @param clanPlayer user who will accept
	 */
	public KingdomAcceptCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		if (clan == null) {
			clanPlayer.sendMessage(ERR + NEED_TEAM);
			return;
		}

		if (!rank.hasPermission("KINGDOM")) {
			clanPlayer.sendMessage(ERR + NO_PERMISSION);
			return;
		}

		if (clan.getKingdomInvite() == null) {
			clanPlayer.sendMessage(ERR + NO_INVITE);
			return;
		}

		Clan kingdomLeader = Clans.getClans().get(clan.getKingdomInvite());
		if (kingdomLeader == null) {
			clanPlayer.sendMessage(ERR + TEAM_NOT_FOUND);
			clan.setKingdomInvite(null);
			return;
		}

		/* Create new Kingdom */
		if (kingdomLeader.getKingdom() == null) {
			new Kingdom(kingdomLeader, clan);
		}

		/* Join current Kingdom */
		else {
			kingdomLeader.getKingdom().addClanMember(clanPlayer.getClan());
			kingdomLeader.getKingdom().saveData();
		}
		kingdomLeader.getKingdom().sendExactMessage(ChatColor.AQUA + " * " + clanPlayer.getClan().getColor() + clanPlayer.getClan().getName() + ChatColor.AQUA + " has joined the kingdom.");
		clan.setKingdomInvite(null);
	}

}
