/*******************************************************************************
 * Copyright 2015 SteveKunG - More Planets Mod
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package stevekung.mods.moreplanets.planets.polongnius.entities;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import stevekung.mods.moreplanets.planets.polongnius.blocks.PolongniusBlocks;

public class EntityPolongniusMeteor extends Entity
{
	public EntityLiving shootingEntity;
	public int size;

	public EntityPolongniusMeteor(World world)
	{
		super(world);
	}

	public EntityPolongniusMeteor(World world, double x, double y, double z, double motX, double motY, double motZ, int size)
	{
		this(world);
		this.size = size;
		this.setSize(1.0F, 1.0F);
		this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
		this.setPosition(x, y, z);
		this.motionX = motX;
		this.motionY = motY;
		this.motionZ = motZ;
		this.setSize(size);
	}

	@Override
	public void onUpdate()
	{
		this.setRotation(this.rotationYaw + 2F, this.rotationPitch + 2F);
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionY -= 0.03999999910593033D;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);

		if (this.worldObj.isRemote)
		{
			this.spawnParticles();
		}

		Vec3 var15 = new Vec3(this.posX, this.posY, this.posZ);
		Vec3 var2 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		MovingObjectPosition var3 = this.worldObj.rayTraceBlocks(var15, var2, true, true, false);
		var15 = new Vec3(this.posX, this.posY, this.posZ);
		var2 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

		if (var3 != null)
		{
			var2 = new Vec3(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
		}

		Entity var4 = null;
		List<?> var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(2.0D, 2.0D, 2.0D));
		double var6 = 0.0D;
		Iterator<?> var8 = var5.iterator();

		while (var8.hasNext())
		{
			Entity var9 = (Entity) var8.next();

			if (var9.canBeCollidedWith() && !var9.isEntityEqual(this.shootingEntity))
			{
				float var10 = 0.01F;
				AxisAlignedBB var11 = var9.getEntityBoundingBox().expand(var10, var10, var10);
				MovingObjectPosition var12 = var11.calculateIntercept(var15, var2);

				if (var12 != null)
				{
					double var13 = var15.distanceTo(var12.hitVec);

					if (var13 < var6 || var6 == 0.0D)
					{
						var4 = var9;
						var6 = var13;
					}
				}
			}
		}

		if (var4 != null)
		{
			var3 = new MovingObjectPosition(var4);
		}
		if (var3 != null)
		{
			this.onImpact(var3);
		}
		if (this.posY <= -20 || this.posY >= 400)
		{
			this.setDead();
		}
	}

	protected void spawnParticles()
	{
		//		GalacticraftCore.proxy.spawnParticle("distanceSmoke", new Vector3(this.posX, this.posY + 1D + Math.random(), this.posZ), new Vector3(0.0D, 0.0D, 0.0D), new Object[] { });
		//		GalacticraftCore.proxy.spawnParticle("distanceSmoke", new Vector3(this.posX + Math.random() / 2, this.posY + 1D + Math.random() / 2, this.posZ), new Vector3(0.0D, 0.0D, 0.0D), new Object[] { });
		//		GalacticraftCore.proxy.spawnParticle("distanceSmoke", new Vector3(this.posX, this.posY + 1D + Math.random(), this.posZ + Math.random()), new Vector3(0.0D, 0.0D, 0.0D), new Object[] { });
		//		GalacticraftCore.proxy.spawnParticle("distanceSmoke", new Vector3(this.posX - Math.random() / 2, this.posY + 1D + Math.random() / 2, this.posZ), new Vector3(0.0D, 0.0D, 0.0D), new Object[] { });
		//		GalacticraftCore.proxy.spawnParticle("distanceSmoke", new Vector3(this.posX, this.posY + 1D + Math.random(), this.posZ - Math.random()), new Vector3(0.0D, 0.0D, 0.0D), new Object[] { });
	}

	protected void onImpact(MovingObjectPosition moving)
	{
		if (!this.worldObj.isRemote)
		{
			if (moving != null)
			{
				BlockPos pos = moving.getBlockPos().offset(moving.sideHit);

				if (this.worldObj.isAirBlock(pos))
				{
					this.worldObj.setBlockState(moving.getBlockPos(), PolongniusBlocks.fallen_polongnius_meteor.getDefaultState(), 3);
				}
				if (moving.entityHit != null)
				{
					moving.entityHit.attackEntityFrom(EntityPolongniusMeteor.causeMeteorDamage(this, this.shootingEntity), 6);
				}
			}
			this.worldObj.newExplosion((Entity) null, this.posX, this.posY, this.posZ, this.size / 3 + 2, false, true);
		}
		this.setDead();
	}

	public static DamageSource causeMeteorDamage(EntityPolongniusMeteor meteor, Entity entity)
	{
		if (entity != null && entity instanceof EntityPlayer)
		{
			StatCollector.translateToLocalFormatted("death." + "meteor", ((EntityPlayer)entity).getGameProfile().getName() + " was hit by a meteor! That's gotta hurt!");
		}
		return new EntityDamageSourceIndirect("explosion", meteor, entity).setProjectile();
	}

	@Override
	protected void entityInit()
	{
		this.dataWatcher.addObject(16, this.size);
		this.noClip = true;
	}

	public int getSize()
	{
		return this.dataWatcher.getWatchableObjectInt(16);
	}

	public void setSize(int size)
	{
		this.dataWatcher.updateObject(16, Integer.valueOf(size));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt)
	{
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt)
	{
	}
}