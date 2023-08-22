package com.Kingdoms.Teams;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class Kingdoms extends AbstractTeamHolder {

	/* Holds all Kingdoms */
	private static HashMap<UUID, Kingdom> kingdoms = new HashMap<UUID, Kingdom>();

	/* Load */
	public Kingdoms() {
		super();
	}

	/* Create from files */
	@Override
	public void loadFiles(File[] files) {

		for (File file : files) {

			Kingdom kingdom = new Kingdom(file);
			kingdoms.put(kingdom.getUuid(), kingdom);

		}

	}

	public static boolean nameExists(String name) {

		for (Kingdom kingdom : kingdoms.values()) {
			if (kingdom.getName().equalsIgnoreCase(name))
				return true;
		}

		return false;
	}

	public static Kingdom getKingdomByName(String name) {
		for (Kingdom kingdom : kingdoms.values()) {
			if (kingdom.getName().equalsIgnoreCase(name)) {
				return kingdom;
			}
		}
		return null;
	}


	public static HashMap<UUID, Kingdom> getKingdoms() {
		return kingdoms;
	}



	public static void setKingdoms(HashMap<UUID, Kingdom> kingdoms) {
		Kingdoms.kingdoms = kingdoms;
	}



	@Override
	public String getFileFolder() {
		return Kingdom.FILE_FOLDER;
	}




}
