package com.kingdoms.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import com.kingdoms.Areas;

public class BlockFromTo implements Listener {

	@EventHandler
	public void onBlockFromTo(BlockFromToEvent event) {
		if (Areas.getLiquids().contains(event.getBlock())) {
			event.setCancelled(true);
		}
	}

}
