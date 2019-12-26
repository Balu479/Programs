#include <stdio.h>
#include <stdlib.h>

/* simulate a direct-mapped cache with 8 blocks 1 word each block*/
void simuDM(char filename[])
{
	unsigned int* tag = (unsigned int *) malloc(8 * sizeof(int));    // tag info

	unsigned int addr;
	int i, j, t;
	int hits, accesses;

	FILE *fp;
	fp = fopen(filename, "r");

	hits = 0;

	accesses = 0;

	while (fscanf(fp, "%x", &addr) > 0) {
	
	accesses += 1;

	printf("%3d: 0x%08x ", accesses, addr);

	i = (addr >> 2) % 8;              // cache index
	t = addr >> 5;                    // tag 
      
	if (tag[i] == t) {                // hit if tag matches
	hits += 1;
	printf("Hit%d ", i);
	} 
	else {
	                                  /* miss and then fill the cache */
	printf("Miss ");
	tag[i] = t;
	}
        printf("\n");
        }
        
	free(tag);

	printf("Hits = %d, Accesses = %d, Hit ratio = %f\n", hits, accesses, ((float)hits)/accesses);

	fclose(fp);
}


/* simulate a full associative cache with 16 blocks and one word each*/
void simuFA(char filename[])
{
  unsigned int* tag = (unsigned int *) malloc(16 * sizeof(int));// tag info
  int lru[15];
	
	unsigned int addr;
	int i, j, t,count;
	count=0;
	int hits, accesses;
	for(i=0;i<=15;i++){
		lru[i]=i;
	}

	FILE *fp;
	fp = fopen(filename, "r");

	hits = 0;

	accesses = 0;

	while (fscanf(fp, "%x", &addr) > 0) {
	
		accesses += 1;

		printf("%3d: 0x%08x ", accesses, addr);
		t = addr >> 2;                    	  // tag 
      		for(i =0;i<15;i++){
			if (tag[i] == t) {                // hit if tag matches
				hits += 1;
				printf("Hit%d ", i);
				if(lru[i]==15) continue;
				if(lru[i]==0){
					for(int j=0;j<=15;j++)
					{
						if(lru[i]==0) lru[i]=15;
						else lru[i]--;
					}
				}
				else{
					lru[i]=15;
					for(int k=0;k<15;k++)
					{
						if(lru[k]>lru[i]) lru[k]--;
					}
				}
		
			} 
			if(tag[i]!=t && lru[i]==0){ 
					tag[i]=t;
					lru[i]=15;
					for(int j=0;j<=15;j++) if(lru[j]!=15) lru[j]--;
							
				}
		}
        }
        
	free(tag);

	printf("Hits = %d, Accesses = %d, Hit ratio = %f\n", hits, accesses, ((float)hits)/accesses);

	fclose(fp);

}



/* simulate a set associative cache: 4-set 2-way cache*/
void simuSA(char filename[])
{
   /*
    To do: please add code to simulate a 4-set 2-way cache, one word each cache block using LRU replacement strategy
   */
}
int main()
{
char filename[] = "trace.txt";
simuDM(filename);
simuFA(filename);
}