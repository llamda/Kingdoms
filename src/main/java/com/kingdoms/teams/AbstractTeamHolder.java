package com.kingdoms.teams;

import java.io.File;
import com.kingdoms.Kingdoms;

public abstract class AbstractTeamHolder {


	public abstract String getFileFolder();

	/* Load all data on when server is started or reloaded */
	public AbstractTeamHolder() {

		File[] files = new File(Kingdoms.CONFIG + getFileFolder()).listFiles();

		if (files == null) {
			return;
		}

		loadFiles(files);

	}


	/* Load Files from group */
	public abstract void loadFiles(File[] files);

}
