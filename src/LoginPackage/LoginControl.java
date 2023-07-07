/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LoginPackage;

import SchermataPrincipalePackage.*;
import DBMSPackage.DBMSBoundaryClass;
import ConsegnaViveriPackage.Viveri;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.LocalDate;
import IscrizionePackage.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.List;
import java.util.Iterator;

/**
 *
 * @author manfredi
 */
public class LoginControl{
    
    public SchermataLogin schermata;
    public PopUp popup = new PopUp();
    private DBMSBoundaryClass DBMSBoundary;
    private CampoTipologia campoTipologia;
    private CampoDatiRappresentantePolo iscrizioneRappresentante;
    private CampoDatiDiocesi iscrizioneDiocesi;
    private CampoDatiAzienda iscrizioneAzienda;
    private PopUpConferma popupConf;
    private Dato dati;
    private PopUpErrore popupErr;
    public boolean bottoneConferma;
    public SchermataPrincipaleAzienda schermataPrincipaleAzienda;
    public SchermataPrincipaleDiocesi schermataPrincipaleDiocesi;
    public SchermataPrincipaleRappresentante schermataPrincipaleRappresentante;
    public SchermataPrincipaleAmministratore schermataPrincipaleAmministratore;
    public EntityUtente utente;
    
    
    //Caso d'uso Login
    public LoginControl(SchermataLogin s){
        this.schermata = s;
        //this.queryLogin();
        this.DBMSBoundary = this.createDBMSBoundary();
        this.bottoneConferma = false;
        this.popup = new PopUp();
        this.popupErr = new PopUpErrore();
        this.popupConf = new PopUpConferma();
        this.dati = new Dato();
        this.schermataPrincipaleRappresentante = new SchermataPrincipaleRappresentante();
        this.schermataPrincipaleAzienda = new SchermataPrincipaleAzienda();
        this.schermataPrincipaleDiocesi = new SchermataPrincipaleDiocesi();


    }
    
    //Query esistenza utente 
    public void verificaEsistenza(){
        Dato dato = this.schermata.getDato();
        String risposta = DBMSBoundary.verificaEsistenza(dato);
        this.utente = new EntityUtente(dato.IDUtente);
        if(risposta.equals("Azienda")){
            this.schermata.setVisible(false);
            this.createSchermataPrincipale(risposta, this.utente);
        }
        else if(risposta.equals("Diocesi")){
            this.schermata.setVisible(false);
            this.createSchermataPrincipale(risposta, this.utente);
        }
        else if(risposta.equals("Rappresentante")){
            this.schermata.setVisible(false);
            this.createSchermataPrincipale(risposta, this.utente);
        }
        else if(risposta.equals("Amministratore")){
            this.schermata.setVisible(false);
            this.createSchermataPrincipale("Amministratore", this.utente);
        }
        else if(risposta.equals("ERROR")){
            this.createPopUp();
        }   
    }
    
    public boolean verificaRisposta(boolean risposta){
        return risposta;
    }
    
    public void createSchermataPrincipale(String tipo, EntityUtente utente){
        if(tipo.equals("Rappresentante")){
            this.schermataPrincipaleRappresentante = new SchermataPrincipaleRappresentante();
            if(this.sospensionePolo(utente) == 2){
                if(this.DBMSBoundary.schemaGenerato() == 0){
                    this.modificaEta();
                    this.previsione();
                    this.calcoloDistribuzione();
                    this.calcoloDistribuzionePolo();
                    this.DBMSBoundary.setGenerato();
                }
                this.schermataPrincipaleRappresentante.mostra(utente);
            }
        }
        else if(tipo.equals("Azienda")){
            this.schermataPrincipaleAzienda = new SchermataPrincipaleAzienda();        
            this.modificaEta();
            this.previsione();           
            this.schermataPrincipaleAzienda.mostra(utente);
        }
        else if(tipo.equals("Diocesi")){
            this.schermataPrincipaleDiocesi = new SchermataPrincipaleDiocesi();
            if(this.DBMSBoundary.schemaGenerato() == 0){
                    this.modificaEta();
                    this.previsione();
                    this.calcoloDistribuzione();
                    this.calcoloDistribuzionePolo();
                    this.DBMSBoundary.setGenerato();
                }
            this.schermataPrincipaleDiocesi.mostra(utente);
        }
        else if(tipo.equals("Amministratore")){
            this.schermataPrincipaleAmministratore = new SchermataPrincipaleAmministratore();
            this.schermataPrincipaleAmministratore.mostra(utente);
        }
    }
    
    
    public SchermataLogin getSchermata(){
        return this.schermata;
    }
    
    private DBMSBoundaryClass createDBMSBoundary(){
        return new DBMSBoundaryClass();
    }
    
    public void createPopUp(){
        //this.popup = new PopUp();
        this.schermata.setVisible(false);
        popup.mostra(this);
    }
    
    //Caso d'uso Iscrizione
    
    public void createCampoDati(){
        String tipologia = this.campoTipologia.getTipologia();
        if(tipologia.equals("Rappresentante del Polo")){
            this.iscrizioneRappresentante = new CampoDatiRappresentantePolo();
            this.iscrizioneRappresentante.mostra(this);
            this.campoTipologia.setVisible(false);
        }
        else if(tipologia.equals("Diocesi")){
            this.iscrizioneDiocesi = new CampoDatiDiocesi();
            this.iscrizioneDiocesi.mostra(this);
            this.campoTipologia.setVisible(false);
        }
        else if(tipologia.equals("Azienda")){
            this.iscrizioneAzienda = new CampoDatiAzienda();
            this.iscrizioneAzienda.mostra(this);
            this.campoTipologia.setVisible(false);
        }
    }
    
    public void createCampiIscrizione(){
        this.campoTipologia = new CampoTipologia();
        campoTipologia.mostra(this);
    }
    
    public void inserisciRappresentante(Dato dati){
        //query inserimento dati rappresentanti passati al rigo 115
        this.DBMSBoundary.inserisciRappresentante(this.dati.nomeRappresentante, this.dati.comune, this.dati.passwordRappresentante,Integer.valueOf(this.dati.idDiocesiAppartenenza));
    }
    
    public void inserisciDiocesi(Dato dati){
        //Query di inserimento Diocesi
        this.DBMSBoundary.inserisciDiocesi(this.dati.nomeDiocesi, this.dati.passwordDiocesi1);
        
    }
    
    public void inserisciAzienda(Dato dati){
        //Query di inserimento Azienda
        this.DBMSBoundary.inserisciAzienda(this.dati.nomeAzienda,this.dati.passwordAzienda1, this.dati.alimentoProdotto);
    }
    
    public void controllaRappresentante(){
        //Controlla password lunga 6 caratteri
        this.dati = this.iscrizioneRappresentante.getDati();
        if(this.dati.passwordRappresentante.equals(this.dati.passwordRappresentante2)){
            if(this.DBMSBoundary.controllaEsistenzaRappresentanteSistema(this.dati.nomeRappresentante)){
                this.inserisciRappresentante(this.dati);
                String idGenerato = String.valueOf(this.DBMSBoundary.id);
                this.popupConf = new PopUpConferma();
                this.popupConf.mostra(this, this.iscrizioneRappresentante, idGenerato);
                
            }
            else{
                this.popupErr.mostraErrore(this.iscrizioneRappresentante, this);
            }
        }
        else{
            this.popupErr.mostraErrore(this.iscrizioneRappresentante, this);
        }
    }
    
    public void controllaDiocesi(){
        //Controlla password lunga 6 caratteri
        this.dati = this.iscrizioneDiocesi.getDati();
        if(this.dati.passwordDiocesi1.equals(this.dati.passwordDiocesi2)){ 
            if(this.DBMSBoundary.controllaEsistenzaDiocesiSistema(this.dati.nomeDiocesi)){ //Metodo che effettua query per vedere se nome diocesi già esiste
                this.inserisciDiocesi(this.dati);
                String idGenerato = String.valueOf(this.DBMSBoundary.id);            
                this.popupConf = new PopUpConferma();
                this.popupConf.mostra(this, this.iscrizioneDiocesi,idGenerato);
            }
            else{
                this.popupErr.mostraErrore(this.iscrizioneDiocesi, this);
            }
        }
        else{
            this.popupErr.mostraErrore(this.iscrizioneDiocesi, this);
        }
    }
    
    public void controllaAzienda(){
        //Controlla password lunga 6 caratteri
        this.dati = this.iscrizioneAzienda.getDati();
        if(this.dati.passwordAzienda1.equals(this.dati.passwordAzienda2)){
            if(this.DBMSBoundary.controllaEsistenzaAziendaSistema(this.dati.nomeAzienda)){
                this.inserisciAzienda(this.dati);
                String idGenerato = String.valueOf(this.DBMSBoundary.id); 
                this.popupConf = new PopUpConferma();
                this.popupConf.mostra(this, this.iscrizioneAzienda,idGenerato);
            }
            else{
                this.popupErr.mostraErrore(this.iscrizioneAzienda, this);
            }
        }
        else{
            this.popupErr.mostraErrore(this.iscrizioneAzienda, this);
        }
    }
    
    private Date getDataDiNascitaDaCodiceFiscale(String codiceFiscale) {
        if (codiceFiscale != null && codiceFiscale.length() >= 6) {
            String annoString = codiceFiscale.substring(6, 8);
            int anno = Integer.parseInt("19" + annoString); // O considera il secolo corretto per i codici fiscali recenti
            String meseString = codiceFiscale.substring(8, 9);
            int mese = convertiMeseDaCodiceFiscale(meseString);
            String giornoString = codiceFiscale.substring(9, 11);
            int giorno = Integer.parseInt(giornoString);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dataString = String.format("%02d/%02d/%04d", giorno, mese, anno);

            try {
                return dateFormat.parse(dataString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private int convertiMeseDaCodiceFiscale(String meseString) {
        switch (meseString) {
            case "A": return 1;
            case "B": return 2;
            case "C": return 3;
            case "D": return 4;
            case "E": return 5;
            case "H": return 6;
            case "L": return 7;
            case "M": return 8;
            case "P": return 9;
            case "R": return 10;
            case "S": return 11;
            case "T": return 12;
            default: return -1;
        }
    }
    
    
    //Automatismi
    public void modificaEta(){
        LocalTime oraAttuale = LocalTime.now();
        if(oraAttuale.equals(LocalTime.MIDNIGHT)){
            List<String> CF = this.DBMSBoundary.getCF();
            Iterator<String> iterator = CF.iterator();
            while(iterator.hasNext()){
                String tmp = iterator.next();
                Date dataNascita = this.getDataDiNascitaDaCodiceFiscale(tmp);
                if(dataNascita != null){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String dataNascitaString = dateFormat.format(dataNascita);
                    String giornoCorrente = String.valueOf(LocalDate.now().getDayOfMonth());
                    String meseCorrente = String.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("MM")));
                    String giornoNascita = dataNascitaString.substring(0, 2);
                    String meseNascita = dataNascitaString.substring(3, 5);
                    
                    if(giornoCorrente.equals(giornoNascita) && meseCorrente.equals(meseNascita)){
                        this.DBMSBoundary.incrementaEta(tmp);
                    }
                    
                }
            }
        }
    } 
    
    private int sospensionePolo(EntityUtente utente){
        int id = utente.getIdUtente();
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String oggi = now.format(formatter);
        String dataScarico = this.DBMSBoundary.getScarico(id);
        if(!(dataScarico.equals(""))){
            if((Integer.valueOf(oggi.substring(3,5))-(Integer.valueOf(dataScarico.substring(3, 5)))) >= 2){
                this.DBMSBoundary.rimuoviScarico(id);
                this.DBMSBoundary.rimuovi(id);
                this.schermata.mostra();
                return 1;
            }
        }
        return 2;
    }
    
    private void previsione(){

        List<Viveri> viveriTot = new ArrayList<>();
        int nComponenti;
        int nPoliTot;
        float alimentoUnitario;
        List<Azienda> aziende = this.DBMSBoundary.getAziende();
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String oggi = now.format(formatter);
        if(oggi.substring(0, 2).equals("1")){
            viveriTot = this.DBMSBoundary.getViveri();
            nComponenti = this.DBMSBoundary.getComponenti();
            nPoliTot = this.DBMSBoundary.getPoli();
            Iterator<Viveri> iteratorV = viveriTot.iterator();
            while(iteratorV.hasNext()){
                Viveri tmp = iteratorV.next();
                alimentoUnitario = tmp.QTA/nPoliTot;
                System.out.println("QTA -->" + tmp.QTA +" : "+ nPoliTot + ": alimento = "+alimentoUnitario);
                if(alimentoUnitario < 3){
                    Iterator<Azienda> iteratorA = aziende.iterator();
                    while(iteratorA.hasNext()){
                        Azienda tmp1 = iteratorA.next();
                        if(tmp1.alimentoProdotto.equals(tmp.nome)){
                            System.out.println("Entrato");
                            this.DBMSBoundary.inviaRichiestaSpeciale(tmp1.idAzienda,tmp.nome,1);
                        }
                    }
                }
            }
        }
    }
    
    private void calcoloDistribuzione(){
        List<Viveri> viveriTot = new ArrayList<>();
        int nComponenti;
        int nPoliTot;
        int nDiocesiTot;
        float alimentoUnitario;
        List<Diocesi> poliXdiocesi = new ArrayList<>();
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String oggi = now.format(formatter);
        LocalTime oraAttuale = LocalTime.now();
        if(oggi.substring(0, 2).equals("2") && oraAttuale.equals(LocalTime.MIDNIGHT)){
            viveriTot = this.DBMSBoundary.getViveri1();
            nComponenti = this.DBMSBoundary.getComponenti();
            nPoliTot = this.DBMSBoundary.getPoli();
            nDiocesiTot = this.DBMSBoundary.getDiocesi();
            Iterator<Viveri> iteratorV = viveriTot.iterator();
            while(iteratorV.hasNext()){
                Viveri tmp = iteratorV.next();
                alimentoUnitario = tmp.QTA/nDiocesiTot;
                poliXdiocesi = this.DBMSBoundary.getPoliXdiocesi();
                Iterator<Diocesi> iteratorD = poliXdiocesi.iterator();
                while(iteratorD.hasNext()){
                    Diocesi tmp1 = iteratorD.next();
                    Iterator<Integer> poliDiocesi = tmp1.idPoliAppartenenti.iterator();
                    while(poliDiocesi.hasNext()){
                        int tmp2 = poliDiocesi.next();
                        if(alimentoUnitario != 0){
                            this.DBMSBoundary.creaSchema(tmp1.IDDiocesi,tmp2,tmp.nome,alimentoUnitario);
                            this.DBMSBoundary.riempiMagazzino(tmp1.IDDiocesi,tmp.nome,(int) alimentoUnitario);
                        }
                    } 
                }
            }
            int alimentoDonato = 0;
            Iterator<Diocesi> iteratorDiocesi = poliXdiocesi.iterator();
            while(iteratorDiocesi.hasNext()){
                Diocesi tmp = iteratorDiocesi.next();
                List<Viveri> viveriDiocesi = this.DBMSBoundary.getViveriDiocesi(tmp.IDDiocesi);
                Iterator<Viveri> iteratoriViveri = viveriDiocesi.iterator();
                while(iteratoriViveri.hasNext()){
                    Viveri tmp3 = iteratoriViveri.next();
                    alimentoDonato = tmp3.QTA /tmp.nPoliAppartenenti;
                    Iterator<Integer> tmpP = tmp.idPoliAppartenenti.iterator();
                    while(tmpP.hasNext()){
                        int tmp1 = tmpP.next();
                        if(alimentoDonato != 0){
                            this.DBMSBoundary.riempiMagazzinoP(tmp1, tmp3.nome, alimentoDonato);
                            this.DBMSBoundary.generaReport(tmp1,tmp3.nome);
                        }
                    }
   
                }
            }
        }
    }
    
    private void calcoloDistribuzionePolo(){
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String oggi = now.format(formatter);
        LocalTime oraAttuale = LocalTime.now();
        if(oggi.substring(0, 2).equals("2") && oraAttuale.equals(LocalTime.MIDNIGHT)){
            List<Integer> poliTot = this.DBMSBoundary.getnPoli();
            int nFamiglie = this.DBMSBoundary.getNFamiglie();
            Iterator<Integer> iterator = poliTot.iterator();
            while(iterator.hasNext()){
                Integer tmp = iterator.next();
                List<Viveri> viveriPolo = this.DBMSBoundary.getViveriPolo(tmp);
                Iterator<Viveri> iteratorV = viveriPolo.iterator();
                while(iteratorV.hasNext()){
                    Viveri tmp1 = iteratorV.next();
                    int alimentoUnitario = tmp1.QTA/nFamiglie;
                    List<Famiglie> referentiFamiglie = this.DBMSBoundary.getFamiglie(tmp);
                    Iterator<Famiglie> iteratorF = referentiFamiglie.iterator();
                    while(iteratorF.hasNext()){
                        Famiglie tmp2 = iteratorF.next();
                        this.DBMSBoundary.creaSchemaPolo(tmp,tmp1.nome,tmp1.QTA,tmp2.CFReferente);
                    }
                }
            }
        }
    }

}
