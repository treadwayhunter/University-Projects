package com.last_haven.game.utils;

/**
 * Enumerable type that holds the different types of items present in the game.
 */
public class ItemStack {

    public enum StackType
    {
        Wood,
        Stone,
        Food,
        Water
    }

    /**
     * The amount that the type has.
     */
    private int amount;

    /**
     * Controls the maximum amount of resource currently in the storage.
     */
    private int maxAmount;

    /**
     * The type of resource.
     */
    private StackType type;

    /**
     * Constructor for initializing the item types.
     * @param initialAmount The initial amount to initialize the types with, this number is typically 0.
     * @param maxStorage The maximum amount of resources this stack can support.
     * @param type The type that this stack supports.
     */
    ItemStack(int initialAmount, int maxStorage, StackType type)
    {
        amount = initialAmount;
        this.type = type;
        this.maxAmount = maxStorage;
    }

    /**
     * Changes the amount in the itemStack.
     * @param changeAmount How much to change the amount by; positive numbers increase the amount, negative numbers decrease the amount.
     * @return If the methods was successful in changing the amount of resources.
     */
    public boolean changeAmount(int changeAmount)
    {
        //If the amount we change it by exceeds our storage we will not update the value.
        if (amount + changeAmount > maxAmount)
        {
            return false;
        }
        else
        {
            amount += changeAmount;
            return true;
        }
    }

    /**
     * Sets the amount in the storage of the target item type.
     * @param setAmount The amount to set the storage to.
     */
    public void setAmount(int setAmount)
    {
        amount = setAmount;
    }

    /**
     * Gets the amount of the resource currently in the stockpile
     * @return The amount of resources in storage (amount attribute)
     */
    public int getAmount()
    {
        return amount;
    }

    /**
     * Gets the current type of item stack.
     * @return The current ItemStack type.
     */
    public StackType getType()
    {
        return type;
    }

    /**
     * Updates the maximum amount of resources.
     * @param newMax The new maximum amount of resources.
     */
    public void setMaxAmount(int newMax)
    {
        this.maxAmount = newMax;
    }

}
