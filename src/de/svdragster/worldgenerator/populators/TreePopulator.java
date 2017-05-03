package de.svdragster.worldgenerator.populators;

import com.sk89q.worldedit.Vector;
import de.svdragster.worldgenerator.WorldGenerator;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

/**
 * Created by Sven on 02.05.2017.
 */
public class TreePopulator extends BlockPopulator {
	
	private static final int CHANCE = 5; // 1 out of 100
	private static final int MIN_HEIGHT = 5;
	private static final int MAX_HEIGHT = 15;
	
	@Override
	public void populate(World world, Random random, Chunk chunk) {
		int rand = random.nextInt(100);
		//Bukkit.getServer().broadcastMessage("Rand: " + rand);
		if (rand <= CHANCE) {
			//Bukkit.getServer().broadcastMessage("Tree");
			int centerX = (chunk.getX() << 4) + random.nextInt(16);
			int centerZ = (chunk.getZ() << 4) + random.nextInt(16);
			int centerY = world.getHighestBlockYAt(centerX, centerZ);
			if (world.getBlockAt(centerX, centerY - 1, centerZ).getType() != Material.GRASS) {
				//Bukkit.getServer().broadcastMessage("No Grass!");
				return;
			}
			
			WorldGenerator.getInstance().paste(WorldGenerator.getInstance().getTree(), world, new Vector(centerX, centerY, centerZ));
			/*int height = random.nextInt(MAX_HEIGHT);
			int currentHeight = 0;
			int leaves = 4;
			if (height < MIN_HEIGHT) {
				height = MIN_HEIGHT;
			}
			
			for (int y = centerY; y < centerY + height; y++) {
				Block top = world.getBlockAt(centerX, y, centerZ);
				if (currentHeight >= 4) {
					for (int x = centerX - leaves; x < centerX + leaves; x++) {
						for (int z = centerX - leaves; z < centerZ + leaves; z++) {
							world.getBlockAt(x, y, z).setType(Material.LEAVES);
						}
					}
					if (MAX_HEIGHT - currentHeight <= 4) {
						leaves--;
					}
				}
				top.setType(Material.LOG);
				currentHeight++;
			}*/
			
		}
	}
	
	
}
