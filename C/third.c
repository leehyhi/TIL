#define _CRT_SECURE_NO_WARNINGS 
#include <stdio.h> 

int main()
{
    FILE* fp = fopen("myInput.txt", "r");
    FILE* fp1 = fopen("myOutput.txt", "w");
    int i;
    int x = 0;
    int max = 0;

    for (i=0; i<=9; i++) {
        fscanf(fp, "%d", &x);
        printf("%d\n", x);
        fscanf(fp1, "%d", &x);
        fprintf(fp1, "%d\n", x);
        if (max < x)
        {
            max = x;
        }
    }
    printf("최대값: %d\n", max);
    fprintf(fp1, "최대값: %d", max);
    fclose(fp);
}