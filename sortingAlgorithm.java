import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class sortingAlgorithm {
    int n = 100; // default n size 100
    //instance variables
    paintIntegers[] origArray = new paintIntegers[n]; // to keep orig array in case user wants to compare
    paintIntegers[] toSort; // the array to be sorted and updated
    Random rand = new Random(); // for the shuffling method
    // for the time elapsed
    long start, end;
    long timeElapsed;
    //ararylist for the animation
    private ArrayList<paintIntegers[]> sorting = new ArrayList<paintIntegers[]>(n * n);
    String[] algos = {"Insertion" , "Merge", "Heap", "Quick", "Radix", "Counting"};

    public sortingAlgorithm(){ //inits arrays and algos
        randomArray(n);
        toSort = origArray.clone(); // leaves origarray alone just in case user wants to compare
        // origarray would be the same no matter what, allowing user to compare
    }

    public ArrayList<paintIntegers[]> sort(String algo){
        //checks which the user selected and does the sort
        start = System.nanoTime(); // starts the timer for the time elapsed
        if(algo.equals("Insertion")){
            insertionSort();
        }else if(algo.equals("Merge")){
            mergeSort();
        }else if(algo.equals("Heap")){
            heapSort();
        }else if(algo.equals("Quick")){
            quickSort();
        }else if(algo.equals("Radix")){
            radixSort();
        }else if(algo.equals("Counting")){
            countingSort();
        }else{
            System.out.println("Tf u doing");
        }
        end = System.nanoTime();
        timeElapsed = end - start; // gets how long the algorithm took to sort
        //returns the arraylist of the animation
        return sorting;
    }

    public void insertionSort(){
        sorting.clear(); // clears the arraylist - to reset
        toSort = paintIntegers.deepCopy(origArray); // resets the array to sort
        for(int i = 1; i < toSort.length; i++){//iterates over the array
            int j = i;//makes j as the current index
            while(j > 0 && toSort[j].val < toSort[j - 1].val){//while elements on the left are less than the current, swaps
                int temp = toSort[j].val;
                //swaps
                toSort[j].val = toSort[j - 1].val;
                toSort[j - 1].val = temp;
                //adds current array to list -> for the animation
                //its like frames, each swap i add to list
                //cant simply do clone, cus somehow its a reference
                //shallow copy, so had to make a new method for deep copy
                sorting.add(paintIntegers.deepCopy(toSort));
                j -= 1;
            }
        }
    }

    public void mergeSort(){
        sorting.clear();
        toSort = paintIntegers.deepCopy(origArray);
        //calls the actual merge sort method
        mergeSortAlgo(toSort, 0, toSort.length - 1);
    }
    public void mergeSortAlgo(paintIntegers[] arr, int lidx, int ridx) {
        if (lidx < ridx)
        {
            //middle to split the array in half
            int mid = (lidx + ridx) / 2;

            //recursive, sort left and right array
            mergeSortAlgo(arr, lidx, mid);
            //left is from lidx to middle
            mergeSortAlgo(arr , mid + 1, ridx);
            //right is from mid to ridx

            //merge together
            merge(arr, lidx, mid, ridx);
        }
    }
    public void merge(paintIntegers[] arr, int lidx, int midx, int ridx){
        //lenght of the left and right arrays
        int leftn = midx - lidx + 1;
        int rightn = ridx - midx;

        //instantiating the left and right arrays
        int Left[] = new int [leftn];
        int Right[] = new int [rightn];

        //copies the data from original to the left and right
        for (int i=0; i < leftn; ++i) {
            Left[i] = arr[lidx + i].val;
        }
        for (int j=0; j < rightn; ++j) {
            Right[j] = arr[midx + 1 + j].val;
        }

        //merges the two arrays and updates the original array
        //initializes leftIndex and rightIndex to iterate over the left and right arrays
        //if it is added to the original array, then where it came from, it would increment
        int leftIndex = 0;
        int rightIndex = 0;
        int k = lidx; //to properly align where the elements to go, left
        //System.out.println(k);
        //while it hasnt gone through all the elements in the left and right array
        //between the two arrays check which one is lowest, and then updates original array
        while (leftIndex < leftn && rightIndex < rightn)
        {
            if (Left[leftIndex] <= Right[rightIndex])
            {
                arr[k].val = Left[leftIndex];
                sorting.add(paintIntegers.deepCopy(arr));//adds current array to list
                leftIndex++;
            }
            else
            {
                arr[k].val = Right[rightIndex];
                sorting.add(paintIntegers.deepCopy(arr));//adds current array to list
                rightIndex++;
            }
            k++;
        }
        //if there are some left on the left array then just adds to original array
        //possible because all the elements in right array have been added
        //all thats left is the sorted elements from the left array
        while (leftIndex < leftn)
        {
            arr[k].val = Left[leftIndex];
            sorting.add(paintIntegers.deepCopy(arr));//adds current array to list
            leftIndex++;
            k++;
        }
        //same explanation as above but with the right array
        while (rightIndex < rightn)
        {
            arr[k].val = Right[rightIndex];
            sorting.add(paintIntegers.deepCopy(arr));//adds current array to list
            rightIndex++;
            k++;
        }
    }

    public void heapSort(){
        //resets the vars
        sorting.clear();
        toSort = paintIntegers.deepCopy(origArray);

        //build the heap by rearranging the array
        for(int i = toSort.length / 2 - 1; i >= 0; i--){
            //heapify to maintain heap strucutre
            heapify(toSort.length, i);
        }
        //by the end, valid heap

        //iterates over array from end to beginning
        for(int i = toSort.length - 1; i >= 0; i--){
            //swaps - jave sucks
            int javasucks = toSort[0].val;
            toSort[0].val = toSort[i].val;
            toSort[i].val = javasucks;
            //adds to arraylist
            sorting.add(paintIntegers.deepCopy(toSort));

            //call heapify to maintain heap
            heapify(i, 0);
        }
    }
    //heapify at node
    public void heapify(int length, int i){
        int largestidx = i; //largest at i - root
        int left = 2 * i + 1; // left child
        int right = 2 * i + 2; //right child

        //if left is larger
        if(left < length && toSort[left].val > toSort[largestidx].val){
            largestidx = left; //makes the largestidx to be the left one
        }

        //right child largest
        if(right < length && toSort[right].val > toSort[largestidx].val){
            largestidx = right; //makes the largestidx to be the right one
        }

        //if largest not root then not valid
        if(largestidx != i){
            //swaps the value -- also javas ucks
            int javesuxsoswap = toSort[i].val;
            toSort[i].val = toSort[largestidx].val;
            toSort[largestidx].val = javesuxsoswap;
            //adds the current array to arraylist as part of animation
            sorting.add(paintIntegers.deepCopy(toSort));

            //heapify again cuz this changes it up
            heapify(length, largestidx);
        }
    }

    public void quickSort(){
        sorting.clear();
        toSort = paintIntegers.deepCopy(origArray);

        //calls helper function to actually sort
        quickSortHelper(0, toSort.length - 1);
    }

    public void quickSortHelper(int start, int end){
        //checks if start is less than end
        //cuz stupid if it is
        if(start < end){
            //partition index calls partition that returns idx
            //and also puts toSort[partitionidx] in the right place
            int partitionidx = partition(start, end);

            //sorts elements before and after the partitions
            //recursive - nice
            //System.out.println("test");
            quickSortHelper(start, partitionidx - 1); //befre
            quickSortHelper(partitionidx + 1, end); //after
        }
    }
    public int partition(int start, int end){
        int pivot = toSort[end].val; //pivot
        int i = start - 1; //idx of the bot

        //iterates from start to end
        for(int j = start; j < end; j++){
            if(toSort[j].val < pivot){//if element is less than pivot
                i++; //move point up

                //swaps java sucks
                int temp = toSort[i].val;
                toSort[i].val = toSort[j].val;
                toSort[j].val = temp;
                //adds to arraylist
                sorting.add(paintIntegers.deepCopy(toSort));
            }
        }

        //swaps one last time, pivot and i + 1
        int javesucks = toSort[i + 1].val;
        toSort[i + 1].val = toSort[end].val;
        toSort[end].val = javesucks;
        //adds to arraylist
        sorting.add(paintIntegers.deepCopy(toSort));

        //return partition idx
        return i + 1;
    }

    //radix sort
    public void radixSort(){
        sorting.clear(); // clears arraylist so previous animations isnt included
        //should take O(bn) with b being the number of bits required to store each key
        //keep checking right most bit till last bit, and sort
        toSort = paintIntegers.deepCopy(origArray);
        //max so knows whats the max bit to stop at
        int max = getMax();
        //essentially 1-10-100-1000.... starting from lowest to highest bit
        for (int bit = 1; max / bit > 0; bit *= 10){ // int so when bit is greater than the max then stops
            radixSortHelp(bit);
        }
    }
    public void radixSortHelp(int exp){
        //output array for the quite 'sorted' array
        int[] output = new int[toSort.length];
        //count array for occurences of digits
        int[] counting = new int[10];
        //initializes the arrays with 0s
        for(int i = 0; i < output.length; i++){
            output[i] = 0;
        }
        for(int i = 0; i < counting.length; i++){
            counting[i] = 0;
        }
        //get the count of occurences
        for(int i = 0; i < toSort.length; i++){
            //get index of the current loop - for example
            //first one would just be exp = 1, so normal val
            //second one would be exp = 10, so the lowest bit would get cut off
            int idx = toSort[i].val / exp;
            counting[idx % 10] += 1; //get lowest bit for that current val
            //adds animation so shows its doing something here
            //if no animation, then automatically sorts - user wouldnt know how it does that
            toSort[i].col = Color.red;
            sorting.add(paintIntegers.deepCopy(toSort));
            //resets the color
            toSort[i].col = Color.black;
        }
        //update count so that it now has the right position of the current digit of the val in output
        for(int i = 1; i < counting.length; i++){
            counting[i] += counting[i - 1];
        }
        //updating output with quite sorted array using the smallest bits each time
        for(int j = toSort.length - 1; j >= 0; j--){
            //get idx
            int idx = toSort[j].val / exp;
            //fills the output array
            output[counting[idx % 10] - 1] = toSort[j].val;
            //decrements the count
            counting[idx % 10] -= 1;
        }
        //once the output is then built, then updates to original array
        for(int i = 0; i < toSort.length; i ++){
            toSort[i].val = output[i];
            //adds animation
            sorting.add(paintIntegers.deepCopy(toSort));
        }
    }
    public int getMax(){
        int max = toSort[0].val;
        for(int i = 1; i < toSort.length; i++){
            if(toSort[i].val > max){
                max = toSort[i].val;
            }
        }
        return max;
    }

    //counting sort
    public void countingSort(){
        sorting.clear();
        toSort = paintIntegers.deepCopy(origArray);

        // get max from array
        int max = getMax();
        // array for all possible values
        int[] countingArray = new int[max + 1];
        //initializes the arrays with 0s
        for(int i = 0; i < countingArray.length; i++){
            countingArray[i] = 0;
        }
        //goes through array to sort, and counts the occurences of each number
        for(int i = 0; i < toSort.length; i++){
            countingArray[toSort[i].val] += 1;
            //adds to 'animation' and makes the value red
            toSort[i].setColor(Color.red);
            //adds to arraylist
            sorting.add(paintIntegers.deepCopy(toSort));
            //resets the color
            toSort[i].setColor(Color.black);
        }
        //placing the correct values, sorting them
        int j = 0; // current index
        for(int i = 0; i <= countingArray.length - 1; i++){
            int count = countingArray[i]; //number of occurences of the value, i
            while(count > 0){
                toSort[j].val = i; //i is the actual value and it gets put in the right place
                j++;//increment the place of the sorted array
                count -= 1;//decrement count
                //animation, adds to arraylist
                sorting.add(paintIntegers.deepCopy(toSort));
            }
        }
    }
    public void setSize(int size){
        // changes the n, size of the arrays to sort
        n = size;
        //makes new array and changes the toSort array
        randomArray(n);
        toSort = origArray.clone();
    }

    public void randomArray(int size){ //fisher yates shuffle
        //makes a new array
        paintIntegers[] arrayNew = new paintIntegers[size];
        paintIntegers.initPaint(arrayNew);
        //updates n
        n = size;
        for(int i = 0; i < arrayNew.length; i++){
            arrayNew[i].val = i + 1;
        }//init the array with 0 to n;
        for(int i = 0; i < arrayNew.length; i++){ // shuffles the array
            //random index past current -> thats why random
            int ridx = i + rand.nextInt(arrayNew.length - i);
            //swap values
            int temp = arrayNew[ridx].val;
            arrayNew[ridx].val = arrayNew[i].val;
            arrayNew[i].val = temp;
        }
        // new origarray array
        origArray = arrayNew.clone();
    }

    private void print(){
        for(int i = 0; i < toSort.length; i++){
            System.out.print(toSort[i].val + ", ");
        }
        System.out.println();
    }

}