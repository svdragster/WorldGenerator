package de.svdragster.worldgenerator;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import de.svdragster.worldgenerator.populators.TreePopulator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Created by Sven on 02.05.2017.
 */

@SuppressWarnings("deprecation")
public class WorldGenerator extends JavaPlugin implements Listener {
	
	private static WorldGenerator instance;
	private static World world = null;
	
	@SuppressWarnings("deprecation")
	CuboidClipboard tree;
	
	@Override
	public void onDisable() {
	
	}
	
	@Override
	public void onEnable() {
		instance = this;
		getServer().getPluginCommand("generate").setExecutor(new GenerateCommand());
		getServer().getPluginManager().registerEvents(this, this);
		
		this.tree = loadArea(new File(getDataFolder().getAbsolutePath() + File.separator + "tree.schematic"));
	}
	
	public static World getWorld() {
		if (world == null) {
			WorldCreator worldCreator = new WorldCreator("tspiele");
			worldCreator = worldCreator.generator(new Generator());
			//worldCreator = worldCreator.environment(World.Environment.NORMAL);
			world = Bukkit.getServer().createWorld(worldCreator);
		}
		return world;
	}
	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new Generator();
	}
	
	@EventHandler
	public void onWorldInit(WorldInitEvent event) {
		event.getWorld().getPopulators().add(new TreePopulator());
	}
	
	@SuppressWarnings("deprecation")
	public CuboidClipboard loadArea(File file) {
		try {
			return CuboidClipboard.loadSchematic(file);
		} catch (Exception e) {
			System.out.println("Error while loading schematic");
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public void paste(CuboidClipboard clipboard, World world, Vector origin) {
		EditSession es = new EditSession(new BukkitWorld(world), 999999999);
		try {
			clipboard.paste(es, origin, true);
		} catch (MaxChangedBlocksException e) {
			e.printStackTrace();
		}
	}
	
	
	public static WorldGenerator getInstance() {
		return instance;
	}
	
	public CuboidClipboard getTree() {
		return tree;
	}
}
