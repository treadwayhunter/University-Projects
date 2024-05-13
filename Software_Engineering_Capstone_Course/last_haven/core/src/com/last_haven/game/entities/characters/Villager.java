package com.last_haven.game.entities.characters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.last_haven.game.entities.Resource;
import com.last_haven.game.utils.*;
import com.last_haven.game.utils.tasks.TaskManager;

/**
 * The villager is one of the primary components of the game and its gameplay loop.
 * Villagers are characters that the player controls by issuing tasks to each villager.
 * A villager can be selected with a click, then issued a command by right-clicking on a target.
 * Villagers also have needs, they need to eat food and drink water, if these important attributes reach zero, the villager begins to die.
 * If all the villagers die, a game over occurs.
 */
public class Villager extends Character {
    private String name;
    private int maxEnergy, energy, maxHunger, hunger, maxThirst, thirst; // these are in addition to the character
    private Resource targetResource;

    private boolean deliveringResources = false;
    private String targetResourceType = "";

    private Position constructionPos;
    public Villager(AssetManager assetManager, Position position, TaskManager taskManager) {
        super(assetManager, position, 0.5f, 1, taskManager);
        //texture = assetManager.get(Asset.VILLAGER_FEMALE, Texture.class);
        initStats();
        updateTexture(assetManager);
        inventory.setMaxStorage(strength);
    }

    public Villager(AssetManager assetManager, float posX, float posY, TaskManager taskManager) {
        super(assetManager, posX, posY, 0.5f, 1, taskManager);
        //texture = assetManager.get(Asset.VILLAGER_FEMALE, Texture.class);
        initStats();
        updateTexture(assetManager);
        inventory.setMaxStorage(strength);
    }

    /**
     * A method that initializes the stats for the villager.
     * The StatGenerator class provides a gaussian distribution with generate(mean, stddev). Although unlikely,
     * if a Villager's stats are below 0, they are hard set to the mean.
     */
    @Override
    public void initStats() {
        name = Names.getRandomName();
        maxHealth = StatGenerator.generate(20, 5);
        if (maxHealth <= 0) maxHealth = 20;
        health = maxHealth;
        maxEnergy = StatGenerator.generate(50, 10);
        if (maxEnergy <= 0) maxEnergy = 50;
        energy = maxEnergy;
        maxHunger = StatGenerator.generate(100, 20);
        if (maxHunger <= 0) maxHunger = 100;
        hunger = maxHunger;
        maxThirst = StatGenerator.generate(100, 20);
        if (maxThirst <= 0) maxThirst = 100;
        thirst = maxThirst;
        strength = StatGenerator.generate(10, 2);
        if (strength <= 0) strength = 10;
        intellect = StatGenerator.generate(10, 2);
        if (intellect <= 0) intellect = 10;
    }

    /**
     * Villager is updated every tick. This will tell them which actions to perform, as well as other important updates
     * such as decrementing hunger, thirst,
     * @param tick The length of a tick.
     */
    @Override
    public void update(int tick) {
        if(!dead) { // villager can only perform actions if they are not dead
            performTasks(tick);
            statChanges(tick);
        }
    }

    /**
     * This function updates the villager's hunger and thirst over time.
     * If either the villager's current 'hunger' or 'thirst' reach zero the villager will instead be decreasing life.
     * @param tick The length of a gameplay tick.
     */
    private void statChanges(int tick) {
        if (tick % statSpeed() == 0) {
            if (hunger > 0) decrementHunger();
            else decrementHealth();

            if (thirst > 0) decrementThirst();
            else decrementHealth();

            if (health <= 0) setDead(); // I wanted dead villagers kept in the Engine. Should I?

            if(hunger <= quarterStat(maxHunger)) {


                int neededFood = maxHunger - hunger;
                int foodAvailable = ResourceStore.getFood();

                if (foodAvailable > 0) {
                    int foodToEat = Math.min(neededFood, foodAvailable);
                    ResourceStore.addFood(-foodToEat);
                    hunger += foodToEat;
                }
            }


            if(thirst <= quarterStat(maxThirst)) {

                int neededWater = maxThirst - thirst;
                int waterAvailable = ResourceStore.getWater();
                if (waterAvailable > 0) {
                    int waterToDrink = Math.min(neededWater, waterAvailable);
                    ResourceStore.addWater(-waterToDrink);
                    thirst += waterToDrink;
                }
            }

            if (thirst >= (int)maxThirst/2 && hunger >= (int)maxHunger/2) {
                incrementHealth();
                incrementEnergy();
            }

            if(getStatus() != "Idle") {
                decrementEnergy();
            }
            else {
                incrementEnergy();
            }

        }
    }

    /**
     * A helper function that helps simplify the reading of update()
     * Actions can be moving, collecting resources, delivering resources, construction,
     * eating, drinking, etc.
     * @param tick The length of a tick.
     */
    private void performTasks(int tick) {
        if ((tick % actionSpeed()) + 1 == actionSpeed()) { // villager perform actions
            // perform an action
            if (currentPath != null) {
                move();
            }
            if (targetResource != null && !isDeliveringResources()) { // if it is not empty
                // then do this go find the resource
                if (currentPath == null) {
                    taskManager.findResource(this);
                }
            }
            if (isDeliveringResources() && currentPath == null) {
                // this is when they arrive at the storehouse. Might want to check for other conditions
                inventory.transferAllResourcesGlobal();
                stopDeliveringResources();
            }
            if(constructionPos != null) {
                //System.out.println("Construction Pos is set");
                //System.out.println(Distance.distance(position, constructionPos));

                if(Distance.distance(position, constructionPos) == 0) {
                    // taskBoss, build a building?
                    //System.out.println("Villager is in construction distance");
                    taskManager.BuildStoreHouse(this, constructionPos);
                    //constructionPos = null;
                }
            }
        }
    }

    /**
     * Lower number is faster.
     * A higher strength should result in a faster, lower speed.
     * @return an integer that represents how many ticks it takes before a villager can move
     */
    private int actionSpeed() {
        int tired = isTired() ? 10 : 0;
        return 20 - strength + tired;
    }

    /**
     * The speed at which stat changes occur.
     * @return
     */
    private int statSpeed() {
        return (strength + intellect) * 6;
    }

    /**
     * Calculates a quarter of a villager's stat, for any stat. Then rounds down to an integer.
     * @return 25% of the given villager stat, casted to an integer.
     */
    private int quarterStat(int stat) {
        return (int)stat/4;
    }
    /**
     * A method for visual inspection of Villager status in the terminal.
     */
    public void getDetails() {
        System.out.println("Name: " + name);
        System.out.println("MaxHealth: " + maxHealth);
        System.out.println("MaxEnergy: " + maxEnergy);
        System.out.println("MaxHunger: " + maxHunger);
        System.out.println("MaxThirst: " + maxThirst);
        System.out.println("Strength : " + strength);
        System.out.println("Intellect: " + intellect);
    }

    /**
     * Decreases the villager's energy by 1 but not below 0.
     */
    public void decrementEnergy() {

        if (energy > 0) {
            energy--;
        }
    }

    /**
     * Decreases the villager's energy by the given amount.
     * If the decrease leaves the villager with negative energy, it defaults to zero.
     * @param amount The amount to decrease the energy by.
     */
    public void decrementEnergy(int amount) {
        energy -= amount;
        if (energy < 0) {
            energy = 0;
        }
    }

    /**
     * Increases the villager's energy by 1, but not above its maximum.
     */
    public void incrementEnergy() {
        energy++;
        if (energy > maxEnergy) {
            energy = maxEnergy;
        }
    }

    /**
     * Increases the villager's energy by the given amount, but not above its maximum.
     * @param amount The amount to increase the energy by.
     */
    public void incrementEnergy(int amount) {
        energy += amount;
        if (energy > maxEnergy) {
            energy = maxEnergy;
        }
    }

    /**
     * Checks if the villager is tired.
     * A villager is considered tired if its energy is less than a quarter of its maximum energy.
     * @return True if the villager is tired, False otherwise.
     */
    public boolean isTired() {
        return energy <= quarterStat(maxEnergy);
    }

    /**
     * Decreases the villager's hunger by 1, but never below zero.
     */
    public void decrementHunger() {
        if(hunger > 0) hunger--;
    }

    /**
     * Decreases the villager's hunger by the provided amount.
     * @param amount The amount to decrease the villager's hunger by.
     */
    public void decrementHunger(int amount) {
        hunger -= amount;
    }

    /**
     * Increases the villager's hunger by 1, but never above its maximum.
     */
    public void incrementHunger() {
        hunger++;
        if (hunger > maxHunger) {
            hunger = maxHunger;
        }
    }

    /**
     * Increases the villager's hunger by a specified amount.
     * @param amount The amount to increase the villager's hunger by.
     */
    public void incrementHunger(int amount) {
        hunger += amount;
    }

    /**
     * Decreases the villager's thirst by 1, but never below 0.
     */
    public void decrementThirst() {
        if(thirst > 0) thirst--;
    }

    /**
     * Decreases the villager's thirst by a specified amount.
     * @param amount The amount to decrease the villager's thirst by.
     */
    public void decrementThirst(int amount) {
        thirst -= amount;
    }

    /**
     * Increases the villager's thirst by 1, but never above its maximum.
     */
    public void incrementThirst() {
        thirst++;
        if (thirst > maxThirst) {
            thirst = maxThirst;
        }
    }

    /**
     * Increases the villager's thirst by a provided amount, but never above its maximum.
     * @param amount The amount to increase the villager's thirst level by.
     */
    public void incrementThirst(int amount) {
        thirst += amount;
        if (thirst > maxThirst) {
            thirst = maxThirst;
        }
    }

    /**
     * Gets the name of the villager.
     * @return The villager's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the current energy level of the villager.
     * @return The villager's current energy level.
     */
    public int getEnergy() {
        return this.energy;
    }

    /**
     * Gets the maximum energy level of the villager.
     * @return The villager's maximum energy level.
     */
    public int getMaxEnergy() {
        return this.maxEnergy;
    }

    /**
     * Gets the villager's current hunger level.
     * @return The current amount of hunger the villager has.
     */
    public int getHunger() {
        return this.hunger;
    }

    /**
     * Gets the villager's maximum hunger amount.
     * @return The maximum amount of hunger a villager can have.
     */
    public int getMaxHunger() {
        return this.maxHunger;
    }

    /**
     * Gets the current level of thirst a villager has.
     * @return The current amount of thirst the villager has.
     */
    public int getThirst() {
        return this.thirst;
    }

    /**
     * Gets the maximum amount of thirst the villager can have.
     * @return The maximum this the village can have.
     */
    public int getMaxThirst() {
        return this.maxThirst;
    }

    /**
     * Updates the villager's 'target' resource.
     * A 'target' resource refers to the resource the villager is currently attempting to path find to, or interact with.
     * @param resource The type of resource to be the villager's new target.
     */
    public void setTargetResource(Resource resource) {
        this.targetResource = resource;
        this.targetResourceType = resource.getClass().getSimpleName();
    }

    /**
     * Updates the villager's construction position.
     * The construction position refers to the position where the villager is attempting to build a structure.
     * @param position The location of the structure.
     */
    public void setConstructionPos(Position position) {
        constructionPos = position;
    }

    /**
     * Gets what the current target resource of the villager is.
     * @return The villager's current target resource.
     */
    public Resource getTargetResource() {
        return this.targetResource;
    }

    /**
     * Gets what type of resource the villager's target resource is.
     * @return The villager's current target resource type.
     */
    public String getTargetResourceType() {
        return targetResourceType;
    }

    /**
     * Clears what the villager's target resource is.
     */
    public void clearTargetResource() {
        targetResource = null;
        targetResourceType = "";
    }

    /**
     * Gets the current status of the villager, as a string.
     * Dead - The villager is dead and cannot perform tasks.
     * Delivering - The villager is currently delivering resources to a store house.
     * Constructing - the villager is currently building a storehouse.
     * Gathering X - Indicates the villager is currently gathering a resource of a specific type.
     * Idle - The villager currently has no task.
     * @return The String representation of the villager's current status.
     */
    public String getStatus() {
        if(isDead()) {
            return "Dead";
        }
        if(isDeliveringResources()) {
            return "Delivering";
        }
        if(constructionPos != null) {
            return "Constructing";
        }
        switch(targetResourceType) {
            case "Tree": return "Gathering Wood";
            case "Rock": return "Gathering Stone";
            case "Mushroom": return "Gathering Food";
            case "WaterResource": return "Collecting Water";
            default: return "Idle";
        }
    }

    /**
     * Updates the villager's delivering resource status to true.
     */
    public void setDeliveringResources() {
        deliveringResources = true;
    }

    /**
     * Updates the villager's delivering resource status to false.
     */
    public void stopDeliveringResources() {
        deliveringResources = false;
    }

    /**
     * Gets if the villager is currently delivering resources or not.
     * @return True if the villager is currently delivering resources, False otherwise.
     */
    public boolean isDeliveringResources() {
        return deliveringResources;
    }

    /**
     * Updates the villager to being dead.
     */
    public void setDead() {
        System.out.println("I was set dead");
        dead = true;
        targetResource = null;
        deliveringResources = false;
        constructionPos = null;
        targetResourceType = "";
        currentPath = null;
    }


    /**
     * Updates the current texture of the villager.
     * @param assetManager The game's asset manager.
     */
    @Override
    protected void updateTexture(AssetManager assetManager) {
        try {
            this.texture = assetManager.get(Asset.VILLAGER_FEMALE, Texture.class);
        }
        catch(NullPointerException e) {

        }
    }
}

