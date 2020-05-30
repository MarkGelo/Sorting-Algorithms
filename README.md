# Sorting-Algorithms
Java GUI that allows the user to specify the algorithm and the size of the array to sort. Can also compare each algorith by ticking the 'compare' box. This will keep the array the same for each algorithm and the user can see the differences in the amount of swaps, animations, and the amount of time taken.

## Instructions
Run window.java\
Choose the algorithm you want to see and click sort\
Change the size of array, by typing an number in text field\
Click stop if you want to\
Click compare if you want to keep arrays the same for each algorithm so you can compare\
For radix and counting sort, red bars means that element was accessed. I added this because otherwise, it wouldn’t be clear what the algorithm was doing, and it would just somewhat instantly sort the array.

## Counting Sort
Essentially counting sort takes in values to be sorted and count the occurrences in an auxiliary array with the index corresponding to the actual value. The size of the auxiliary depends on the range, or highest value of the array.\
For it to do that, it iterates through the array, (n), and fills up the auxiliary array with the number of occurrences of the values
Then, it loops through the auxiliary array, (range), and updates the array, essentially sorting it, using the count and the index of the auxiliary array.\
Thus, the time complexity is just O(n + range), with range being the range of the values in the array. For example, if the values are just from 1 – 100, the range/size of the auxiliary array is just 100.
![](images/counting1.png) ![](images/counting2.png)

## Radix Sort
Radix sort sorts digit by digit starting from the least significant digit. It’s similar to counting sort but is more for larger numbers.
It essentially does counting sort but for each digit of the values.\
So, it would be O(#digits * (n + range)). But in this case, the range would just be 10, because the digits can only be from 0 – 9.\
The #digits depends on the maximum value in the array, which is just log(max)\
Thus, the time complexity is just O(log(max)*(n+10)) which can just be simplified to O(nlog(max)).
![](images/radix1.png) ![](images/radix2.png)

## Heap Sort
First build a max heap from the elements in the array using heapify\
Then swap the root with the last element and then heapify again but excluding the last element\
Heapify takes O(log n) but since it’s doing heapify for each element in the array, O(n), Heap sort takes O(nlogn)\
The best, worst, and average case are the same – O(nlogn)
![](images/heap1.png)

## Quick Sort
A divide and conquer algorithm\
Selects a pivot element and partitions the other elements based on if they are less or greater than the pivot. The partitioned sub arrays are then sorted.\
Partition is linear time but adding the recursive call for the elements before and after the pivot, the time complexity is – O(nlogn)\
Best and average case are the same but worst case is O(n^2). This happens when each partition, the pivot is always the smallest or largest element.
![](images/quick1.png)

## Insertion Sort
Insertion Sort time complexity is O(n^2)\
It iterates over the array while checking if the elements to the left are less than the current one, if so swaps\
Worst case is when array is reversely sorted
![](images/insertion1.png)

## Merge Sort
Merge Sort time complexity is O(nlog(n)) or T(n) = 2T(n/2)+O(n)\
Divides itself into two halves, and recurses for the two halves and then merges the two together to get a fully sorted array. Takes linear time to merge the two, so it’s just nlog(n)\
No worst case. Time complexity is O(nlog(n)) for everything –worst, average, best case.
![](images/merge1.png)




