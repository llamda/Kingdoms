package com.Kingdoms;

import org.bukkit.Chunk;

public class AreaChunk {

	private String worldName;
	private int x;
	private int z;

	public AreaChunk(String worldName, int x, int z) {
		setWorldName(worldName);
		setX(x);
		setZ(z);
	}

	public AreaChunk(Chunk chunk) {
		setWorldName(chunk.getWorld().getName());
		setX(chunk.getX());
		setZ(chunk.getZ());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((worldName == null) ? 0 : worldName.hashCode());
		result = prime * result + x;
		result = prime * result + z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AreaChunk other = (AreaChunk) obj;
		if (worldName == null) {
			if (other.worldName != null)
				return false;
		} else if (!worldName.equals(other.worldName))
			return false;
		if (x != other.x)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return x + " " + z;
	}

	public static AreaChunk fromString(String worldName, String string) {
		String[] data = string.split(" ");
		return new AreaChunk(worldName, Integer.valueOf(data[0]), Integer.valueOf(data[1]));
	}

	public AreaChunk getRelative(int x, int z) {
		return new AreaChunk(getWorldName(), getX() + x, getZ() + z);
	}

	public Chunk getChunk() {
		return Kingdoms.instance.getServer().getWorld(worldName).getChunkAt(x, z);
	}

	public String getWorldName() {
		return worldName;
	}

	public void setWorldName(String worldName) {
		this.worldName = worldName;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
}
