/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestioneFamiglie;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author manfredi
 */
public class DatiComponente {
    public String nome;
    public String cognome;
    public int eta;
    public String CF;
    public String bisogni;
    
    public DatiComponente(String nome, String cognome, int eta, String CF, String bisogni){
        this.nome = nome;
        this.cognome = cognome;
        this.eta = eta;
        this.CF = CF;
        this.bisogni = bisogni;
    }
}
