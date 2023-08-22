package com.Kingdoms.Teams;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;

import com.Kingdoms.Area;
import com.Kingdoms.Areas;
import com.Kingdoms.Connections;
import com.Kingdoms.KingdomsUtils;

public class Clan extends AbstractTeam {


	public final static String FILE_FOLDER = "Clans";


	private String tag;
	private ChatColor color;

	private Set<ClanRank> ranks = new HashSet<ClanRank>();

	private List<UUID> areas = new ArrayList<UUID>();

	private Kingdom kingdom;
	private UUID kingdomInvite;

	/**
	 * Create new Clan
	 * @param clanPlayer clan leader
	 * @param name clan name
	 */
	public Clan(ClanPlayer clanPlayer, String name) {

		/* Create new File */
		loadNew();


		/* Create defaults */
		setName(name);
		loadDefaults();
		getClanRankByNumber(1).getPlayers().add(clanPlayer.getUuid());


		/* Save file */
		saveData();


		/* Assign leader to clan */
		clanPlayer.setClan(this);


		/* Add clan to clan list */
		Clans.getClans().put(getUuid(), this);
	}



	/**
	 * Load Clan from file
	 * @param file file to load
	 */
	public Clan(File file) {

		/* Load given file */
		loadFile(file);


		/* Remove any areas that could not be loaded */
		updateAreas();


		/* Reset anything not loaded properly (Broken Config?) to default values */
		loadDefaults();

	}



	/* Specific data to load for this Team */
	@Override
	public void loadFileData() {


		/* Generic values */
		setUuid(UUID.fromString(getTeamConfig().getString("UUID")));
		setName(getTeamConfig().getString("Name"));
		setColor(ChatColor.valueOf(getTeamConfig().getString("Color")));
		setTag(getTeamConfig().getString("Tag"));


		/* Load each rank */
		for (String rank : getTeamConfig().getConfigurationSection("Ranks").getKeys(false)) {

			// Sub directory of rank data
			String dir = "Ranks." + rank + ".";

			// Read
			int rankNumber = Integer.valueOf(rank);
			String title = getTeamConfig().getString(dir + "Title");


			// Players
			Set<UUID> players = new HashSet<UUID>();
			List<String> stringPlayers = getTeamConfig().getStringList(dir + "Players");

			for (String stringPlayer : stringPlayers) {
				players.add(UUID.fromString(stringPlayer));
			}


			// Permissions
			Set<String> permissions = new HashSet<String>(getTeamConfig().getStringList(dir + "Permissions"));


			// Create new rank from data
			getRanks().add(new ClanRank(rankNumber, title, players, permissions));
		}


		/* Load Area UUIDs */
		for (String areaUUID : getTeamConfig().getStringList("Areas")) {
			getAreas().add(UUID.fromString(areaUUID));
		}

	}



	/* Default values that potentially did not get loaded from file */
	public void loadDefaults() {

		if (getColor() == null)
			setColor(ChatColor.GRAY);

		if (getRanks().isEmpty()) {
			ClanRank leader = new ClanRank(1, "Leader");
			leader.getPermissions().add("ALL");

			getRanks().add(leader);
			getRanks().add(new ClanRank(2, "Member"));
		}

	}



	/* Save all data to file */
	@Override
	public void saveData() {

		/* Generic values */
		getTeamConfig().set("UUID", getUuid().toString());
		getTeamConfig().set("Name", getName());
		getTeamConfig().set("Color", KingdomsUtils.chatColorToString(getColor()));
		getTeamConfig().set("Tag", getTag());

		/* Ranks */
		for (ClanRank rank : getRanks()) {
			String rankPath = "Ranks." + rank.getRankNumber() + ".";

			getTeamConfig().set(rankPath + "Title", rank.getTitle());
			getTeamConfig().set(rankPath + "Players", rank.getPlayersAsString().toArray());
			getTeamConfig().set(rankPath + "Permissions", rank.getPermissions().toArray());
		}

		/* Area UUID list */
		List<String> areas = new ArrayList<String>();

		for (UUID uuid : getAreas()) {
			areas.add(uuid.toString());
		}

		getTeamConfig().set("Areas", areas);


		/* Save file */
		super.saveData();
	}

	public void disband() {
		Clans.getClans().remove(getUuid());

		// Connections.


		for (UUID uuid : getMemberUuids()) {
			ClanPlayer clanPlayer;
			if ((clanPlayer = Connections.getClanPlayer(uuid)) == null) {
				clanPlayer = new ClanPlayer(uuid);
				clanPlayer.setClan(null);
				clanPlayer.saveData();
			}
			clanPlayer.setClan(null);
		}

		for (UUID uuid : getAreas()) {
			Area area = Areas.getAreas().get(uuid);
			area.getFile().delete();
			Areas.getAreas().remove(uuid);
		}


		if (kingdom != null) {
			if (kingdom.getMemberClans().size() == 1) {
				kingdom.disband();
			} else {
				kingdom.getMemberClans().remove(this);
				kingdom.saveData();
				kingdom = null;
			}
		}

		// TODO REMOVE ACTIONS WITH OTHER TEAMS (WAR)?

		getFile().delete();
	}

	public boolean isKingdomLeader() {

		if (kingdom == null || kingdom.getLeader() != this)
			return false;

		return true;
	}


	public void updateAreas() {

		List<UUID> removed = new ArrayList<UUID>();

		for (UUID uuid : getAreas()) {
			if (!Areas.getAreas().containsKey(uuid)) {
				removed.add(uuid);
			}
		}

		getAreas().removeAll(removed);
	}

	public ClanRank getClanRankByNumber(int rank) {
		for (ClanRank clanRank : getRanks()) {
			if (clanRank.getRankNumber() == rank)
				return clanRank;
		}
		return null;
	}

	public ClanRank getRankByName(String name) {

		name = name.toLowerCase();

		for (ClanRank rank : getRanks()) {

			if (rank.getTitle().toLowerCase().equals(name)) {
				return rank;
			}
		}

		return null;
	}

	public void addMember(ClanPlayer player) {
		int lowestRank = ranks.size();
		getClanRankByNumber(lowestRank).getPlayers().add(player.getUuid());
		saveData();
	}

	public void removeMember(ClanPlayer player) {

		ClanRank rank = Clans.getClanRank(player.getUuid());
		getClanRankByNumber(rank.getRankNumber()).getPlayers().remove(player.getUuid());
		player.setClan(null);
		saveData();
	}



	public Set<UUID> getMemberUuids() {
		Set<UUID> players = new HashSet<UUID>();
		for (ClanRank rank : getRanks()) {
			players.addAll(rank.getPlayers());
		}
		return players;
	}

	public boolean isAreaOwner(UUID areaUuid) {
		return getAreas().contains(areaUuid);
	}

	public boolean exists() {
		return Clans.getClans().values().contains(this);
	}

	public String getTag() {
		return tag;
	}


	public void setTag(String tag) {
		this.tag = tag;
	}


	public ChatColor getColor() {
		return color;
	}


	public void setColor(ChatColor color) {
		this.color = color;
	}


	public Set<ClanRank> getRanks() {
		return ranks;
	}

	public void setRanks(Set<ClanRank> ranks) {
		this.ranks = ranks;
	}

	public List<UUID> getAreas() {
		return areas;
	}

	public void setAreas(List<UUID> areas) {
		this.areas = areas;
	}



	@Override
	public List<ClanPlayer> getMembers() {

		List<ClanPlayer> players = new ArrayList<ClanPlayer>();


		for (ClanRank rank : getRanks()) {

			for (UUID uuid : rank.getPlayers()) {

				if (Connections.getClanPlayers().containsKey(uuid)) {

					players.add(Connections.getClanPlayers().get(uuid));
				}

				else {

					players.add(new ClanPlayer(uuid));
				}

			}

		}
		return players;

	}

	@Override
	public String getMessageFormat(String message) {
		return ChatColor.GREEN + "[TEAM] " + ChatColor.DARK_GREEN + message;
	}

	@Override
	public String getFileFolder() {
		return FILE_FOLDER;
	}




	public Kingdom getKingdom() {
		return kingdom;
	}



	public void setKingdom(Kingdom kingdom) {
		this.kingdom = kingdom;
	}



	public UUID getKingdomInvite() {
		return kingdomInvite;
	}



	public void setKingdomInvite(UUID kingdomInvite) {
		this.kingdomInvite = kingdomInvite;
	}




}
