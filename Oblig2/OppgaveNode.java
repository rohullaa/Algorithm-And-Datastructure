//klasse for aa instantiere OppgaveNode-objekt
class OppgaveNode {
    public int noekkel; // noekkel for aa identifisere en bestemt oppgave
    public Boolean startet = false; // opprinnelig er oppgaven ikke startet, saa merk den som false
    public Oppgave verdi; // verdi of Oppgave node

    public OppgaveNode(Oppgave o) { // konstruktoer for aa initialisere OppgaveNode med noekkel og verdi
        this.noekkel = o.sent_start;
        this.verdi = o;

    }

    public void satt_til_slutt() { // markerer en OppgaveNode som ferdig oppgave
        this.startet = true;
        this.noekkel = verdi.sent_ferdig;
    }
}