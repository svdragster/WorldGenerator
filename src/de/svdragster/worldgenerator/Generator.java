package de.svdragster.worldgenerator;

import de.svdragster.worldgenerator.populators.FlowerPopulator;
import de.svdragster.worldgenerator.populators.TreePopulator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Sven on 02.05.2017.
 */
public class Generator extends ChunkGenerator {
	
	private Material[] stone = new Material[]{Material.STONE, Material.COBBLESTONE, Material.ENDER_STONE, Material.CLAY_BRICK, Material.GLOWSTONE};
	
	//This is where you stick your populators - these will be covered in another tutorial
	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		return Arrays.asList(new TreePopulator(), new FlowerPopulator());
	}
	//This needs to be set to return true to override minecraft's default behaviour
	@Override
	public boolean canSpawn(World world, int x, int z) {
		return true;
	}
	//This converts relative chunk locations to bytes that can be written to the chunk
	public int xyzToByte(int x, int y, int z) {
		return (x * 16 + z) * 128 + y;
	}
	
	/*@SuppressWarnings("deprecation")
	@Override
	public byte[] generate(World world, Random rand, int chunkx, int chunkz) {
		byte[] result = new byte[32768];
		//This will set the floor of each chunk at bedrock level to bedrock
		for(int x=0; x<16; x++){
			for(int z=0; z<16; z++) {
				int y1 = world.getHighestBlockYAt(x+ 1, z);
				int y2 = world.getHighestBlockYAt(x - 1, z);
				int y3 = world.getHighestBlockYAt(x, z + 1);
				int y4 = world.getHighestBlockYAt(x, z - 1);
				if (y1 <= 1) {
					y1 = 95 + rand.nextInt(10);
				}
				if (y2 <= 1) {
					y2 = 95 + rand.nextInt(10);
				}
				if (y3 <= 1) {
					y3 = 95 + rand.nextInt(10);
				}
				if (y4 <= 1) {
					y4 = 95 + rand.nextInt(10);
				}
				int medium = (y1 + y2 + y3 + y4) / 4;
				for (int y = 0; y < medium; y++) {
					Material material;
					if (y == medium - 1) {
						material = Material.GRASS;
					} else if (y >= medium - 6) {
						material = Material.DIRT;
					} else if (y <= 1) {
						material = Material.BEDROCK;
					} else {
						material = stone[rand.nextInt(stone.length - 1)];
					}
					result[xyzToByte(x, y, z)] = (byte) material.getId();
				}
			}
		}
		return result;
	}*/
	
	@SuppressWarnings("deprecation")
	void setBlock(int x, int y, int z, byte[][] chunk, Material material) {
		if (y < 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0) {
			if (chunk[y >> 4] == null) {
				chunk[y >> 4] = new byte[16 * 16 * 16];
			}
			chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (byte) material.getId();
		}
	}
	
	private int multitude = 10; //how much we multiply the value between -1 and 1. It will determine how "steep" the hills will be.
	
	@SuppressWarnings("deprecation")
	@Override
	public byte[][] generateBlockSections(World world, Random dontuse, int ChunkX, int ChunkZ, BiomeGrid biome) {
		SimplexOctaveGenerator gen1 = new SimplexOctaveGenerator(world,8);
		gen1.setScale(1.0D / 64.0D);
		byte[][] chunk = new byte[world.getMaxHeight() / 16][];
		Random rand = new Random(world.getSeed());
		for (int x=0; x<16; x++) { //loop through all of the blocks in the chunk that are lower than maxHeight
			for (int z=0; z<16; z++) {
				int realX = x + ChunkX * 16; //used so that the noise function gives us
				int realZ = z + ChunkZ * 16; //different values each chunk
				double frequency = 0.5; // the reciprocal of the distance between points
				double amplitude = 0.5; // The distance between largest min and max values
				int sea_level = 64;
				
				double maxHeight = gen1.noise(realX, realZ, frequency, amplitude) * multitude + sea_level;
				for (int y=0; y<maxHeight; y++) {
					if (y >= maxHeight - 1) {
						setBlock(x, y, z, chunk, Material.GRASS);
					} else if (y >= maxHeight - 3){
						setBlock(x, y, z, chunk, Material.DIRT);
					} else {
						setBlock(x, y, z, chunk, Material.STONE);
					}
				}
			}
		}
		int r = rand.nextInt(10);
		if (r <= 1) {
			if (multitude < 20) {
				multitude += 1;
			}
		} else if (r <= 3) {
			if (multitude > 4) {
				multitude -= 1;
			}
		}
		return chunk;
	}
	
	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		return new Location(world, 0, 100, 0);
	}
}
