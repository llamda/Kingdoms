package com.kingdoms.events;

import com.kingdoms.Area;
import com.kingdoms.AreaUpgrade;
import com.kingdoms.Areas;
import org.bukkit.entity.Phantom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawn implements Listener {

	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if (event.getEntity() instanceof Phantom phantom) {
			Area area = Areas.getChunkOwner(phantom.getLocation().getChunk());
			if (area != null && area.hasAreaUpgrade(AreaUpgrade.PHANTOM)) {
				event.setCancelled(true);
			}
		}
	}
}
