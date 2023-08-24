package com.kingdoms.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import com.kingdoms.Area;
import com.kingdoms.AreaUpgrade;
import com.kingdoms.Areas;
import com.kingdoms.Connections;
import com.kingdoms.teams.ClanPlayer;

public class PlayerBucketEmpty implements Listener {

	@EventHandler
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {

		Player player = event.getPlayer();
		ClanPlayer clanPlayer = Connections.getClanPlayer(player.getUniqueId());

		Block liquid = event.getBlockClicked().getRelative(event.getBlockFace());

		Area area  = Areas.getChunkOwner(liquid);
		if (Events.canNotBuild(clanPlayer, area)) {
			if (area.hasAreaUpgrade(AreaUpgrade.CLEAN)) {
				Events.saveState(liquid.getState());
			}
			Areas.getLiquids().add(liquid);
		}
	}
}
