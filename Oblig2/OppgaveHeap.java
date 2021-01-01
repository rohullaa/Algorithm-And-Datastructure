//Importer alle noedvendige biblioteker
import java.util.*;

class OppgaveHeap{
    public int stoerrelse = 0, maksOppgave=50; //definere novaerende stoerrelse og maksimal Oppgave av prosjektet
    public OppgaveNode[] oppgaver = new OppgaveNode[maksOppgave]; // lager liste over Oppgave


    // funksjon for aa legge til en ny Oppgave
    public void leggTil(Oppgave t){
        stoerrelse++; // oek storrelsen med en
        if(stoerrelse > oppgaver.length - 1){ // hvis antall Oppgave er stoerre enn maksOppgave, saa opprett en ny Oppgave-liste med mer tilgjengelig stoerrelse
            int ny_array_stoerrelse = oppgaver.length * 2;
            OppgaveNode[] ny_Oppgaver = new OppgaveNode[ny_array_stoerrelse];
            for(int i = 1; i < oppgaver.length ; i++) ny_Oppgaver[i] = oppgaver[i];
            oppgaver = ny_Oppgaver; // tilordne den nye Oppgave-listen over stoerre stoerrelse med den forrige
        }

        OppgaveNode novaerende = new OppgaveNode(t);
        oppgaver[stoerrelse] = novaerende;
        int novaerende_indeks = stoerrelse;

        if(novaerende_indeks != 1){
            int foreldreIndeks = novaerende_indeks / 2;
            OppgaveNode foreldre = oppgaver[foreldreIndeks];
            while(novaerende_indeks != 1 && foreldre.noekkel > novaerende.noekkel ){
                oppgaver[foreldreIndeks] = novaerende;
                oppgaver[novaerende_indeks] = foreldre;
                novaerende_indeks = foreldreIndeks;
                foreldreIndeks = novaerende_indeks / 2;
                foreldre = oppgaver[foreldreIndeks];
            }
        }
    }

    // funksjon for aa legge til sluttid
    public void leggtilFerdig(OppgaveNode node){
        stoerrelse++;
        node.satt_til_slutt();
        oppgaver[stoerrelse] = node;
        int novaerende_indeks = stoerrelse;
        if(!(novaerende_indeks == 1)){
            int foreldreIndeks = novaerende_indeks / 2;
            OppgaveNode foreldre = oppgaver[foreldreIndeks];
            while (novaerende_indeks != 1 && foreldre.noekkel > node.noekkel) {
                oppgaver[foreldreIndeks] = node;
                oppgaver[novaerende_indeks] = foreldre;
                novaerende_indeks = foreldreIndeks;
                foreldreIndeks = novaerende_indeks / 2;
                foreldre = oppgaver[foreldreIndeks];
            }
        }

    }

    //returnerer den foerste Oppgave fra Oppgave-listen.
    public OppgaveNode popFoerst(){

        //if list is empty
        if(stoerrelse == 0){
            return null;
        }

        //hvis inneholder bare en Oppgave
        else if(stoerrelse == 1){
            OppgaveNode topp = oppgaver[1];
            oppgaver[1] = null;
            stoerrelse --;
            return topp;
        }

        // hvis ikke over, saa pop den foerste Oppgave og omorganiser Oppgave-listen deretter
        else{
            OppgaveNode foerste = oppgaver[1];
            OppgaveNode siste = oppgaver[stoerrelse];
            oppgaver[1] = siste;
            oppgaver[stoerrelse] = null;
            stoerrelse --;

            int novaerende_indeks = 1;

            while(true){
                OppgaveNode venstre = null;
                OppgaveNode hoeyre = null;
                int venstre_indeks = novaerende_indeks * 2;
                int hoeyre_indeks = novaerende_indeks * 2 + 1;


                if(venstre_indeks <= stoerrelse){
                    venstre = oppgaver[venstre_indeks];
                }
                if(hoeyre_indeks <= stoerrelse){
                    hoeyre = oppgaver[hoeyre_indeks];
                }

                //hvis det ikke er barn
                if(venstre == null && hoeyre == null){
                    break;
                }
                //hvis venstre barn, men ikke hoeyre
                else if(venstre != null && hoeyre == null){
                    if(siste.noekkel > venstre.noekkel){
                        oppgaver[venstre_indeks] = siste;
                        oppgaver[novaerende_indeks] = venstre;
                        novaerende_indeks = venstre_indeks;
                    }
                    else{
                        break;
                    }
                }

                //hvis hoeyre barn men ingen venstre
                else if(hoeyre != null && venstre == null){
                    if(siste.noekkel > hoeyre.noekkel){
                        oppgaver[hoeyre_indeks] = siste;
                        oppgaver[novaerende_indeks] = hoeyre;
                        novaerende_indeks = hoeyre_indeks;
                    }
                    else{
                        break;
                    }
                }

                //hvis begge barn og hvis novaerende mindre / like baade venstre og hoeyre
                else if(siste.noekkel <= venstre.noekkel && siste.noekkel <= hoeyre.noekkel){
                    break;
                }
                else{
                    OppgaveNode minste = null;
                    int minste_indeks = -1;
                    if(venstre.noekkel < hoeyre.noekkel){
                        minste = venstre;
                        minste_indeks = venstre_indeks;
                    }
                    else{
                        minste  = hoeyre;
                        minste_indeks = hoeyre_indeks;
                    }

                    oppgaver[minste_indeks] = siste;
                    oppgaver[novaerende_indeks] = minste;
                    novaerende_indeks = minste_indeks;
                }

            }
            return foerste;
        }
    }

    //retur verdi av Oppgave node ved de gitte indeksene
    public Oppgave hent_indeks(int indeks){
        return (indeks>stoerrelse || indeks<1 ? null: oppgaver[indeks].verdi);
    }

    //returnerer den foerste Oppgave node verdien
    public Oppgave hent_foerst(){
        return oppgaver[1].verdi;
    }

    //returnerer siste Oppgave node verdi
    public Oppgave hent_siste(){
        return oppgaver[oppgaver.length - 1].verdi;
    }

    //sjekker om Oppgave liste er tom eller ikke
    public Boolean er_tom(){
        return stoerrelse == 0;
    }

    //returnerer stoerrelse av Oppgave-listen
    public int stoerrelse(){
        return stoerrelse;
    }
}
