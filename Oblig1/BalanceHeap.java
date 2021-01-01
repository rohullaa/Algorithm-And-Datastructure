// importere alle nødvendige biblioteker
import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.Scanner;
import java.util.Arrays;


class BalanceHeap
{
	public static void main (String[] args) throws java.lang.Exception
	{
		Scanner sc = new Scanner(System.in); //skanner klasse for å lese input fra konsollen
		int n; // størrelse på sortert array av integers
		System.out.println("Oppgi størrelse på array: \n");
		n=sc.nextInt(); //Angi størrelse på sortert array av integers
		PriorityQueue<Integer> PriorityQueue2 = new PriorityQueue<Integer>(); //Priority Queue

		System.out.println("\nOppgi elementene: \n");

		for(int i=0;i<n;i++){
		    int input= sc.nextInt(); // lese input integer fra konsoll
		    PriorityQueue2.offer(input); // legge til alle elementene i PriorityQueue2
		}

		System.out.println("\nOutput:\n");

        Object[] objArray = PriorityQueue2.toArray();
        Integer Array[] = new Integer[n]; // lage integer array objekt
        System.arraycopy(objArray, 0, Array, 0, n);
		skrivUt_balansert_rekkefølgen(Array,0,n-1); // kaller denne funksjonen for å skrive ut ønsket output

	}

	public static void skrivUt_balansert_rekkefølgen(Integer arr[],int l, int r){ // dele og erobre teknikk
	    if(l>r){ // base condition
	        return;
	    }

	    int mid=(l+r)/2; // får midt indeks av array

	    System.out.println(arr[mid]); // skriv alltid ut midtelementet i hver subkall
	    skrivUt_balansert_rekkefølgen(arr,mid+1,r); //rekursiv kall på høyre halvdel av array
	    skrivUt_balansert_rekkefølgen(arr,l,mid-1); // rekursiv kall på venstre halvdel av array
	}
}
