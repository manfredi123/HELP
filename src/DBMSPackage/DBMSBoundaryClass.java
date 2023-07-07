/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package DBMSPackage;
import LoginPackage.Dato;
import java.sql.*;
import ConsegnaViveriPackage.Viveri;
import GestioneMagazzinoPackage.Errore;
import GestioneAccount.SchemaDistribuzioneDiocesi;
import GestioneAccount.SchemaDistribuzionePoli;
import GestioneMagazzinoPackage.Donazioni;
import GestioneSistemaPackage.Report;
import GestioneFamiglie.DatiFamiglia;
import GestioneFamiglie.DatiComponente;
import GestioneSistemaPackage.Utente;
import LoginPackage.*;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author manfredi
 */
public class DBMSBoundaryClass{
        public String url = "jdbc:mysql://localhost:3306/help";
        public String username = "root";
        public String password = "";
        public int idGenerato = 0;
        public int id;
        public int idGeneratoFamiglia = 0;
        public List<Viveri> viveriRichiesti = new ArrayList<>();
        public List<Errore> erroriDiocesi = new ArrayList<>();
        public List<SchemaDistribuzioneDiocesi> schemaDiocesi = new ArrayList<>();
        public List<SchemaDistribuzionePoli> schemaPoli = new ArrayList<>();
        public List<Donazioni> donazioni = new ArrayList<>();
        public List<Viveri> datiMagazzino = new ArrayList<>();
        public List<Report> report = new ArrayList<>();
        public List<Utente> utentiRichiesti = new ArrayList<>();
        private Connection connection;
        PopUp1 popUp = new PopUp1();
        
    public DBMSBoundaryClass(){
        
    }
    //Query del Login
    
    public String verificaEsistenza(Dato dato) {
    try {
        int idUtente = dato.IDUtente;
        char[] passwordChars = dato.passwordLogin;
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(this.url, this.username, this.password);

        String query = "SELECT CASE WHEN EXISTS (SELECT * FROM Rappresentante WHERE IDRappresentante = ? AND BINARY Password = ?) THEN 'Rappresentante' WHEN EXISTS (SELECT * FROM Diocesi WHERE IDDiocesi = ? AND BINARY Password = ?) THEN 'Diocesi' WHEN EXISTS (SELECT * FROM Azienda WHERE IDAzienda = ? AND BINARY Password = ?) THEN 'Azienda' WHEN EXISTS (SELECT * FROM Amministratore WHERE IDAdmin = ? AND BINARY Password = ?) THEN 'Amministratore' ELSE 'Nessuna tabella' END AS Tabella";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idUtente);
        statement.setString(2, new String(passwordChars));
        statement.setInt(3, idUtente);
        statement.setString(4, new String(passwordChars));
        statement.setInt(5, idUtente);
        statement.setString(6, new String(passwordChars));
        statement.setInt(7, idUtente);
        statement.setString(8, new String(passwordChars));

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            System.out.println("Tabella: " + resultSet.getString("Tabella"));

            String tabella = resultSet.getString("Tabella");
            if (tabella.equals("Azienda")) {
                System.out.println("Password corretta per Azienda");
                return "Azienda";
            } else if (tabella.equals("Diocesi")) {
                System.out.println("Password corretta per Diocesi");
                return "Diocesi";
            } else if (tabella.equals("Rappresentante")) {
                System.out.println("Password corretta per Rappresentante");
                return "Rappresentante";
            } else if (tabella.equals("Amministratore")) {
                System.out.println("Password corretta per Amministratore");
                return "Amministratore";
            }
        }
        
        connection.close();
    } catch (Exception e) {
        this.popUp.mostra(this);
    }
    return "ERROR";
}


    
    //Query dell'iscrizione - Controlli sui dati immessi in fase di iscrizione
    public boolean controllaEsistenzaDiocesiSistema(String nomeDiocesi){//Controlla se la diocesi che vuole iscriversi è già presente
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select Nome from Diocesi where Diocesi.Nome ='" + nomeDiocesi + "'");
            while(!resultSet.next()){
                return true; 
            }
            connection.close();
        }
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return false;
    }
    
    public boolean controllaEsistenzaAziendaSistema(String nomeAzienda){//Controlla se l'azienda che vuole iscriversi è già presente
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select Nome from Azienda where Azienda.Nome ='" + nomeAzienda + "'");
            
            while(!resultSet.next()){
                return true;
            }
            connection.close();
        }
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return false;
    }
    
    public boolean controllaEsistenzaRappresentanteSistema(String nomeRappresentante){//Controlla se l'azienda che vuole iscriversi è già presente
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select Nome from Rappresentante where (Rappresentante.Nome ='" + nomeRappresentante + "')");
            
            while(!resultSet.next()){
                return true;
            }
            connection.close();
        }
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return false;
    }
    
     //Query dell'iscrizione - Inserimento dati immessi in fase di iscrizione 
 
    private int generateID() {
    int id = 0;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT MAX(id) AS max_id FROM ( SELECT IDRappresentante AS id FROM `Rappresentante` UNION ALL SELECT IDAzienda AS id FROM `Azienda` UNION ALL SELECT IDDiocesi AS id FROM `Diocesi`) AS combined_tables");
        
        if (resultSet.next()) {
            id = resultSet.getInt("max_id") + 1; // incremento di 1 il massimo ID
            this.idGenerato = id;
        }
        
        connection.close();
    } catch (Exception e) {
        this.popUp.mostra(this);
    }
    return id;
}

    public void inserisciRappresentante(String nome, String comune, String password, int idDiocesiAppartenenza) {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
        Statement statement = connection.createStatement();
        this.id = this.generateID();
        statement.executeUpdate("INSERT INTO Rappresentante (IDRappresentante, Comune, Password, IDDiocesiAppartenenza, Nome) VALUES ('" + this.id + "','" + comune + "','" + password + "','" + idDiocesiAppartenenza + "','" + nome + "')");
        ResultSet resultSet =  statement.executeQuery("SELECT MAX(IDMagazzino) FROM Magazzino");
        int idMagazzino = -1;
        while(resultSet.next()){
            idMagazzino = resultSet.getInt(1);
        }
        statement.executeUpdate("INSERT INTO MagazziniPolo (IDPolo, IDMagazzino) VALUES("+this.id+","+idMagazzino+")");
        connection.close();
    } catch (Exception e) {
        this.popUp.mostra(this);
    }
}
    
    public void inserisciAzienda(String nome,String password, String alimentoProdotto){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            this.id = this.generateID();
            statement.executeUpdate("INSERT INTO `Azienda` (IDAzienda, Nome, Password, AlimentoProdotto) VALUES ('" + this.id + "','" + nome + "','" + password + "','"+alimentoProdotto+"')");
            List<Integer> magazzini = this.getMagazzini();
            Iterator<Integer> iterator = magazzini.iterator();
            while(iterator.hasNext()){
                int tmp = iterator.next();
                statement.executeUpdate("INSERT INTO Magazzino(IDMagazzino,Viveri,QTAViveri) VALUES ('"+tmp+"','"+alimentoProdotto+"',0)");
            }
            connection.close();
        }
        catch(Exception e){
            this.popUp.mostra(this);
        }
    }
    
    public List<Integer> getMagazzini(){
        List<Integer> magazzini = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT IDMagazzino FROM Magazzino");
            while(resultSet.next()){
                magazzini.add(resultSet.getInt(1));
            }
            connection.close();
        }
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return magazzini;
    }
    
    public void inserisciDiocesi(String nome, String password){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            this.id = this.generateID();
            statement.executeUpdate("INSERT INTO `Diocesi` (`IDDiocesi`, `Password`, `Nome`) VALUES ('" + this.id + "','" + password + "','" + nome + "')");
            ResultSet resultSet =  statement.executeQuery("SELECT MAX(mp.IDMagazzino) FROM MagazziniDiocesi m JOIN MagazziniPolo mp");
            int idMagazzino = -1;
            while(resultSet.next()){
                idMagazzino = resultSet.getInt(1) + 1;
            }
            statement.executeUpdate("INSERT INTO MagazziniDiocesi (IDDiocesi, IDMagazzino) VALUES("+this.id+","+idMagazzino+")");
            connection.close();
        }                      
        catch(Exception e){
            System.out.println(e);
            this.popUp.mostra(this);
        }
    }

    //Caso d'uso Consegna Viveri
    public void aggiungi(List<Viveri> v, int IDutente){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            Iterator<Viveri> iterator = v.iterator();
            while(iterator.hasNext()){
                Viveri tmp = iterator.next();
                statement.executeUpdate("INSERT INTO `DonazioniAzienda` (`IDAzienda`, `Viveri`, `QTAViveri`) VALUES ('"+IDutente + "','" + tmp.nome +"'," +tmp.QTA+")");
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
    }
    
    //Caso d'uso Gestione Account
    public List<Viveri> richiesteSpeciali(int idUtente){ //CONTROLLA query
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ViveriRichiesti, QTAViveri FROM RichiesteSpeciali WHERE (RichiesteSpeciali.IDAzienda = " + idUtente + ")");
            while(resultSet.next()){
                this.viveriRichiesti.add(new Viveri(resultSet.getString(1), resultSet.getInt(2)));
            }
            
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return this.viveriRichiesti;
    } 
    
    public void confermaRichiesteSpeciali(int idUtente){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM RichiesteSpeciali WHERE IDAzienda = " + idUtente);
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
    }

    //Caso d'uso controllo errori (diocesi)
    public List<Errore> richiediErrori(int idUtente){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT e.Errore, r.Nome FROM ErroriDiocesi e JOIN Rappresentante r ON e.IDPoloCoinvolto = r.IDRappresentante WHERE e.IDDiocesi =" + idUtente); 
            
            while(resultSet.next()){
                this.erroriDiocesi.add(new Errore(resultSet.getString(1),resultSet.getString(2)));
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return this.erroriDiocesi;
    }
    
    //Dopo aver visualizzato gli errori questi vengono rimossi
    public void rimuoviErrori(int idUtente){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM ErroriDiocesi WHERE IDDiocesi = " + idUtente);
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
    }
    
    //Caso d'uso scarica schema di donazione - Diocesi
    public List<SchemaDistribuzioneDiocesi> richiediSchema(int idUtente){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM SchemaDistribuzioneDiocesi WHERE IDDiocesi =" + idUtente); 
            System.out.println(resultSet);
            while(resultSet.next()){
                
                this.schemaDiocesi.add(new SchemaDistribuzioneDiocesi(resultSet.getInt(3),resultSet.getInt(1),resultSet.getString(2), resultSet.getInt(4)));
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return this.schemaDiocesi;
    }
    
     //Caso d'uso scarica schema di donazione - Polo
    public List<SchemaDistribuzionePoli> richiediSchema(int idUtente, boolean operation){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM SchemaDistribuzionePolo WHERE IDPolo =" + idUtente); 
            System.out.println(resultSet);
            while(resultSet.next()){
                this.schemaPoli.add(new SchemaDistribuzionePoli(resultSet.getString(1),resultSet.getInt(4),resultSet.getString(2), resultSet.getInt(3)));
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return this.schemaPoli;
    }
    //Caso d'uso verifica carico
    public List<Donazioni> donazioniRicevute(int idUtente){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM DonazioniRicevutePolo WHERE IDPoloDestinatario =" + idUtente); 
            while(resultSet.next()){
                this.donazioni.add(new Donazioni(resultSet.getString(2),resultSet.getInt(3)));
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return this.donazioni;
    }
    
    //Caso d'uso scarico magazzino - Diocesi
    public List<Viveri> datiMagazzino(int idUtente){
        this.datiMagazzino.clear();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT m.Viveri, m.QTAViveri FROM Magazzino m JOIN MagazziniDiocesi md ON m.IDMagazzino = md.IDMagazzino WHERE md.IDDiocesi =" + idUtente); 
            while(resultSet.next()){
                this.datiMagazzino.add(new Viveri(resultSet.getString(1), resultSet.getInt(2)));
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return this.datiMagazzino;
    }
    //come sopra, ma per il polo
    public List<Viveri> datiMagazzino(int idUtente, boolean isPolo){
        this.datiMagazzino.clear();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            System.out.println();
            ResultSet resultSet = statement.executeQuery("SELECT m.Viveri, m.QTAViveri FROM Magazzino m JOIN MagazziniPolo md ON m.IDMagazzino = md.IDMagazzino WHERE md.IDPolo =" + idUtente); 
            while(resultSet.next()){
                this.datiMagazzino.add(new Viveri(resultSet.getString(1), resultSet.getInt(2)));
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return this.datiMagazzino;
    }
    
    
    //Rimuovi per la diocesi
    public void rimuovi(List<Viveri> daRimuovere, int idUtente){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
            Statement statement = connection.createStatement();
            int idMagazzino = 0;

            for (Viveri viveri : daRimuovere) {
                ResultSet resultSet = statement.executeQuery("SELECT IDMagazzino FROM MagazziniDiocesi WHERE IDDiocesi = " + idUtente);
                if (resultSet.next()) {
                    idMagazzino = resultSet.getInt(1);
                }

            
                String selectQuery = "SELECT SUM(QTAViveri) FROM Magazzino WHERE IDMagazzino = " + idMagazzino + " AND Viveri = '" + viveri.nome + "'";
                resultSet = statement.executeQuery(selectQuery);

                int quantitaPresente = 0;
                if (resultSet.next()) {
                    quantitaPresente = resultSet.getInt(1);
                }

            
                int quantitaDaRimuovere = Math.min(viveri.QTA, quantitaPresente);

            
                String updateQuery = "UPDATE Magazzino SET QTAViveri = QTAViveri - " + quantitaDaRimuovere + " WHERE IDMagazzino = " + idMagazzino + " AND Viveri = '" + viveri.nome + "'";
                statement.executeUpdate(updateQuery);

            
                if (quantitaPresente - quantitaDaRimuovere == 0) {
                    String deleteQuery = "DELETE FROM Magazzino WHERE IDMagazzino = " + idMagazzino + " AND Viveri = '" + viveri.nome + "'";
                    statement.executeUpdate(deleteQuery);
                }
            }

            connection.close();
        }   
        catch (Exception e) {
            this.popUp.mostra(this);
        }
    }
    //Rimuovi per il polo
    public void rimuovi(List<Viveri> daRimuovere, int idUtente, boolean isPolo){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
            Statement statement = connection.createStatement();
            int idMagazzino = 0;

            for (Viveri viveri : daRimuovere) {
                ResultSet resultSet = statement.executeQuery("SELECT IDMagazzino FROM MagazziniPolo WHERE IDPolo = " + idUtente);
                if (resultSet.next()) {
                    idMagazzino = resultSet.getInt(1);
                }

            
                String selectQuery = "SELECT SUM(QTAViveri) FROM Magazzino WHERE IDMagazzino = " + idMagazzino + " AND Viveri = '" + viveri.nome + "'";
                resultSet = statement.executeQuery(selectQuery);

                int quantitaPresente = 0;
                if (resultSet.next()) {
                    quantitaPresente = resultSet.getInt(1);
                }

            
                int quantitaDaRimuovere = Math.min(viveri.QTA, quantitaPresente);

            
                String updateQuery = "UPDATE Magazzino SET QTAViveri = QTAViveri - " + quantitaDaRimuovere + " WHERE IDMagazzino = " + idMagazzino + " AND Viveri = '" + viveri.nome + "'";
                statement.executeUpdate(updateQuery);

            
                if (quantitaPresente - quantitaDaRimuovere == 0) {
                    String deleteQuery = "DELETE FROM Magazzino WHERE IDMagazzino = " + idMagazzino + " AND Viveri = '" + viveri.nome + "'";
                    statement.executeUpdate(deleteQuery);
                }
            }

            connection.close();
        }   
        catch (Exception e) {
            this.popUp.mostra(this);
        }
    }
    
    //Caso d'uso gestione sistema - visualizzaReport
    public List<Report> richiediReport(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT r.IDReport, rp.Comune FROM Report r JOIN Rappresentante rp on r.IDPolo = rp.IDRappresentante"); 
            while(resultSet.next()){
                this.report.add(new Report(resultSet.getInt(1),null,"" ,resultSet.getString(2)));
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return this.report;
    }
    //Una volta scelto il report la query prende il report selezionato
    public List<Report> richiediReport(int idReport){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Report WHERE IDReport = " + idReport); 
            while(resultSet.next()){
                this.report.add(new Report(resultSet.getInt(1),resultSet.getDate(4),resultSet.getString(3),null));
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return this.report;
    }
    
    //Caso d'uso Gestione famiglie - iscrizione famiglia
    public boolean controllaCF(String CF){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ComponentiFamiglie WHERE CF = '" + CF+"'"); 
            while(!resultSet.next()){
                return true;
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return false;
    }
    
    //Query che prende l'id più alto tra quelli delle famiglie
    public int generaID(){
        int id = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MAX(IDFamiglia) AS max_id FROM `Famiglie`");
        
            if (resultSet.next()) {
                id = resultSet.getInt("max_id") + 1; // incremento di 1 il massimo ID
                this.idGeneratoFamiglia = id;
            }
        
            connection.close();
        } catch (Exception e) {
            this.popUp.mostra(this);
        }
        return this.idGeneratoFamiglia;
    }
    
    //Query che inserisce la famiglia
    public void inserisciFamiglia(DatiFamiglia famiglia){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO `Famiglie` (`IDFamiglia`, `IDPoloAppartenenza`, `Recapito`, `CFReferente`) VALUES ('"+famiglia.IDFamiglia + "','" + famiglia.IDPoloAppartenenza +"'," +famiglia.recapito+",'"+famiglia.CFReferente+"')");
         
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            Iterator<DatiComponente> iterator = famiglia.componenti.iterator();
            
            while(iterator.hasNext()){
                DatiComponente tmp = iterator.next();
                statement.executeUpdate("INSERT INTO `ComponentiFamiglie` (`IDFamigliaAppartenenza`, `Nome`, `Cognome`,`Eta`,`CF`,`BisogniSpeciali`) VALUES ('"+famiglia.IDFamiglia + "','" + tmp.nome +"','" +tmp.cognome+"'," + tmp.eta + ",'"+ tmp.CF + "','"+ tmp.bisogni + "')");
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        
    
    }
   
    //Caso d'uso gestione sistema - elimina account
    public List<Utente> richiediRappresentanti(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement(); 
            ResultSet resultSet = statement.executeQuery("SELECT IDRappresentante, Nome FROM Rappresentante");
            while(resultSet.next()){
                this.utentiRichiesti.add(new Utente(resultSet.getInt(1), resultSet.getString(2)));
            }
            
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return this.utentiRichiesti;
    }
    
    
    public List<Utente> richiediDiocesi(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement(); 
            ResultSet resultSet = statement.executeQuery("SELECT IDDiocesi, Nome FROM Diocesi");
            while(resultSet.next()){
                this.utentiRichiesti.add(new Utente(resultSet.getInt(1), resultSet.getString(2)));
            }
            
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return this.utentiRichiesti;
    }
    
    public List<Utente> richiediAziende(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement(); 
            ResultSet resultSet = statement.executeQuery("SELECT IDAzienda, Nome FROM Azienda");
            while(resultSet.next()){
                this.utentiRichiesti.add(new Utente(resultSet.getInt(1), resultSet.getString(2)));
            }
            
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return this.utentiRichiesti;
    }
    
    
    public void rimuovi(int id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
            Statement statement = connection.createStatement();
        
        
        
            String deleteAziendaQuery = "DELETE FROM Azienda WHERE IDAzienda = ?";
            String deleteDiocesiQuery = "DELETE FROM Diocesi WHERE IDDiocesi = ?";
            String deleteRappresentanteQuery = "DELETE FROM Rappresentante WHERE IDRappresentante = ?";
        
            PreparedStatement preparedStatementAzienda = connection.prepareStatement(deleteAziendaQuery);
            PreparedStatement preparedStatementDiocesi = connection.prepareStatement(deleteDiocesiQuery);
            PreparedStatement preparedStatementRappresentante = connection.prepareStatement(deleteRappresentanteQuery);
        
            preparedStatementAzienda.setInt(1, id);
            preparedStatementDiocesi.setInt(1, id);
            preparedStatementRappresentante.setInt(1, id);
        
            int rowsAffectedAzienda = preparedStatementAzienda.executeUpdate();
            int rowsAffectedDiocesi = preparedStatementDiocesi.executeUpdate();
            int rowsAffectedRappresentante = preparedStatementRappresentante.executeUpdate();
        
            int totalRowsAffected = rowsAffectedAzienda + rowsAffectedDiocesi + rowsAffectedRappresentante;
        
            
            connection.close();
        } catch (Exception e) {
            this.popUp.mostra(this);
        }
    }   

    //Caso d'uso aggiungi componente 
    public boolean verificaCF(String CF){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Famiglie WHERE CFReferente = '" + CF+"'"); 
            while(resultSet.next()){
                return true;
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return false;
    }
    
    public boolean verificaCF(String CF, int operation){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ComponentiFamiglie WHERE CF = '" + CF+"'"); 
            while(resultSet.next()){
                return true;
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return false;
    }
    
    public List<DatiComponente> getCompon(String CF){
        List<DatiComponente> famiglia = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            String sql = "SELECT * " +
                         "FROM ComponentiFamiglie " +
                         "WHERE IDFamigliaAppartenenza IN (" +
                         "    SELECT IDFamiglia " +
                         "    FROM Famiglie " +
                         "    WHERE CFReferente = '" +CF+ "'" +
                         ")";
            
            // Creazione dello statement
            Statement stmt = connection.createStatement();
            
            // Esecuzione della query
            ResultSet resultSet = stmt.executeQuery(sql);
            while(!resultSet.next()){
                famiglia.add(new DatiComponente(resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4),resultSet.getString(5),resultSet.getString(6)));
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return famiglia;
    }
    
    public int getIDFamiglia(String CF){
        int id = 0;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            String sql = "SELECT IDFamigliaAppartenenza " +
                         "FROM ComponentiFamiglie " +
                         "WHERE IDFamigliaAppartenenza IN (" +
                         "    SELECT IDFamiglia " +
                         "    FROM Famiglie " +
                         "    WHERE CFReferente = '"+CF+ "'" +
                         ")";
            
            // Creazione dello statement
            Statement stmt = connection.createStatement();
            
            // Esecuzione della query
            ResultSet resultSet = stmt.executeQuery(sql);
            while(resultSet.next()){
                id = resultSet.getInt(1);
            }
                
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return id;
    }
    
    
    public void inserisciComponente(DatiComponente dati, int IDFamiglia){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();

            statement.executeUpdate("INSERT INTO `ComponentiFamiglie` (`IDFamigliaAppartenenza`, `Nome`, `Cognome`,`Eta`,`CF`,`BisogniSpeciali`) VALUES ('"+IDFamiglia + "','" + dati.nome +"','" +dati.cognome+"'," + dati.eta + ",'"+ dati.CF + "','"+ dati.bisogni + "')");
            
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
    }
    
    public void elimina(String CF){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();

            statement.executeUpdate("DELETE FROM ComponentiFamiglie WHERE CF = '"+ CF+"'");
            
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
    }
    
    public String getBisogni(String CF){
        String bisogni = "";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT BisogniSpeciali FROM ComponentiFamiglie WHERE CF = '" + CF+"'"); 
            while(resultSet.next()){
                bisogni = resultSet.getString(1);
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return bisogni;
    }
    
    public void aggiornaBisogni(String bisogni,String CF){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();

            statement.executeUpdate("UPDATE `ComponentiFamiglie` SET BisogniSpeciali ='" + bisogni +"' WHERE CF ='" + CF+"'");
            
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        
      
    }
    
    public boolean verifica(String pass, int idUtente){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Rappresentante WHERE IDRappresentante ='"+idUtente+"' AND Password ='"+pass+"'");
            while(resultSet.next()){
                 return true;
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return false;
    }
    
    public void sospendi(int idUtente){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM Rappresentante WHERE IDRappresentante = '" + idUtente+"'"); 
            
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        
    }
    
    
    //Caso d'uso segnalazione errore
    public int getDiocesiAppartenenza(int idRappresentante){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT IDDiocesiAppartenenza FROM Rappresentante WHERE IDRappresentante ='"+idRappresentante+"'");
            while(resultSet.next()){
                 return resultSet.getInt(1);
            }
            connection.close();
        }                      
        catch(Exception e){
            
            this.popUp.mostra(this);
        }
        return -1;
    }
    
    public void registraErrore(String errore, int idDiocesi, int idPolo){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            
            LocalDate currentDate = LocalDate.now();
            statement.executeUpdate("INSERT INTO `ErroriDiocesi` (`IDDiocesi`, `IDPoloCoinvolto`, `Errore`, `DataEmissione`) VALUES ('"+idDiocesi + "','" + idPolo +"','" +errore+"','"+ currentDate+"')");
            
            connection.close();
        }                      
        catch(Exception e){
            System.out.println(e);
            this.popUp.mostra(this);
        }
    }
    
    public List<Report> richiediReportDate(int id, int date){
        List<Report> report = new ArrayList<>();
        if(date == 3){
            try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Report WHERE DataAggiunta >='"+LocalDate.now().minusMonths(3)+"' AND DataAggiunta <= '" + java.sql.Date.valueOf(LocalDate.now())+"' AND IDPolo = "+ id;
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println(query);
            while(resultSet.next()){
                report.add(new Report(resultSet.getInt(1),resultSet.getDate(4),resultSet.getString(3),""));
            }
            connection.close();
            }                      
            catch(Exception e){
                this.popUp.mostra(this);
            }
        }
        else if(date == 6){
            try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Report WHERE DataAggiunta >='"+LocalDate.now().minusMonths(6)+"' AND DataAggiunta <= '" + java.sql.Date.valueOf(LocalDate.now())+"' AND IDPolo = "+ id);
            while(resultSet.next()){
                report.add(new Report(resultSet.getInt(1),resultSet.getDate(4),resultSet.getString(3),""));
            }
            connection.close();
            }                      
            catch(Exception e){
                this.popUp.mostra(this);
            }
        }
        else if(date == 1){
            try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Report WHERE DataAggiunta >='"+LocalDate.now().minusMonths(12)+"' AND DataAggiunta <= '" + java.sql.Date.valueOf(LocalDate.now())+"' AND IDPolo = "+ id);
            while(resultSet.next()){
                report.add(new Report(resultSet.getInt(1),resultSet.getDate(4),resultSet.getString(3),""));
            }
            connection.close();
            }                      
            catch(Exception e){
                this.popUp.mostra(this);
            }
        }
        
        return report;
    }
    
    //Automatismi
    public List<String> getCF(){
        List<String> cf = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT CF FROM ComponentiFamiglia");
            while(resultSet.next()){
                cf.add(resultSet.getString(1));
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return cf;
    }
    
    public void incrementaEta(String CF) {
       try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE Persone SET Eta = Eta + 1 WHERE CodiceFiscale ='" + CF + "'");
           
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
    }
    
    public void modificaDataScarico(int id, String data){
        System.out.println(data);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM DateScarichi WHERE IDPolo='"+ id+"'");
            
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO DateScarichi(IDPolo, DataScarico) VALUES ('"+id+"','"+ data+"')");
            
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        
    }
    
    public String getScarico(int id){
        String data = "";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT DataScarico FROM DateScarichi WHERE IDPolo ='"+id+"'");
            while(resultSet.next()){
                data = resultSet.getString(1);
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return data;
    }
    
    public void rimuoviScarico(int id){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM DateScarichi WHERE IDPolo ='"+id+"'");
            
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
    }

    public List<Viveri> getViveri(){
        List<Viveri> viveriTot = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Magazzino m JOIN MagazziniPolo mp on m.IDMagazzino = mp.IDMagazzino");
            while(resultSet.next()){
                viveriTot.add(new Viveri(resultSet.getString(2),resultSet.getInt(3)));
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return viveriTot;
    }
    
    public List<Viveri> getViveriDiocesi(int idDiocesi){
        List<Viveri> viveriTot = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Magazzino m JOIN MagazziniDiocesi mp on m.IDMagazzino = mp.IDMagazzino WHERE IDDiocesi ="+idDiocesi);
            while(resultSet.next()){
                viveriTot.add(new Viveri(resultSet.getString(2),resultSet.getInt(3)));
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return viveriTot;
    }
    
    
    public List<Viveri> getViveri1(){
        List<Viveri> viveriTot = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM DonazioniAzienda");
            while(resultSet.next()){
                System.out.println("TESTTEST " + resultSet.getString(2) + ":"+resultSet.getInt(3));
                viveriTot.add(new Viveri(resultSet.getString(2),resultSet.getInt(3)));
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return viveriTot;
    }
    
    public int getComponenti(){
        int componenti = 0;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM ComponentiFamiglie");
            while(resultSet.next()){
                componenti = resultSet.getInt(1);
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return componenti;
    }
    
    public int getPoli(){
        int npoli = 0;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM Rappresentante");
            while(resultSet.next()){
                npoli = resultSet.getInt(1);
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return npoli;
    }
    
    public int getDiocesi(){
        int ndiocesi = 0;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM Diocesi");
            while(resultSet.next()){
                ndiocesi = resultSet.getInt(1);
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return ndiocesi;
    }
    
    public List<Diocesi> getPoliXdiocesi(){
        List<Diocesi> poliXdiocesi = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            String query = "SELECT IDDiocesiAppartenenza,COUNT(*) as NumeroElementi FROM Rappresentante JOIN Diocesi on Rappresentante.IDDiocesiAppartenenza = Diocesi.IDDiocesi GROUP BY IDDiocesi";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                poliXdiocesi.add(new Diocesi(resultSet.getInt(1),resultSet.getInt(2),this.getPoli(resultSet.getInt(1))));
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return poliXdiocesi;
       


    }
    
    public LinkedList<Integer> getPoli(int idDiocesi){
        LinkedList<Integer> listaPoli = new LinkedList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT IDRappresentante FROM Rappresentante WHERE IDDiocesiAppartenenza ='"+idDiocesi+"'");
            while(resultSet.next()){
                listaPoli.add(resultSet.getInt(1));
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return listaPoli;
    } 
    
    public List<Azienda> getAziende(){
        List<Azienda> aziende = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT IDAzienda, AlimentoProdotto FROM Azienda");
            while(resultSet.next()){
                aziende.add(new Azienda(resultSet.getInt(1),resultSet.getString(2)));
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return aziende;
    }
    
    public void creaSchema(int idDiocesi, int idPolo, String viveri, float QTAViveri){
         try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO SchemaDistribuzioneDiocesi(IDDiocesi, NomeViveri,IDPoloDestinatario,QTAViveri) VALUES ('"+idDiocesi+"','"+viveri +"','"+idPolo+"',"+QTAViveri+")");
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
    }
    
    public void inviaRichiestaSpeciale(int id, String nome, int qta){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            System.out.println("DEBUG INSERIMENTO ----> " + id + ",,," + nome+"....." +qta);
            statement.executeUpdate("INSERT INTO RichiesteSpeciali(IDAzienda, ViveriRichiesti,QTAViveri) VALUES ('"+id+"','"+ nome+"','"+qta+"')");
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
    }
    
    public void riempiMagazzino(int idDiocesi, String nome, int QTAViveri){
        int idMagazzino = -1;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT IDMagazzino FROM MagazziniDiocesi WHERE IDDiocesi ='"+idDiocesi+"'");
            while(resultSet.next()){
                idMagazzino = resultSet.getInt(1);
            }
        }
        catch(Exception e){
            this.popUp.mostra(this);
        }
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Magazzino(IDMagazzino, Viveri,QTAViveri) VALUES ('"+idMagazzino+"','"+ nome+"','"+QTAViveri+"')");
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
    }  
    
    public void riempiMagazzinoP(int idPolo, String nome, int QTAViveri){
        int idMagazzino = -1;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT IDMagazzino FROM MagazziniPolo WHERE IDPolo ='"+idPolo+"'");
            while(resultSet.next()){
                idMagazzino = resultSet.getInt(1);
            }
        }
        catch(Exception e){
            this.popUp.mostra(this);
        }
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Magazzino(IDMagazzino, Viveri,QTAViveri) VALUES ('"+idMagazzino+"','"+ nome+"','"+QTAViveri+"')");
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
    }  
    
    public List<Integer> getnPoli(){
        List<Integer> nPoli = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT IDRappresentante FROM Rappresentante");
            while(resultSet.next()){
                nPoli.add(resultSet.getInt(1));
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return nPoli;
    }
        
    public int getNFamiglie(){
        int nFamiglie = -1;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            String query = "SELECT COUNT(*) FROM ComponentiFamiglie";

            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                nFamiglie = resultSet.getInt(1);
            }
            connection.close();
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return nFamiglie;
    }
    
    public List<Viveri> getViveriPolo(int id){
        List<Viveri> viveriPolo = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT VIVERI, QTAViveri FROM Magazzino m JOIN MagazziniPolo mp on m.IDMagazzino = mp.IDMagazzino WHERE mp.IDPolo ='"+id+"'");
            while(resultSet.next()){
                viveriPolo.add(new Viveri(resultSet.getString(1),resultSet.getInt(2)));
            }
        }
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return viveriPolo;
    }
    
    public List<Famiglie> getFamiglie(int id){
        List<Famiglie> referentiFamiglie = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT CFReferente, IDFamiglia FROM Famiglie WHERE IDPoloAppartenenza ='"+id+"'");
            while(resultSet.next()){
                System.out.println("GETFAMIGLIE "+ resultSet.getString(1) + resultSet.getInt(2));
                referentiFamiglie.add(new Famiglie(resultSet.getString(1),resultSet.getInt(2)));
            }
        }
        catch(Exception e){
            this.popUp.mostra(this);
        }
        return referentiFamiglie;
    }
    
    public void creaSchemaPolo(int idPolo,String nome,int QTA,String famigliaDestinataria){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            System.out.println("TEST --> " + idPolo + ","+nome+","+QTA+","+famigliaDestinataria);
            statement.executeUpdate("INSERT INTO SchemaDistribuzionePolo(FamigliaDestinataria, Viveri,QTAViveri,IDPolo) VALUES ('"+famigliaDestinataria+"','"+ nome+"','"+QTA+"',"+idPolo+")");
            //stetement.executeUpdate("ISERT INTO Report (IDReport,")
        }                      
        catch(Exception e){
            this.popUp.mostra(this);
        }
    }
    
    public void generaReport(int idPolo, String nomeAlimento){
        int idReport = 0;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MAX(IDReport) FROM Report");
            while(resultSet.next()){
                idReport = resultSet.getInt(1);
            }
            connection.close();

        }
        catch(Exception e){
            this.popUp.mostra(this);
        }
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String oggi = now.format(formatter);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            System.out.println(oggi);
            statement.executeUpdate("INSERT INTO Report (IDReport, IDPolo,ViveriAggiunti,DataAggiunta) VALUES('"+idReport+"','"+idPolo+"','"+nomeAlimento+"','"+oggi+"')");
        }
        catch(Exception e){
            this.popUp.mostra(this);
        }           
    }
    
    public int schemaGenerato(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM generato");
            while(resultSet.next()){
                return resultSet.getInt(1);
            }
        }
        catch(Exception e){
            this.popUp.mostra(this);  
        }
        return 2;
    }
    
    public void setGenerato(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(this.url,this.username,this.password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM generato");
            statement.executeUpdate("INSERT INTO generato(generato) VALUES (1)");
            
        }
        catch(Exception e){
            this.popUp.mostra(this);  
        }
    }
    
    public boolean connect() {
        try {
            this.connection = DriverManager.getConnection(this.url, this.username, this.password);
            System.out.println("Connessione al database stabilita.");
            return true;
        } catch (Exception e) {
            this.popUp.mostra(this);
            return false;
        }
    }

    public boolean handleConnectionFailureAndRetry() {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                System.out.println("Connessione persa. Tentativo di riconnessione...");
                return this.connect();
                
            } 
        } catch (Exception e) {
            this.popUp.mostra(this);
            return false;
        }
        return true;
    } 
}
        


