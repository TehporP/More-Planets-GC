/*******************************************************************************
 * Copyright 2015 SteveKunG - More Planets Mod
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package stevekung.mods.moreplanets.nibiru.core.render.tileentities;

import micdoodle8.mods.galacticraft.core.client.model.block.GCCoreModelTreasureChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import stevekung.mods.moreplanets.nibiru.tileentities.NibiruTileEntityTreasureChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class NibiruTileEntityTreasureChestRenderer extends TileEntitySpecialRenderer
{
	private static final ResourceLocation treasureChestTexture = new ResourceLocation("nibiru:textures/model/nibiruTreasureChest.png");

	private final GCCoreModelTreasureChest chestModel = new GCCoreModelTreasureChest();

	public void renderGCTileEntityTreasureChestAt(NibiruTileEntityTreasureChest par1GCTileEntityTreasureChest, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!par1GCTileEntityTreasureChest.hasWorldObj())
		{
			var9 = 0;
		}
		else
		{
			par1GCTileEntityTreasureChest.getBlockType();
			var9 = par1GCTileEntityTreasureChest.getBlockMetadata();
		}

		GCCoreModelTreasureChest var14 = null;
		var14 = this.chestModel;
		this.bindTexture(NibiruTileEntityTreasureChestRenderer.treasureChestTexture);

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) par2, (float) par4 + 1.0F, (float) par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		short var11 = 0;

		if (var9 == 2)
		{
			var11 = 180;
		}

		if (var9 == 3)
		{
			var11 = 0;
		}

		if (var9 == 4)
		{
			var11 = 90;
		}

		if (var9 == 5)
		{
			var11 = -90;
		}

		GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		float var12 = par1GCTileEntityTreasureChest.prevLidAngle + (par1GCTileEntityTreasureChest.lidAngle - par1GCTileEntityTreasureChest.prevLidAngle) * par8;

		var12 = 1.0F - var12;
		var12 = 1.0F - var12 * var12 * var12;

		if (var14 != null)
		{
			var14.chestLid.rotateAngleX = -(var12 * (float) Math.PI / 4.0F);
			var14.renderAll(!par1GCTileEntityTreasureChest.locked);
		}
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderGCTileEntityTreasureChestAt((NibiruTileEntityTreasureChest) par1TileEntity, par2, par4, par6, par8);
	}
}