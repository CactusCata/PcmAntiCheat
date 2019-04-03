package fr.cactuscata.pcmanticheat.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import fr.cactuscata.pcmevent.command.CCommandExecutor;
import fr.cactuscata.pcmevent.enums.PrefixMessage;

public class Cheater extends CCommandExecutor {

	public Cheater() {
		super(false, PrefixMessage.PREFIX, "Veuillez préciser l'action");
	}

	@Override
	public final void onCommand(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	protected List<String> onTabCompletation(String[] args) {

		return null;
	}

}
