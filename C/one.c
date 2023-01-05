#include <stdio.h>
#include <ctype.h>

int main(void) {

	FILE *fp;
    fp=fopen("myInput.txt", "rt");
	int c, count = 0;
	char file1[100];

	while((c = fgetc(fp)) != EOF ) {

		if( isprint(c) )

		count++;

	}

	fclose(fp);

	printf("문자 개수:%d\n", count);

	return 0;

}