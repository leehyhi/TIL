#define _CRT_SECURE_NO_WARNINGS 
#include <stdio.h> 

int main()
{
    FILE* fp = fopen("myInput.txt", "r");
    FILE* fp1 = fopen("myOutput.txt", "w");
    int i;
    int x = 0;
    int sum = 0;

    for (i=0; i<=9; i++) {
        fscanf(fp, "%d", &x);
        printf("%d\n", x);
        fscanf(fp1, "%d\n", &x);
        fprintf(fp1, "%d\n", x);
        sum += x;
    }
    printf("합은: %d\n", sum);
    fprintf(fp1, "합은: %d", sum);
    fclose(fp);
}