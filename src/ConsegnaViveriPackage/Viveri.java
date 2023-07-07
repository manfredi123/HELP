/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConsegnaViveriPackage;

/**
 *
 * @author manfredi
 */
public class Viveri {
    public String nome;
    public int QTA;
    
    public Viveri(String nome, int QTA){
        this.nome = nome;
        this.QTA = QTA;
    }
    
    public Viveri(){
        this("",-1);
    }
    
    @Override
    public String toString(){
        return this.nome + " " + this.QTA;
    }
}
