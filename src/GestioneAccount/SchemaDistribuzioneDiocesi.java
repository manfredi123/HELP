/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestioneAccount;

/**
 *
 * @author manfredi
 */
public class SchemaDistribuzioneDiocesi{
    public int idPoloDestinatario;
    public int idDiocesi;
    public String nomeViveri;
    public int QTAViveri;
    
    public SchemaDistribuzioneDiocesi(int idPolo, int idDiocesi, String nomeViveri,int QTA){
        this.idPoloDestinatario = idPolo;
        this.idDiocesi = idDiocesi;
        this.nomeViveri = nomeViveri;
        this.QTAViveri = QTA;
    }
    
    
}
