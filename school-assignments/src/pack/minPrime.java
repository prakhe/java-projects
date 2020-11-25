package pack;

import java.util.Scanner;

public class minPrime {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //Initializing variables
        int[] arr = new int[10];
        int min = 1000;
        //Taking user input into the array
        for(int i=0;i<10;i++)
            arr[i] = sc.nextInt();
        //Going through each element of the array
        for(int i=0;i<10;i++)
        {
            //Checking if current element is prime
            //(count of numbers it is divisible by should be 2)
            int x = arr[i];
            int count = 0;
            for(int j=1;j<=x;j++)
            {
                if(x%j==0)
                    count++;
            }
            //If it is prime, we check if it is minimum
            if(count==2)
                if(x<min)
                    //if it is minimum so far, then this is our new minimum
                    min=x;
        }
        //Displaying the minimum prime number if we found it
        if(min!=1000)
            System.out.println("The smallest prime number entered was: "+min);
    }
}