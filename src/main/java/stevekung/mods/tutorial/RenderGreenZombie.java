package stevekung.mods.tutorial;

import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGreenZombie extends RenderLiving
{
    private static final ResourceLocation ghastTextures = new ResourceLocation("youtuber:textures/entity/green_zombie.png");

    public RenderGreenZombie()
    {
        super(new ModelPig(), 0.5F);
    }

    protected ResourceLocation getEntityTexture(EntityGreenZombie p_110775_1_)
    {
        return ghastTextures;
    }

    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return this.getEntityTexture((EntityGreenZombie)p_110775_1_);
    }
}