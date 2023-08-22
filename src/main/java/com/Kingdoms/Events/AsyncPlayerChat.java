package com.Kingdoms.Events;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.Kingdoms.Connections;
import com.Kingdoms.Kingdoms;
import com.Kingdoms.Teams.Clan;
import com.Kingdoms.Teams.ClanPlayer;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class AsyncPlayerChat implements Listener {

	// TODO rewrite with better info popups

	/*
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {

		Player player = event.getPlayer();
		ClanPlayer clanPlayer = Connections.getClanPlayer(player.getUniqueId());

		ChatColor color = ChatColor.WHITE;
		String tag = "";

		if (clanPlayer.getClan() != null && clanPlayer.getClan().getTag() != null) {

			color = clanPlayer.getClan().getColor();
			tag = "[" + clanPlayer.getClan().getTag() + "]";
		}

		String rank = ChatColor.GOLD + "*";

		String admin = (player.isOp()) ? ChatColor.DARK_RED + "*" : "";

		event.setFormat(rank + ChatColor.WHITE + clanPlayer.getName() + color + tag + ChatColor.WHITE + " : %2$s");
	}
	*/
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {

		Player player = event.getPlayer();
		ClanPlayer clanPlayer = Connections.getClanPlayer(player.getUniqueId());

		Clan clan = null;
		String tag = null;
		if ((clan = clanPlayer.getClan()) != null) {
			tag = ((tag = clan.getTag()) != null) ? "[" + tag + "]" : "";
		}

		TextComponent message = new TextComponent();


		TextComponent rank = new TextComponent("*");
		boolean donator = true;
		if (donator) {
			rank.setColor(ChatColor.GOLD);
			message.addExtra(rank);
		}

		TextComponent username = new TextComponent(clanPlayer.getName());
		int timePlayed = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20 / 60 / 60;
		username.setHoverEvent(new HoverEvent (HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(timePlayed + " hours played").create()));
		// username.setColor(ChatColor.WHITE);
		message.addExtra(username);


		if (tag != null) {
			TextComponent tagComponent = new TextComponent(tag);
			tagComponent.setColor(clan.getColor().asBungee());
			tagComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(clan.getName()).create()));
			message.addExtra(tagComponent);
		}

		message.addExtra(" : " + event.getMessage());




		event.setCancelled(true);

		System.out.println(message.toPlainText());
		for (Player p : Kingdoms.instance.getServer().getOnlinePlayers()) {
			p.spigot().sendMessage(message);
		}


		//String rank = ChatColor.GOLD + "*";
		//String admin = (player.isOp()) ? ChatColor.DARK_RED + "*" : "";

		// event.setFormat(rank + ChatColor.WHITE + clanPlayer.getName() + color + tag + ChatColor.WHITE + " : %2$s");
	}
}
