package fr.cactuscata.pcmanticheat.cheats.list;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import fr.cactuscata.pcmanticheat.cheats.ICheat;
import fr.cactuscata.pcmevent.utils.PlayerPcm;

public class Aimbot implements ICheat {

	private int aimbotLevel;
	private final List<String> warnList = new ArrayList<>();

	@Override
	public void addWarn(String warn, PlayerPcm playerPcm) {
		this.warnList.add(warn);
	}

	@Override
	public void resetWarn() {
		this.warnList.clear();
	}

	@Override
	public boolean hasWarn() {
		return !this.warnList.isEmpty();
	}

	@Override
	public int getAlertNumber() {
		return this.warnList.size();
	}

	@Override
	public ItemStack getItemStack() {
		// TODO Auto-generated method stub
		return null;
	}

	public void incrementeAimbotLevel() {
		this.aimbotLevel++;
	}

	public int getAimbotLevel() {
		return this.aimbotLevel;
	}

	@Override
	public List<String> getWarnList() {
		return this.warnList;
	}

	public void resetAimbotLevel(){
		this.aimbotLevel = 0;
	}
	
}
