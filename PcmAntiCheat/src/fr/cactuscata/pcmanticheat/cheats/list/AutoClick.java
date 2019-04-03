package fr.cactuscata.pcmanticheat.cheats.list;

public interface AutoClick {

	public void updateClicks();

	public int getClicks();

	public void setClicks(final int clicks);

	public int getMaxClick();

	public void setMaxClick(final int maxClick);

	public long getLastAlert();

	public void setLastAlert(final long lastAlert);

	public long getLastBlockInteraction();

	public void setLastBlockInteraction(long lastBlockInteraction);

	public int[] getClick();

}
