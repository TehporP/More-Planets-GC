package stevekung.mods.tutorial;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockGreenEnder extends Block
{
	public BlockGreenEnder(String name)
	{
		super(Material.rock);
		this.setBlockName(name);
		this.setHardness(1.5F);
		this.setHarvestLevel("pickaxe", 0);
		this.setBlockTextureName("youtuber:green_egg");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn()
	{
		return TutorialCore.tutorial;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		for (int i = 0; i < 1; i++)
		{
			ret.add(new ItemStack(Blocks.dragon_egg, 1, 0));
		}
		for (int i = 0; i < 4; i++)
		{
			ret.add(new ItemStack(Items.ender_pearl, 1, 0));
		}
		return ret;
	}
}