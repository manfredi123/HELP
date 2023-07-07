/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestioneAccount;
import DBMSPackage.DBMSBoundaryClass;
import GestioneSistemaPackage.Report;
import LoginPackage.EntityUtente;
import java.util.List;
import java.util.Iterator;
import ConsegnaViveriPackage.Viveri;
import SchermataPrincipalePackage.*;
import LoginPackage.SchermataLogin;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author manfredi
 */


public class GestioneAccountControl {
    int idUtente = 0;
    GestioneRichiesteBND gestioneRichiesteBND;
    DBMSBoundaryClass DBMSBoundary = new DBMSBoundaryClass();
    SchermataRichiesteSpecialiBND schermataRichieste = new SchermataRichiesteSpecialiBND();
    SchermataPrincipaleAzienda schermataPrincipaleAzienda;
    SchermataPrincipaleDiocesi schermataDiocesi;
    SchermataPrincipaleRappresentante schermataRappresentante;
    SchermataSchemaBND schermataSchemaDiocesi;
    SchermataSchemaBNDPolo schermataSchemaPolo;
    GestioneAccountBND gestioneAccountBND;
    InserimentoPasswordBND inserimentoPasswordBND;
    SchermataLogin schermataLogin = new SchermataLogin();
    VisualizzaDateBND visualizzaDateBND;
    PopUp popUp = new PopUp();
    PopUp1 popUp1 = new PopUp1();
    
    
    List<Viveri> viveriRichiesti;
    
    public GestioneAccountControl(){
        
    }
    
    public GestioneAccountControl(SchermataPrincipaleDiocesi schermataDiocesi){
        this.schermataDiocesi = schermataDiocesi;
    }
    
    public GestioneAccountControl(SchermataPrincipaleRappresentante schermataRappresentante){
        this.schermataRappresentante = schermataRappresentante;
    }
    
    public void createGestioneRichieste(EntityUtente utente,SchermataPrincipaleAzienda schermataAzienda){
        this.idUtente = utente.getIdUtente();
        this.schermataPrincipaleAzienda = schermataAzienda;
        this.gestioneRichiesteBND = new GestioneRichiesteBND();
        this.gestioneRichiesteBND.mostra(this);
    }
    
    public void createGestioneAccountBoundary(){
        this.gestioneAccountBND = new GestioneAccountBND();
        this.gestioneAccountBND.mostra(this);
        this.schermataRappresentante.setVisible(false);
    }
    
    public void createInserimentoPassword(){
        this.inserimentoPasswordBND = new InserimentoPasswordBND();
        this.inserimentoPasswordBND.mostra(this);
    }
    
    public void createVisualizzaDateBND(){
        this.visualizzaDateBND = new VisualizzaDateBND();
        this.visualizzaDateBND.mostra(this);
    }
         
    
    //Mostra su schermataRichiesteSpeciali l'array viveri richiesti prendendolo da database
    public void richiesteSpeciali(){
        this.viveriRichiesti = this.DBMSBoundary.richiesteSpeciali(this.idUtente);
        Iterator<Viveri> iterator = this.viveriRichiesti.iterator();
        while(iterator.hasNext()){
            Viveri tmp = iterator.next();
            System.out.println(tmp.nome + " : " + tmp.QTA);
        }
        this.schermataRichieste.mostra(this.viveriRichiesti, this);
        System.out.println("Gestione Account --> Richieste speciali");
        Iterator<Viveri> iterator1 = this.viveriRichiesti.iterator();
        while(iterator1.hasNext()){
            Viveri tmp = iterator1.next();
            System.out.println(tmp.nome + ",,,"+tmp.QTA);
        }
        this.gestioneRichiesteBND.setVisible(false);
    }
    
    //Quando l'azienda clicca OK nelle richieste speciali la query rimuove dalle richieste speciali la richiesta
    public void confermaRichiesteSpeciali(){
        this.DBMSBoundary.confermaRichiesteSpeciali(this.idUtente);
        //Da qui si deve comunicare al sistema che l'azienda donerà quanto richiesto
    }
    
    //Caso d'uso scarica schema di donazione
    public void richiediSchema(){
        int idUtente = this.schermataDiocesi.utente.getIdUtente();
        List<SchemaDistribuzioneDiocesi> schemaDistribuzione = this.DBMSBoundary.richiediSchema(idUtente);
        this.schermataSchemaDiocesi = new SchermataSchemaBND();
        this.schermataSchemaDiocesi.mostra(schemaDistribuzione,this);
        this.schermataDiocesi.setVisible(false);
    }
    //Stesso di sopra, ma utilizzato per il polo
    public void richiediSchema(boolean operation){
        int idUtente = this.schermataRappresentante.utente.getIdUtente();
        List<SchemaDistribuzionePoli> schemaDistribuzione = this.DBMSBoundary.richiediSchema(idUtente, true);
        this.schermataSchemaPolo = new SchermataSchemaBNDPolo();
        this.schermataSchemaPolo.mostra(schemaDistribuzione, this);
        this.schermataRappresentante.setVisible(false);
    }
    
    public void richiediReportDate(int id, int date){
        this.generaPDF(this.DBMSBoundary.richiediReportDate(id, date));
        this.schermataRappresentante.mostra();
    }
    
    public void generaPDF(List<Report> report){
        String nomeFile = "/Users/manfredi/desktop/output.txt";

        try {
            FileWriter fileWriter = new FileWriter(nomeFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Report r : report) {
                bufferedWriter.write(String.valueOf(r.IDReport)+"-"+r.viveriAggiunti +"-"+r.dataAggiunta.toString());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
