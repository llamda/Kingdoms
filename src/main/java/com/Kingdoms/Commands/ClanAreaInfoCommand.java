package com.Kingdoms.Commands;

import org.bukkit.ChatColor;

import com.Kingdoms.Area;
import com.Kingdoms.AreaUpgrade;
import com.Kingdoms.Areas;
import com.Kingdoms.Teams.ClanPlayer;

public class ClanAreaInfoCommand extends Command {

	public ClanAreaInfoCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		Area area;
		if (argc < 3) {
			area = Areas.getChunkOwner(chunk);
		} else {
			String areaName = args[2];
			for (int i = 3; i < args.length; i++) {
				areaName += " " + args[i];
			}
			area = Areas.getAreaByName(areaName);
		}

		if (area == null) {
			msg(ERR + AREA_NOT_FOUND);
			return;
		}

		if (clan == null || !clan.isAreaOwner(area.getUuid()) || !rank.hasPermission("AREA_INFO")) {
			msg(ERR + NO_PERMISSION);
			return;
		}


		String message = "";
		String coords = area.getFormattedAreaCoordinates();

		ChatColor c = Areas.getClanOwner(area).getColor();

		message += c + "[" + WHITE + area.getAreaName() + c + "] (" + WHITE + coords + c + ")\n";
		message += "  Total Chunks: (" + WHITE + area.getAreaChunks().size() + c + "/" + WHITE + area.getMaxAreaSize() + c + ")\n";
		message += "  Active Upgrades:";
		for (AreaUpgrade upgrade : AreaUpgrade.values()) {
			message += ChatColor.RESET + " ";
			if (!area.hasAreaUpgrade(upgrade)) {
				message += ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH;
			}	else {
				message += ChatColor.RESET;
			}
			message += upgrade.toString().toLowerCase();
		}
		player.sendMessage(message);
	}

}
