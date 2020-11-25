package pack;

import java.util.Scanner;

public class pairsOfVowels {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the String:");
        String str = sc.nextLine();
        str = str.toUpperCase();
        //To store each of the pairs of vowels
        String temp = "";
        //To count the total number of vowel pairs found
        int count = 0;
        System.out.print("Pair of vowels: ");
        for(int i=0;i<str.length()-1;i++)
        {
            //To check if the character in the string at the current position is a vowel
            if(str.charAt(i)=='A' | str.charAt(i)=='E' | str.charAt(i)=='I' | str.charAt(i)=='O' | str.charAt(i)=='U')
            {
                //To check if the character in the string at the next position is a vowel
                if(str.charAt(i+1)=='A' | str.charAt(i+1)=='E' | str.charAt(i+1)=='I' | str.charAt(i+1)=='O' | str.charAt(i+1)=='U')
                {
                    //if both current and next characters are vowels then we have a pair of vowels
                    temp = temp+str.charAt(i)+str.charAt(i+1);
                    count++;
                    System.out.print(temp+" ");
                    temp = "";
                }
            }
        }
        System.out.println();
        System.out.println("No of Pair of Vowels: "+count);
    }
}
