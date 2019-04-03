package fr.cactuscata.pcmanticheat.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import fr.cactuscata.pcmevent.command.CCommandExecutor;
import fr.cactuscata.pcmevent.enums.PrefixMessage;
import fr.cactuscata.pcmevent.utils.PlayerPcm;

public final class Verif extends CCommandExecutor {

	private static final Map<Player, PlayerPcm> verifiers = new HashMap<>();

	public Verif() {
		super(true, PrefixMessage.PREFIX, "Veuillez préciser le joueur !");
	}

	@Override
	public final void onCommand(final CommandSender sender, final String[] args) {

		final Player player = Bukkit.getPlayerExact(args[0]);
		if (player == null || !player.isOnline()) {
			sender.sendMessage(PrefixMessage.PREFIX + "Le joueur " + args[0] + " n'est pas connecté !");
			return;
		}

		final Inventory inventory = Bukkit.createInventory(null, 27, "§cVerif > " + args[0]);
		inventory.setItem(0, new ItemStack(Material.FEATHER));
		inventory.setItem(1, new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		inventory.setItem(2, new ItemStack(Material.BOW));
		inventory.setItem(3, new ItemStack(Material.DIAMOND_SWORD));
		inventory.setItem(4, new ItemStack(Material.GLASS));
		inventory.setItem(5, new ItemStack(Material.STICK));
		inventory.setItem(6, new ItemStack(Material.STICK));
		inventory.setItem(7, new ItemStack(new Potion(PotionType.INSTANT_HEAL, 1).toItemStack(1)));
		inventory.setItem(8, new ItemStack(Material.COOKED_BEEF));
		inventory.setItem(9, new ItemStack(Material.ENDER_PEARL));
		inventory.setItem(10, new ItemStack(Material.FIREWORK_CHARGE));
		inventory.setItem(11, new ItemStack(Material.FIREBALL));
		inventory.setItem(30, new ItemStack(Material.BOOK));
		inventory.setItem(31, new ItemStack(Material.EXP_BOTTLE));
		inventory.setItem(32, new ItemStack(Material.COMMAND));
		inventory.setItem(33, new ItemStack(Material.WATCH));
		verifiers.put((Player) sender, PlayerPcm.getPlayersPcm().get(player));
		((Player) sender).openInventory(inventory);

	}

	public static final Map<Player, PlayerPcm> getVerifiers() {
		return verifiers;
	}

}
