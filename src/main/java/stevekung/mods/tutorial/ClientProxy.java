package stevekung.mods.tutorial;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends ServerProxy
{
	@Override
	public void registerRenderer()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityGreenZombie.class, new RenderGreenZombie());
	}
}