package com.kingdoms.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ChunkUnload implements Listener {

	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent event) {
		Events.restoreChunk(event.getChunk());
	}

}
