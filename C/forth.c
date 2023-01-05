#include<stdio.h>
 
int desort(int b[])
{
    int a = 0;
    int c = 0;
    int temp = 0;
 
    for(a=0; a<9; a++)
    {
        for(c=0; c<9-a; c++)
        {
            if(b[c] <b[c+1])
            {
                temp = b[c+1];
                b[c+1] = b[c];
                b[c] = temp;
            }
        }
    }
    
    for(a=0; a<10; a++)
    {
    printf("%d\n", b[a]);
    }
	return 0;
}
 
int main()
{
    int i = 0;
    int b[10] = {0};
 
    FILE *rfp = fopen("a.txt","r");
    FILE *wfp = fopen("output.txt","w");

    for(i=0; i<10; i++)
    {
        fscanf(rfp, "%d\n", &b[i]);
    }
    
    desort(b);
    for(i=0; i<10; i++)
    {
        fprintf(wfp, "%d\n", b[i]);
    }
}