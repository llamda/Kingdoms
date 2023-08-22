package com.Kingdoms.Teams;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class Clans extends AbstractTeamHolder {

	// Holds all Clans
	private static HashMap<UUID, Clan> clans = new HashMap<UUID, Clan>();

	public Clans() {
		super();
	}

	@Override
	public void loadFiles(File[] files) {

		for (File file : files) {

			Clan clan = new Clan(file);
			clans.put(clan.getUuid(), clan);

		}

	}




	public static boolean clanExists(String name) {
		return (getClanByName(name) == null) ?  false : true;
	}

	public static Clan getClanByName(String name) {
		for (UUID uuid : clans.keySet()) {

			Clan clan = getClans().get(uuid);
			if (clan.getName().equalsIgnoreCase(name)) {
				return clan;
			}
		}
		return null;
	}

	public static boolean tagExists(String tag) {
		return (getClanByTag(tag) == null) ?  false : true;
	}

	public static Clan getClanByTag(String tag) {
		for (UUID uuid : clans.keySet()) {

			Clan clan = getClans().get(uuid);

			if (clan.getTag() == null) {
				continue;
			}

			if (clan.getTag().equalsIgnoreCase(tag)) {
				return clan;
			}
		}
		return null;
	}

	public static Clan getClanById(UUID uniqueId) {
		return getClans().get(uniqueId);
	}

	public static Clan getClan(UUID playerUuid) {
		for (UUID uuid : clans.keySet()) {
			Clan clan = clans.get(uuid);
			if (clan.getMemberUuids().contains(playerUuid)) {
				return clan;
			}
		}
		return null;
	}

	public static ClanRank getClanRank(UUID playerUuid) {
		for (UUID uuid : getClans().keySet()) {
			Clan clan = getClans().get(uuid);
			for (ClanRank rank : clan.getRanks()) {
				if (rank.getPlayers().contains(playerUuid)) {
					return rank;
				}
			}
		}
		return null;
	}

	public static HashMap<UUID, Clan> getClans() {
		return clans;
	}

	public static void setClans(HashMap<UUID, Clan> clans) {
		Clans.clans = clans;
	}

	@Override
	public String getFileFolder() {
		return Clan.FILE_FOLDER;
	}

}
