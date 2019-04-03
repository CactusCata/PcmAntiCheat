package fr.cactuscata.pcmanticheat.cheats.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.cactuscata.pcmanticheat.cheats.ICheat;
import fr.cactuscata.pcmevent.utils.ItemBuilder;
import fr.cactuscata.pcmevent.utils.PlayerPcm;

public class AutoRightClick implements ICheat, AutoClick {

	private final List<String> warnList = new ArrayList<>();
	private int clicks = 0;
	private int[] click = new int[5];
	private int maxClicks = 0;
	private long lastAlert = 0L;
	private long lastBlockInteraction;
	private byte switchItem = 0, cache = 0;

	public final int getClicks() {
		return this.clicks;
	}

	public final void setClicks(final int clicks) {
		this.clicks = clicks;
	}

	public final int getMaxClick() {
		return this.maxClicks;
	}

	public final void setMaxClick(final int maxClick) {
		this.maxClicks = maxClick;
	}

	public final long getLastAlert() {
		return this.lastAlert;
	}

	public final void setLastAlert(final long lastAlert) {
		this.lastAlert = lastAlert;
	}

	public final long getLastBlockInteraction() {
		return lastBlockInteraction;
	}

	public final void setLastBlockInteraction(long lastBlockInteraction) {
		this.lastBlockInteraction = lastBlockInteraction;
	}

	public final int[] getClick() {
		return this.click;
	}

	public final void updateClicks() {
		// for(int i = 4; i > 0; i--)
		// click[i] = click[i - 1];
		click[4] = click[3];
		click[3] = click[2];
		click[2] = click[1];
		click[1] = click[0];
		click[0] = clicks;
		clicks = 0;
	}

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
		return this.warnList.isEmpty();
	}

	@Override
	public ItemStack getItemStack() {

		this.cache++;
		if (cache == 5) {
			this.cache = 0;
			this.switchItem++;
		}

		switch (this.switchItem) {
		case 0:
			return new ItemBuilder(Material.GOLD_BLOCK, click[0] > 64 ? 64 : click[0])
					.setDisplayName("§cClicks les 5 dernieres secondes :").setLore(Arrays.asList("§f- " + click[0],
							"§f- " + click[1], "§f- " + click[2], "§f- " + click[3], "§f- " + click[4]))
					.build();
		case 1:
			return new ItemBuilder(Material.EMERALD_BLOCK, this.clicks > 64 ? 64 : this.clicks)
					.setDisplayName("§9Clics actuellement: " + this.clicks).build();
		default:
			return new ItemBuilder(Material.DIAMOND_BLOCK, this.maxClicks > 64 ? 64 : this.maxClicks)
					.setDisplayName(" ").setLore(Arrays.asList("§eMaximum de", "§eclicks: §f" + this.maxClicks))
					.build();
		}

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
