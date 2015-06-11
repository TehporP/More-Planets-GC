package stevekung.mods.uncrafter;

import net.minecraft.item.ItemStack;

public class UncrafterRecipe
{
	private ItemStack recipeOutput;
	private ItemStack[] recipeInput;

	public UncrafterRecipe(ItemStack output, ItemStack[] input)
	{
		this.recipeOutput = output;
		this.recipeInput = input;
	}

	public ItemStack getRecipeOutput()
	{
		return this.recipeOutput;
	}

	public ItemStack[] getRecipeInput()
	{
		return this.recipeInput;
	}
}