
import java.sql.SQLException;
import java.util.Scanner;

public class accessClass {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner sc= new Scanner(System.in);
        actionsClass obj= new actionsClass();

        System.out.println("Enter the username and password");
        String uname= sc.nextLine();

        String pass= sc.nextLine();
        obj.createConnection(uname,pass);

        System.out.println("The database is calibrated: "+obj.isCalib().next());

        String cont="y";
        while(cont.equals("y") || cont.equals("Y"))
        {
            System.out.println("Choose the operation to perform \n 1. Insert \n 2. Update \n 3. Delete");
            int ch= Integer.parseInt(sc.nextLine());
            switch (ch)
            {
                case 1:
                {
                    System.out.println("Enter the wing and the flat number");
                    String wing= sc.nextLine();
                    int fno= Integer.parseInt(sc.nextLine());
                    System.out.println("Flat exists: "+obj.checkFlat(wing,fno));
                    System.out.println("Enter the name, phone number and if it is rented (true/false)");
                    String name= sc.nextLine();
                    long phno= Long.parseLong(sc.nextLine());
                    boolean rent= Boolean.parseBoolean(sc.nextLine());
                    obj.setData(wing,fno,name,phno,rent);
                    System.out.println("Insertion was completed!");
                }
                break;

                case 2:
                {
                    System.out.println("Enter the wing and the flat number");
                    String wing= sc.nextLine();
                    int fno= Integer.parseInt(sc.nextLine());
                    System.out.println("Flat exists: "+obj.checkFlat(wing,fno));
                    System.out.println("Enter the name, phone number and if it is rented (true/false)");
                    String name= sc.nextLine();
                    long phno= Long.parseLong(sc.nextLine());
                    boolean rent= Boolean.parseBoolean(sc.nextLine());
                    obj.setData(wing,fno,name,phno,rent);
                    System.out.println("Updating was completed!");
                }
                break;

                case 3:
                {
                    System.out.println("Enter the wing and the flat number");
                    String wing= sc.nextLine();
                    int fno= Integer.parseInt(sc.nextLine());
                    System.out.println("Flat exists: "+obj.checkFlat(wing,fno));
                    obj.remove(wing,fno);
                    System.out.println("Deletion was completed!");
                }
                break;
            }
            System.out.println("Do you want to continue? (y/n)");
            cont= sc.nextLine();
        }
    }
}
