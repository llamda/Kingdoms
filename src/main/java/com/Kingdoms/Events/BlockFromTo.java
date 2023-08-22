package com.Kingdoms.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import com.Kingdoms.Areas;

public class BlockFromTo implements Listener {

	@EventHandler
	public void onBlockFromTo(BlockFromToEvent event) {

		/*
		if (Areas.getLiquids().contains(event.getBlock())) {
			Areas.getLiquids().add(event.getToBlock());
			Events.saveState(event.getToBlock().getState());
		}
		*/

		if (Areas.getLiquids().contains(event.getBlock())) {
			event.setCancelled(true);
		}

	}

}
