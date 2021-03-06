/*******************************************************************************
 * Copyright 2015 SteveKunG - More Planets Mod
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package stevekung.mods.moreplanets.moons.koentus.itemblocks;

import net.minecraft.block.Block;
import stevekung.mods.moreplanets.common.itemblocks.ItemBlockBaseMP;

public class ItemBlockKoentus extends ItemBlockBaseMP
{
	public ItemBlockKoentus(Block block)
	{
		super(block);
	}

	@Override
	public String[] getBlockVariantsName()
	{
		return new String[] { "surface_rock", "sub_surface_rock", "rock", "cobblestone", "tin_ore", "copper_ore", "white_crystal_ore", "emp_crystal_ore", "bacterial_fossil_ore", "white_crystal_block", "emp_crystal_block", "solid_meteoric_iron", "ancient_stone", "ancient_stone_brick", "dungeon_brick" };
	}
}