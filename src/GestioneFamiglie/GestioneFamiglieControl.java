/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestioneFamiglie;
import SchermataPrincipalePackage.SchermataPrincipaleRappresentante;
import DBMSPackage.DBMSBoundaryClass;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author manfredi
 */
public class GestioneFamiglieControl {
    
    public int idUtente;
    SchermataPrincipaleRappresentante schermataRappresentante;
    GestioneFamigliaBND gestioneFamigliaBND;
    schermataDatiPreliminari schermataDatiPreliminari;
    InserimentoComponentiBND schermataInserimentoComponenti;
    InserimentoDatiBND schermataInserimentoDati;
    InserimentoRecapitoBND schermataInserimentoRecapito;
    InserimentoCFBND inserimentoCF;
    ComponentiFamigliaBND componentiFamiglia;
    InserimentoDatiNuovo inserimentoDatiNuovo;
    InserimentoCFBNDElimina inserimentoCFElimina;
    InserimentoCFBNDBisogni inserimentoCFBisogni;
    CampoBisogniBND campoBisogni;
    DBMSBoundaryClass DBMSBoundary = new DBMSBoundaryClass();
    List<DatiComponente> componentiAggiunti = new ArrayList<>();
    PopUp popUp;
    PopUp1 popUp1;
    public int counter = 1;
    public String CFReferente;
    public int idFamiglia;
    
    public GestioneFamiglieControl(SchermataPrincipaleRappresentante schermataRappresentante){
        this.schermataRappresentante = schermataRappresentante;
    }
    
    public void createGestioneFamigliaBND(){
        this.gestioneFamigliaBND = new GestioneFamigliaBND();
        this.gestioneFamigliaBND.mostra(this);
        this.schermataRappresentante.setVisible(false);
    }
    
    public void createSchermataDatiPreliminari(){
        this.schermataDatiPreliminari = new schermataDatiPreliminari();
        this.schermataDatiPreliminari.mostra(this);
    }
    
    public void createInserimentoDatiBND(){
        this.schermataInserimentoDati = new InserimentoDatiBND();
        this.schermataInserimentoDati.mostra(this);
    }
    
    public void createInserimentoDatiNuovo(){
        this.inserimentoDatiNuovo = new InserimentoDatiNuovo();
        this.inserimentoDatiNuovo.mostra(this);
    }
    
    public void createInserimentoCF(){
        this.inserimentoCF = new InserimentoCFBND();
        this.inserimentoCF.mostra(this);
    }
    
    public void createInserimentoCFElimina(){
        this.inserimentoCFElimina = new InserimentoCFBNDElimina();
        this.inserimentoCFElimina.mostra(this);
    }
    public void createInserimentoCFBisogni(){
        this.inserimentoCFBisogni = new InserimentoCFBNDBisogni();
        this.inserimentoCFBisogni.mostra(this);
    }
    public void createComponentiFamiglia(){
        this.componentiFamiglia = new ComponentiFamigliaBND();
        
    }
    
    public void createCampoBisogniBND(String CF){
        this.campoBisogni = new CampoBisogniBND();
        this.campoBisogni.mostra(this,CF);
    }
    
    public void verificaIsee(){
        if(this.schermataDatiPreliminari.getIsee() < 6000){
            this.schermataInserimentoComponenti = new InserimentoComponentiBND();
            this.schermataDatiPreliminari.setVisible(false);
            this.schermataInserimentoComponenti.mostra(this);
        }
        else{
            PopUp popUp = new PopUp();
            popUp.mostraErrore(this);
            this.schermataDatiPreliminari.setVisible(false);
        }
    }
    
    
    private boolean isNomeCognomeValido(String input) {
    // Definisci il pattern regex per controllare che la stringa contenga solo lettere e spazi
    String regex = "^[a-zA-Z ]+$";

    // Confronta la stringa con il pattern regex
    return input.matches(regex);
    }
    
    private boolean contieneSoloNumeri(String input) {
    // Definisci il pattern regex per controllare se la stringa contiene solo numeri
    String regex = "\\d+";

    // Confronta la stringa con il pattern regex
    return input.matches(regex);
    }

    public void controllaDati(String nome, String cognome, String eta, String CF, String bisogni){
        int etaConverted = 0;
        
        if(this.isNomeCognomeValido(nome)){
            if(this.isNomeCognomeValido(cognome)){
                if(this.contieneSoloNumeri(eta)){
                    etaConverted = Integer.valueOf(eta);
                    if(etaConverted >= 0){
                        if(this.DBMSBoundary.controllaCF(CF)){
                            if(this.counter == 1){
                                this.CFReferente = CF;
                            }
                            this.componentiAggiunti.add(new DatiComponente(nome,cognome,etaConverted,CF,bisogni));
                            if(this.counter < this.schermataDatiPreliminari.getComponenti()){
                                this.schermataInserimentoComponenti.mostra(this);
                                System.out.println("Contatore --> " + this.counter + " NComponenti --> " + this.schermataDatiPreliminari.getComponenti());
                            }
                            if(this.counter == this.schermataDatiPreliminari.getComponenti()){
                                System.out.println("Entrato " + this.schermataDatiPreliminari.getComponenti());
                                this.schermataInserimentoRecapito = new InserimentoRecapitoBND();
                                this.schermataInserimentoRecapito.mostra(this);
                                this.schermataInserimentoDati.setVisible(false);
                            }
                            

                            }
                        }
                    }
                }
            }
        else{
            this.schermataInserimentoComponenti.mostra(this);
            }
    }
    


    
    private int generaId(){
        int idFamiglia = this.DBMSBoundary.generaID();
        return idFamiglia;
    }
    
    public void registraFamiglia(){
        this.idUtente = this.schermataRappresentante.utente.getIdUtente();
        long recapito = this.schermataInserimentoRecapito.recapito;
        DatiFamiglia famiglia = new DatiFamiglia(this.generaId(), this.idUtente,recapito,this.componentiAggiunti,this.CFReferente);
        this.DBMSBoundary.inserisciFamiglia(famiglia);
        this.schermataRappresentante.mostra();
    }
    
    //Caso d'uso aggiungi componente
    public void verificaCF(String CF){
        System.out.println(this.DBMSBoundary.verificaCF(CF));
        if(this.DBMSBoundary.verificaCF(CF)){
            List<DatiComponente> famiglia = this.DBMSBoundary.getCompon(CF);
            this.createComponentiFamiglia();
            this.componentiFamiglia.mostra(this,famiglia);
        }
        else{
            this.popUp1 = new PopUp1();
            this.popUp1.mostraErrore(this);
        }
    }
    
    public void verificaCF(String CF, boolean operation){
        System.out.println(CF);
        if(this.DBMSBoundary.verificaCF(CF)){
            this.createInserimentoDatiNuovo();
            this.inserimentoCF.setVisible(false);
        }
        else{
            this.popUp1 = new PopUp1();
            this.popUp1.mostraErrore(this);
        }
    }
   
    public void verificaCF(String CF, int operation){
        if(this.DBMSBoundary.verificaCF(CF, operation)){
            this.DBMSBoundary.elimina(CF);
            this.schermataRappresentante.mostra();
        }
        else{
            this.popUp1 = new PopUp1();
            this.popUp1.mostraErrore(this);
        }
    }
    
    public void verificaCFBisogni(String CF){
        System.out.println(CF + " test");
        if(this.DBMSBoundary.verificaCF(CF, 1)){
            System.out.println("Entrato");
            this.createCampoBisogniBND(CF);
            this.inserimentoCFBisogni.setVisible(false);
        }
        else{
            this.popUp1 = new PopUp1();
            this.popUp1.mostraErrore(this);
        }
    }
    
    public void controllaDatiNuovo(String nome, String cognome, String eta, String CF, String bisogni, String CFRef){
        int etaConverted = 0;
        DatiComponente dati;
        if(this.isNomeCognomeValido(nome)){
            if(this.isNomeCognomeValido(cognome)){
                if(this.contieneSoloNumeri(eta)){
                    etaConverted = Integer.valueOf(eta);
                    if(etaConverted >= 0){
                        if(this.DBMSBoundary.controllaCF(CF)){
                            dati = new DatiComponente(nome,cognome,etaConverted,CF,bisogni);
                            this.idFamiglia = this.DBMSBoundary.getIDFamiglia(CFRef);
                            this.DBMSBoundary.inserisciComponente(dati,this.idFamiglia);
                            this.schermataRappresentante.mostra();
                            }
                        }
                    }
                }
            }
        else{
            this.popUp1 = new PopUp1();
            this.popUp1.mostraErrore(this);
        }
    }
    
    public void aggiornaBisogni(String bisogni,String CF){
        this.DBMSBoundary.aggiornaBisogni(bisogni,CF);
    }
}
