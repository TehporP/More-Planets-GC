/*******************************************************************************
 * Copyright 2015 SteveKunG - More Planets Mod
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package stevekung.mods.moreplanets.nibiru.items.armor;

import micdoodle8.mods.galacticraft.api.item.IBreathableArmor;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import stevekung.mods.moreplanets.core.proxy.ClientProxyMP;
import stevekung.mods.moreplanets.nibiru.core.ModuleNibiru;
import stevekung.mods.moreplanets.nibiru.items.NibiruItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NibiruArmorBreathableRedGem extends ItemArmor implements IBreathableArmor
{
	public NibiruArmorBreathableRedGem(int par1, EnumArmorMaterial par2EnumArmorMaterial, int par3, int par4)
	{
		super(par1, par2EnumArmorMaterial, par3, par4);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		if (stack.itemID == NibiruArmorItems.breathableRedGemHelmet.itemID)
		{
			return "nibiru:textures/model/armor/breathableRedGem_1.png";
		}
		return null;
	}

	@Override
	public CreativeTabs getCreativeTab()
	{
		return ModuleNibiru.nibiruTab;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack)
	{
		return ClientProxyMP.morePlanetItemRarity;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(this.getUnlocalizedName().replace("item.", "nibiru:"));
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		if (par2ItemStack.itemID == NibiruItems.nibiruBasicItem.itemID && par2ItemStack.getItemDamage() == 2)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean canBreathe(ItemStack par1ItemStack, EntityPlayer playerWearing, EnumGearType type)
	{
		if (par1ItemStack.itemID == NibiruArmorItems.breathableRedGemHelmet.itemID)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean handleGearType(EnumGearType gearType)
	{
		if (EnumGearType.HELMET != null)
		{
			return true;
		}
		return false;
	}

	@Override
	public Item setUnlocalizedName(String par1Str)
	{
		super.setTextureName(par1Str);
		super.setUnlocalizedName(par1Str);
		return this;
	}
}