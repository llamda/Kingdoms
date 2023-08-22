package com.Kingdoms.Commands;

import java.util.regex.Pattern;

import com.Kingdoms.Teams.ClanPlayer;

public class Commands {

	private static final Pattern invalid = Pattern.compile("[^a-zA-Z0-9_-_]");


	public static void newClanCommand(ClanPlayer clanPlayer, String[] args) {

		// Show help if no command specified
		if (args.length < 1) {
			new ClanHelpCommand(clanPlayer, args);
			return;
		}

		if (hasInvalidArgs(args)) {
			clanPlayer.sendMessage(Command.ERR + "Arguments must be alphanumeric.");
			return;
		}

		switch(args[0].toUpperCase()) {

			/* GENERAL CLAN COMMANDS */
			case"CREATE":		new ClanCreateCommand(clanPlayer, args);	break;
			case"TAG":			new ClanTagCommand(clanPlayer, args);		break;
			case"COLOR":		new ClanColorCommand(clanPlayer, args);		break;
			case"DISBAND":		new ClanDisbandCommand(clanPlayer, args);	break;
			case"INFO":			new ClanInfoCommand(clanPlayer, args);		break;
			case"HELP":			new ClanHelpCommand(clanPlayer,	args);		break;
			case"LIST":			new ClanListCommand(clanPlayer, args);		break;
			case"INVITE":		new ClanInviteCommand(clanPlayer, args);	break;
			case"ACCEPT":		new ClanAcceptCommand(clanPlayer, args);	break;
			case"LEAVE":		new ClanLeaveCommand(clanPlayer, args);		break;
			case"KICK":			new ClanKickCommand(clanPlayer, args);		break;

			/* RANK COMMANDS */
			case"RCREATE":
			case"RANKCREATE":		new ClanRankCreateCommand(clanPlayer, args);		break;
			case"RDEL":
			case"RDELETE":
			case"RANKDELETE":		new ClanRankDeleteCommand(clanPlayer, args);		break;
			case"RNAME":
			case"RRENAME":
			case"RANKRENAME":		new ClanRankRenameCommand(clanPlayer, args);		break;
			case"RSET":
			case"RANKSET":			new ClanRankSetCommand(clanPlayer, args);			break;
			case"RPERM":
			case"RPERMISSION":
			case"RANKPERM":
			case"RANKPERMISSION":	new ClanRankPermissionCommand(clanPlayer, args);	break;
			case"RPERMS":
			case"RPERMISSIONS":
			case"RANKPERMS":
			case"RANKPERMISSIONS":	new ClanRankPermissionsCommand(clanPlayer, args);	break;
			case"RINFO":
			case"RANKINFO":			new ClanRankInfoCommand(clanPlayer, args);			break;
			case"RMASSMOVE":
			case"RANKMASSMOVE":		new ClanRankMassMoveCommand(clanPlayer, args);		break;

			/* AREA COMMANDS */
			case"AREA":				newClanAreaCommand(clanPlayer, args);				break;

			/* Show Help Otherwise */
			default:				new ClanHelpCommand(clanPlayer, args);				break;
		}

	}


	public static void newClanAreaCommand(ClanPlayer clanPlayer, String[] args) {

		// Show help if no command specified
		if (args.length < 2) {
			new ClanHelpCommand(clanPlayer, new String[] {"", "4"});
			return;
		}

		switch(args[1].toUpperCase()) {
			case"CREATE":		new ClanAreaCreateCommand(clanPlayer, args);		break;
			case"EXPAND":		new ClanAreaExpandCommand(clanPlayer, args);		break;
			case"INFO":			new ClanAreaInfoCommand(clanPlayer, args);			break;
			case"UPGRADE":		new ClanAreaUpgradeCommand(clanPlayer, args);		break;
			case"UPGRADES":		new ClanAreaUpgradesCommand(clanPlayer, args);		break;
			case"MAP":			new ClanAreaMapCommand(clanPlayer, args);			break;
			default:
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
			clanPlayer.sendMessage(Command.ERR + "Arguments must be alphanumeric.");
			return;
		}

		switch(args[0].toUpperCase()) {
			/* GENERAL KINGDOM COMMANDS */
			case"INVITE":	new KingdomInviteCommand(clanPlayer, args);	break;
			case"ACCEPT":	new KingdomAcceptCommand(clanPlayer, args);	break;
			case"LEAVE":	new KingdomLeaveCommand(clanPlayer, args);	break;
			case"INFO":		new KingdomInfoCommand(clanPlayer, args);	break;
			case"RENAME":	new KingdomRenameCommand(clanPlayer, args);	break;
			case"LIST":		new KingdomListCommand(clanPlayer, args);	break;
			case"KICK":		new KingdomKickCommand(clanPlayer, args);	break;
			default: 		new ClanHelpCommand(clanPlayer, new String[] {"", "5"}); break;
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
