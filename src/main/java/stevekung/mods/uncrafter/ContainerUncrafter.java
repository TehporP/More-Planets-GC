package stevekung.mods.uncrafter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerUncrafter extends Container
{
	protected TileEntityUncrafter tileEntity;

	public ContainerUncrafter(InventoryPlayer inventoryPlayer, TileEntityUncrafter te)
	{
		this.tileEntity = te;
		this.addSlotToContainer(new Slot(this.tileEntity, 0, 36, 35));
		this.addSlotToContainer(new Slot(this.tileEntity, 1, 94, 17));
		this.addSlotToContainer(new Slot(this.tileEntity, 4, 94, 35));
		this.addSlotToContainer(new Slot(this.tileEntity, 7, 94, 53));
		this.addSlotToContainer(new Slot(this.tileEntity, 2, 112, 17));
		this.addSlotToContainer(new Slot(this.tileEntity, 5, 112, 35));
		this.addSlotToContainer(new Slot(this.tileEntity, 8, 112, 53));
		this.addSlotToContainer(new Slot(this.tileEntity, 3, 130, 17));
		this.addSlotToContainer(new Slot(this.tileEntity, 6, 130, 35));
		this.addSlotToContainer(new Slot(this.tileEntity, 9, 130, 53));
		this.bindPlayerInventory(inventoryPlayer);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.tileEntity.isUseableByPlayer(player);
	}

	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for (int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int par2)
	{
		ItemStack itemStack = null;
		Slot slot = (Slot)this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemStack2 = slot.getStack();
			itemStack = itemStack2.copy();

			if (par2 < 10)
			{
				if (!this.mergeItemStack(itemStack2, 10, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(itemStack2, 0, 10, false))
			{
				return null;
			}
			if (itemStack2.stackSize == 0)
			{
				slot.putStack((ItemStack)null);
			}
			else
			{
				slot.onSlotChanged();
			}
		}
		return itemStack;
	}
}