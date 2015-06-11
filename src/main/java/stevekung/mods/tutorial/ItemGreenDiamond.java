package stevekung.mods.tutorial;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemGreenDiamond extends Item
{
	public ItemGreenDiamond(String name)
	{
		super();
		this.setUnlocalizedName(name);
		this.setTextureName("youtuber:green_diamond");
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTab()
    {
        return TutorialCore.tutorial;
    }
}