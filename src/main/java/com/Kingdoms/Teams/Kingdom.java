package com.Kingdoms.Teams;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Kingdom extends AbstractTeam {


	public final static String FILE_FOLDER = "Kingdoms";

	private Clan leader;
	private List<Clan> memberClans = new ArrayList<Clan>();



	public Kingdom(Clan leader, Clan member) {

		/* Create new File */
		loadNew();


		/* name */
		setName(leader.getName());

		/* Add Clans */
		this.leader = leader;
		addClanMember(leader);
		addClanMember(member);


		/* Save file */
		saveData();

		/* Add kingdom to kingdom list */
		Kingdoms.getKingdoms().put(getUuid(), this);
	}



	public Kingdom(File file) {

		/* Load given file */
		loadFile(file);


		/* Update members */
	}


	/* Save all data to file */
	@Override
	public void saveData() {

		/* Generic values */
		getTeamConfig().set("UUID", getUuid().toString());
		getTeamConfig().set("Name", getName());
		getTeamConfig().set("Leader", leader.getUuid().toString());


		/* Clan member list */
		List<String> members = new ArrayList<String>();

		for (Clan clan : getMemberClans()) {
			members.add(clan.getUuid().toString());
		}

		getTeamConfig().set("Members", members);


		/* Save file */
		super.saveData();

	}

	public void removeMember(Clan clan) {
		getMemberClans().remove(clan);
		clan.setKingdom(null);
		saveData();
	}

	public void disband() {
		Kingdoms.getKingdoms().remove(getUuid());
		for (Clan clan : getMemberClans()) {
			clan.setKingdom(null);
		}
		getFile().delete();
	}


	@Override
	public void loadFileData() {


		/* Generic values */
		setUuid(UUID.fromString(getTeamConfig().getString("UUID")));
		setName(getTeamConfig().getString("Name"));

		System.out.println(getName() + " " + getTeamConfig().getString("Leader"));

		UUID leaderUUID = UUID.fromString(getTeamConfig().getString("Leader"));
		leader = Clans.getClans().get(leaderUUID);


		/* Add Clans from UUID */
		for (String stringUuid : getTeamConfig().getStringList("Members")) {

			UUID uuid = UUID.fromString(stringUuid);

			if (!Clans.getClans().containsKey(uuid))
				continue;

			Clan clan = Clans.getClans().get(uuid);

			getMemberClans().add(clan);
			clan.setKingdom(this);
		}

		/* Save file */
		super.saveData();
	}


	/**
	 * Add a Clan to this kingdom's members. Must be saved afterwards
	 * @param clan clan to add
	 */
	public void addClanMember(Clan clan) {

		memberClans.add(clan);
		clan.setKingdom(this);

	}


	@Override
	public List<ClanPlayer> getMembers() {

		List<ClanPlayer> members = new ArrayList<ClanPlayer>();

		for (Clan clan : memberClans) {
			members.addAll(clan.getMembers());
		}

		return members;
	}



	@Override
	public TextComponent getMessagePrefix() {
		return Component.text("[KINGDOM] ", NamedTextColor.AQUA);
	}

	@Override
	public TextColor messageColor() {
		return NamedTextColor.DARK_AQUA;
	}


	@Override
	public String getFileFolder() {
		return FILE_FOLDER;
	}



	public TextColor getColor() {
		return leader.getColor();
	}



	public List<Clan> getMemberClans() {
		return memberClans;
	}



	public Clan getLeader() {
		return leader;
	}



	public void setLeader(Clan leader) {
		this.leader = leader;
	}



	public void setMemberClans(List<Clan> memberClans) {
		this.memberClans = memberClans;
	}

}
