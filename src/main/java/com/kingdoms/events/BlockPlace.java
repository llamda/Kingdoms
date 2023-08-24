package com.kingdoms.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.kingdoms.Area;
import com.kingdoms.AreaUpgrade;
import com.kingdoms.Areas;
import com.kingdoms.Connections;
import com.kingdoms.teams.ClanPlayer;

public class BlockPlace implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		ClanPlayer clanPlayer = Connections.getClanPlayer(player.getUniqueId());
		Block block = event.getBlock();
		Area area = Areas.getChunkOwner(block);

		if (Events.canNotBuild(clanPlayer, area)) {
			if (area.hasAreaUpgrade(AreaUpgrade.TNT) && block.getType() == Material.TNT) {
				event.setCancelled(true);
				return;
			}

			if (area.hasAreaUpgrade(AreaUpgrade.CLEAN)) {
				Events.saveState(event.getBlockReplacedState());
			}
		}
	}

}
