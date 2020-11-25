package pack;

import java.util.Scanner;

public class arrayFrequency {
    int getFrequency(int[] arr, int find)
    {
        //Returns the frequency of the given number (find) in the given array
        int count = 0;
        for(int i=0;i<arr.length;i++)
        {
            if(arr[i]==find)
                count++;
        }
        return count;
    }

    public static void main(String[] args) {
        arrayFrequency obj = new arrayFrequency();
        Scanner sc = new Scanner(System.in);
        int[] arr = new int[10];
        for(int i=0;i<10;i++)
        {
            System.out.println("Enter the number: ");
            arr[i] = sc.nextInt();
        }
        for(int i=0;i<10;i++)
        {
            //Checking if the number in the array at 'i'th position is a 2 digit number
            if(arr[i]>=10 && arr[i]<=99)
            {
                //if it is 2 digit then we need to find its frequency and print it
                int count = obj.getFrequency(arr,arr[i]);
                System.out.println("Frequency of "+arr[i]+" = "+count);
            }
        }
    }
}