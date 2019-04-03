package fr.cactuscata.pcmanticheat.cheats;

import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.cactuscata.pcmanticheat.cheats.list.AutoClick;
import fr.cactuscata.pcmevent.utils.Maths;
import fr.cactuscata.pcmevent.utils.PlayerPcm;
import fr.cactuscata.pcmevent.utils.Tps;

public final class CheckRunnable extends BukkitRunnable {

	public final void run() {
		for (final PlayerPcm playerPcm : PlayerPcm.getPlayersPcm().values()) {
			setAction(playerPcm.getCheats().getAutoLeftClick(), playerPcm);
			setAction(playerPcm.getCheats().getAutoRightClick(), playerPcm);
		}

	}

	private final void setAction(AutoClick cheatClick, PlayerPcm playerPcm) {
		final int ping = playerPcm.getPing();
		final double tps = Maths.arrondidouble(Tps.getTps(), 2);
		final int antiLag = (int) ((20.0D - tps) * 2.0D) + ping / 50;
		if ((cheatClick.getClicks() >= 18 + antiLag)
				&& (cheatClick.getLastAlert() + 1000L < System.currentTimeMillis())) {
			cheatClick.setLastAlert(System.currentTimeMillis());
			for (final Player players : Bukkit.getOnlinePlayers().stream().filter(x -> x.isOp())
					.collect(Collectors.toList()))
				players.sendMessage("§f[§9§lAntiCheat§f]§r §3 " + playerPcm.getPlayer().getName() + "§b a fait §4"
						+ cheatClick.getClicks() + " §bclics (MS: " + ping + " [TPS: " + tps + "])");

			((ICheat) cheatClick).addWarn(cheatClick.getClicks() + "", playerPcm);
			if (!Cheats.getCheaters().contains(playerPcm.getPlayer().getName()))
				Cheats.getCheaters().add(playerPcm.getPlayer().getName());
		}

		cheatClick.updateClicks();

	}

}
