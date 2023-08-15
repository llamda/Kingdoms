package com.Kingdoms.Commands;

import java.util.Collection;

import com.Kingdoms.Teams.ClanPlayer;
import com.Kingdoms.Teams.Kingdom;
import com.Kingdoms.Teams.Kingdoms;

public class KingdomListCommand extends Command {

	public KingdomListCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);
		
		Collection<Kingdom> kingdoms = Kingdoms.getKingdoms().values();
		String message = INFO_DARK + "Showing All Kingdoms (" + INFO + "Total: " + kingdoms.size() + INFO_DARK + ")\n";
		
		for (Kingdom k : kingdoms) {			
			int opc = k.getOnlineMembers().size();
			int tpc = k.getMembers().size();
			message += k.getColor() + k.getName() + " [" + opc + "/" + tpc + "]";
		}
		msg(message);
	}

}
