//Importer alle noedvendige biblioteker
import java.io.FileNotFoundException;

class Oblig2 {
    public static void main(String[] args) {
        Planlegger obj = null; // opprette Planleggger-objekt

        if (args.length != 1) {
            System.out.println("Oppgi inputfilen som argument vsom: java Oblig2 filnavn.txt");
            System.exit(0);
        }
        
        try {
            obj = new Planlegger(args[0]); // hvis ikke funnet satt til standard
        } catch (FileNotFoundException e) {
            System.out.println("Fil ikke funnet!");
        }

        // sjekk om prosjektet er realiserbart eller ikke, hvis ikke, skrivut sykler og terminer.
        obj.realiserbar();

        // skrive ut simulering av programmet etter aa ha utfoert funksjonen sett_tidlig og sett_sent
        obj.simulere();

        // skrive ut info om oppgaver etter at simuleringen er utfoert
        obj.skrivut_info();
    }
}
