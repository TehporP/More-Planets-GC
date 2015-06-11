package stevekung.mods.tutorial;

import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCustomIce extends BlockBreakable
{
	protected BlockCustomIce(String name)
	{
		super("ice", Material.ice, false);
		this.setBlockName(name);
		this.setStepSound(soundTypeGlass);
		this.setHardness(1.0F);
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		return super.shouldSideBeRendered(world, x, y, z, 1 - side);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn()
	{
		return TutorialCore.tutorial;
	}
}