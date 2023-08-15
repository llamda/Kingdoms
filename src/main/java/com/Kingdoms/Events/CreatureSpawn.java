package com.Kingdoms.Events;

import org.bukkit.entity.Phantom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.Kingdoms.Area;
import com.Kingdoms.AreaUpgrade;
import com.Kingdoms.Areas;

public class CreatureSpawn implements Listener {
	
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		
		if (event.getEntity() instanceof Phantom) {
			Phantom phantom = (Phantom) event.getEntity();
		
			Area area = Areas.getChunkOwner(phantom.getLocation().getChunk());
			if (area != null && area.hasAreaUpgrade(AreaUpgrade.PHANTOM)) {
				event.setCancelled(true);
			}
		}
	}
}
