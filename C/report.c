#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAXLINE 256
int main(int argc, char *argv[])
{    
    int i, bword = 0, count = 0;
    char fn[MAXLINE], buffer[MAXLINE];
    FILE *fp;
    if (argc > 1)
        strcpy(fn, argv[1]);
    else
        strcpy(fn, "myInput.txt");
    if ((fp = fopen(fn, "r")) == NULL) {
        printf("File not found: %s\n", fn);
        exit(0);
    }
    while (!feof(fp)) {
        if (fgets(buffer, MAXLINE, fp) == NULL) break;
        for (i = 0; i < strlen(buffer); i++) {
            if (buffer[i] == ' ' || buffer[i] == '\n' || buffer[i] == '\t') {
                if (bword) {
                    count++;
                    bword = 0;
                }
            }
            else {
                if (!bword)
                    bword = 1;
            }
        }
    }
    fclose(fp);
    printf("%d\n", count);
    return 0;
}