package myPack;

import java.util.Scanner;

public class thealgoclass {

	public Scanner sc;
	public int g,p;
	public long A,B;
	
	thealgoclass(int x, int y)
	{
		sc= new Scanner(System.in);
		p= x;
		g= y;
	}
	
	int generate_public_key_for_a()
	{
		System.out.println("A: Enter your secret number");
		int a= sc.nextInt();
		A= (long) (Math.pow(g, a) % p);
		return a;
	}
	
	int generate_public_key_for_b()
	{
		System.out.println("B: Enter your secret number");
		int b= sc.nextInt();
		B= (long) (Math.pow(g, b) % p);
		return b;
	}
	
	long generate_shared_secret_key_for_a(int a)
	{
		long s= (long) (Math.pow(B,a) % p);
		return s;
	}
	
	long generate_shared_secret_key_for_b(int b)
	{
		long s= (long) (Math.pow(A,b) % p);
		return s;
	}
	
	void execute()
	{
		int a= generate_public_key_for_a();
		int b= generate_public_key_for_b();
		long s1= generate_shared_secret_key_for_a(a);
		long s2= generate_shared_secret_key_for_b(b);
		
		if(s1==s2)
		{
			System.out.println("Algorithm was successful.");
			System.out.println("Public key is:"+s1);
		}
		else
			System.out.println("Algorithm failed");
	}
	
	public static void main(String[] args) {
		Scanner sc= new Scanner(System.in);
		
		System.out.println("Enter the prime number");
		int x= sc.nextInt();
		System.out.println("Enter the generator");
		int y= sc.nextInt();
		
		thealgoclass t= new thealgoclass(x,y);
		
		t.execute();
	}

}
