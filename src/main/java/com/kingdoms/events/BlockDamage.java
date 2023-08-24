package com.kingdoms.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.kingdoms.Area;
import com.kingdoms.AreaUpgrade;
import com.kingdoms.Areas;
import com.kingdoms.Connections;
import com.kingdoms.teams.ClanPlayer;

public class BlockDamage implements Listener {

	// TODO fix instant-breaks

	private static final PotionEffect FATIGUE_LVL1 = new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 1);
	private static final PotionEffect FATIGUE_LVL2 = new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 2);

	@EventHandler
	public void onBlockDamage(BlockDamageEvent event) {

		Player player = event.getPlayer();
		ClanPlayer clanPlayer = Connections.getClanPlayer(player.getUniqueId());
		Block block = event.getBlock();

		Area area = Areas.getChunkOwner(block);
		if (Events.canNotBuild(clanPlayer, area)) {

			if (!player.hasPotionEffect(PotionEffectType.SLOW_DIGGING)) {
				event.setCancelled(true);
			}

			if (area.hasAreaUpgrade(AreaUpgrade.STURDY)) {
				player.addPotionEffect(FATIGUE_LVL2);
			} else {
				player.addPotionEffect(FATIGUE_LVL1);
			}

			if (event.getInstaBreak() && Math.random() > 0.01) {
				event.setCancelled(true);
			}

		} else {
			PotionEffect potion = player.getPotionEffect(PotionEffectType.SLOW_DIGGING);
			// Player has effect longer than Elder Guardians give
			if (potion != null && potion.getDuration() > 10000) {
				player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
			}
		}
	}

}
