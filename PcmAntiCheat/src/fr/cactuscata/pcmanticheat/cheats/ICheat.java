package fr.cactuscata.pcmanticheat.cheats;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import fr.cactuscata.pcmevent.utils.PlayerPcm;

public interface ICheat {

	public void addWarn(String warn, PlayerPcm playerPcm);

	public void resetWarn();

	public boolean hasWarn();

	public int getAlertNumber();

	public ItemStack getItemStack();
	
	public List<String> getWarnList();

}
