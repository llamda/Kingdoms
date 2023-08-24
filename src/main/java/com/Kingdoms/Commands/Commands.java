package com.Kingdoms.Commands;

import java.util.regex.Pattern;

import com.Kingdoms.Teams.ClanPlayer;
import net.kyori.adventure.text.Component;

public class Commands {

	private static final Pattern invalid = Pattern.compile("[^a-zA-Z0-9_#]");


	public static void newClanCommand(ClanPlayer clanPlayer, String[] args) {

		// Show help if no command specified
		if (args.length < 1) {
			new ClanHelpCommand(clanPlayer, args);
			return;
		}

		if (hasInvalidArgs(args)) {
			clanPlayer.sendMessage(Component.text("Arguments must be alphanumeric.", Command.ERR));
			return;
		}

		switch (args[0].toUpperCase()) {

			/* GENERAL CLAN COMMANDS */
			case "CREATE" -> new ClanCreateCommand(clanPlayer, args);
			case "TAG" -> new ClanTagCommand(clanPlayer, args);
			case "COLOR" -> new ClanColorCommand(clanPlayer, args);
			case "DISBAND" -> new ClanDisbandCommand(clanPlayer, args);
			case "INFO" -> new ClanInfoCommand(clanPlayer, args);
			case "HELP" -> new ClanHelpCommand(clanPlayer, args);
			case "LIST" -> new ClanListCommand(clanPlayer, args);
			case "INVITE" -> new ClanInviteCommand(clanPlayer, args);
			case "ACCEPT" -> new ClanAcceptCommand(clanPlayer, args);
			case "LEAVE" -> new ClanLeaveCommand(clanPlayer, args);
			case "KICK" -> new ClanKickCommand(clanPlayer, args);


			/* RANK COMMANDS */
			case "RCREATE", "RANKCREATE" -> new ClanRankCreateCommand(clanPlayer, args);
			case "RDEL", "RDELETE", "RANKDELETE" -> new ClanRankDeleteCommand(clanPlayer, args);
			case "RNAME", "RRENAME", "RANKRENAME" -> new ClanRankRenameCommand(clanPlayer, args);
			case "RSET", "RANKSET" -> new ClanRankSetCommand(clanPlayer, args);
			case "RPERM", "RPERMISSION", "RANKPERM", "RANKPERMISSION" ->
					new ClanRankPermissionCommand(clanPlayer, args);
			case "RPERMS", "RPERMISSIONS", "RANKPERMS", "RANKPERMISSIONS" ->
					new ClanRankPermissionsCommand(clanPlayer, args);
			case "RINFO", "RANKINFO" -> new ClanRankInfoCommand(clanPlayer, args);
			case "RMASSMOVE", "RANKMASSMOVE" -> new ClanRankMassMoveCommand(clanPlayer, args);


			/* AREA COMMANDS */
			case "AREA" -> newClanAreaCommand(clanPlayer, args);


			/* Show Help Otherwise */
			default -> new ClanHelpCommand(clanPlayer, args);
		}

	}


	public static void newClanAreaCommand(ClanPlayer clanPlayer, String[] args) {

		// Show help if no command specified
		if (args.length < 2) {
			new ClanHelpCommand(clanPlayer, new String[] {"", "4"});
			return;
		}

		switch (args[1].toUpperCase()) {
			case "CREATE" -> new ClanAreaCreateCommand(clanPlayer, args);
			case "EXPAND" -> new ClanAreaExpandCommand(clanPlayer, args);
			case "INFO" -> new ClanAreaInfoCommand(clanPlayer, args);
			case "UPGRADE" -> new ClanAreaUpgradeCommand(clanPlayer, args);
			case "UPGRADES" -> new ClanAreaUpgradesCommand(clanPlayer, args);
			case "MAP" -> new ClanAreaMapCommand(clanPlayer, args);
			default -> {
			}
		}
	}


	public static void newClanChatCommand(ClanPlayer clanPlayer, String[] args) {
		new ClanChatCommand(clanPlayer, args);
	}


	public static void newKingdomCommand(ClanPlayer clanPlayer, String[] args) {

		// Show help if no command specified
		if (args.length < 1) {
			new ClanHelpCommand(clanPlayer, new String[] {"", "5"});
			return;
		}

		if (hasInvalidArgs(args)) {
			clanPlayer.sendMessage(Component.text("Arguments must be alphanumeric.", Command.ERR));
			return;
		}

		/* GENERAL KINGDOM COMMANDS */
		switch (args[0].toUpperCase()) {
			case "INVITE" -> new KingdomInviteCommand(clanPlayer, args);
			case "ACCEPT" -> new KingdomAcceptCommand(clanPlayer, args);
			case "LEAVE" -> new KingdomLeaveCommand(clanPlayer, args);
			case "INFO" -> new KingdomInfoCommand(clanPlayer, args);
			case "RENAME" -> new KingdomRenameCommand(clanPlayer, args);
			case "LIST" -> new KingdomListCommand(clanPlayer, args);
			case "KICK" -> new KingdomKickCommand(clanPlayer, args);
			default -> new ClanHelpCommand(clanPlayer, new String[]{"", "5"});
		}


	}

	public static void newKingdomChatCommand(ClanPlayer clanPlayer, String[] args) {
		new KingdomChatCommand(clanPlayer, args);
	}





	/**
	 * Returns if args contains any invalid characters
	 */
	public static boolean hasInvalidArgs(String[] args) {
		for (String arg : args) {
			if (invalid.matcher(arg).find()) {
				return true;
			}
		}
		return false;
	}
}
