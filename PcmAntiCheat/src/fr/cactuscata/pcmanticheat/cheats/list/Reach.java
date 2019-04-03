package fr.cactuscata.pcmanticheat.cheats.list;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.cactuscata.pcmanticheat.cheats.ICheat;
import fr.cactuscata.pcmevent.utils.ItemBuilder;
import fr.cactuscata.pcmevent.utils.PlayerPcm;

public class Reach implements ICheat {

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
	public ItemStack getItemStack() {
		return new ItemBuilder(Material.ENDER_PEARL, getAlertNumber() > 64 ? 64 : getAlertNumber())
				.setDisplayName("Reach :").setGlowing(hasWarn()).setLore(this.warnList).build();
	}

	@Override
	public int getAlertNumber() {
		return this.warnList.size();
	}

	@Override
	public List<String> getWarnList() {
		return this.warnList;
	}
	
}
