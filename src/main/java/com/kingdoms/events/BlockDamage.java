package com.kingdoms.events;

import com.kingdoms.Area;
import com.kingdoms.AreaUpgrade;
import com.kingdoms.Areas;
import com.kingdoms.Connections;
import com.kingdoms.KingdomsUtils;
import com.kingdoms.teams.ClanPlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlockDamage implements Listener {

	// TODO fix instant-breaks

	private static final PotionEffect FATIGUE_LVL1 = new PotionEffect(PotionEffectType.SLOW_DIGGING, PotionEffect.INFINITE_DURATION, 1);
	private static final PotionEffect FATIGUE_LVL2 = new PotionEffect(PotionEffectType.SLOW_DIGGING, PotionEffect.INFINITE_DURATION, 2);

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

			player.addPotionEffect(area.hasAreaUpgrade(AreaUpgrade.STURDY) ? FATIGUE_LVL2 : FATIGUE_LVL1);

			if (event.getInstaBreak() && Math.random() > 0.01) {
				event.setCancelled(true);
			}

		} else {
			KingdomsUtils.removeInfiniteSlowDig(player);
		}
	}

}
