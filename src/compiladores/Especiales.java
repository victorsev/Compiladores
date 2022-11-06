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
public class Especiales {
    //listado de los caracteres especiales
    final char EPSILON=(char)7;
    final char FIN=(char)0;
    final int ERORR=2000;
    final int OMITIR=2001;

    public char getFIN() {
        return FIN;
    }

    public int getERORR() {
        return ERORR;
    }

    public int getOMITIR() {
        return OMITIR;
    }
    

    public char getEPSILON() {
        return EPSILON;
    }
    
    
}
