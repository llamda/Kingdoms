package com.Kingdoms;

import java.util.UUID;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import com.Kingdoms.Teams.Clan;
import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.Clans;
import com.Kingdoms.Teams.Kingdom;

public class KingdomCommands {

	private final static ChatColor err = ChatColor.RED;
	private final static ChatColor success = ChatColor.GREEN;
	// private final static ChatColor info = ChatColor.GRAY;
	private final static ChatColor white = ChatColor.WHITE;

	public static void newClanCommand(UUID uuid, String command, String[] args) {

		ClanPlayer clanPlayer = Connections.getClanPlayer(uuid);

		if (command.equals("kingdom")) {

			// Show help if no command specified
			if (args.length < 1) {
				// clanHelp(clanPlayer, new String[]{"1"});
				return;
			}

			// Check for invalid arguments
			Pattern argPattern = Pattern.compile("[^a-zA-Z0-9_-_]");
			for (String arg : args) {
				if (argPattern.matcher(arg).find()) {
					clanPlayer.sendMessage(ChatColor.RED + "Invalid argument. Arguments must be alphanumeric.");
					return;
				}
			}

			switch (args[0].toUpperCase()) {

				/* GENERAL CLAN COMMANDS */
				case"INVITE":		kingdomInvite(clanPlayer, args);	break;
				case"ACCEPT":		kingdomAccept(clanPlayer);			break;
				case"LEAVE":		kingdomLeave(clanPlayer);			break;
				case"INFO":			kingdomInfo(clanPlayer, args);		break;
				case"RENAME":		kingdomRename(clanPlayer, args);	break;
				case"LIST":
				}
		}

		/* TEAM CHAT */
		if (command.equals("k")) {
			kingdomChat(clanPlayer, args);
		}
	}

	/**
	 * Invites a new Clan to the user's Kingdom.
	 * 
	 * @param clanPlayer
	 *            user
	 * @param args
	 *            name of Clan to invite
	 */
	private static void kingdomInvite(ClanPlayer clanPlayer, String[] args) {

		/* Check if user can invite team */
		if (args.length < 2) {
			clanPlayer.sendMessage(err + "Usage: /kingdom invite <tag or team name>");
			return;
		}

		if (clanPlayer.getClan() == null) {
			clanPlayer.sendMessage(err + "You must be in a team to create a kingdom.");
			return;
		}

		if (!Clans.getClanRank(clanPlayer.getUuid()).hasPermission("KINGDOM")) {
			clanPlayer.sendMessage(err + "You do not have permission to invite.");
			return;
		}

		/* Check for Clan with given args as tag or name */
		Clan target;
		String check = args[1];
		for (int i = 2; i < args.length; i++)
			check += " " + args[i];

		if (Clans.tagExists(check)) {
			target = Clans.getClanByTag(check);
		}

		else if (Clans.clanExists(check)) {
			target = Clans.getClanByName(check);
		}

		else {
			clanPlayer.sendMessage(err + "Could not find team.");
			return;
		}

		/* Check if team can be invited */
		if (target.getKingdom() != null) {
			clanPlayer.sendMessage(err + "Team is already part of a kingdom.");
			return;
		}

		if (target.getKingdomInvite() == clanPlayer.getClan().getUuid()) {
			clanPlayer.sendMessage(err + "Already invited that team.");
			return;
		}

		/* Create new Kingdom */
		if (clanPlayer.getKingdom() == null) {

			if (target == clanPlayer.getClan()) {
				clanPlayer.sendMessage(err + "You can not invite your own team to a kingdom.");
				return;
			}

			clanPlayer.getClan().sendExactMessage(ChatColor.AQUA + " * " + target.getColor() + target.getName() + ChatColor.AQUA + " has been invited to your kingdom.");

			target.sendExactMessage(ChatColor.AQUA + " * " + clanPlayer.getClan().getColor() + clanPlayer.getClan().getName() + ChatColor.AQUA + " has invited you to join their kingdom.");

			target.setKingdomInvite(clanPlayer.getClan().getUuid());
		}

		/* Invite to current Kingdom */
		else {

			if (!clanPlayer.getClan().isKingdomLeader()) {
				clanPlayer.sendMessage(err + "Your team is not the kingdom leader.");
				return;
			}

			clanPlayer.getKingdom().sendExactMessage(ChatColor.AQUA + " * " + target.getColor() + target.getName() + ChatColor.AQUA + " has been invited to the Kingdom.");

			target.sendExactMessage(ChatColor.AQUA + " * " + clanPlayer.getKingdom().getColor() + clanPlayer.getKingdom().getName() + ChatColor.AQUA + " has invited you to join their Kingdom.");

			target.setKingdomInvite(clanPlayer.getClan().getUuid());
		}

	}

	/**
	 * Accept an invite to a Kingdom
	 * 
	 * @param clanPlayer
	 *            user who will accept
	 */
	private static void kingdomAccept(ClanPlayer clanPlayer) {

		if (clanPlayer.getClan() == null) {
			clanPlayer.sendMessage(err + "You must be in a team to create a kingdom.");
			return;
		}

		if (!Clans.getClanRank(clanPlayer.getUuid()).hasPermission("KINGDOM")) {
			clanPlayer.sendMessage(err + "You do not have permission to invite.");
			return;
		}

		if (clanPlayer.getClan().getKingdomInvite() == null) {
			clanPlayer.sendMessage(err + "Your team has not been invited to any kingdoms recently.");
			return;
		}

		Clan kingdomLeader = Clans.getClans().get(clanPlayer.getClan().getKingdomInvite());

		if (kingdomLeader == null) {
			clanPlayer.sendMessage(err + "Team no longer exists.");
			clanPlayer.getClan().setKingdomInvite(null);
			return;
		}

		/* Create new Kingdom */
		if (kingdomLeader.getKingdom() == null) {

			new Kingdom(kingdomLeader, clanPlayer.getClan());

		}

		/* Join current Kingdom */
		else {

			kingdomLeader.getKingdom().addClanMember(clanPlayer.getClan());
			kingdomLeader.getKingdom().saveData();

		}

		kingdomLeader.getKingdom().sendExactMessage(ChatColor.AQUA + " * " + clanPlayer.getClan().getColor() + clanPlayer.getClan().getName() + ChatColor.AQUA + " has joined the kingdom.");

	}

	/**
	 * Leave a Kingdom
	 * 
	 * @param clanPlayer
	 *            user
	 */
	public static void kingdomLeave(ClanPlayer clanPlayer) {

		Kingdom kingdom = clanPlayer.getKingdom();
		Clan clan = clanPlayer.getClan();

		if (kingdom == null) {
			clanPlayer.sendMessage("You are not part of a kingdom.");
			return;
		}

		if (clan.isKingdomLeader()) {
			clanPlayer.sendMessage(err + "Your team is the kingdom leader.");
			return;
		}

	}

	/**
	 * Show info for a Kingdom
	 * 
	 * @param clanplayer
	 *            user to show info to
	 * @param args
	 *            optional kingdom name
	 */
	private static void kingdomInfo(ClanPlayer clanPlayer, String[] args) {

		/* Show info for other Kingdom */
		if (args.length > 1) {
			return;
		}

		/* Show info for own Kingdom */

		if (clanPlayer.getKingdom() == null) {
			clanPlayer.sendMessage(err + "Usage: /kingdom info [kingdom]");
			return;
		}

		showKingdomInfo(clanPlayer, clanPlayer.getKingdom());

	}

	private static void showKingdomInfo(ClanPlayer clanPlayer, Kingdom kingdom) {

		/* Kingdom Name */
		String side1 = kingdom.getColor() + "" + ChatColor.STRIKETHROUGH + "+---+" + ChatColor.RESET + "   ";
		String side2 = kingdom.getColor() + "   " + ChatColor.STRIKETHROUGH + "+---+";
		clanPlayer.sendMessage(side1 + white + kingdom.getName() + side2);

		/* Kingdom Teams */
		String message = "";
		for (Clan clan : kingdom.getMemberClans()) {

			ChatColor color = clan.getColor();

			String tag;
			tag = (clan.getTag() == null) ? "" : clan.getTag();

			int onlinePlayers = clan.getOnlineMembers().size();
			int totalPlayers = clan.getMembers().size();

			// General info message
			message += "   " + color + tag + " " + white + clan.getName() + color + " [" + onlinePlayers + "/" + totalPlayers + "]\n";

		}
		clanPlayer.sendMessage(message);

	}

	private static void kingdomRename(ClanPlayer clanPlayer, String[] args) {

		if (args.length < 2) {
			clanPlayer.sendMessage(err + "Usage: /kingdom rename <new name>");
			return;
		}

		if (clanPlayer.getKingdom() == null) {
			clanPlayer.sendMessage(err + "You must be in a kingdom to rename your kingdom.");
			return;
		}

		if (!Clans.getClanRank(clanPlayer.getUuid()).hasPermission("KINGDOM")) {
			clanPlayer.sendMessage(err + "You do not have permission to invite.");
			return;
		}

		if (!clanPlayer.getClan().isKingdomLeader()) {
			clanPlayer.sendMessage(err + "Your team is not the kingdom leader.");
			return;
		}

		String name = args[1];
		for (int i = 2; i < args.length; i++) {
			name += " " + args[i];
		}

		if (com.Kingdoms.Teams.Kingdoms.nameExists(name)) {
			clanPlayer.sendMessage(err + "Name already exists.");
			return;
		}

		Kingdom kingdom = clanPlayer.getKingdom();
		kingdom.setName(name);
		kingdom.saveData();

		clanPlayer.sendMessage(success + "Name changed.");
	}

	/* KINGDOM CHAT */

	/**
	 * Send a chat message to all players in the user's Kingdom
	 * 
	 * @param clanPlayer
	 *            user
	 * @param args
	 *            message
	 */
	private static void kingdomChat(ClanPlayer clanPlayer, String[] args) {

		if (clanPlayer.getKingdom() == null) {
			clanPlayer.sendMessage(err + "You must be in a kingdom to team chat.");
			return;
		}

		if (args.length == 0) {
			clanPlayer.sendMessage(err + "Usage: /k <message>");
			return;
		}

		// Send coordinates to kingdom if message is @loc
		String words = "";
		if (args.length == 1 && args[0].equalsIgnoreCase("@LOC")) {

			Location loc = clanPlayer.getPlayer().getLocation();
			words = " I am at X:" + (int) loc.getX() + " Y:" + (int) loc.getY() + " Z:" + (int) loc.getZ();

		}

		// Otherwise send combined message
		else {

			for (int i = 0; i < args.length; i++) {
				words += " " + args[i];
			}

		}

		String message = clanPlayer.getName() + ":" + ChatColor.AQUA + words;
		clanPlayer.getKingdom().sendMessage(message);
	}
}
