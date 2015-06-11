package stevekung.mods.tutorial;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import cpw.mods.fml.common.registry.GameRegistry;

public class TutorialBlocks
{
	public static Block greenEnder;
	public static Block ice;

	public static void init()
	{
		initBlocks();
		registerBlocks();
	}

	private static void initBlocks()
	{
		greenEnder = new BlockGreenEnder("green_ender");
		ice = new BlockCustomIce("test_ice");
	}

	private static void registerBlocks()
	{
		GameRegistry.registerBlock(greenEnder, greenEnder.getUnlocalizedName().replace("tile.", ""));
		GameRegistry.registerBlock(ice, ice.getUnlocalizedName().replace("tile.", ""));
	}
}