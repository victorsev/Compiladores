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
public class ConjIJ {
    public int j;
    public HashSet<Estado> conjIJ;
    public int[] TransicionesAFD;
    
    public ConjIJ(int CardAlf){
        j=-1;
        conjIJ=new HashSet<Estado>();
        conjIJ.clear();
        TransicionesAFD=new int[CardAlf+1];
        for(int k=0; k<=CardAlf; k++){
            TransicionesAFD[k]=-1;
        }
        
    }
}
