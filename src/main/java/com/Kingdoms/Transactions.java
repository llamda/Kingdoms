package com.Kingdoms;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Transactions {

	private Player player;
	
	public static final Material CURRENCY = Material.GOLD_BLOCK;
	
	public static final int AREA_CREATE_PRICE = 5;
	public static final int AREA_EXPAND_PRICE = 1;
	
	public Transactions(Player player) {
		this.player = player;
	}
	
	
	/**
	 * Checks if player's inventory holds the given amount of material
	 */
	public boolean canAfford(Material type, int amount) {
		
		if (player.getGameMode() == GameMode.CREATIVE) {
			return true;
		}
		
		ItemStack item = new ItemStack(type);
		if (player.getInventory().containsAtLeast(item, amount)) {
			return true;
		}
		return false;
	}
	
	public boolean canAfford(int amount) {
		return canAfford(CURRENCY, amount);
	}
	
	
	/**
	 * Removes given amount of material from player's inventory
	 */
	public void pay(Material type, int amount) {
		
		Kingdoms.instance.getLogger().info(player.getName() + " just paid " + amount + " " + CURRENCY.toString());
		for (ItemStack item : player.getInventory().getContents()) {
			
			if (item == null) {
				continue;
			}
			
			if (item.getType() != type || item.hasItemMeta()) {
				continue;
			}	
			
			if (item.getAmount() >= amount) {
				item.setAmount(item.getAmount() - amount);
				return;
			}
			
			amount -= item.getAmount();
			item.setAmount(0);
		}
	}
	
	public String canNotAffordString(int price) {
		String plural = (price > 1) ? "s" : "";
		return "You need " + price + " " + Transactions.CURRENCY.toString().toLowerCase() + plural + " to do that.";
	}
	
	public void pay(int amount) {
		pay(CURRENCY, amount);
	}
}
