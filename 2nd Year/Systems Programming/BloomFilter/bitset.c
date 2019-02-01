#define _GNU_SOURCE
#include <stdlib.h>
#include <assert.h>
#include <stdio.h>
#include <limits.h>
#include "bitset.h"



int BITS_IN_WORD  = sizeof(unsigned)*8;

// create a new, empty bit vector set with a universe of 'size' items
struct bitset *bitset_new(int size)
{
	bitset *newSet = (bitset *)malloc(sizeof(bitset));
	newSet->size_in_bits = size;
	newSet->bits_in_word = BITS_IN_WORD;
	newSet->size_in_words = size/newSet->bits_in_word;
	if(newSet->size_in_bits%newSet->bits_in_word>0)
	{
	   newSet->size_in_words++;
	}
	newSet->data = malloc(sizeof(unsigned) *newSet->size_in_words);
	
	for(int i= 0; i<size/newSet->bits_in_word; i++){
		newSet->data[i] = 0;
	}
	return newSet;
}

// get the size of the universe of items that could be stored in the set
int bitset_size(struct bitset * this)
{
	return this->size_in_bits;
}

// check to see if an item is in the set
int bitset_lookup(bitset * this, int item)
{
	int value = 0;
	int i = item/BITS_IN_WORD;
	int pos = item%BITS_IN_WORD;
	unsigned int flag = 1;
	flag = flag << pos;

	if((this->data[i] & flag) != 0)
	{
	  value = 1;
	}
	else{
	  value = 0;	
	}
	
	return value;

}

// get the number of items that are stored in the set
int bitset_cardinality(struct bitset * this)
{
	int count = 0;
	for(int i = 0; i < this->size_in_bits; i++){
		if(bitset_lookup(this, i)==1)
			count++;
	}
	//printf("(%d)\n", count);
	return count;
}


// add an item, with number 'item' to the set
// has no effect if the item is already in the set
void bitset_add(struct bitset * this, int item)
{
	
	int i = item/BITS_IN_WORD;
	int pos = item%BITS_IN_WORD;
        //printf("[%d %d]",i,pos);
	unsigned int flag = 1;
	flag = flag<<pos;
       // printf("(%d %d)", this->data[i], flag);
	this->data[i] = this->data[i] | flag;
        //printf("(%d %d)", this->data[i], flag);
	//printf("%d\n", i);
}

// remove an item with number 'item' from the set
void bitset_remove(struct bitset * this, int item)
{
	
	int i = item/BITS_IN_WORD;
	int pos = item%BITS_IN_WORD;
	unsigned int flag = 1;
	flag = flag<<pos;
	flag = ~flag;
	this->data[i] = this->data[i] & flag;

}

// place the union of src1 and src2 into dest;
// all of src1, src2, and dest must have the same size universe
void bitset_union(struct bitset * dest, struct bitset * src1,
		  struct bitset * src2)
{
	 if(bitset_size(dest)==bitset_size( src1) && 
	   bitset_size( src1)==bitset_size(src2))
	 {   
		for(int i = 0; i< dest->size_in_words; i++)
		{
			dest->data[i] = src1->data[i] | src2->data[i];
		}
	 }
}

// place the intersection of src1 and src2 into dest
// all of src1, src2, and dest must have the same size universe
void bitset_intersect(struct bitset * dest, struct bitset * src1,
		      struct bitset * src2)
{
	if(bitset_size(dest)==bitset_size( src1) && 
	   bitset_size( src1)==bitset_size(src2))
	{   
		for(int i = 0; i< dest->size_in_words; i++)
		{
			dest->data[i] = src1->data[i] & src2->data[i];
		}
	}
}


// print the contents of the bitset
void bitset_print(struct bitset * this)
{
  int i;
  int size = bitset_size(this);
  for ( i = 0; i < size; i++ ) {
    if ( bitset_lookup(this, i) == 1 ) {
      printf("%d ", i);
    }
  }
  printf("\n");
}

/*// add the characters from a string to a bitset
void add_chars_to_set(struct bitset * this, char * s)
{
  int i;
  for ( i = 0; s[i] != 0; i++ ) {
    unsigned char temp = s[i];
    bitset_add(this, temp);
  }
}

void remove_chars_to_set(struct bitset * this, char * s)
{
  int i;
  for ( i = 0; s[i] != 0; i++ ) {
    unsigned char temp = s[i];
    bitset_remove(this, temp);
  }
}

// small routine to test a bitset
void test_bitset(char * string1, char * string2)
{
  struct bitset * a = bitset_new(256);
  struct bitset * b = bitset_new(256);
  struct bitset * c = bitset_new(256);

  add_chars_to_set(a, string1);
  add_chars_to_set(b, string2);

  // print the contents of the sets
  bitset_print(a);
  bitset_print(b);

  // compute and print the union of sets
  bitset_union(c, a, b);
  bitset_print(c);

  // compute and print the intersection of sets
  bitset_intersect(c, a, b);
  bitset_print(c);

  int value = bitset_cardinality(c);
  


}

int main(){
	test_bitset("what","who");	
	return 0;
}*/
