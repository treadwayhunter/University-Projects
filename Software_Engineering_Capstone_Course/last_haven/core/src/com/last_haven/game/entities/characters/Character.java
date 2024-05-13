package com.last_haven.game.entities.characters;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.assets.AssetManager;
import com.last_haven.game.entities.Entity;
import com.last_haven.game.pathfinding.Node;
import com.last_haven.game.utils.Position;
import com.last_haven.game.utils.tasks.Task;
import com.last_haven.game.utils.tasks.*;

import java.util.PriorityQueue;

/**
 * Characters represent the 'living' entities in the world.
 * They are the villagers, bandits, and any other living creatures that may be implemented later.
 */
public abstract class Character extends Entity {
    /**
     * TODO Characters also need a starting Node
     * The Position was fine to start with, but Characters traverse Nodes.
     */
    /**
     * Numbers that represent the various stats of a creature.
     * maxHealth is the maximum amount of hit points that a character has.
     * health is the current amount of hit points that a character has.
     * Strength determines how fast a character gathers resources and how much melee damage they do.
     * Intellect does...?
     */
    protected int maxHealth, health, strength, intellect; // These are the stats shared by villagers and bandits
    /**
     * Stores if a character is dead or not.
     */
    protected boolean dead = false;
    /**
     * Stores if the character is selected by the mouse.
     */
    protected boolean isSelected = false;
    /**
     * Stores is a character is busy or not.
     */
    protected boolean isBusy = false;
    /**
     * Stores the current path that a character is taking.
     */
    protected GraphPath<Node> currentPath;
    /**
     * Stores the queue of tasks that a character has.
     */
    protected PriorityQueue<Task> taskQueue;


    /**
     * Stores the taskManager object.
     */
    TaskManager taskManager;

    /**
     * Character constructor if a specific location is provided.
     * @param assetManager A manager for all the assets associated with the character.
     * @param position The position where the character is to be created.
     * @param unitWidth The width of the character.
     * @param unitHeight The height of the character.
     * @param taskManager The task manager needed for completion of tasks.
     */

    public Character(AssetManager assetManager, Position position, float unitWidth, float unitHeight, TaskManager taskManager) {
        super(assetManager, position, unitWidth, unitHeight);
        /*taskQueue = new PriorityQueue<>();
        Task t = new Idle(0, true, 10);
        taskQueue.add(t);*/
        this.taskManager = taskManager;
    }

    /**
     * Character constructor based on if the position of the character is provided as separate x and y components.
     * @param assetManager The asset manager associated with the character.
     * @param posX The X position of where to make the character.
     * @param posY The Y position of where to make the character.
     * @param unitWidth The width of the character.
     * @param unitHeight The height of the character.
     * @param taskManager The task manager necessary for completing tasks.
     */
    public Character(AssetManager assetManager, float posX, float posY, float unitWidth, float unitHeight, TaskManager taskManager) {
        super(assetManager, posX, posY, unitWidth, unitHeight);
        /*taskQueue = new PriorityQueue<>();
        Task t = (Task)new Idle(0, true, 10);
        taskQueue.add(t);
        taskQueue.add(new SayMessage(1, false, "Hello!", 5));
        taskQueue.add(new SayMessage(2, false, "No I'm first!", 1));*/
        this.taskManager = taskManager;

    }

    /**
     * Initializes the stats of a character.
     */
    public abstract void initStats();

    /**
     * Returns the maximum health of the character.
     * @return Maximum Hit points.
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Sets the maximum health of the character.
     * @param maxHealth New maximum hit points to use.
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * Returns the current health of the character.
     * @return The current amount of hit points the creature has.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the current amount of health a character has.
     * @param health New Current hit points the character.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Returns the strength of the character.
     * @return The character's strength.
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Sets the strength of the character.
     * @param strength New strength value.
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     * Gets the intelligence of the character.
     * @return The intellect of the character.
     */
    public int getIntellect() {
        return intellect;
    }

    /**
     * Sets the intelligence of the character.
     * @param intellect New intellect value.
     */
    public void setIntellect(int intellect) {
        this.intellect = intellect;
    }

    /**
     * Decreases the amount of hit points a character has by 1.
     */
    public void decrementHealth() {
        //health--;
        if (health <= 0) {
            setDead();
        }
        else health--;
    }

    /**
     * Decreases the amount of hit points a character has by a specific value.
     * @param amount The amount to decrease the character's health by.
     */
    public void decrementHealth(int amount) {
        //health = health - amount;
        if (health <= 0) {
            setDead();
        }
        else health -= amount;
    }

    /**
     * Increases the amount of hit points a character has by 1.
     */
    public void incrementHealth() {
        health++;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    /**
     * Increases the amount of hit points a character has by a specific value.
     * @param amount The amount of hit points to add to the creature.
     */
    public void incrementHealth(int amount) {
        health = health + amount;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    /**
     * Updates the creature's isDead boolean to true.
     */
    public abstract void setDead();

    public boolean isDead() {
        return dead;
    }

    /**
     * Updates the character's isSelected value to true.
     */
    public void setSelected() {
        isSelected = true;
    }

    /**
     * Returns if the character is selected or not.
     * @return if the character is selected.
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * Updates the isSelected value to false.
     */
    public void deSelect() {
        isSelected = false;
    }

    /**
     * Updates the current position of the character to the specific position.
     * @param position The new position that the character will move to.
     */
    private void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Completes the current task in the character's queue.
     */
    public void doTask()
    {
        //Set the villager's bust status
        isBusy = true;

        //If the queue has nothing in it, queue an idle task.
        if (taskQueue.isEmpty())
        {
            taskQueue.add(new Idle(0 , true, 1));
        }

        //Temporary task object
        Task currentTask = null;

        //If the queue is not empty, get the task at the top
        if (!taskQueue.isEmpty())
        {
            currentTask = taskQueue.poll();
        }

        //If the current task is not null
        if (currentTask != null)
        {
            //Execute the task
            currentTask.executeTask();

            //If the task is repeatable, re-add it to the queue
            if (currentTask.isRepeatable())
            {
                taskQueue.add(currentTask);
            }
        }
        //Villager is no longer busy, so update accordingly
        isBusy = false;
    }

    /**
     * Returns if the character is busy or not.
     * @return If the character is busy.
     */
    public boolean getBusy() { return isBusy; }

    /**
     * Updates the character's movement path to the given value.
     * @param path The new path the character should use.
     */
    public void setCurrentPath(GraphPath<Node> path) {
        currentPath = path;
    }

    /**
     * Moves the character based on the current path.
     */
    protected void move() {
        if (currentPath != null) {
            //System.out.println("PATH IS NOT NULL, MOVE VILLAGER");
            //System.out.println("Move");
            Node topNode = currentPath.get(0);
            GraphPath<Node> newPath = new DefaultGraphPath<>();
            /*
            for (int i = 1; i < currentPath.getCount(); i++) {
                newPath.add(currentPath.get(i));
            }
            */
            if (currentPath.getCount() > 1) {
                for (int i = 1; i < currentPath.getCount(); i++) {
                    newPath.add(currentPath.get(i));
                }
            }
            else {
                newPath = null;
            }
            setPosition(topNode.getPosition());
            setCurrentPath(newPath);
        }
    }

    /**
     * Updates the character based on the given tick value.
     * @param tick The length of the tick.
     */
    public abstract void update(int tick);

}
