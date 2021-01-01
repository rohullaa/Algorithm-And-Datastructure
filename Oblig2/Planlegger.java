
//Importer alle noedvendige biblioteker
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

class Planlegger{

    // lage graf ved hjelp av HashMap dvs. Oppgave (toppunkt) -> [kanter]
    public HashMap<Integer, int[]> avhengighetskart = new HashMap<>();
    public HashMap<Integer, Oppgave> oppgaver = new HashMap<>();
    public int min_ferdig_tid = -1;

    // Hovedlogikk for prosjektet Planleggger
    public Planlegger (String filename) throws FileNotFoundException {

        Scanner skanner = new Scanner(new File(filename)); // samle inn informasjon om prosjektet fra inputfile
        int oppgave_telle = Integer.valueOf(skanner.nextLine());

        while (skanner.hasNextLine()) { // til siste linje er naadd, les og lagre all informasjon om prosjektet
            String linje = skanner.nextLine();
            if (!linje.equals("")) {
                String[] info = linje.split("\\s+");

                int oppgaveId = Integer.valueOf(info[0]);
                String oppgaveNavn = info[1];
                int oppgaveTid = Integer.valueOf(info[2]);
                int oppgaveArbeider = Integer.valueOf(info[3]);

                int[] avhengigheter = null;

                if (info.length - 4 == 1) {
                    avhengigheter = new int[] { 0 };

                } else {
                    avhengigheter = new int[info.length - 4];
                    for (int s = 4; s < info.length - 1; s++) {
                        avhengigheter[s - 4] = Integer.valueOf(info[s]);
                    }
                }
                this.avhengighetskart.put(oppgaveId, avhengigheter); // legge til avhengigheter av hver oppgave 
                oppgaver.put(oppgaveId, new Oppgave(oppgaveId, oppgaveNavn, oppgaveTid, oppgaveArbeider)); // legge til informasjon om hver oppgave i
                                                                                         // Oppgaveliste
            }
        }
        for (Oppgave sitt : oppgaver.values()) { // for hver oppgave fra oppgavelisten, legg til forgjengere
            int[] novaerende_avhengighet = avhengighetskart.get(sitt.id);
            for (int id : novaerende_avhengighet) {
                if (id != 0) {
                    Oppgave forgjenger = oppgaver.get(id);
                    sitt.leggtil_forgjenger(forgjenger);
                    forgjenger.leggtil_anstendig(sitt);

                }
            }
        }
    }

    public LinkedList<Oppgave> oppdage_sykel() { // Enkel DFS for aa sjekke om det finnes en sykel!
        LinkedList<Oppgave> sjekk = null;
        for (Oppgave o : oppgaver.values()) {
            if (o.besoek_status == 0) { // hvis ikke besoekt saa kall naboene
                sjekk = o.realiserbar(new LinkedList<Oppgave>());
                if (sjekk != null) // hvis funnet sykel return true
                    return sjekk;
            }
        }
        return null; // ellers returnere null
    }

    public void realiserbar() { // Algoritme for aa sjekke om prosjektet er realiserbar eller ikke
        LinkedList<Oppgave> sykel = this.oppdage_sykel();
        if (sykel != null) {

            while (sykel.getFirst() != sykel.getLast()) {
                sykel.removeFirst();
            }

            System.out.println("Prosjektplan er ikke realiserbar, sykel funnet se nedenfor:");
            for (Oppgave o : sykel) {
                if (o == sykel.getLast()) {
                    System.out.print("(" + o.id + ", " + o.navn + ")");
                } else {
                    System.out.print("(" + o.id + ", " + o.navn + ")" + " ==> ");
                }
            }
            System.exit(0); // avsluttet programmet
        }
    }

    // denne funksjonen setter tidlig_ferdig som per minimum ferdig  tid
    public void sett_tidlig() {
        for (Oppgave o : oppgaver.values()) {
            if (o.tidlig_start == -1) {
                o.sett_tidlig();
            }
            if (o.tidlig_ferdig > this.min_ferdig_tid) {
                this.min_ferdig_tid = o.tidlig_ferdig;
            }
        }
    }

    // paa samme maate for sent_start
    public void sett_sent() {
        if (this.min_ferdig_tid == -1) {
            this.sett_tidlig();
        }

        for (Oppgave o : oppgaver.values()) {
            if (o.slack == -1) {
                o.sett_sent(this.min_ferdig_tid);
            }
        }
    }

    // denne funksjonen skriver ut Oppgave-informasjonen.
    public void skrivut_info() {
        System.out.println("------------------------------------------------------------------------");
        System.out.println("**** Oppgave oversikt ****");
        for (Oppgave o : oppgaver.values()) {
            System.out.println("\nOppgave id: " + o.id + " |  Oppgave navn: " + o.navn);
            System.out.println("Tid krevet: " + o.tid);
            System.out.println("Manpower krevet: " + o.arbeider);
            System.out.print("Tidlig start: " + o.tidlig_start);
            System.out.println("    |    Tidlig ferdig: " + o.tidlig_ferdig);
            System.out.print("Sent start: " + o.sent_start);
            System.out.println("     |    Sent ferdig: " + o.sent_ferdig);
            System.out.println("Slack: " + o.slack);
            System.out.println("Oppgave avhengighet: ");
            if (o.etterkommere.size() == 0) {
                System.out.println("   Ingen");
            } else {
                System.out.print("   ");
                for (Oppgave p : o.etterkommere) {
                    System.out.print(p.id + " ");
                }
                System.out.println("\n");
            }
        }

        System.out.println("\n\nMinimum sluttid for dette prosjektet er: " + this.min_ferdig_tid);
        System.out.println("\n------------------------------------------------------------------------");
    }

    // denne funksjonen stimulerer den generelle vellykkede prosjektplanen effektivt
    public void simulere() {
        if (this.min_ferdig_tid == -1) {
            this.sett_sent();
        }

        LinkedList<Oppgave> gjenstaede_oppgaven = new LinkedList<>();
        LinkedList<Oppgave> novaerende_loping = new LinkedList<>();
        gjenstaede_oppgaven.addAll(this.oppgaver.values());

        int novaerende_arbeider = 0;
        Boolean sjekk_print = false;

        System.out.println("\n******** Simulering start ********\n");
        for (int tid = 0; tid < this.min_ferdig_tid + 1; tid++) {
            LinkedList<Oppgave> ferdig = new LinkedList<>();
            LinkedList<Oppgave> startet = new LinkedList<>();
            Iterator<Oppgave> sitt_q = novaerende_loping.iterator();
            while (sitt_q.hasNext()) {
                Oppgave d = sitt_q.next();
                if (d.sent_ferdig == tid) {
                    sjekk_print = true;
                    ferdig.add(d);
                    for (Oppgave barn : d.etterkommere) {
                        barn.tellForgjeng--;
                    }
                    novaerende_arbeider -= d.arbeider;
                    sitt_q.remove();
                }
            }

            Iterator<Oppgave> sitt_c = gjenstaede_oppgaven.iterator();
            while (sitt_c.hasNext()) {
                Oppgave o = sitt_c.next();
                if (o.tellForgjeng == 0 && tid == o.sent_start) {
                    sjekk_print = true;
                    novaerende_loping.add(o);
                    startet.add(o);
                    novaerende_arbeider += o.arbeider;
                    sitt_c.remove();
                }
            }

            if (sjekk_print) {
                System.out.println("\nTid: " + tid);
                for (Oppgave o : ferdig) {
                    System.out.println("Ferdig Oppgave Id: " + o.id);
                }
                for (Oppgave o : startet) {
                    System.out.println("Starter Oppgave Id: " + o.id);
                }

                System.out.println("novaerende staff: " + novaerende_arbeider + "\n");
                sjekk_print = false;
            }
        }

        System.out.println("**** kortest mulig tid for dette prosjektet er " + this.min_ferdig_tid + " ****\n");
    }

    // simulering ved hjelp av heap
    public void simulere_heap() {
        OppgaveHeap heap = new OppgaveHeap(); // legg til alle Oppgave i heap (MinHeap), noekkel= Id

        for (Oppgave o : oppgaver.values()) {
            heap.leggTil(o);
        }

        OppgaveNode novaerende = heap.popFoerst();
        int novaerende_tid = novaerende.verdi.sent_start;
        int arbeider = novaerende.verdi.arbeider;
        System.out.println("------------------------------------------------------------------------");
        System.out.println("**** Oppgave oversikt ****");

        System.out.println("\nTid: " + novaerende_tid);
        System.out.println("Starter Oppgave Id: " + novaerende.verdi.id);
        heap.leggtilFerdig(novaerende);
        int teller = 0;
        while (!heap.er_tom()) {
            novaerende = heap.popFoerst();
            if (novaerende.verdi.sent_start == novaerende_tid && !novaerende.startet) {
                System.out.println("Starter Oppgave Id: " + novaerende.verdi.id);
                arbeider += novaerende.verdi.arbeider;
                heap.leggtilFerdig(novaerende);
            } else if (novaerende.verdi.sent_ferdig == novaerende_tid && novaerende.startet) {
                System.out.println("Ferdig Oppgave Id: " + novaerende.verdi.id);
                arbeider -= novaerende.verdi.arbeider;
            } else {
                System.out.println("novaerende staff: " + arbeider);
                if (novaerende_tid < novaerende.verdi.sent_start && !novaerende.startet) {
                    novaerende_tid = novaerende.verdi.sent_start;
                    arbeider += novaerende.verdi.arbeider;
                    System.out.println("\nTid: " + novaerende_tid);
                    System.out.println("Starter Oppgave Id: " + novaerende.verdi.id);
                    heap.leggtilFerdig(novaerende);
                } else if (novaerende_tid < novaerende.verdi.sent_ferdig && novaerende.startet) {
                    novaerende_tid = novaerende.verdi.sent_ferdig;
                    arbeider -= novaerende.verdi.arbeider;
                    System.out.println("\nTid: " + novaerende_tid);
                    System.out.println("Ferdig Oppgave Id: " + novaerende.verdi.id);

                }

            }
        }
        System.out.println("novaerende staff: " + arbeider);
        System.out.println("**** kortest mulig tid for dette prosjektet er " + novaerende_tid + " ****\n");
    }

    //sett til standard all oppgavelatert informasjon etter at simuleringen er fullfoert
    public void toerke_alle_oppgaver() {
        this.min_ferdig_tid = -1;
        for (Oppgave o : oppgaver.values()) {
            o.tidlig_start = -1;
            o.tidlig_ferdig = -1;
            o.sent_start = -1;
            o.sent_ferdig = -1;
            o.slack = -1;
            o.kritisk = null;
            o.besoek_status = -1;
        }
    }
}
// End of Planleggger Class
