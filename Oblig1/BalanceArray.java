// importere alle nødvendige biblioteker
import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.Scanner;


class BalanceArray
{
	public static void main (String[] args) throws java.lang.Exception
	{
		Scanner sc = new Scanner(System.in); //skanner klasse for å lese input fra konsollen
		int n; // størrelse på sortert array av integers
		System.out.println("Oppgi størrelse på array: \n");
		n=sc.nextInt(); //Angi størrelse på sortert array av integers
		System.out.println("\nOppgi elementene: \n");
		int[] arr = new int[n]; // integer array
		for(int i=0;i<n;i++){
		    int input= sc.nextInt(); // lese input integer fra konsoll
		    arr[i]=input; // lagre den i array
		}
		System.out.println("\nOutput:\n");
		skrivUt_balansert_rekkefølgen(arr,0,n-1); // kaller denne funksjonen for å skrive ut ønsket output

	}

	public static void skrivUt_balansert_rekkefølgen(int[] arr,int l, int r){ // dele og erobre teknikk
	    if(l>r){ // base condition
	        return;
	    }

	    int mid=(l+r)/2; //får midt indeks av array

	    System.out.println(arr[mid]); // skriv alltid ut midtelementet i hver subkall
	    skrivUt_balansert_rekkefølgen(arr,mid+1,r); //rekursiv kall på høyre halvdel av array
	    skrivUt_balansert_rekkefølgen(arr,l,mid-1); // rekursiv kall på venstre halvdel av array
	}
}
