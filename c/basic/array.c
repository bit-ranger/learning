#include <stdio.h>

int main(){
	int nums[3][4] = {{1,2,3,4},
		{5,6,7,8},
		{9,10,11,12}};
	int sum = 0;

	for(int i=0; i<3; i++){
		for(int j=0; j<4; j++){
			sum += nums[i][j];
		}
	}

	printf("%d\n", sum);


	//计算数组长度

	char testLen[3];
	printf("%d\n",sizeof(testLen));

	char testLen2[] = {'A','B','C'};
	printf("%d\n",sizeof(testLen2));

	return 0;
}
