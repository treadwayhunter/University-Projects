package com.last_haven.game.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * This class handles the inventory of objects, including transfer between inventories.
 */
public class Inventory
{

    /**
     * List of the types of items, there should only be one type of each item in the array.
     */
    ArrayList<ItemStack> stacks = new ArrayList<>();
    /**
     * Constructor to create and inventory and the initial amounts of those resources.
     * @param initialAmounts The initial amount of each resource in storage.
     * @param maxStorage The maximum amount that each resource type that the inventory can store.
     */
    public Inventory(int initialAmounts, int maxStorage)
    {
        for (ItemStack.StackType i: ItemStack.StackType.values()) {
            stacks.add(new ItemStack(initialAmounts, maxStorage, i));
        }
    }

    /**
     * Changes the amount in the inventory of the given type
     * @param amount How much to change the storage by; positive numbers increase, while negative numbers decrease
     * @param type Specify which resource to increase or decrease
     * @return True if the method was successful in both finding the itemStack type and added the amount of resources to the storage, false otherwise
     */
    public boolean changeAmount(int amount, ItemStack.StackType type)
    {
        for (int i = 0; i < stacks.size(); i++) {
            if (stacks.get(i).getType() == type)
                return stacks.get(i).changeAmount(amount);
        }
        return false;
    }

    /**
     * "Transfers" resources to another inventory
     * @param amountToTransfer How many resources to transfer
     * @param type Which type of resource to transfer
     * @param targetInventory Which inventory to transfer the resources to
     */
    public void transferResources(int amountToTransfer, ItemStack.StackType type, Inventory targetInventory)
    {
        changeAmount(-amountToTransfer, type);
        targetInventory.changeAmount(amountToTransfer, type);
    }

    /**
     * Transfers all the resources to the global resource storage.
     */
    public void transferAllResourcesGlobal() {
        /*int amount = getAmount(type);
        changeAmount(amount, type);
        switch(type) {
            case Wood: ResourceStore.addWood(amount); break;
            case Stone: ResourceStore.addStone(amount); break;
            default: System.out.println("transferAllResourcesGlobal() broken");
        }*/

        for (ItemStack i : stacks) {
            switch(i.getType()) {
                case Wood: {
                    ResourceStore.addWood(i.getAmount());
                    break;
                }
                case Stone: {
                    ResourceStore.addStone(i.getAmount());
                    break;
                }
                case Food:  {
                    ResourceStore.addFood(i.getAmount());
                    break;
                }
                case Water: {
                    ResourceStore.addWater(i.getAmount());
                    break;
                }
            }
            changeAmount(-i.getAmount(), i.getType());

        }
    }

    /**
     * Gets the current amount of resources of a given type.
     * @param type The type of resource we want to find the amount of.
     * @return The amount of resources of a desired type.
     */
    public int getAmount(ItemStack.StackType type)
    {
        for (int i = 0; i < stacks.size(); i++)
        {
            if (stacks.get(i).getType() == type)
                return stacks.get(i).getAmount();
        }
        return -1;
    }

    /**
     * Sets the max resource amount of all types of resources in the inventory.
     * @param newMax The new maximum resource amount.
     */
    public void setMaxStorage(int newMax)
    {
        for (int i = 0; i < stacks.size(); i++) {
            stacks.get(i).setMaxAmount(newMax);
        }
    }

    /**
     * Sets the maximum resource storage of a specific type.
     * @param newMax The new maximum amount of resources for the storage.
     * @param type The type of resource stack to update the maximum amount of resources.
     */
    public void setMaxSpecificStorage(int newMax, ItemStack.StackType type)
    {
        for (int i = 0; i < stacks.size(); i++) {
            if (stacks.get(i).getType() == type)
                stacks.get(i).setMaxAmount(newMax);
        }
    }


}
