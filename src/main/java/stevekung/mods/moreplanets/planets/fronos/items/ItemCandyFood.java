/*******************************************************************************
 * Copyright 2015 SteveKunG - More Planets Mod
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package stevekung.mods.moreplanets.planets.fronos.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stevekung.mods.moreplanets.common.items.ItemFoodMP;

public class ItemCandyFood extends ItemFoodMP
{
	private static int[] foodHunger = new int[] {
		2,
		5,
		4
	};
	private static float[] foodSaturation = new float[] {
		0.2F,
		0.6F,
		0.3F
	};

	public ItemCandyFood(String name)
	{
		super();
		this.setUnlocalizedName(name);
		this.setHasSubtypes(true);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemStack)
	{
		return EnumAction.EAT;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack itemStack, World world, EntityPlayer player)
	{
		--itemStack.stackSize;
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		this.onFoodEaten(itemStack, world, player);
		player.getFoodStats().addStats(this, itemStack);
		return itemStack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
	{
		for (int i = 0; i < this.getItemVariantsName().length; i++)
		{
			list.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public int getHealAmount(ItemStack itemStack)
	{
		return foodHunger[itemStack.getItemDamage()];
	}

	@Override
	public float getSaturationModifier(ItemStack itemStack)
	{
		return foodSaturation[itemStack.getItemDamage()];
	}

	@Override
	public String[] getItemVariantsName()
	{
		return new String[] { "ovantine_powder", "chocolate_bars", "caramel" };
	}
}