/*******************************************************************************
 * Copyright 2015 SteveKunG - More Planets Mod
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package stevekung.mods.moreplanets.planets.fronos.world.gen.feature;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import stevekung.mods.moreplanets.common.blocks.IFronosGrass;

public class WorldGenFrostedCake extends WorldGenerator
{
	private IBlockState frostedCake;

	public WorldGenFrostedCake(IBlockState state)
	{
		this.frostedCake = state;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
		for (int i = 0; i < 64; ++i)
		{
			BlockPos blockpos1 = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

			if (world.isAirBlock(blockpos1) && world.getBlockState(blockpos1.down()).getBlock() instanceof IFronosGrass)
			{
				world.setBlockState(blockpos1, this.frostedCake, 2);
			}
		}
		return true;
	}
}