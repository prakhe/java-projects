package pack;

import java.util.Scanner;

public class Sort {
    int[] arr;
    int num;

    //Constructor to initialize the variables
    Sort()
    {
        arr = new int[50];
        num = 50;
    }

    //Function to take user inputs
    void inpdata()
    {
        Scanner sc = new Scanner(System.in);
        for(int i=0;i<num;i++)
        {
            System.out.println("Enter the number: ");
            arr[i] = sc.nextInt();
        }
    }

    //Funtion to perform the sort and display the array
    void bubsort()
    {
        //Performing bubble sort
        for(int i=0;i<num-1;i++)
        {
            //Nested for loop to go through the array multiple times
            for(int j=0;j<num-i-1;j++)
            {
                //Swapping if the current element is bigger than its next one (ascending)
                if(arr[j]>arr[j+1])
                {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }

        System.out.println("The sorted array is: ");

        //Displaying the sorted array
        for(int i=0;i<num;i++)
        {
            System.out.print(arr[i]+" ");
        }
    }

    public static void main(String[] args) {
        //Using object to call our methods
        Sort obj = new Sort();
        obj.inpdata();
        obj.bubsort();
    }
}