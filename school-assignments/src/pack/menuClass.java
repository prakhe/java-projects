package pack;

import java.util.Scanner;

public class menuClass {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your choice: \n 1.Perfect Number \n 2.Special 2 digit number " +
                "\n 3. Multiple Harshad Number");
        int ch = sc.nextInt();
        System.out.println("Enter your number");
        int num = sc.nextInt();

        switch (ch)
        {
            case 1:
            {
                //Case to check for perfect number
                int sum = 0;
                //Getting sum of its positive divisors
                for(int i=1;i<num;i++)
                {
                    if(num%i==0)
                        sum+=i;
                }
                //If the sum is same as the number itself, it is a perfect number
                if(num==sum)
                    System.out.println("The number is a perfect number");
                else
                    System.out.println("The number is not a perfect number");
            }break;

            case 2:
            {
                //Case to check for special 2 digit number
                int temp = num;
                //Getting the first digit
                int digit1 = temp%10;
                temp = temp/10;
                //Getting the second digit
                int digit2 = temp%10;
                //Calculating the sum and product of the 2 digits and
                //comparing the sum of that to the number itself
                int sum = digit1+digit2;
                int prod = digit1*digit2;
                if((sum+prod)==num)
                    System.out.println("The number is a special 2 digit number");
                else
                    System.out.println("The number is not a special 2 digit number");
            }break;

            case 3:
            {
                //Case to check for Multiple Harshad number
                int temp = num;
                int sum = 0;
                //Calculating the sum of the digits
                while(temp!=0)
                {
                    int digit = temp%10;
                    sum+=digit;
                    temp=temp/10;
                }
                //If num is divisible by the sum of its digits we know it is Harshad number
                //but we need to check for multiple Harshad
                if(num%sum==0)
                {
                    int num2 = num/sum;
                    int temp2 = num2;
                    int sum2 = 0;
                    //Same way checking for the next number
                    while(temp2!=0)
                    {
                        int digit = temp2%10;
                        sum2+=digit;
                        temp2=temp2/10;
                    }
                    //If even the next number (number/sum) is Harshad then original number
                    //was multiple harshad
                    if(num2%sum2==0)
                        System.out.println("The number is a Multiple Harshad Number");
                    else
                        System.out.println("The number is not a Multiple Harshad Number");
                }
                else
                    System.out.println("The number is not a Multiple Harshad Number");
            }break;
        }
    }
}
