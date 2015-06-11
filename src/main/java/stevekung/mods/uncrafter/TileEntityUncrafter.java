package stevekung.mods.uncrafter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityUncrafter extends TileEntity implements IInventory, ISidedInventory
{
	private ItemStack[] inventoryContents;

	public TileEntityUncrafter()
	{
		this.inventoryContents = new ItemStack[10];
	}

	@Override
	public int getSizeInventory()
	{
		return this.inventoryContents.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return this.inventoryContents[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack)
	{
		this.inventoryContents[slot] = itemStack;

		if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit())
		{
			itemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public ItemStack decrStackSize(int slot, int count)
	{
		ItemStack stack = this.getStackInSlot(slot);

		if (stack != null)
		{
			if (stack.stackSize <= count)
			{
				this.setInventorySlotContents(slot, null);
			}
			else
			{
				stack = stack.splitStack(count);

				if (stack.stackSize == 0)
				{
					this.setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		ItemStack stack = this.getStackInSlot(slot);

		if (stack != null)
		{
			this.setInventorySlotContents(slot, null);
		}
		return stack;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) < 64.0D;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("Inventory", 10);

		for (int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound compound = list.getCompoundTagAt(i);
			byte slot = compound.getByte("Slot");

			if (slot >= 0 && slot < this.inventoryContents.length)
			{
				this.inventoryContents[slot] = ItemStack.loadItemStackFromNBT(compound);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();

		for (int i = 0; i < this.inventoryContents.length; i++)
		{
			ItemStack stack = this.inventoryContents[i];

			if (stack != null)
			{
				NBTTagCompound compound = new NBTTagCompound();
				compound.setByte("Slot", (byte)i);
				stack.writeToNBT(compound);
				list.appendTag(compound);
			}
		}
		nbt.setTag("Inventory", list);
	}

	@Override
	public String getInventoryName()
	{
		return "Uncrafter";
	}

	@Override
	public boolean canInsertItem(int par1, ItemStack itemStack, int par3)
	{
		return this.isStackValidForSlot(par1, itemStack);
	}

	@Override
	public boolean canExtractItem(int par1, ItemStack itemStack, int par3)
	{
		return true;
	}

	@Override
	public void updateEntity()
	{
		if (this.worldObj.isRemote)
		{
			return;
		}

		if (this.inventoryContents[0] != null)
		{
			for (UncrafterRecipe currentRecipe : UncrafterRecipeManager.instance.recipes)
			{
				if (currentRecipe.getRecipeOutput().getItem() == this.inventoryContents[0].getItem() && this.inventoryContents[0].stackSize >= currentRecipe.getRecipeOutput().stackSize && currentRecipe.getRecipeOutput().getItemDamage() == this.inventoryContents[0].getItemDamage())
				{
					if (this.getUncraft(currentRecipe))
					{
						this.uncraft(currentRecipe);
						return;
					}
				}
			}
		}
	}

	private boolean getUncraft(UncrafterRecipe recipe)
	{
		for (int counter = 0; counter < 9; counter++)
		{
			if (recipe.getRecipeInput()[counter] == null && this.inventoryContents[counter + 1] != null)
			{
				return false;
			}
			if (recipe.getRecipeInput()[counter] != null && this.inventoryContents[counter + 1] != null)
			{
				if (this.inventoryContents[counter + 1].stackSize == this.inventoryContents[counter + 1].stackSize || this.inventoryContents[counter + 1] != recipe.getRecipeInput()[counter] || this.inventoryContents[counter + 1].getItemDamage() != recipe.getRecipeInput()[counter].getItemDamage())
				{
					return false;
				}
			}
		}
		return true;
	}

	private void uncraft(UncrafterRecipe recipe)
	{
		for (int counter = 0; counter < 9; counter++)
		{
			if (recipe.getRecipeInput()[counter] != null)
			{
				if (this.inventoryContents[counter + 1] == null)
				{
					this.inventoryContents[counter + 1] = new ItemStack(recipe.getRecipeInput()[counter].getItem(), 1, recipe.getRecipeInput()[counter].getItemDamage());
				}
				else
				{
					this.inventoryContents[counter + 1].stackSize += 1;
				}
			}
		}
		if (this.inventoryContents[0].stackSize == recipe.getRecipeOutput().stackSize)
		{
			this.inventoryContents[0] = null;
		}
		else
		{
			this.inventoryContents[0].stackSize -= recipe.getRecipeOutput().stackSize;
		}
		this.markDirty();
	}

	public boolean isStackValidForSlot(int i, ItemStack itemStack)
	{
		return i < 2;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return this.isStackValidForSlot(i, itemstack);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		if (side == 0)
		{
			return new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		}
		return new int[] { 0 };
	}

	@Override
	public void closeInventory()
	{
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public void openInventory()
	{
	}
}