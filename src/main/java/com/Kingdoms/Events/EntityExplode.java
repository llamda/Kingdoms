package com.Kingdoms.Events;

import org.bukkit.event.Listener;

public class EntityExplode implements Listener {

	/*
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {

		boolean inArea = false;

		for (Block block : event.blockList()) {

			if (Events.isArea(block.getLocation())) {
				inArea = true;
				break;
			}
		}

		if (!inArea)
			return;

		event.setYield(0.0f);

		for (int i = 0; i < event.blockList().size(); i++) {
			Block block = event.blockList().get(i);

			if (block.getType() == Material.TNT)
				continue;

			if (block.getState() instanceof InventoryHolder) {
				event.blockList().remove(block);
				continue;
			}

			Events.saveState(block.getState());
		}
	}
	*/
}
