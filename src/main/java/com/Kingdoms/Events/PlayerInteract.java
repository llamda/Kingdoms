package com.Kingdoms.Events;

import java.util.Arrays;
import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.Kingdoms.Area;
import com.Kingdoms.AreaChunk;
import com.Kingdoms.AreaUpgrade;
import com.Kingdoms.Areas;
import com.Kingdoms.Connections;
import com.Kingdoms.Teams.ClanPlayer;

public class PlayerInteract implements Listener {
	
	private static final Material[] locked_materials = {
		Material.LEVER,
		Material.STONE_BUTTON,
		Material.OAK_TRAPDOOR,
		Material.SPRUCE_TRAPDOOR,
		Material.BIRCH_TRAPDOOR,
		Material.JUNGLE_TRAPDOOR,
		Material.ACACIA_TRAPDOOR,
		Material.DARK_OAK_TRAPDOOR,
		Material.OAK_FENCE_GATE,
		Material.SPRUCE_FENCE_GATE,
		Material.BIRCH_FENCE_GATE,
		Material.JUNGLE_FENCE_GATE,
		Material.ACACIA_FENCE_GATE,
		Material.DARK_OAK_FENCE_GATE,
		Material.OAK_BUTTON,
		Material.SPRUCE_BUTTON,
		Material.BIRCH_BUTTON,
		Material.JUNGLE_BUTTON,
		Material.ACACIA_BUTTON,
		Material.DARK_OAK_BUTTON,
		Material.OAK_DOOR,
		Material.SPRUCE_DOOR,
		Material.BIRCH_DOOR,
		Material.JUNGLE_DOOR,
		Material.ACACIA_DOOR,
		Material.DARK_OAK_DOOR
	};
	private static final HashSet<Material> lockedSet = new HashSet<Material>(Arrays.asList(locked_materials));
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		Material item = player.getInventory().getItemInMainHand().getType();
		Action action = event.getAction();
		
		/* Show area center on compass */
		if (item == Material.COMPASS && (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK)) {
			
			Area area = Areas.getChunkOwner(new AreaChunk(player.getLocation().getChunk()));
			if (area == null) {
				return;
			}	
			
			Location center = area.getAreaCenterLocation();
			player.setCompassTarget(center);
			player.sendMessage(ChatColor.GREEN + "Area center at: (X:" + (int) center.getX() + ", Z:" + (int) center.getZ() + ")");
		}
		
		if (action == Action.LEFT_CLICK_AIR) {
			PotionEffect potion = player.getPotionEffect(PotionEffectType.SLOW_DIGGING);
			// Player has effect longer than Elder Gaurdians give
			if (potion != null && potion.getDuration() > 10000) {
				player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
			}
		}
		
		if (action == Action.RIGHT_CLICK_BLOCK) {
			
			Block block = event.getClickedBlock();
			Area area = Areas.getChunkOwner(block);
			ClanPlayer clanPlayer = Connections.getClanPlayer(player.getUniqueId());
			
			if (!Events.canBuild(clanPlayer, area)) {
				if (area.hasAreaUpgrade(AreaUpgrade.LOCK) && getLockedset().contains(block.getType())) {
					event.setCancelled(true);
					return;
				}
				
				if (area.hasAreaUpgrade(AreaUpgrade.TNT)) {
					Material mainhand = player.getInventory().getItemInMainHand().getType();
					Material offhand = player.getInventory().getItemInOffHand().getType();
					if (mainhand == Material.TNT_MINECART || offhand == Material.TNT_MINECART) {
						event.setCancelled(true);
					}
				}
				
			}
			
		}
		
		
		// TODO opening doors etc...
		
		
	}

	public static HashSet<Material> getLockedset() {
		return lockedSet;
	}
}
