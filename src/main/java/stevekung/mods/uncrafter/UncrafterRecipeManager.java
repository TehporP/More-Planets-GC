package stevekung.mods.uncrafter;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.FMLLog;

public class UncrafterRecipeManager
{
	public static UncrafterRecipeManager instance;
	public ArrayList<UncrafterRecipe> recipes = new ArrayList();
	private ArrayList<ItemStack> warnedItems = new ArrayList();

	public UncrafterRecipeManager()
	{
		for (Object currentRecipe : CraftingManager.getInstance().getRecipeList())
		{
			try
			{
				if (currentRecipe instanceof ShapedRecipes)
				{
					this.addShapedRecipe((ShapedRecipes)currentRecipe);
				}
				else if (currentRecipe instanceof ShapelessRecipes)
				{
					this.addShapelessRecipe((ShapelessRecipes)currentRecipe);
				}
				else if (currentRecipe instanceof ShapedOreRecipe)
				{
					this.addShapedOreDictRecipe((ShapedOreRecipe)currentRecipe);
				}
				else if (currentRecipe instanceof ShapelessOreRecipe)
				{
					this.addShapelessOreDictRecipe((ShapelessOreRecipe)currentRecipe);
				}
			}
			catch (Exception e) {}
		}
	}

	private void addShapedRecipe(ShapedRecipes recipe)
	{
		if (!this.checkOutputExists(this.convertStack(recipe.getRecipeOutput())))
		{
			ItemStack[] newRecipe = new ItemStack[9];

			for (byte counter = 0; counter < 9; counter = (byte)(counter + 1))
			{
				newRecipe[counter] = null;
			}
			for (byte counter = 0; counter < recipe.recipeItems.length; counter = (byte)(counter + 1))
			{
				newRecipe[counter] = this.convertStack(recipe.recipeItems[counter]);
			}

			if (recipe.recipeWidth == 2)
			{
				newRecipe[7] = newRecipe[5];
				newRecipe[6] = newRecipe[4];
				newRecipe[4] = newRecipe[3];
				newRecipe[3] = newRecipe[2];
				newRecipe[2] = null;
				newRecipe[5] = null;
				newRecipe[8] = null;
			}
			if (recipe.recipeWidth == 1)
			{
				newRecipe[7] = newRecipe[2];
				newRecipe[4] = newRecipe[1];
				newRecipe[1] = newRecipe[0];
				newRecipe[0] = null;
				newRecipe[2] = null;
			}
			if (recipe.recipeHeight == 1)
			{
				newRecipe[3] = newRecipe[0];
				newRecipe[4] = newRecipe[1];
				newRecipe[5] = newRecipe[2];
				newRecipe[0] = null;
				newRecipe[1] = null;
				newRecipe[2] = null;
			}

			UncrafterRecipe newRecipe2 = new UncrafterRecipe(recipe.getRecipeOutput(), newRecipe);

			if (!this.checkContainerItems(newRecipe2))
			{
				this.recipes.add(newRecipe2);
			}
		}
	}

	private void addShapelessRecipe(ShapelessRecipes recipe)
	{
		if (!this.checkOutputExists(this.convertStack(recipe.getRecipeOutput())))
		{
			ItemStack[] newRecipe = new ItemStack[9];

			for (byte counter = 0; counter < 9; counter = (byte)(counter + 1))
			{
				newRecipe[counter] = null;
			}
			for (byte counter = 0; counter < recipe.recipeItems.size(); counter = (byte)(counter + 1))
			{
				newRecipe[counter] = this.convertStack(recipe.recipeItems.get(counter));
			}

			UncrafterRecipe newRecipe2 = new UncrafterRecipe(recipe.getRecipeOutput(), newRecipe);

			if (!this.checkContainerItems(newRecipe2))
			{
				this.recipes.add(newRecipe2);
			}
		}
	}

	private void addShapedOreDictRecipe(ShapedOreRecipe recipe)
	{
		if (!this.checkOutputExists(this.convertStack(recipe.getRecipeOutput())))
		{
			ItemStack[] newRecipe = new ItemStack[9];

			for (byte counter = 0; counter < 9; counter = (byte)(counter + 1))
			{
				newRecipe[counter] = null;
			}
			for (byte counter = 0; counter < recipe.getInput().length; counter = (byte)(counter + 1))
			{
				newRecipe[counter] = this.convertStack(recipe.getInput()[counter]);
			}

			UncrafterRecipe newRecipe2 = new UncrafterRecipe(recipe.getRecipeOutput(), newRecipe);

			if (!this.checkContainerItems(newRecipe2))
			{
				this.recipes.add(newRecipe2);
			}
		}
	}

	private void addShapelessOreDictRecipe(ShapelessOreRecipe recipe)
	{
		if (!this.checkOutputExists(this.convertStack(recipe.getRecipeOutput())))
		{
			ItemStack[] newRecipe = new ItemStack[9];

			for (byte counter = 0; counter < 9; counter = (byte)(counter + 1))
			{
				newRecipe[counter] = null;
			}
			for (byte counter = 0; counter < recipe.getInput().size(); counter = (byte)(counter + 1))
			{
				newRecipe[counter] = this.convertStack(recipe.getInput().get(counter));
			}

			UncrafterRecipe newRecipe2 = new UncrafterRecipe(recipe.getRecipeOutput(), newRecipe);

			if (!this.checkContainerItems(newRecipe2))
			{
				this.recipes.add(newRecipe2);
			}
		}
	}

	private ItemStack convertStack(Object stack)
	{
		ItemStack result = null;

		if (stack instanceof ItemStack)
		{
			result = ((ItemStack)stack).copy();
		}
		if (stack instanceof ArrayList)
		{
			if (((ArrayList)stack).size() > 0)
			{
				for (Object currentStack : (ArrayList)stack)
				{
					if (currentStack instanceof ItemStack)
					{
						result = ((ItemStack)currentStack).copy();
					}
				}
			}
		}
		if (result != null)
		{
			if (result.getItemDamage() < 0)
			{
				result.setItemDamage(0);
			}
			if (result.getItemDamage() == 32767)
			{
				result.setItemDamage(0);
			}
		}
		return result;
	}

	private boolean checkOutputExists(ItemStack recipeOutput)
	{
		for (UncrafterRecipe currentRecipe : this.recipes)
		{
			if (recipeOutput.getItem() == currentRecipe.getRecipeOutput().getItem() && recipeOutput.getItemDamage() == currentRecipe.getRecipeOutput().getItemDamage())
			{
				for (ItemStack currentStack : this.warnedItems)
				{
					if (recipeOutput.getItem() == currentStack.getItem() && recipeOutput.getItemDamage() == currentStack.getItemDamage())
					{
						return true;
					}
				}
				this.warnedItems.add(recipeOutput);
				FMLLog.warning("[Uncrafter] Found multiple recipes with the same output! Item: %d, Metadata: %d", new Object[] { Integer.valueOf(recipeOutput.getItem().getUnlocalizedName()), Integer.valueOf(recipeOutput.getItemDamage()) });
				return true;
			}
		}
		return false;
	}

	private boolean checkContainerItems(UncrafterRecipe recipe)
	{
		boolean result = false;

		for (ItemStack stack : recipe.getRecipeInput())
		{
			if (stack != null)
			{
				result = result || stack.getItem().hasContainerItem(stack);
			}
		}
		return result;
	}
}