package com.last_haven.game.utils.tasks;

import java.util.ArrayList;

/**
 * @deprecated
 * What is a Task?
 * Each villager can be assigned a task, or even a queue of tasks.
 * Each villager will perform all the tasks in the queue.
 * However, some tasks can be repeated... so they'll need to be re-queued
 *      I think this is solvable by having two different data structures
 *      List<Task> assignedTasks;
 *      Queue<Task> queuedTasks;
 *      It might not be necessary...
 *
 * Tasks - a job to be completed, and a flag determining whether they are repeatable or one-shot.
 *      0 - move
 *      1x - harvest resource
 *      2x - store resource in storage
 */
public abstract class Task implements Comparable<Task> {
    private int taskID; // TODO, I think this was supposed to identify the task such as Collect/Gather/Idle/MoveTo
    private boolean repeatable; // if true the task can be re-queued, else let the task be removed
    protected int priority; //Higher value means higher priority, different from ID
    private ArrayList<Task> subTasks = new ArrayList<>(); //List of subtasks (not fully implemented yet)

    public Task(Integer taskID, boolean repeatable, int priority) {
        // TODO include checks for real taskIDs
        this.taskID = taskID;
        this.repeatable = repeatable;
        this.priority = priority;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public int getTaskID() {
        return taskID;
    }

    public void executeTask()
    {
        //Code for task goes here.
    }

    //Handles the comparing or priorities for tasks.
    @Override
    public int compareTo(Task other)
    {
        return Integer.compare(this.priority, other.priority);
    }
}
