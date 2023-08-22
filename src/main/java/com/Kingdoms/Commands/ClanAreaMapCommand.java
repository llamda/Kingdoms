package com.Kingdoms.Commands;

import java.util.HashSet;

import com.Kingdoms.Area;
import com.Kingdoms.AreaChunk;
import com.Kingdoms.Areas;
import com.Kingdoms.Teams.ClanPlayer;

import net.md_5.bungee.api.ChatColor;

public class ClanAreaMapCommand extends Command {

	public ClanAreaMapCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		Area area  = Areas.getChunkOwner(chunk);
		if (area == null) {
			msg(ERR + MUST_BE_INSIDE_AREA);
			return;
		}


		int maxx = Integer.MIN_VALUE;
		int maxz = Integer.MIN_VALUE;
		int minx = Integer.MAX_VALUE;
		int minz = Integer.MAX_VALUE;

		HashSet<AreaChunk> chunks = area.getAreaChunks();
		for (AreaChunk c : chunks) {

			if (c.getX() < minx) {
				minx = c.getX();
			}

			if (c.getX() > maxx) {
				maxx = c.getX();
			}

			if (c.getZ() < minz) {
				minz = c.getZ();
			}

			if (c.getZ() > maxz) {
				maxz = c.getZ();
			}
		}
		int width = maxx - minx;
		int height = maxz - minz;
		// player.sendMessage(width + "x" + height);

		width = (width > 25) ? 25 : width;
		height = (height > 25) ? 25 : height;

		for (int z = minz, zn = minz + height; z <= zn; z++) {

			String message = "";

			for (int x = minx, xn = minx + width; x <= xn; x++) {
				AreaChunk here = new AreaChunk(area.getWorldName(), x, z);

				if (chunks.contains(here)) {
					if (here.equals(area.getCenterChunk())) {
						message += ChatColor.GOLD + "█";
					} else {
						message += ChatColor.GREEN + "█";
					}
				} else {
					message += ChatColor.RED + "█";
				}

			}
			msg(message);
		}

	}

}
