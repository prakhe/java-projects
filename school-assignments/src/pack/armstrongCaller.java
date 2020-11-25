package pack;

import java.util.Scanner;

public class armstrongCaller {

    boolean isArmstrong(int n)
    {
        //Seperate each digit and add its cube to the total sum
        int temp=n,digit=0,sum=0;
        while(temp!=0)
        {
            digit=temp%10;
            sum+=Math.pow(digit,3);
            temp=temp/10;
        }
        //If sum is equal to the original number then this will return true
        return (sum == n);
    }

    void call()
    {
        Scanner sc = new Scanner(System.in);
        //giving max an initial value to compare to later
        int max = -1;
        for(int i=0;i<10;i++)
        {
            //Each time a number is entered, isArmstrong is called to check if it is an armstrong's number
            System.out.println("Enter the "+(i+1)+" number");
            int num = sc.nextInt();
            if(isArmstrong(num))
                //If it is an armstrong's number then it is checked if it is the biggest we found so far
                if(num>max)
                    max = num;
        }
        //If no armstrongs number was found, the value stored in max won't change from -1
        if(max!=-1)
            System.out.println("Largest Armstrong's number was: "+max);
        else
            System.out.println("None of them were an Armstrong's number");
    }



    public static void main(String[] args) {
        armstrongCaller obj = new armstrongCaller();
        System.out.println(obj.isArmstrong(370));
        obj.call();
    }
}