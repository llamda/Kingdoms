package com.kingdoms;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public class KingdomsUtils {

	public static TextColor stringToTextColor(String string) {
		var color = TextColor.fromCSSHexString(string);
		if (color != null) return color;

		return switch(string.toUpperCase()) {
			case "AQUA" ->  		NamedTextColor.AQUA;
			case "BLACK" -> 		NamedTextColor.BLACK;
			case "BLUE" -> 			NamedTextColor.BLUE;
			case "DARK_AQUA" -> 	NamedTextColor.DARK_AQUA;
			case "DARK_BLUE" -> 	NamedTextColor.DARK_BLUE;
			case "DARK_GRAY" -> 	NamedTextColor.DARK_GRAY;
			case "DARK_GREEN" -> 	NamedTextColor.DARK_GREEN;
			case "DARK_PURPLE" -> 	NamedTextColor.DARK_PURPLE;
			case "DARK_RED" -> 		NamedTextColor.DARK_RED;
			case "GOLD" -> 			NamedTextColor.GOLD;
			case "GRAY" -> 			NamedTextColor.GRAY;
			case "GREEN" -> 		NamedTextColor.GREEN;
			case "LIGHT_PURPLE" -> 	NamedTextColor.LIGHT_PURPLE;
			case "RED" -> 			NamedTextColor.RED;
			case "WHITE" -> 		NamedTextColor.WHITE;
			case "YELLOW" -> 		NamedTextColor.YELLOW;
			default -> 				null;
		};
	}

}








