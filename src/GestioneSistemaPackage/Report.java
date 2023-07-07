/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestioneSistemaPackage;
import java.util.Date;
/**
 *
 * @author manfredi
 */
public class Report {
    
    public int IDReport;
    public Date dataAggiunta;
    public String viveriAggiunti;
    public String comunePolo;
    
    public Report(int IDReport, Date dataAggiunta, String viveri, String comune){
        this.IDReport = IDReport;
        this.dataAggiunta = dataAggiunta;
        this.viveriAggiunti = viveri;
        this.comunePolo = comune;
    }
    
}
