#include <fcntl.h>             // 提供open()函数  
#include <unistd.h>
#include <stdio.h>
#include <dirent.h>            // 提供目录流操作函数  
#include <string.h>
#include <sys/stat.h>          // 提供属性操作函数  
#include <sys/types.h>         // 提供mode_t 类型
#include <stdlib.h>

void scan_dir(char *dir, int depth)       // 定义目录扫描函数  
{  
	DIR *dp;                              // 定义子目录流指针  
	struct dirent *entry;                 // 定义dirent结构指针保存后续目录  
	struct stat statbuf;                  // 定义statbuf结构保存文件属性  
	if((dp = opendir(dir)) == NULL)       // 打开目录，获取子目录流指针，判断操作是否成功  
	{  
		puts("can't open dir.");  
		return;  
	}  
	chdir (dir);                          // 切换到当前目录  
	while((entry = readdir(dp)) != NULL)  // 获取下一级目录信息  
	{  
		lstat(entry->d_name, &statbuf); // 获取下一级成员属性  
		if(S_IFDIR & statbuf.st_mode)    // 判断下一级成员是否是目录  
		{  
			if (strcmp(".", entry->d_name) == 0 || strcmp("..", entry->d_name) == 0)  
				continue;  

			printf("%*s%s/\n", depth, "", entry->d_name);  // 输出目录名称  
			scan_dir(entry->d_name, depth+4);              // 递归调用自身，扫描下一级目录的内容  
		}  
		else
		{
			printf("%*s%s\n", depth, "", entry->d_name);  // 输出属性不是目录的成员  
		}
	}  
	chdir("..");                                                  // 回到上级目录  
	closedir(dp);                                                 // 关闭子目录流  
}  


int main()  
{  
	char *path = "/home/sllx/Document";
	printf("*********** scan %s start ***********", path);  
	scan_dir(path, 0);  
	printf("*********** scan %s end *************", path);  
	return 0;  
}
