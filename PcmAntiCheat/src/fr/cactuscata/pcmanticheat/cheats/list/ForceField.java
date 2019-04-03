package fr.cactuscata.pcmanticheat.cheats.list;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.cactuscata.pcmanticheat.cheats.ICheat;
import fr.cactuscata.pcmevent.utils.ItemBuilder;
import fr.cactuscata.pcmevent.utils.PlayerPcm;
import fr.cactuscata.pcmevent.utils.Tps;

public class ForceField implements ICheat {

	private int forcefieldlevel;
	private final List<String> warnList = new ArrayList<>();
	
	@Override
	public void addWarn(String warn, PlayerPcm playerPcm) {
		this.warnList.add(
				"§c[§f" + new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()) + "§c] §2Bot touché en 1sec : "
						+ this.forcefieldlevel + "§4 (MS: " + playerPcm.getPing() + " [TPS: " + Tps.getTps() + "])");

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
		return new ItemBuilder(Material.DIAMOND_SWORD, getAlertNumber() > 64 ? 64 : getAlertNumber())
					.setDisplayName("FF - Forcefield level : " + this.forcefieldlevel).setGlowing(getAlertNumber() != 0)
					.setLore(this.warnList).build();
	}

	@Override
	public int getAlertNumber() {
		return this.warnList.size();
	}

	public void incremente() {
		this.forcefieldlevel++;
	}

	public int getForcefieldLevel() {
		return this.forcefieldlevel;
	}
	
	public void resetForceFieldLevel(){
		this.forcefieldlevel = 0;
	}

	@Override
	public List<String> getWarnList() {
		return this.warnList;
	}
	
}
