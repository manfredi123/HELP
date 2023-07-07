/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestioneSistemaPackage;
import SchermataPrincipalePackage.SchermataPrincipaleAmministratore;
import DBMSPackage.DBMSBoundaryClass;
import GestioneMagazzinoPackage.Errore;
import java.util.List;
/**
 *
 * @author manfredi
 */
public class GestioneSistemaControl {
    
    SchermataPrincipaleAmministratore schermataAmministratore;
    VisualizzaReportBND visualizzaReport;
    VisualizzaReportSceltoBND visualizzaScelta;
    VisualizzaErroriBND visualizzaErrori;
    SelezionaTipologiaBND selezionaTipologia;
    SchermataProfiliBND schermataProfili = new SchermataProfiliBND();
    InserimentoPasswordBND inserimentoPassword;
    PopUp popUp = new PopUp();
    int idUtente;
    
    DBMSBoundaryClass DBMSBoundary = new DBMSBoundaryClass();
    public List<Report> reportRichiesti;
    
    public GestioneSistemaControl(SchermataPrincipaleAmministratore schermataAmministratore){
        this.schermataAmministratore = schermataAmministratore;
    }
    
    public void createVisualizzaReport(){
        this.visualizzaReport = new VisualizzaReportBND();
        this.schermataAmministratore.setVisible(false);
        List<Report> report = this.DBMSBoundary.richiediReport();
        this.visualizzaReport.mostra(this, report);
    }
    
    public void createVisualizzaErrori(){
        this.visualizzaErrori = new VisualizzaErroriBND();
        this.schermataAmministratore.setVisible(false);
        this.visualizzaErrori.mostra(schermataAmministratore, this);
    }
    
    public void createSelezionaTipologia(){
        this.selezionaTipologia = new SelezionaTipologiaBND();
        this.selezionaTipologia.mostra(this);
        this.schermataAmministratore.setVisible(false);
    }
    
    public void createInserimentoPasswordBND(){
        this.inserimentoPassword = new InserimentoPasswordBND();
        this.inserimentoPassword.mostra(this);
    }
    
    public void richiediErrori(int idDiocesi){
        List<Errore> errori = this.DBMSBoundary.richiediErrori(idDiocesi);
        for(int i=0; i<errori.size(); i++){
            System.out.println(errori.get(i).errore + " : " + errori.get(i).nomePoloCoinvolto);
        }
        this.visualizzaErrori.mostraErrori(errori);
    }
    
    public void richiediReport(int numReport){
        this.reportRichiesti = this.DBMSBoundary.richiediReport(numReport);
        this.visualizzaScelta = new VisualizzaReportSceltoBND();
        this.visualizzaScelta.mostra(reportRichiesti, this);
    }
    
}
