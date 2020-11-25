import java.util.Scanner;

public class ImplementingClass {

    public static void main(String[] args)
    {
        Scanner sc= new Scanner(System.in);

        String ptype, bodysec="";
        boolean ispain= false;

        System.out.println("Enter your problem type: (Physical OR Emotional)");
        ptype= sc.nextLine();

        if(ptype.equalsIgnoreCase("Physical"))
        {
            System.out.println("Is it a pain related problem? (Yes OR No)");
            String pain= sc.nextLine();
            ispain= pain.equalsIgnoreCase("Yes");

            if(ispain)
            {
                System.out.println("Enter the section of the body where the pain is being experienced");
                System.out.println("(Upper/Middle/Lower)");
                bodysec= sc.nextLine();
            }
        }

        KnowledgeClass obj;

        if(ptype.equalsIgnoreCase("Physical"))
            obj=new KnowledgeClass(ptype,ispain,bodysec);
        else
            obj= new KnowledgeClass(ptype);

        System.out.println("Required basic information has been gathered... Checking for symptoms...");
        obj.performAnalysis();

        sc.close();
    }
}
