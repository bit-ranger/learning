#include <stdio.h>

int main(){
	int nums[] = {1,2,3,4,5};
	int max=-1;
	for (int i=0; i<5; i++){
		if(nums[i] > max){
			max = nums[i];
		}
	}
	printf("%d", max);
	return 0;
}
