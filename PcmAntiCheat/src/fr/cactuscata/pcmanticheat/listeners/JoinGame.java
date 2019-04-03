package fr.cactuscata.pcmanticheat.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.cactuscata.pcmanticheat.cheats.Cheats;
import fr.cactuscata.pcmevent.enums.PrefixMessage;
import fr.cactuscata.pcmevent.utils.PlayerPcm;

public final class JoinGame implements Listener {

	@EventHandler
	public final void join(final PlayerJoinEvent event) {

		final Player player = event.getPlayer();

		new Cheats(PlayerPcm.getPlayersPcm().get(player));

		if (player.isOp())
			player.sendMessage(PrefixMessage.PREFIX
					+ "Liste de tout les joueurs qui ont fait une alerte cps et qui sont actuellement connecté : §c"
					+ Cheats.getCheaters() + " §e!");
	}
}
