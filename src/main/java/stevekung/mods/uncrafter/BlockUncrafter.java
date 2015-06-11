package stevekung.mods.uncrafter;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockUncrafter extends BlockContainer implements ITileEntityProvider
{
	private IIcon[] textures;

	public BlockUncrafter(String name)
	{
		super(Material.wood);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setStepSound(soundTypeWood);
		this.setHardness(2.0F);
		this.setBlockName(name);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9)
	{
		player.openGui(Uncrafter.instance, -1, world, x, y, z);
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		TileEntityUncrafter uncrafter = (TileEntityUncrafter) world.getTileEntity(x, y, z);

		if (uncrafter != null)
		{
			for (int j1 = 0; j1 < uncrafter.getSizeInventory(); ++j1)
			{
				ItemStack itemstack = uncrafter.getStackInSlot(j1);

				if (itemstack != null)
				{
					float f = world.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
					EntityItem entityitem;

					for (float f2 = world.rand.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem))
					{
						int k1 = world.rand.nextInt(21) + 10;

						if (k1 > itemstack.stackSize)
						{
							k1 = itemstack.stackSize;
						}

						itemstack.stackSize -= k1;
						entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
						float f3 = 0.05F;
						entityitem.motionX = (float) world.rand.nextGaussian() * f3;
						entityitem.motionY = (float) world.rand.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float) world.rand.nextGaussian() * f3;

						if (itemstack.hasTagCompound())
						{
							entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						}
					}
				}
			}
			world.func_147453_f(x, y, z, block);
		}
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if (side >= 2)
		{
			return this.textures[2];
		}
		return this.textures[side];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.textures = new IIcon[3];
		this.textures[0] = par1IconRegister.registerIcon("uncrafter:uncrafter_bottom");
		this.textures[1] = par1IconRegister.registerIcon("uncrafter:uncrafter_top");
		this.textures[2] = par1IconRegister.registerIcon("uncrafter:uncrafter_side");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityUncrafter();
	}
}