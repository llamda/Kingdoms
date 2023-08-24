package com.Kingdoms.Commands;

import com.Kingdoms.AreaChunk;
import com.Kingdoms.Kingdoms;
import com.Kingdoms.Teams.Clan;
import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.ClanRank;
import com.Kingdoms.Teams.Clans;
import com.Kingdoms.Teams.Kingdom;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

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


	public final static TextColor ERR = NamedTextColor.RED;
	public final static TextColor ERR_DARK = NamedTextColor.DARK_RED;
	public final static TextColor SUCCESS = NamedTextColor.GREEN;
	public final static TextColor SUCCESS_DARK = NamedTextColor.DARK_GREEN;
	public final static TextColor INFO = NamedTextColor.GRAY;
	public final static TextColor INFO_DARK = NamedTextColor.DARK_GRAY;
	public final static TextColor WHITE = NamedTextColor.WHITE;

	public final static String USAGE = "Usage: ";
	public final static String NEED_TEAM = "You must be in a team to do that.";
	public final static String IN_TEAM = "You are already in a team.";
	public final static String TEAM_EXISTS = "Team already exists.";
	public final static String TAG_CHAR_LIMIT = "Tags can not be longer than 10 characters.";
	public final static String NO_PERMISSION = "You do not have permission to do that.";
	public final static String TAG_EXISTS = "Tag already in use.";
	public final static String UNKNOWN_COLOR = "Unknown color. ";
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

	public final static String CLAN_CHAT 			= "/t <message|@loc>";
	public final static String CLAN_CREATE 			= "/team create <team name>";
	public final static String CLAN_TAG				= "/team tag <tag>";
	public final static String CLAN_COLOR			= "/team color <color|list>";
	public final static String CLAN_INVITE			= "/team invite <player>";
	public final static String CLAN_ACCEPT			= "/team accept";
	public final static String CLAN_INFO			= "/team info [tag|team name]";
	public final static String CLAN_LIST			= "/team list";
	public final static String CLAN_DISBAND			= "/team disband";
	public final static String CLAN_KICK			= "/team kick <player>";
	public final static String CLAN_LEAVE			= "/team leave";
	public final static String CLAN_RANK_CREATE 	= "/team rankcreate <rank name>";
	public final static String CLAN_RANK_DELETE 	= "/team rankdelete <rank name>";
	public final static String CLAN_RANK_RENAME 	= "/team rankrename <rank#> <new name>";
	public final static String CLAN_RANK_SET 		= "/team rankset <player> <rank name>";
	public final static String CLAN_RANK_PERM 		= "/team rankpermission <rank#> <permission> <true|false>";
	public final static String CLAN_RANK_PERMS		= "/team rankpermissions";
	public final static String CLAN_RANK_INFO 		= "/team rankinfo <rank name>";
	public final static String CLAN_RMM				= "/team rankmassmove <rank#from> <rank#to>";

	public final static String CLAN_AREA_CREATE 	= "/team area create <area name>";
	public final static String CLAN_AREA_EXPAND 	= "/team area expand";
	public final static String CLAN_AREA_INFO 		= "/team area info [area name]";
	public final static String CLAN_AREA_MAP 		= "/team area map";
	public final static String CLAN_AREA_UPGRADE 	= "/team area upgrade <upgrade>";
	public final static String CLAN_AREA_UPGRADES 	= "/team area upgrades";

	public final static String KINGDOM_INVITE		= "/kingdom invite <tag or team name>";
	public final static String KINGDOM_ACCEPT		= "/kingdom accept";
	public final static String KINGDOM_CHAT			= "/k <message|@loc>";
	public final static String KINGDOM_RENAME		= "/kingdom rename <new name>";
	public final static String KINGDOM_KICK			= "/kingdom kick <tag or team name>";
	public final static String KINGDOM_LEAVE		= "/kingdom leave";
	public final static String KINGDOM_INFO			= "/kingdom info [kingdom name]";
	public final static String KINGDOM_LIST			= "/kingdom list";

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

	public void error(String error) {
		player.sendMessage(Component.text(error, ERR));
	}

	public void success(String success) {
		player.sendMessage(Component.text(success, SUCCESS));
	}

	public void usage(String usage) {
		player.sendMessage(Component.text(USAGE + usage, ERR));
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

	public UUID getUniqueId (String name) {
		return Kingdoms.instance.getServer().getOfflinePlayer(name).getUniqueId();
	}


	public static TextComponent wrap(char left, String content, char right, TextColor outer) {
		return wrap(left, content, right, outer, NamedTextColor.WHITE);
	}

	public static TextComponent wrap(char left, String content, char right, TextColor outer, TextColor inner) {
		return Component.text()
				.append(Component.text(left, outer))
				.append(Component.text(content, inner))
				.append(Component.text(right, outer))
				.build();
	}
}
