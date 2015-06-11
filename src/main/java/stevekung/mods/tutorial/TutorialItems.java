package stevekung.mods.tutorial;

import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class TutorialItems
{
	public static Item greenDiamond;

	public static void init()
	{
		initItems();
		registerItems();
	}

	private static void initItems()
	{
		greenDiamond = new ItemGreenDiamond("green_diamond");
	}

	private static void registerItems()
	{
		GameRegistry.registerItem(greenDiamond, greenDiamond.getUnlocalizedName().replace("item.", ""));
	}
}