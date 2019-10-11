#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(void)
{
    if(fork() == 0) //启动一个子进程,fork函数要返回两次，一次在父进程中返回，一次在子进程中返回，在父进程中，fork函数返回值为子进程的pid（如果成功调用的话），在子进程中，fork函数的返回值为0。
    {
        printf("the child\n");
        if(fork() == 0) //启动一个孙子进程
        {
            printf("do something you want\n");
			execlp("drracket", NULL);
            sleep(5);
            printf("done\n");
            exit(0);
        }
        else //子进程立刻退出，使孙子进程成为孤儿进程
            exit(0);
    }
    else
    { //父进程立即阻塞.等待子线程退出，避免产生僵尸进程
        wait(NULL);
        printf("the parent\n");
        sleep(10);
    }
    return 0;
}
