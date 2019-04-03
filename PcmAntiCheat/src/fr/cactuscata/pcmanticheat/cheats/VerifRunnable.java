package fr.cactuscata.pcmanticheat.cheats;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.cactuscata.pcmanticheat.commands.Verif;
import fr.cactuscata.pcmevent.utils.ItemBuilder;
import fr.cactuscata.pcmevent.utils.PlayerPcm;
import fr.cactuscata.pcmevent.utils.Tps;

public final class VerifRunnable extends BukkitRunnable {

	@Override
	public final void run() {
		for (final Player players : Verif.getVerifiers().keySet()) {
			if ((players.getOpenInventory().getTopInventory() != null)
					&& (players.getOpenInventory().getTopInventory().getTitle().startsWith("§cVerif >"))) {
				if (Bukkit.getPlayer(players.getOpenInventory().getTopInventory().getName().split("> ")[1]) != null) {
					final PlayerPcm playerPcm = Verif.getVerifiers().get(players);
					final Cheats cheats = playerPcm.getCheats();

					for (int i = 0, j = cheats.getCheats().size(); i < j; i++) {
						players.getOpenInventory().getTopInventory().setItem(i,
								cheats.getCheats().get(i).getItemStack());
					}

					final int numberOfAllAlerte = cheats.getNumberTotalAlert();
					players.getOpenInventory().getTopInventory().setItem(30,
							new ItemBuilder(Material.BOOK, numberOfAllAlerte > 64 ? 64 : numberOfAllAlerte)
									.setDisplayName("§cNombre d'alertes: " + numberOfAllAlerte)
									.setLore(cheats.getTotalLore()).setGlowing(numberOfAllAlerte != 0).build());

					final int ping = playerPcm.getPing();
					players.getOpenInventory().getTopInventory().setItem(31,
							new ItemBuilder(Material.EXP_BOTTLE, ping > 64 ? 64 : ping)
									.setDisplayName("§6Ping: §f" + ping).build());

					players.getOpenInventory().getTopInventory().setItem(32,
							new ItemBuilder(Material.COMMAND, (int) Tps.getTps())
									.setDisplayName("Tps server : " + Tps.getTps()).build());

					final int minutesConnected = (int) playerPcm.getMinutesConnected();
					players.getOpenInventory().getTopInventory().setItem(33,
							new ItemBuilder(Material.WATCH, minutesConnected > 64 ? 64 : minutesConnected)
									.setDisplayName("Temps de jeu : " + minutesConnected).build());

				}
			} else
				Verif.getVerifiers().remove(players);

		}
	}
}