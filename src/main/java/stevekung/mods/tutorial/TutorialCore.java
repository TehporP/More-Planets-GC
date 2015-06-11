package stevekung.mods.tutorial;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(name = TutorialCore.NAME, version = TutorialCore.VERSION, modid = TutorialCore.MOD_ID)
public class TutorialCore
{
	public static final String NAME = "Tutorial Mod";
	public static final String VERSION = "1.0.0";
	public static final String MOD_ID = "Tutorial";

	@SidedProxy(clientSide = "stevekung.mods.tutorial.ClientProxy", serverSide = "stevekung.mods.tutorial.ServerProxy")
	public static ServerProxy proxy;

	@Instance(TutorialCore.MOD_ID)
	public static TutorialCore instance;
	
	public static CreativeTabs tutorial;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		TutorialBlocks.init();
		TutorialItems.init();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		tutorial = new CreativeTabs("tutorial")
		{
			@Override
			public Item getTabIconItem()
			{
				return null;
			}
			
			@Override
		    @SideOnly(Side.CLIENT)
		    public ItemStack getIconItemStack()
		    {
		        return new ItemStack(TutorialItems.greenDiamond);
		    }
		};
		EntityRegistry.registerGlobalEntityID(EntityGreenZombie.class, "GreenZombie", EntityRegistry.findGlobalUniqueEntityId(), -16744448, -16731392);
		proxy.registerRenderer();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		GameRegistry.addShapelessRecipe(new ItemStack(TutorialItems.greenDiamond), new ItemStack(Items.diamond), new ItemStack(Blocks.tallgrass, 1, 1) );
	}
}