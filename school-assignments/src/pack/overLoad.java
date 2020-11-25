package pack;

public class overLoad {

    void prStr(String s1, String s2)
    {
        int count1=0, count2=0;
        //To count the number of vowels in the first string
        for(int i=0;i<s1.length();i++)
        {
            if(s1.charAt(i)=='A' | s1.charAt(i)=='E' | s1.charAt(i)=='I' | s1.charAt(i)=='O' | s1.charAt(i)=='U' |
                    s1.charAt(i)=='a' | s1.charAt(i)=='e' | s1.charAt(i)=='i' | s1.charAt(i)=='o' | s1.charAt(i)=='u')
                count1++;
        }
        //To check the number of vowels in the second string
        for(int i=0;i<s2.length();i++)
        {
            if(s2.charAt(i)=='A' | s2.charAt(i)=='E' | s2.charAt(i)=='I' | s2.charAt(i)=='O' | s2.charAt(i)=='U' |
                    s2.charAt(i)=='a' | s2.charAt(i)=='e' | s2.charAt(i)=='i' | s2.charAt(i)=='o' | s2.charAt(i)=='u')
                count2++;
        }
        //To compare the counts of vowels and check for the bigger one
        if(count1>count2)
            System.out.println("The string with more vowels is: "+s1);
        else if(count2>count1)
            System.out.println("The string with more vowels is: "+s2);
        else
            System.out.println("Equal number of vowels in both");
    }

    void prStr(String s, char ch)
    {
        //To replace all occurences of spaces with given character in the string
        s = s.replaceAll(" ",String.valueOf(ch));
        System.out.println(s);
    }

    void prStr(String s)
    {
        //To find the first and last index of letter 'G' in the given string.
        char letterToCheck = 'G';
        System.out.println("The first position of the letter "+letterToCheck+" is: "+s.indexOf(letterToCheck));
        System.out.println("The first position of the letter "+letterToCheck+" is: "+s.lastIndexOf(letterToCheck));
    }

    public static void main(String[] args) {
        overLoad obj = new overLoad();
        //This will call the first of the overloaded functions to check which string has more number of vowels
        obj.prStr("String one to check", "String two to check");
        //This will call the second of the overloaded functions to replace all occurences of space with given char
        obj.prStr("Hello World this is an example",'g');
        //This will call the third of the overloaded functions to give first and last position of the letter 'G' in the
        //given string
        obj.prStr("G: A g string to check first and last pos of G");
    }
}