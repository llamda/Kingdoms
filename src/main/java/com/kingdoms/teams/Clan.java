package com.kingdoms.teams;

import com.kingdoms.Area;
import com.kingdoms.Areas;
import com.kingdoms.Connections;
import com.kingdoms.Kingdoms;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class Clan extends AbstractTeam {


	public final static String FILE_FOLDER = "Clans";


	private String tag;

	private TextColor color = NamedTextColor.GRAY;

	private final Set<ClanRank> ranks = new HashSet<>();

	private final List<UUID> areas = new ArrayList<>();

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
		setUuid(UUID.fromString(Objects.requireNonNull(getTeamConfig().getString("UUID"))));
		setName(getTeamConfig().getString("Name"));

		setColor(Optional.ofNullable(TextColor.fromCSSHexString(Objects.requireNonNull(getTeamConfig().getString("Color"))))
				.orElse(NamedTextColor.GRAY));
		setTag(getTeamConfig().getString("Tag"));


		/* Load each rank */
		for (String rank : Objects.requireNonNull(getTeamConfig().getConfigurationSection("Ranks")).getKeys(false)) {

			// Subdirectory of rank data
			String dir = "Ranks." + rank + ".";

			// Read
			int rankNumber = Integer.parseInt(rank);
			String title = getTeamConfig().getString(dir + "Title");


			// Players
			Set<UUID> players = new HashSet<>();
			List<String> stringPlayers = getTeamConfig().getStringList(dir + "Players");

			for (String stringPlayer : stringPlayers) {
				players.add(UUID.fromString(stringPlayer));
			}


			// Permissions
			Set<String> permissions = new HashSet<>(getTeamConfig().getStringList(dir + "Permissions"));


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
			setColor(NamedTextColor.GRAY);

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
		getTeamConfig().set("Color", getColor().asHexString());
		getTeamConfig().set("Tag", getTag());

		/* Ranks */
		for (ClanRank rank : getRanks()) {
			String rankPath = "Ranks." + rank.getRankNumber() + ".";

			getTeamConfig().set(rankPath + "Title", rank.getTitle());
			getTeamConfig().set(rankPath + "Players", rank.getPlayersAsString().toArray());
			getTeamConfig().set(rankPath + "Permissions", rank.getPermissions().toArray());
		}

		/* Area UUID list */
		List<String> areas = new ArrayList<>();

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
			if (!area.getFile().delete()) {
				Kingdoms.instance.getLogger().warning("Failed to delete area " + uuid + "?");
			}
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

		if (!getFile().delete()) {
			Kingdoms.instance.getLogger().warning("Failed to delete clan " + this.getUuid() + "?");
		}
	}

	public boolean isKingdomLeader() {
		return kingdom != null && kingdom.getLeader() == this;
	}


	public void updateAreas() {

		List<UUID> removed = new ArrayList<>();

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
		getClanRankByNumber(Objects.requireNonNull(rank).getRankNumber()).getPlayers().remove(player.getUuid());
		player.setClan(null);
		saveData();
	}



	public Set<UUID> getMemberUuids() {
		Set<UUID> players = new HashSet<>();
		for (ClanRank rank : getRanks()) {
			players.addAll(rank.getPlayers());
		}
		return players;
	}

	public boolean isAreaOwner(UUID areaUuid) {
		return getAreas().contains(areaUuid);
	}

	public boolean exists() {
		return Clans.getClans().containsValue(this);
	}

	public String getTag() {
		return tag;
	}


	public void setTag(String tag) {
		this.tag = tag;
	}


	public TextColor getColor() {
		return color;
	}


	public void setColor(TextColor color) {
		this.color = color;
	}


	public Set<ClanRank> getRanks() {
		return ranks;
	}

	public List<UUID> getAreas() {
		return areas;
	}


	@Override
	public List<ClanPlayer> getMembers() {

		List<ClanPlayer> players = new ArrayList<>();


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
	public TextComponent getMessagePrefix() {
		return Component.text("[TEAM] ", NamedTextColor.GREEN);
	}

	@Override
	public TextColor messageColor() {
		return NamedTextColor.DARK_GREEN;
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
