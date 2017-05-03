package de.svdragster.worldgenerator;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Sven on 02.05.2017.
 */
public class GenerateCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if (commandSender instanceof Player) {
			final Player player = (Player) commandSender;
			player.sendMessage("Hold on...");
			World world = WorldGenerator.getWorld();
			player.sendMessage("Woosh! " + world.getName() + ", "
					+ world.getBlockAt(0, 10, 0) + ", "
					+ world.getBlockAt(0, 0, 0));
			player.setGameMode(GameMode.CREATIVE);
			player.teleport(new Location(world, 0, 110, 0));
		}
		return true;
	}
}
