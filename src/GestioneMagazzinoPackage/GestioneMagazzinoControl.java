/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestioneMagazzinoPackage;
import SchermataPrincipalePackage.SchermataPrincipaleDiocesi;
import SchermataPrincipalePackage.SchermataPrincipaleRappresentante;
import ConsegnaViveriPackage.Viveri;
import GestioneAccount.SchemaDistribuzioneDiocesi;
import DBMSPackage.DBMSBoundaryClass;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.time.LocalDate;
import java.util.Date;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author manfredi
 */
public class GestioneMagazzinoControl {
    
    
    SchermataPrincipaleDiocesi schermataDiocesi;
    SchermataPrincipaleRappresentante schermataRappresentante;
    ControlloErroriBND schermataControlloErrori = new ControlloErroriBND();
    GestioneMagazzinoBND gestioneMagazzinoBND;
    DonazioniRicevuteBND schermataDonazioniRicevuteBND;
    ScaricoMagazzinoBND schermataScaricoMagazzinoBND;
    ScaricoMagazzinoBNDPolo schermataScaricoMagazzinoBNDPolo;
    ModificaContenutoBND modificaContenutoBND;
    SchermataSegnalazioneErroreBND segnalazioneErrore;
    DBMSBoundaryClass DBMSBoundary = new DBMSBoundaryClass();
    PopUp1 popUp = new PopUp1();
    PopUp3 popUp3 = new PopUp3();
    int idUtente;
    
    public GestioneMagazzinoControl(SchermataPrincipaleDiocesi schermataDiocesi){
        this.schermataDiocesi = schermataDiocesi;
        this.idUtente = this.schermataDiocesi.utente.getIdUtente();
        this.DBMSBoundary = new DBMSBoundaryClass();
    }
    
    public GestioneMagazzinoControl(SchermataPrincipaleRappresentante schermataRappresentante){
        this.schermataRappresentante = schermataRappresentante;
        this.idUtente = this.schermataRappresentante.utente.getIdUtente();
    }
    
    public void createGestioneMagazzinoBND(SchermataPrincipaleRappresentante schermataRappresentante){
        this.schermataRappresentante = schermataRappresentante;
        this.gestioneMagazzinoBND = new GestioneMagazzinoBND();
        this.gestioneMagazzinoBND.mostra(this);
        this.schermataRappresentante.setVisible(false);
    }
    
    public void createDonazioniRicevute(){
        this.schermataDonazioniRicevuteBND = new DonazioniRicevuteBND();
    }
    
    public void createSchermataSegnalazioneErrore(){
        this.segnalazioneErrore = new SchermataSegnalazioneErroreBND();
        this.segnalazioneErrore.mostra(this);
    }
    
    
    public void richiediErrori(){
        List<Errore> erroriDiocesi;
        this.idUtente = this.schermataDiocesi.utente.getIdUtente();
        System.out.println("TEST "+ this.idUtente);
        erroriDiocesi = this.DBMSBoundary.richiediErrori(this.idUtente);
        for(int i=0; i<erroriDiocesi.size(); i++){
            System.out.println(erroriDiocesi.get(i));
        }
        this.schermataControlloErrori.mostraErrori(erroriDiocesi);
       
        this.schermataControlloErrori.mostra(schermataDiocesi, this);
    }
    
    public void rimuoviErrori(){
        this.DBMSBoundary.rimuoviErrori(this.idUtente);
        this.schermataDiocesi.setVisible(true);
    }
    
    //Caso d'uso verifica carico
    public void donazioniRicevute(GestioneMagazzinoBND gestioneMagazzinoBND){
        List<Donazioni> donazioni;
        donazioni = this.DBMSBoundary.donazioniRicevute(this.idUtente);
        this.gestioneMagazzinoBND.setVisible(false);
        this.createDonazioniRicevute();
        this.schermataDonazioniRicevuteBND.mostra(this,donazioni);
    }
    
    public void datiMagazzino(){
        this.idUtente = this.schermataDiocesi.utente.getIdUtente();
        List<Viveri> datiMagazzino = this.DBMSBoundary.datiMagazzino(idUtente);        
        this.schermataScaricoMagazzinoBND = new ScaricoMagazzinoBND();
        this.schermataScaricoMagazzinoBND.mostra(this,datiMagazzino);
        this.schermataDiocesi.setVisible(false);
        
    }
    
    public void datiMagazzino(boolean isPolo){
        this.idUtente = this.schermataRappresentante.utente.getIdUtente();
        List<Viveri> datiMagazzino = this.DBMSBoundary.datiMagazzino(idUtente, true);        
        this.schermataScaricoMagazzinoBNDPolo = new ScaricoMagazzinoBNDPolo();
        this.schermataScaricoMagazzinoBNDPolo.mostra(this,datiMagazzino);
        this.schermataRappresentante.setVisible(false);
    }
    
    public void datiMagazzino1(){
        this.idUtente = this.schermataRappresentante.utente.getIdUtente();
        List<Viveri> datiMagazzino = this.DBMSBoundary.datiMagazzino(idUtente, true);        
        this.modificaContenutoBND = new ModificaContenutoBND();
        this.modificaContenutoBND.mostra(this,datiMagazzino);
        this.schermataRappresentante.setVisible(false);
    }
    
    public void rimuovi(List<Viveri> daRimuovere){
        this.DBMSBoundary.rimuovi(daRimuovere, this.idUtente);
    }
    public void rimuovi(List<Viveri> daRimuovere, boolean isPolo){
        this.DBMSBoundary.rimuovi(daRimuovere, this.idUtente, true);
    }
    
    public void modificaDataScarico(){
        int id = this.schermataRappresentante.utente.getIdUtente();
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String oggi = now.format(formatter);
        this.DBMSBoundary.modificaDataScarico(id,oggi);
    }
    
}
