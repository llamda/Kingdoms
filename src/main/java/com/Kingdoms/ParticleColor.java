package com.Kingdoms;

import org.bukkit.ChatColor;

public class ParticleColor {

	private double R;
	private double G;
	private double B;

	private static final double max = -0.01;
	private static final double min = 0.001;
	private static final double onethird = 0.333;
	private static final double twothird = 0.666;

	/**
	 * Convert ChatColor to particle RGB colors.
	 *
	 * http://minecraft.gamepedia.com/Commands#particle
	 * http://minecraft.gamepedia.com/Formatting_codes#Color_codes
	 * @param color color to convert
	 */
	public ParticleColor(ChatColor color) {
		switch(color) {
		case AQUA:				R = onethird;	G = max;		B = max;		break;
		case BLACK:				R = min;		G = min;		B = min;		break;
		case BLUE:				R = onethird;	G = onethird;	B = max;		break;
		case DARK_AQUA:			R = min;		G = twothird;	B = twothird;	break;
		case DARK_BLUE:			R = min;		G = min;		B = twothird;	break;
		case DARK_GRAY:			R = onethird;	G = onethird;	B = onethird;	break;
		case DARK_GREEN:		R = min;		G = twothird;	B = min;		break;
		case DARK_PURPLE:		R = twothird;	G = min;		B = twothird;	break;
		case DARK_RED:			R = twothird;	G = min;		B = min;		break;
		case GOLD:				R = max;		G = twothird;	B = min;		break;
		case GRAY:				R = twothird;	G = twothird;	B = twothird;	break;
		case GREEN:				R = onethird;	G = max;		B = onethird;	break;
		case LIGHT_PURPLE:		R = max;		G = onethird;	B = max;		break;
		case RED:				R = max;		G = onethird;	B = onethird;	break;
		case WHITE:				R = max;		G = max;		B = max;		break;
		case YELLOW:			R = max;		G = max;		B = onethird;	break;
		default:				R = min; 		G = min; 		B = min; 		break;
		}
	}

	public ParticleColor(double xd, double yd, double zd) {
		setR(xd);
		setG(yd);
		setB(zd);
	}

	public double getR() {
		return R;
	}

	public void setR(double r) {
		R = r;
	}

	public double getG() {
		return G;
	}

	public void setG(double g) {
		G = g;
	}

	public double getB() {
		return B;
	}

	public void setB(double b) {
		B = b;
	}

}
