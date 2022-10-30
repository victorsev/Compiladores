/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores;

/**
 *
 * @author Jorge Ortega
 */
public class Transicion {

    private char SimbInf;
    private char SimbSup;
    private Estado Edo;

    public Transicion(char simb, Estado e) {
        SimbInf = simb;
        SimbSup = simb;
        Edo = e;
    }

    public Transicion(char simb1, char simb2, Estado e) {
        SimbInf = simb1;
        SimbSup = simb2;
        Edo = e;
    }

    public Transicion() {
        Edo = null;
    }

    public void SetTransicion(char s1, char s2, Estado e) {
        SimbInf = s1;
        SimbSup = s2;
        Edo = e;
    }

    public void SetTransicion(char s1, Estado e) {
        SimbInf = s1;
        SimbSup = s1;
        Edo = e;
    }

    public char getSimbInf() {
        return SimbInf;
    }

    public void setSimbInf(char SimbInf) {
        this.SimbInf = SimbInf;
    }

    public char getSimbSup() {
        return SimbSup;
    }

    public void setSimbSup(char SimbSup) {
        this.SimbSup = SimbSup;
    }
    
    public Estado GetEdoTrans(char s){
        if(SimbInf<=s && s<=SimbSup){
            return Edo;
        }
        return null;
    }
}
