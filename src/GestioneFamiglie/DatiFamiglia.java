/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestioneFamiglie;
import java.util.List;
/**
 *
 * @author manfredi
 */

public class DatiFamiglia {
    public int IDPoloAppartenenza;
    public List<DatiComponente> componenti;
    public long recapito;
    public int IDFamiglia;
    public String CFReferente;
    
    public DatiFamiglia(int idFamiglia,int polo, long recapito, List<DatiComponente> componenti, String referente){
        this.IDFamiglia = idFamiglia;
        this.IDPoloAppartenenza = polo;
        this.recapito = recapito;
        this.componenti = componenti;
        this.CFReferente = referente;
    }
    
}
