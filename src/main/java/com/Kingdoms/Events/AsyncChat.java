package com.Kingdoms.Events;

import com.Kingdoms.Connections;
import com.Kingdoms.Kingdoms;
import com.Kingdoms.Teams.Clan;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Optional;

public class AsyncChat implements Listener {

	private static final int HOUR = 20 * 60 * 60;

	@EventHandler
	public void onAsyncChatEvent(AsyncChatEvent event) {
		Player player = event.getPlayer();
		Clan clan = Connections.getClanPlayer(player.getUniqueId()).getClan();

		TextComponent message = Component.empty()
				.append(rank())
				.append(userInfo(player))
				.append(tag(clan))
				.append(Component.text(" : "))
				.append(event.message());

		event.setCancelled(true);

		Kingdoms.instance.getComponentLogger().info(message);
		Kingdoms.instance.getServer().getOnlinePlayers().forEach(p -> p.sendMessage(message));
	}

	// TODO: implement actual ranks
	private static TextComponent rank() {
		return Component.text("*").color(NamedTextColor.GOLD);
	}

	private static TextComponent userInfo(Player player) {
		int hours = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / HOUR;
		return Component.text(player.getName())
				.hoverEvent(Component.text(hours + " hours played"));
	}

	public static TextComponent tag(Clan clan) {
		if (clan == null) return Component.empty();

		return Component.text(Optional.ofNullable(clan.getTag()).map(tag -> "[" + tag + "]").orElse(""))
				.color(clan.getColor())
				.hoverEvent(Component.text(clan.getName()));
	}
}
