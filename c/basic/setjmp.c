#include <stdio.h>
#include <stdlib.h>
#include <setjmp.h>

int main()
{
   int val;
   jmp_buf env_buffer;

   /* 保存 longjmp 的调用环境 */
   val = setjmp( env_buffer );
   if( val != 0 ) 
   {
      printf("出现异常，错误为 %d\n", val);
      exit(0);
   }
   printf("函数调用\n");
   longjmp(env_buffer, 998);
   
   return(0);
}

