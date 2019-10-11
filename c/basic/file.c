#include <stdio.h>

int main()
{
	FILE *fp;

	fp = fopen("/tmp/test.txt", "w+");
	fprintf(fp, "This is testing for fprintf...\n");
	fputs("This is testing for fputs...\n", fp);
	fclose(fp);


	fp = fopen("/tmp/test.txt", "r");
	char buff[255];
	fscanf(fp, "%s", buff);  //读取至一个空格
	printf("1: %s\n", buff ); 

	fgets(buff, 255, (FILE*)fp);  //读取至行尾
	printf("2: %s\n", buff );

	fgets(buff, 255, (FILE*)fp);
	printf("3: %s\n", buff );


	fclose(fp);
	return 0;
}
