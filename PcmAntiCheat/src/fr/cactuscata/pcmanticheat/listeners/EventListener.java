package fr.cactuscata.pcmanticheat.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import fr.cactuscata.pcmanticheat.cheats.list.AutoClick;
import fr.cactuscata.pcmanticheat.cheats.list.FastBow;
import fr.cactuscata.pcmanticheat.cheats.list.Fly;
import fr.cactuscata.pcmanticheat.cheats.list.Reach;
import fr.cactuscata.pcmevent.utils.PlayerPcm;

public final class EventListener implements Listener {

	private final Plugin plugin;
	private Location locationCache;

	public EventListener(final Plugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public final void onTakeDamage(final EntityDamageByEntityEvent event) {

		if (((event.getEntity() instanceof Player)) && ((event.getDamager() instanceof LivingEntity))
				&& ((event.getCause() == DamageCause.ENTITY_ATTACK)
						|| (event.getCause() == DamageCause.ENTITY_EXPLOSION)
						|| (event.getCause() == DamageCause.PROJECTILE))) {
			final Player p = (Player) event.getEntity();

			if ((p.getLocation().getBlock().getRelative(-1, 0, 0).getType() != Material.AIR)
					|| (p.getLocation().getBlock().getRelative(1, 0, 0).getType() != Material.AIR)
					|| (p.getLocation().getBlock().getRelative(0, 0, -1).getType() != Material.AIR)
					|| (p.getLocation().getBlock().getRelative(0, 0, 1).getType() != Material.AIR)) {
				return;
			}
			final Location from = p.getLocation();

			Bukkit.getScheduler().runTaskLater(this.plugin, new Runnable() {
				public void run() {
					EventListener.this.locationCache = p.getLocation();
				}
			}, 1L);
			if (this.locationCache != null) {
				final Vector vec = this.locationCache.toVector();
				final double i = vec.distance(from.toVector());
				final PlayerPcm playerPcm = PlayerPcm.getPlayersPcm().get(p);
				if ((playerPcm != null) && (i < 0.75D))
					playerPcm.getCheats().getAntiKockBack().addWarn(null, playerPcm);

			}
		}

		if (((event.getDamager() instanceof Player)) && ((event.getEntity() instanceof LivingEntity))
				&& (event.getCause() != DamageCause.PROJECTILE)) {
			Player p = (Player) event.getDamager();

			Reach reach = PlayerPcm.getPlayersPcm().get(p).getCheats().getReach();
			Location victim = event.getEntity().getLocation();

			double reachDistance = p.getLocation().distance(victim);
			if (reachDistance > 4.2D) {
				reach.addWarn(reachDistance + "", PlayerPcm.getPlayersPcm().get(p));
			}
		}

	}

	@EventHandler
	public void fastbow(EntityShootBowEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;

		if (e.getForce() != 1.0D)
			return;
		final PlayerPcm playerPcm = PlayerPcm.getPlayersPcm().get((Player) e.getEntity());
		final FastBow fastBow = playerPcm.getCheats().getFastBow();

		fastBow.addCount();
		if (fastBow.getCount() > 2) {
			fastBow.addWarn(null, playerPcm);
			fastBow.resetCount();
		}
	}

	@EventHandler
	public void fly(PlayerMoveEvent event) {

		PlayerPcm playerPcm = PlayerPcm.getPlayersPcm().get(event.getPlayer());
		Fly fly = playerPcm.getCheats().getFly();

		Location to = event.getTo();
		Location from = event.getFrom();

		double i = to.distance(from);
		if ((player.getFallDistance() == 0.0D) && (i > 1.0D) && (!player.getAllowFlight()))
			fly.addWarn(null, playerPcm);

	}

	@EventHandler
	public final void onInventoryClick(final InventoryClickEvent event) {
		if ((event.getClickedInventory() != null) && (event.getClickedInventory().getTitle().startsWith("§cVerif >")))
			event.setCancelled(true);
	}

	@EventHandler
	public final void onClick(final PlayerInteractEvent event) {
		final PlayerPcm playerPcm = PlayerPcm.getPlayersPcm().get(event.getPlayer());
		final AutoClick autoClick;

		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
			autoClick = playerPcm.getCheats().getAutoLeftClick();
		else
			autoClick = playerPcm.getCheats().getAutoRightClick();

		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_AIR) {
			if ((autoClick.getLastBlockInteraction() > System.currentTimeMillis()) && (autoClick.getClicks() >= 10))
				event.setCancelled(true);

			if (autoClick.getClicks() > autoClick.getMaxClick())
				autoClick.setMaxClick(autoClick.getClicks());

			autoClick.setClicks(autoClick.getClicks() + 1);
		} else if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			autoClick.setLastBlockInteraction((System.currentTimeMillis() + 5000L));
		}
	}

}
