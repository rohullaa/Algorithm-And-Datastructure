
//Importer alle noedvendige biblioteker
import java.util.LinkedList;

class Oppgave {
    // erklaere og initialisere alle Oppgave-relaterte detaljer
    public int id;
    public int tid;
    public int arbeider;
    public String navn;
    public int tidlig_start = -1; // standard verdier
    public int tidlig_ferdig = -1;
    public int sent_start = -1;
    public int sent_ferdig = -1;
    public int slack = -1;
    public Boolean kritisk = null;
    public LinkedList<Oppgave> forgjengere = new LinkedList<>(); // Liste over alle forgjengere av en Oppgave
    public LinkedList<Oppgave> etterkommere = new LinkedList<>(); // Liste over alle etterkommere av en Oppgave
    public int besoek_status = 0;
    public int tellForgjeng = 0;

    // konstruktoer av Oppgave-klassen
    public Oppgave(int id, String navn, int tid, int arbeider) {
        this.id = id;
        this.tid = tid;
        this.arbeider = arbeider;
        this.navn = navn;
    }

    // legger til forgjenger av en oppgave (avhengigheter av en bestemt oppgave)
    public void leggtil_forgjenger(Oppgave forgjenger) {
        this.forgjengere.add(forgjenger);
        this.tellForgjeng++;
    }

    //legger til anstendig av en Oppgave
    public void leggtil_anstendig(Oppgave anstendig) {
        this.etterkommere.add(anstendig);
    }

    // sjekker hvis prosjektet er realiserbart - i utgangspunktet sjekk hvis grafen er syklisk
    public LinkedList<Oppgave> realiserbar(LinkedList<Oppgave> novaerende_vei) {

        if (this.besoek_status == 1) { // vis allerede besoekt node return tilbake
            novaerende_vei.add(this);
            return novaerende_vei;
        }

        else {
            novaerende_vei.add(this);
            this.besoek_status = 1;
            LinkedList<Oppgave> sjekk = null;
            for (Oppgave t : this.forgjengere) { // gaa gjennom forgjengeren og sjekk hvis det er en sykel
                sjekk = t.realiserbar(novaerende_vei);
                if (sjekk != null) {
                    return sjekk;
                }
            }
            this.besoek_status = 2;
            novaerende_vei.removeLast(); //O (1) sletting, siden bare siste element er slettet.
            return null;
        }
    }

    // denne funksjonen setter tidlig_start tid av en Oppgave
    public void sett_tidlig() {
        if (this.forgjengere.size() == 0) {
            this.tidlig_start = 0;
            this.tidlig_ferdig = tid;
            return;
        } else {
            int maks_tid = -1;
            for (Oppgave forgjeng : forgjengere) {
                if (forgjeng.tidlig_start == -1) {
                    forgjeng.sett_tidlig();
                }
                if (forgjeng.tidlig_ferdig > maks_tid) {
                    maks_tid = forgjeng.tidlig_ferdig;
                }
            }
            this.tidlig_start = maks_tid;
            this.tidlig_ferdig = this.tidlig_start + tid;
            return;

        }
    }

    // denne funksjonen angir sent_start og sent_ferdig tid av en Oppgave
    public void sett_sent(int min_ferdig_tid) {
        if (this.etterkommere.size() == 0) { // hvis det ikke er noen etterkommere saa er Oppgave kritisk og slack er null
            if (this.tidlig_ferdig == min_ferdig_tid) {
                this.sent_start = this.tidlig_start;
                this.sent_ferdig = this.tidlig_ferdig;
                this.slack = 0;
                this.kritisk = true;
                return;
            } else {
                if (this.id == 8) {
                }
                this.slack = min_ferdig_tid - this.tidlig_ferdig;
                this.kritisk = false;
                this.sent_start = this.tidlig_start + this.slack;
                this.sent_ferdig = this.tidlig_ferdig + this.slack;
                return;
            }
        } else {
            for (Oppgave anstendig : this.etterkommere) {
                if (anstendig.slack == -1) {
                    anstendig.sett_sent(min_ferdig_tid);
                }
            }
            int minst_slack = -1;
            for (Oppgave anstendig : this.etterkommere) {
                if (minst_slack == -1) {
                    minst_slack = anstendig.tidlig_start - this.tidlig_ferdig;
                }
                int novaerende_slack = anstendig.tidlig_start - this.tidlig_ferdig;
                if (novaerende_slack < minst_slack) {
                    minst_slack = novaerende_slack;
                }
            }

            if (minst_slack == 0) { // hvis minimumsslack er null, er ogsaa oppgaven kritisk
                this.sent_start = this.tidlig_start;
                this.sent_ferdig = this.tidlig_ferdig;
                this.slack = 0;
                this.kritisk = true;
                return;
            } else { // else slack = minimum slack verdi og Oppgave er ikke kritisk
                this.slack = minst_slack;
                this.kritisk = false;
                this.sent_start = this.tidlig_start + this.slack;
                this.sent_ferdig = this.tidlig_ferdig + this.slack;
                return;
            }
        }
    }
}
// End of Oppgave Class
