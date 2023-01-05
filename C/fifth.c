#define _CRT_SECURE_NO_WARNINGS 
#include <stdio.h> 

int main()
{
    FILE* fp1 = fopen("myOutput.txt", "w");
    int A[2][3] = {{1, 3, 5}, {2, 4, 6}};
    int B[3][3] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

    int mul;

    for(int i=0; i<2; i++) {
        for(int j=0; j<3; j++) {
            mul = 0;
            for(int k=0; k<3; k++) {
                mul += (A[i][k]*B[k][j]);
            }
            fscanf(fp1, "%d ", &mul);
            fprintf(fp1, "%d ", mul);
            printf("%d ", mul);
        }
        fprintf(fp1, "\n");
        printf("\n");
    }
}