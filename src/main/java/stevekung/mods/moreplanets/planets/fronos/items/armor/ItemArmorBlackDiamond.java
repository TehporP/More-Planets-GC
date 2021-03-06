/*******************************************************************************
 * Copyright 2015 SteveKunG - More Planets Mod
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package stevekung.mods.moreplanets.planets.fronos.items.armor;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import stevekung.mods.moreplanets.common.items.armor.ItemArmorMP;
import stevekung.mods.moreplanets.planets.fronos.items.FronosItems;

public class ItemArmorBlackDiamond extends ItemArmorMP
{
	public ItemArmorBlackDiamond(String name, ArmorMaterial material, int render, int type)
	{
		super(material, render, type);
		this.setUnlocalizedName(name);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		if (stack.getItem() == FronosArmorItems.black_diamond_helmet || stack.getItem() == FronosArmorItems.black_diamond_chestplate || stack.getItem() == FronosArmorItems.black_diamond_boots)
		{
			return "moreplanets:textures/model/armor/black_diamond_1.png";
		}
		if (stack.getItem() == FronosArmorItems.black_diamond_leggings)
		{
			return "moreplanets:textures/model/armor/black_diamond_2.png";
		}
		return null;
	}

	@Override
	public Item getRepairItems()
	{
		return FronosItems.fronos_item;
	}

	@Override
	public int getRepairItemsMetadata()
	{
		return 4;
	}
}