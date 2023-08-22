package com.Kingdoms.Commands;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.Kingdoms.AreaChunk;
import com.Kingdoms.Kingdoms;
import com.Kingdoms.Teams.Clan;
import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.ClanRank;
import com.Kingdoms.Teams.Clans;
import com.Kingdoms.Teams.Kingdom;

public abstract class Command {

	protected ClanPlayer clanPlayer;
	protected Clan clan;
	protected ClanRank rank;
	protected Player player;
	protected String name;
	protected Location loc;
	protected int argc;
	protected Kingdom kingdom;
	protected AreaChunk chunk;


	public final static ChatColor ERR = ChatColor.RED;
	public final static ChatColor ERR_DARK = ChatColor.DARK_RED;
	public final static ChatColor SUCCESS = ChatColor.GREEN;
	public final static ChatColor SUCCESS_DARK = ChatColor.DARK_GREEN;
	public final static ChatColor INFO = ChatColor.GRAY;
	public final static ChatColor INFO_DARK = ChatColor.DARK_GRAY;
	public final static ChatColor WHITE = ChatColor.WHITE;
	public final static ChatColor STRIKETHROUGH = ChatColor.STRIKETHROUGH;
	public final static ChatColor RESET = ChatColor.RESET;


	public final static String USAGE = ERR + "Usage: ";
	public final static String NEED_TEAM = "You must be in a team to do that.";
	public final static String IN_TEAM = "You are already in a team.";
	public final static String TEAM_EXISTS = "Team already exists.";
	public final static String TAG_CHAR_LIMIT = "Tags can not be longer than 10 characters.";
	public final static String NO_PERMISSION = "You do not have permission to do that.";
	public final static String TAG_EXISTS = "Tag already in use.";
	public final static String UNKNOWN_COLOR = "Unknown color. ";
	public final static String COLORS = "Available colors: " + ChatColor.BLACK + "black" + INFO + ", " + ChatColor.DARK_BLUE + "dark_blue" + INFO + ", " + ChatColor.DARK_GREEN + "dark_green" + INFO + ", " + ChatColor.DARK_AQUA + "dark_aqua" + INFO + ", " + ChatColor.DARK_RED + "dark_red" + INFO + ", " + ChatColor.DARK_PURPLE + "dark_purple" + INFO + ", " + ChatColor.GOLD + "gold" + INFO + ", " + ChatColor.GRAY + "gray" + INFO + ", " + ChatColor.DARK_GRAY + "dark_gray" + INFO + ", " + ChatColor.BLUE + "blue" + INFO + ", " + ChatColor.GREEN + "green" + INFO + ", " + ChatColor.AQUA + "aqua" + INFO + ", " + ChatColor.RED + "red" + INFO + ", " + ChatColor.LIGHT_PURPLE + "light_purple" + INFO + ", " + ChatColor.YELLOW + "yellow" + INFO + ", " + ChatColor.WHITE + "white";
	public final static String IN_KINGDOM = "You must not be the leader of a kingdom to do that.";
	public final static String TEAM_NOT_FOUND = "Could not find team.";
	public final static String PLAYER_NOT_FOUND = "Could not find player.";
	public final static String PLAYER_HAS_TEAM = "Player already has team.";
	public final static String ALREADY_INVITED = "You already invited that player.";
	public final static String NO_INVITE = "No invitation found.";
	public final static String MUST_APPOINT_NEW = "You must appoint a new team leader to do that.";
	public final static String RANK_EXISTS = "Rank already exists.";
	public final static String RANK_NOT_FOUND = "Could not find rank.";
	public final static String NON_EMPTY_RANK = "Can not delete rank with players.";
	public final static String PERM_NOT_FOUND = "Could not find permission.";
	public final static String NON_BOOLEAN = "Value must be true or false";
	public final static String NEARBY_AREA = "Too close to existing area.";
	public final static String TEAM_HAS_KINGDOM = "Team is already part of a kingdom.";
	public final static String ALREADY_INVITED_KINGDOM = "Already invited that team.";
	public final static String CANNOT_INVITE_SELF = "You can not invite your own team to a kingdom.";
	public final static String NEED_KINGDOM = "You must be in a kingdom to do that.";
	public final static String KINGDOM_NOT_FOUND  = "Could not find Kingdom.";
	public final static String MUST_APPOINT_NEW_KINGDOM = "You must appoint a new Kingdom leader to do that.";
	public final static String KINGDOM_EXISTS = "Kingdom name already exists.";
	public final static String AREA_EXISTS = "Area name already exists.";
	public final static String AREA_NOT_FOUND = "Area not found.";
	public final static String MUST_BE_INSIDE_AREA = "You must be inside an area to do that.";
	public final static String UNKNOWN_UPGRADE = "Unknown upgrade.";
	public final static String ALREADY_UPGRADED = "Area upgrade already purchased.";

	public final static String CLAN_CHAT 		= "/t <message|@loc>";
	public final static String CLAN_HELP 		= "/team help [1-" + ClanHelpCommand.PAGES + "]";
	public final static String CLAN_CREATE 		= "/team create <team name>";
	public final static String CLAN_TAG			= "/team tag <tag>";
	public final static String CLAN_COLOR		= "/team color <color|list>";
	public final static String CLAN_INVITE		= "/team invite <player>";
	public final static String CLAN_ACCEPT		= "/team accept";
	public final static String CLAN_INFO		= "/team info [tag|team name]";
	public final static String CLAN_LIST		= "/team list";
	public final static String CLAN_DISBAND		= "/team disband";
	public final static String CLAN_KICK		= "/team kick <player>";
	public final static String CLAN_LEAVE		= "/team leave";
	public final static String CLAN_RCREATE		= "/team rankcreate <rank name>";
	public final static String CLAN_RDELETE		= "/team rankdelete <rank name>";
	public final static String CLAN_RRENAME		= "/team rankrename <rank#> <new name>";
	public final static String CLAN_RSET		= "/team rankset <player> <rank name>";
	public final static String CLAN_RPERM		= "/team rankpermission <rank#> <permission> <true|false>";
	public final static String CLAN_RPERMS		= "/team rankpermissions";
	public final static String CLAN_RINFO		= "/team rankinfo <rank name>";
	public final static String CLAN_RMM			= "/team rankmassmove <rank#from> <rank#to>";

	public final static String CLAN_ACREATE		= "/team area create <area name>";
	public final static String CLAN_AEXPAND		= "/team area expand";
	public final static String CLAN_AINFO		= "/team area info [area name]";
	public final static String CLAN_AUPGRADE	= "/team area upgrade <upgrade>";
	public final static String CLAN_AUPGRADES	= "/team area upgrades";

	public final static String KINGDOM_INVITE	= "/kingdom invite <tag or team name>";
	public final static String KINGDOM_ACCEPT	= "/kingdom accept";
	public final static String KINGDOM_CHAT		= "/k <message|@loc>";
	public final static String KINGDOM_RENAME	= "/kingdom rename <new name>";
	public final static String KINGDOM_KICK		= "/kingdom kick <tag or team name>";
	public final static String KINGDOM_LEAVE	= "/kingdom leave";
	public final static String KINGDOM_INFO		= "/kingdom info [kingdom name]";
	public final static String KINGDOM_LIST		= "/kingdom list";

	public Command(ClanPlayer clanPlayer, String[] args) {
		this.clanPlayer = clanPlayer;
		this.clan = clanPlayer.getClan();
		this.rank = Clans.getClanRank(clanPlayer.getUuid());
		this.player = clanPlayer.getPlayer();
		this.name = clanPlayer.getName();
		this.loc = player.getLocation();
		this.argc = args.length;
		this.kingdom = (clan != null) ? clan.getKingdom() : null;
		this.chunk = clanPlayer.getChunk();
	}

	public void msg(String message) {
		player.sendMessage(message);
	}

	public void broadcast(String message) {
		Kingdoms.instance.getServer().broadcastMessage(message);
	}

	public boolean isOnline(UUID uniqueId) {
		return (Kingdoms.instance.getServer().getPlayer(uniqueId) != null);
	}

	public boolean isOnline(String name) {
		return (Kingdoms.instance.getServer().getPlayerExact(name) != null);
	}

	public String getName(UUID uniqueId) {
		return Kingdoms.instance.getServer().getOfflinePlayer(uniqueId).getName();
	}

	@SuppressWarnings("deprecation")
	public UUID getUniqueId (String name) {
		return Kingdoms.instance.getServer().getOfflinePlayer(name).getUniqueId();
	}

}
