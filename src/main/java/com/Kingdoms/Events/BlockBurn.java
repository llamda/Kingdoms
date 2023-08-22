package com.Kingdoms.Events;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;

import com.Kingdoms.Area;
import com.Kingdoms.AreaUpgrade;
import com.Kingdoms.Areas;

public class BlockBurn implements Listener {

	@EventHandler
	public void onBlockBurn(BlockBurnEvent event) {

		Block block = event.getBlock();
		Area area = Areas.getChunkOwner(block);
		if (area != null && area.hasAreaUpgrade(AreaUpgrade.FIRE)) {
			Events.saveState(block.getState());
		}

		/*
		if (!Events.isArea(block.getLocation()))
			return;

		Events.saveState(block.getState());
		*/
	}

}
