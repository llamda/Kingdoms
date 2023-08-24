package com.kingdoms;

public enum AreaUpgrade {
	STURDY(20, "Increased difficulty to break blocks."),
	CLEAN(20, "Clean blocks and liquids left inside your area."),
	PHANTOM(1, "Disables phantoms from spawning."),
	SPAWNER(5, "Spawners can not be broken."),
	LOCK(10, "Locks doors, levers, buttons, etc."),
	TNT(5, "TNT can not be placed inside your area."),
	FIRE(5, "Burnt blocks will be restored inside your area.");
	// ALARM(10, "")

	private int cost;
	private String info;
	AreaUpgrade(final int cost, final String info) {
		this.setCost(cost);
		this.setInfo(info);
	}

	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
