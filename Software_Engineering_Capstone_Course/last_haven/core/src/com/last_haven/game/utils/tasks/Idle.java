package com.last_haven.game.utils.tasks;

/**
 * @deprecated
 */
public class Idle extends Task {
    //This task does ... nothing! This is either a placeholder or the final task for a character that has nothing to do.
    //This task may never actually be added in game, but acts as a last resort so a character always has a task.
    public Idle(Integer taskID, boolean repeatable, int priority) {
        super(taskID, repeatable, priority);
    }

    public void executeTask()
    {
        //TODO: REMOVE FOR FINAL GAME
        System.out.println("Idlining...");
    }

}
