/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores;

import java.util.HashSet;

/**
 *
 * @author Jorge Ortega
 */
public class Estado {
    static int contadorIdEstado=0;
    public int IdEstado;
    public boolean EdoAcept;
    int Token;
    public HashSet<Transicion> Trans = new HashSet<Transicion>();
    
    public Estado(){
        EdoAcept=false;
        Token=-1;
        
        IdEstado=contadorIdEstado++;
        Trans.clear();
    }

    public int getIdEstado() {
        return IdEstado;
    }

    public void setIdEstado(int IdEstado) {
        this.IdEstado = IdEstado;
    }

    public boolean isEdoAcept() {
        return EdoAcept;
    }

    public void setEdoAcept(boolean EdoAcept) {
        this.EdoAcept = EdoAcept;
    }

    public int getToken() {
        return Token;
    }

    public void setToken(int Token) {
        this.Token = Token;
    }

    public HashSet<Transicion> getTrans() {
        return Trans;
    }

    public void setTrans(HashSet<Transicion> Trans) {
        this.Trans = Trans;
    }
    
}
