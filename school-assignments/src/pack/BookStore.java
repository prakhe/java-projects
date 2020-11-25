package pack;

import java.util.Scanner;

public class BookStore {
    //private class variables
    private String book_name, author_name, publication_name;
    private double cost;

    //constructor to initialize values of class variables
    BookStore()
    {
        this.book_name = "";
        this.author_name = "";
        this.publication_name = "";
        this.cost = 0.0d;
    }

    //function to accept details using scanner class
    void acceptDetails()
    {
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter the name of the book:");
        this.book_name = sc.nextLine();
        System.out.println("Enter the name of the author:");
        this.author_name = sc.nextLine();
        System.out.println("Enter the name of the publication:");
        this.publication_name = sc.nextLine();
        System.out.println("Enter the cost of the book:");
        this.cost = sc.nextDouble();
    }

    //function to calculate discount on the cost of books
    void calculateDiscount()
    {
        double disc_perc = 13.5d;
        double disc_amt = (this.cost*disc_perc)/100;
        double disc_cost = this.cost - disc_amt;
        System.out.println("The discount amount is: "+disc_amt);
        System.out.println("The cost after discount is: "+disc_cost);
    }

    //function to display the details of the book
    void displayDetails()
    {
        System.out.println("The name of the book is: "+this.book_name);
        System.out.println("The name of the author is: "+this.author_name);
        System.out.println("The name of the publication is: "+this.publication_name);
        System.out.println("The cost of the book is: "+this.cost);
    }

    //main method
    public static void main(String[] args) {
        //object creation to access and call all functions of the class "BookStore"
        BookStore book = new BookStore();
        book.acceptDetails();
        book.calculateDiscount();
        book.displayDetails();
    }
}
