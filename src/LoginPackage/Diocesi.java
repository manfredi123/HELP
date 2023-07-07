/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LoginPackage;
import java.util.LinkedList;
/**
 *
 * @author manfredi
 */
public class Diocesi {
    public int IDDiocesi;
    public int nPoliAppartenenti;
    LinkedList<Integer> idPoliAppartenenti;
    
    public Diocesi(int IDDiocesi, int nPoli, LinkedList<Integer> idPoli){
        this.IDDiocesi = IDDiocesi;
        this.nPoliAppartenenti = nPoli;
        this.idPoliAppartenenti = idPoli;
    }
}
