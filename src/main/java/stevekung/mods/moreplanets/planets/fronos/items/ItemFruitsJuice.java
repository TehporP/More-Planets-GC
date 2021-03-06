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
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stevekung.mods.moreplanets.common.items.ItemFoodMP;

public class ItemFruitsJuice extends ItemFoodMP
{
	public ItemFruitsJuice(String name)
	{
		super();
		this.setUnlocalizedName(name);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemStack)
	{
		return EnumAction.DRINK;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack itemStack, World world, EntityPlayer player)
	{
		--itemStack.stackSize;
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		this.onFoodEaten(itemStack, world, player);
		player.getFoodStats().addStats(this, itemStack);

		if (!player.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle)))
		{
			player.entityDropItem(new ItemStack(Items.glass_bottle, 1, 0), 0.0F);
		}

		if (!world.isRemote)
		{
			switch (itemStack.getItemDamage())
			{
			case 0:
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 2400, 1));
				break;
			case 1:
				player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 2400, 1));
				break;
			case 2:
				player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 2400, 1));
				break;
			case 3:
				player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 2400, 1));
				break;
			}
		}
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
		return 5;
	}

	@Override
	public float getSaturationModifier(ItemStack itemStack)
	{
		return 0.6F;
	}

	@Override
	public String[] getItemVariantsName()
	{
		return new String[] { "strawberry_juice", "berry_juice", "kiwi_juice", "lemon_juice" };
	}
}