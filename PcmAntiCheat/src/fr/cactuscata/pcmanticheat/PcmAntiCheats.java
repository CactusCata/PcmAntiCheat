package fr.cactuscata.pcmanticheat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.cactuscata.pcmanticheat.cheats.Cheats;
import fr.cactuscata.pcmanticheat.cheats.CheckRunnable;
import fr.cactuscata.pcmanticheat.cheats.VerifRunnable;
import fr.cactuscata.pcmanticheat.cheats.utils.PacketListeners;
import fr.cactuscata.pcmanticheat.commands.Cheater;
import fr.cactuscata.pcmevent.utils.PlayerPcm;

public class PcmAntiCheats extends JavaPlugin {

	@Override
	public void onEnable() {
		new VerifRunnable().runTaskTimerAsynchronously(this, 0L, 1L);
		new CheckRunnable().runTaskTimerAsynchronously(this, 0L, 20L);
		getCommand("cheater").setExecutor(new Cheater());
	}

	@Override
	public void onDisable() {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			Cheats cheats = PlayerPcm.getPlayersPcm().get(p).getCheats();
			if (cheats.getBot() != null) {
				cheats.removeBot();
			}
			PacketListeners.unRegisterPlayer(p);
		}
	}

}
