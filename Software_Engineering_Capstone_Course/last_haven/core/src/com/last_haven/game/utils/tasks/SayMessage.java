package com.last_haven.game.utils.tasks;

//Say Message is more of a test task, allowing us to test the system before implementing functional tasks.

/**
 * @deprecated
 */
public class SayMessage extends Task
{
    private String msg;
    public SayMessage(Integer taskID, boolean repeatable, String message, int priority) {
        super(taskID, repeatable, priority);
        msg = message;
    }
    public void executeTask()
    {
        System.out.println(msg);
    }
}
