#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>

int main()
{
    pid_t pid = fork();
    if (pid < 0)
    {
        perror("fork failed to create");
        return 1;
    }
    else if (pid == 0)
    {
        printf("This is the child.\n");
    }
    else
    {
        wait(NULL);
        printf("This is the parent.\n");
    }
    return 0;
}