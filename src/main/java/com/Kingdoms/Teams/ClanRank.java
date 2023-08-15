package com.Kingdoms.Teams;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ClanRank {

	private int rank;
	private String title;
	private Set<UUID> players = new HashSet<UUID>();
	
	
	
	/**	Permissions
	 * ALL			Gives all permissions.
	 * TAG			Can change clan tag.
	 * COLOR		Can change clan color.
	 * DISBAND		Can disband clan.
	 * INVITE		Can invite new players.
	 * KICK			Can kick players from clan.
	 * RANKEDIT		Can edit clan ranks.
	 * AREA			Can edit clan areas.
	 * AREA_INFO	Can view information on held areas.
	 * KINGDOM		Can edit kingdoms.
	 */
	
	public static final String[] permissionList = {
			"ALL",
			"TAG",
			"COLOR",
			"DISBAND",
			"INVITE",
			"KICK",
			"RANKEDIT",
			"AREA",
			"AREA_INFO",
			"KINGDOM"
	};
	
	private Set<String> permissions = new HashSet<String>();
	
	public ClanRank(int rank, String title) {
		setRankNumber(rank);
		setTitle(title);
	}
	
	public ClanRank(int rank, String title, Set<UUID> players, Set<String> permissions) {
		setRankNumber(rank);
		setTitle(title);
		setPlayers(players);
		setPermissions(permissions);
	}
	
	public boolean hasPermission(String permission) {
		
		if (getPermissions().contains("ALL")) {
			return true;
		}
		
		return getPermissions().contains(permission);
	}
	
	public boolean hasPermissions(String[] permissions) {
		
		for (String permission : permissions) {
			if (!getPermissions().contains(permission)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isValidPermission(String permission) {
	
		for (String p : permissionList) {
			
			if (p.equals(permission)) {
				return true;
			}
		}
		return false;
	}
	
	public Set<String> getPlayersAsString() {
		Set<String> players = new HashSet<String>();
		for (UUID uuid : getPlayers()) {
			players.add(uuid.toString());
		}
		return players;
	}

	public int getRankNumber() {
		return rank;
	}

	public void setRankNumber(int rank) {
		this.rank = rank;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<UUID> getPlayers() {
		return players;
	}

	public void setPlayers(Set<UUID> players) {
		this.players = players;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}	
}
