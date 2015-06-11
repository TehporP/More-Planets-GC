package stevekung.mods.uncrafter;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import stevekung.mods.stevecore.RegisterHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "Uncrafter", name = "Uncrafter", version = "1.8.0", dependencies = "after:Forge@[10.12.2.1147,);")
public class Uncrafter
{
	@SidedProxy(clientSide = "stevekung.mods.uncrafter.ClientProxy", serverSide = "stevekung.mods.uncrafter.CommonProxy")
	public static CommonProxy proxy;

	@Instance("Uncrafter")
	public static Uncrafter instance;

	public static Block uncrafter;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Uncrafter.uncrafter = new BlockUncrafter("uncrafter");
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new UncrafterGuiHandler());
		RegisterHelper.registerBlock(Uncrafter.uncrafter);
		LanguageRegistry.addName(Uncrafter.uncrafter, "Uncrafter");
		GameRegistry.registerTileEntity(TileEntityUncrafter.class, "Uncrafter");
		GameRegistry.addShapedRecipe(new ItemStack(Uncrafter.uncrafter), new Object[] { "III", "ICI", "PRP", 'I', Items.iron_ingot, 'C', Blocks.crafting_table, 'P', Blocks.piston, 'R', Items.redstone });
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		UncrafterRecipeManager.instance = new UncrafterRecipeManager();
	}
}
