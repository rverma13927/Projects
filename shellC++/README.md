1. Understand the Requirements

At its core, a shell must:

    Accept user commands (input parsing).
    Execute commands using system calls like fork() and execvp().
    Handle errors gracefully (e.g., command not found).
    Provide built-in commands (e.g., cd, exit).


What Happens with fork()?

When fork() is called:

    The current process (the parent) is duplicated.
    Two processes begin execution from the same point where fork() was called:
        In the parent process, fork() returns the PID of the child (a positive number).
        In the child process, fork() returns 0.
        If there's an error in fork(), it returns -1 (in the parent).

Both processes now have separate execution flows, even though they share the same program code. Each process follows its own control path depending on the return value of fork().
