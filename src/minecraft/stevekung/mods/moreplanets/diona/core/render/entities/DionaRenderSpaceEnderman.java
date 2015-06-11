/*******************************************************************************
 * Copyright 2015 SteveKunG - More Planets Mod
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package stevekung.mods.moreplanets.diona.core.render.entities;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import stevekung.mods.moreplanets.diona.core.models.entities.DionaModelSpaceEnderman;
import stevekung.mods.moreplanets.diona.entities.DionaEntitySpaceEnderman;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DionaRenderSpaceEnderman extends RenderLiving
{
	private static final ResourceLocation endermanEyesTexture = new ResourceLocation("diona:textures/model/spaceEnderman/endermanEyesSpace.png");
	private static final ResourceLocation endermanTextures = new ResourceLocation("diona:textures/model/spaceEnderman/endermanSpace.png");

	private DionaModelSpaceEnderman endermanModel;
	private Random rnd = new Random();

	public DionaRenderSpaceEnderman()
	{
		super(new DionaModelSpaceEnderman(), 0.5F);
		this.endermanModel = (DionaModelSpaceEnderman)super.mainModel;
		this.setRenderPassModel(this.endermanModel);
	}

	public void renderEnderman(DionaEntitySpaceEnderman par1, double par2, double par4, double par6, float par8, float par9)
	{
		this.endermanModel.isCarrying = par1.getCarried() > 0;
		this.endermanModel.isAttacking = par1.isScreaming();

		if (par1.isScreaming())
		{
			double d3 = 0.02D;
			par2 += this.rnd.nextGaussian() * d3;
			par6 += this.rnd.nextGaussian() * d3;
		}

		super.doRenderLiving(par1, par2, par4, par6, par8, par9);
	}

	protected ResourceLocation getEndermanTextures(DionaEntitySpaceEnderman par1)
	{
		return endermanTextures;
	}

	protected void renderCarrying(DionaEntitySpaceEnderman par1, float par2)
	{
		super.renderEquippedItems(par1, par2);

		if (par1.getCarried() > 0)
		{
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glPushMatrix();
			float f1 = 0.5F;
			GL11.glTranslatef(0.0F, 0.6875F, -0.75F);
			f1 *= 1.0F;
			GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(-f1, -f1, f1);
			int i = par1.getBrightnessForRender(par2);
			int j = i % 65536;
			int k = i / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.bindTexture(TextureMap.locationBlocksTexture);
			this.renderBlocks.renderBlockAsItem(Block.blocksList[par1.getCarried()], par1.getCarryingData(), 1.0F);
			GL11.glPopMatrix();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}
	}

	protected int renderEyes(DionaEntitySpaceEnderman par1, int par2, float par3)
	{
		if (par2 != 0)
		{
			return -1;
		}
		else
		{
			this.bindTexture(endermanEyesTexture);
			float f1 = 1.5F;
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			GL11.glDisable(GL11.GL_LIGHTING);

			if (par1.isInvisible())
			{
				GL11.glDepthMask(false);
			}
			else
			{
				GL11.glDepthMask(true);
			}

			char c0 = 61680;
			int j = c0 % 65536;
			int k = c0 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
			return 1;
		}
	}

	@Override
	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		this.renderEnderman((DionaEntitySpaceEnderman)par1EntityLiving, par2, par4, par6, par8, par9);
	}

	@Override
	protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
	{
		return this.renderEyes((DionaEntitySpaceEnderman)par1EntityLivingBase, par2, par3);
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
	{
		this.renderCarrying((DionaEntitySpaceEnderman)par1EntityLivingBase, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return this.getEndermanTextures((DionaEntitySpaceEnderman)par1Entity);
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		this.renderEnderman((DionaEntitySpaceEnderman)par1Entity, par2, par4, par6, par8, par9);
	}
}