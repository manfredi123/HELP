/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConsegnaViveriPackage;
import SchermataPrincipalePackage.SchermataPrincipaleAzienda;
import java.util.List;
import java.util.ArrayList;
import DBMSPackage.*;
/**
 *
 * @author manfredi
 */
public class ConsegnaViveriControl{
    public SchermataPrincipaleAzienda schermataAzienda;
    public InserimentoDatiViveriSchermata schermataInserimentoDati = new InserimentoDatiViveriSchermata();
    public DBMSBoundaryClass DBMSBoundary = new DBMSBoundaryClass();
    public PopUp popUp = new PopUp();
    public PopUp popUpInvio = new PopUp();
    public List<Viveri> viveriDonati = new ArrayList<>();
    public int IDutente;
    //Variabili di servizio per il metodo inserisciViveri
    public int counter = 0;
    public int QTAViveri;
   
   
    public ConsegnaViveriControl(SchermataPrincipaleAzienda az){
        this.schermataAzienda = az;
    }
   //Caso d'uso aggiungi donazione
    public AggiungiDonazioneSchermata createAggiungiDonazione(){
        return new AggiungiDonazioneSchermata();
    }
   
    public InserimentoViveriSchermata createInserimentoViveri(){
        return new InserimentoViveriSchermata();
    }
  
   
    public synchronized void inserisciViveri(InserimentoViveriSchermata schermata){
        this.IDutente = this.schermataAzienda.utente.getIdUtente();
        this.QTAViveri = schermata.getQta();
        
        if(this.counter < this.QTAViveri)
            this.schermataInserimentoDati.mostra(this);
        else{
            this.popUpInvio.mostraConferma(this, true);
        }
       
    }
    //Stesso metodo di sopra, ma questo viene chiamato dal pop up dal secondo inserimento in poi
    public synchronized void inserisciViveri(){
        this.IDutente = this.schermataAzienda.utente.getIdUtente();
        this.viveriDonati = this.schermataInserimentoDati.getViveri();
        if(this.counter < this.QTAViveri)
            this.schermataInserimentoDati.mostra(this);
        else{
            this.aggiungi();
            this.schermataAzienda.mostra();
        }
    }
    
    
    //Metodo chiamato dopo l'inserimento della donazione per inseririrla nel database
    public void aggiungi(){
        this.viveriDonati = this.schermataInserimentoDati.getViveri();
        this.DBMSBoundary.aggiungi(this.viveriDonati, this.IDutente);
        this.schermataAzienda.mostra();
    }    
   
}
