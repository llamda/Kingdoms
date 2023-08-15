package com.Kingdoms.Commands;

import com.Kingdoms.Teams.ClanPlayer;

public class ClanHelpCommand extends Command {

	public static final int PAGES = 5;
	
	
	/**
	 * Shows help pages to user
	 */
	public ClanHelpCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		int page;
		
		/* Give page 1 unless page number is given */
		if (argc < 2) {
			page = 1;
		}
		
		else {
			try {
				page = Integer.parseInt(args[1]);
			} catch(NumberFormatException e) {
				page = 1;
			}
			
			if (page < 1 || page > PAGES) {
				page = 1;
			}
		}
		
		msg(INFO_DARK + "Kingdoms Help [" + page + "/" + PAGES + "]");
		switch(page) {
		case 1:
			msg(INFO + "  " +
				CLAN_HELP 	+ "\n  " + 
				CLAN_CREATE + "\n  " + 	
				CLAN_TAG	+ "\n  " +
				CLAN_COLOR	+ "\n  " +
				CLAN_INVITE + "\n  " + 
				CLAN_INFO	+ "\n  " +
				CLAN_LIST	+ "\n  " +
				CLAN_CHAT);
			break;
		case 2:
			msg(INFO + "  " +
				CLAN_DISBAND	+ "\n  " +
				CLAN_KICK		+ "\n  " +
				CLAN_LEAVE);
			break;
		case 3:
			msg(INFO + "  " +
				CLAN_RCREATE 	+ "\n  " + 
				CLAN_RDELETE 	+ "\n  " +
				CLAN_RRENAME	+ "\n  " + 
				CLAN_RSET		+ "\n  " +
				CLAN_RPERM		+ "\n  " +
				CLAN_RPERMS		+ "\n  " +
				CLAN_RINFO		+ "\n  " +
				CLAN_RMM);
			break;
		case 4:
			msg(INFO + "  " +
				CLAN_ACREATE 	+ "\n  " +
				CLAN_AEXPAND 	+ "\n  " +
				CLAN_AINFO	 	+ "\n  " +
				CLAN_AUPGRADE 	+ "\n  " +
				CLAN_AUPGRADES);			
			break;
		case 5:
			msg(INFO + "  " +
				KINGDOM_INVITE 	+ "\n  " + 
				KINGDOM_ACCEPT 	+ "\n  " +
				KINGDOM_RENAME	+ "\n  " + 
				KINGDOM_LEAVE	+ "\n  " +
				KINGDOM_KICK	+ "\n  " +
				KINGDOM_INFO	+ "\n  " +
				KINGDOM_LIST	+ "\n  " +
				KINGDOM_CHAT);
			break;
		}
		
	}

}
