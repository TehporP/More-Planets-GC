package stevekung.mods.tutorial;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityGreenZombie extends EntityZombie
{
	public EntityGreenZombie(World world)
	{
		super(world);
	}

	@Override
	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		for (int i = 0; i < 1; ++i)
		{
			this.entityDropItem(new ItemStack(TutorialItems.greenDiamond, this.rand.nextInt(4) + 1), 1.0F);
		}
	}
}