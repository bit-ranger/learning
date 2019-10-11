#include <stdio.h>
#include <math.h>
#include <string.h>
#include <stdlib.h>
#include <errno.h>
#include <stdio.h>

extern int errno ;

int str_equal(const char *str1, const char *str2){
	int diff = strcmp(str1,str2);
	return diff == 0 ? 1 : 0;

}

int figure(const char operator, const int operand_l, const int operand_r){
	int result = 0;

	if (operator == '+'){
		result = operand_l + operand_r;
	} else if (operator == '-'){
		result = operand_l - operand_r;
	} else if (operator == '*'){
		result = operand_l * operand_r;
	} else if (operator == '/'){
		result = operand_l / operand_r;
	} else if (operator == '%'){
		result = operand_l % operand_r;
	} else {
		errno = 998;
		perror("unsupported operator!");
	}

	return result;
}

//int main(int argC, char *argV[]){
	//printf("program path %s\n", argV[0]);
	//int result = figure(argV[1], atoi(argV[2]), atoi(argV[3]));
int main(){
	char operator;
	int operand_l,operand_r;
	scanf("%d %c %d", &operand_l, &operator, &operand_r);
	int result = figure(operator, operand_l, operand_r);
	printf("%d\n", result);

	return 0;	
}
