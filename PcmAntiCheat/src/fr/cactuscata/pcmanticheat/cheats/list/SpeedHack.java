package fr.cactuscata.pcmanticheat.cheats.list;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import fr.cactuscata.pcmanticheat.cheats.ICheat;
import fr.cactuscata.pcmevent.utils.PlayerPcm;
import fr.cactuscata.pcmevent.utils.Tps;

public class SpeedHack implements ICheat {

	private int movementPacket;
	private final List<String> warnList = new ArrayList<>();

	@Override
	public void addWarn(String warn, PlayerPcm playerPcm) {
		this.warnList.add("§c[§f" + new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()) + "§c] §4 (MS: "
				+ playerPcm.getPing() + " [TPS: " + Tps.getTps() + "])");
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAlertNumber() {
		return this.warnList.size();
	}

	public void incrementeMovementPacket() {
		this.movementPacket++;
	}

	public int getMovementPacket() {
		return this.movementPacket;
	}
	
	@Override
	public List<String> getWarnList() {
		return this.warnList;
	}

}
