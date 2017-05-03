package de.svdragster.worldgenerator.populators;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

/**
 * Created by Sven on 02.05.2017.
 */
public class FlowerPopulator extends BlockPopulator {
	
	@SuppressWarnings("deprecation")
	@Override
	public void populate(World world, Random random, Chunk chunk) {
		int xx = (chunk.getX() << 4);
		int zz = (chunk.getZ() << 4);
		for (int x = xx; x<xx+16; x++) {
			for (int z = zz; z<zz+16; z++) {
				int y = world.getHighestBlockYAt(x, z);
				if (world.getBlockAt(x, y - 1, z).getType() != Material.GRASS) {
					//Bukkit.getServer().broadcastMessage("No Grass!");
					return;
				}
				final int rand = random.nextInt(10);
				Block block = world.getBlockAt(x, y, z);
				if (rand <= 4) {
					block.setType(Material.RED_ROSE);
					block.setData((byte) random.nextInt(8));
				} else if (rand >= 5 && rand <= 9) {
					block.setType(Material.LONG_GRASS);
					block.setData((byte) 1);
				}
			}
		}
	}
	
	
}
