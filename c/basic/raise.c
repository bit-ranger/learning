#include <signal.h>
#include <stdlib.h>
#include <stdio.h>

void signal_catchfunc(int);

int main()
{
	int ret;

	ret = (int)signal(SIGINT, signal_catchfunc);

	if( ret == (int)SIG_ERR) 
	{
		printf("错误：不能设置信号处理程序。\n");
		exit(0);
	}
	printf("开始生成一个信号\n");
	ret = raise(SIGINT);
	if( ret !=0 ) 
	{
		printf("错误：不能生成 SIGINT 信号。\n");
		exit(0);
	}

	printf("退出...\n");
	return(0);
}

void signal_catchfunc(int signal)
{
	printf("!! 信号捕获 !!\n");
}
