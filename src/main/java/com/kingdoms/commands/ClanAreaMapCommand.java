package com.kingdoms.commands;

import com.kingdoms.Area;
import com.kingdoms.AreaChunk;
import com.kingdoms.Areas;
import com.kingdoms.teams.ClanPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.HashSet;

public class ClanAreaMapCommand extends Command {

	public static final String SQUARE = "\u2588";
	public static final String PLAYER = "\u263B";

	public ClanAreaMapCommand(ClanPlayer clanPlayer, String[] args) {
		super(clanPlayer, args);

		Area area  = Areas.getChunkOwner(chunk);
		if (area == null) {
			error(MUST_BE_INSIDE_AREA);
			return;
		}

		int maxX = Integer.MIN_VALUE;
		int maxZ = Integer.MIN_VALUE;
		int minX = Integer.MAX_VALUE;
		int minZ = Integer.MAX_VALUE;

		HashSet<AreaChunk> chunks = area.getAreaChunks();
		for (AreaChunk c : chunks) {
			minX = Math.min(minX, c.getX());
			maxX = Math.max(maxX, c.getX());
			minZ = Math.min(minZ, c.getZ());
			maxZ = Math.max(maxZ, c.getZ());
		}
		int width = maxX - minX;
		int height = maxZ - minZ;

		width = Math.min(width, 25);
		height = Math.min(height, 25);

		for (int z = minZ - 1, zn = minZ + height + 1; z <= zn; z++) {

			TextComponent.Builder message = Component.text();

			for (int x = minX - 1, xn = minX + width + 1; x <= xn; x++) {
				AreaChunk here = new AreaChunk(area.getWorldName(), x, z);
				String s = (here.equals(chunk)) ? PLAYER : SQUARE;

				if (chunks.contains(here)) {
					if (here.equals(area.getCenterChunk())) {
						message.append(Component.text(s, NamedTextColor.GOLD));
					} else {
						message.append(Component.text(s, NamedTextColor.GREEN));
					}
				} else {
					message.append(Component.text(s, NamedTextColor.RED));
				}

			}
			player.sendMessage(message.build());
		}
	}
}
