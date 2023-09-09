package com.kingdoms.events;

import com.kingdoms.Area;
import com.kingdoms.AreaUpgrade;
import com.kingdoms.Areas;
import com.kingdoms.Connections;
import com.kingdoms.teams.ClanPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntityTarget implements Listener {

	@EventHandler
	public void onBlockBurn(EntityTargetEvent event) {
		if (event.getTarget() instanceof Player player) {
			ClanPlayer clanPlayer = Connections.getClanPlayer(player.getUniqueId());
			Area area  = Areas.getChunkOwner(player.getChunk());

			if (area == null || !area.hasAreaUpgrade(AreaUpgrade.MOBS) || Events.canNotBuild(clanPlayer, area)) {
				return;
			}

			event.setCancelled(true);
		}
	}
}
