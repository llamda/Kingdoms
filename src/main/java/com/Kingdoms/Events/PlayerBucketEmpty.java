package com.Kingdoms.Events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import com.Kingdoms.Area;
import com.Kingdoms.AreaUpgrade;
import com.Kingdoms.Areas;
import com.Kingdoms.Connections;
import com.Kingdoms.Teams.ClanPlayer;

public class PlayerBucketEmpty implements Listener {

	@EventHandler
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {

		Player player = (Player) event.getPlayer();
		ClanPlayer clanPlayer = Connections.getClanPlayer(player.getUniqueId());

		Block liquid = event.getBlockClicked().getRelative(event.getBlockFace());

		Area area  = Areas.getChunkOwner(liquid);
		if (!Events.canBuild(clanPlayer, area)) {
			if (area.hasAreaUpgrade(AreaUpgrade.CLEAN)) {
				Events.saveState(liquid.getState());
			}
			Areas.getLiquids().add(liquid);
		}

		/*
		if (Events.canBuild(clanPlayer, liquid)) {
			return;
		}

		Areas.getLiquids().add(liquid);
		Events.saveState(liquid.getState());
		*/
	}
}
