/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LoginPackage;

/**
 *
 * @author manfredi
 */

public class EntityUtente {
    public int idUtente;
    public String tipologia;
    
    public EntityUtente(int ID){
        this.idUtente = ID;
      
    }

    public int getIdUtente(){
        return this.idUtente;
    }
    
    public EntityUtente getUtente(){
        return this;
    }
}


