/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Jorge Ortega
 */
public class Compiladores {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        AFN a1 = new AFN();
//        a1.CrearAFNBasico('A');
//        AFN a2 = new AFN();
//        AFN a3 = new AFN();
//        a2.CrearAFNBasico('B', 'Z');
//        for(Estado e: a1.EdosAcept){
//            System.out.println(e.IdEstado);
//        }
//        for(Estado e: a2.EdosAcept){
//            System.out.println(e.IdEstado);
//        }
//        System.out.println(a1.EdosAFN);
//        System.out.println(a2.EdosAFN);
//        a1.cerraduraSuma();
//        System.out.println(a1.Alfabeto);
//        System.out.println(a1.EdosAFN);
//        for(Estado e: a1.EdosAcept){
//            System.out.println(e.IdEstado);
//        }
//        System.out.println("Id de los estados del AFN final");
//        for(Estado e: a1.EdosAFN){
//            System.out.println(e.IdEstado);
//        }
        
        // creaacion de (a|b)+c+
        AFN a =new AFN();
        a.CrearAFNBasico('a');
        AFN b =new AFN();
        b.CrearAFNBasico('b');
        //(a|b)
        a.unirAFN(b);
        //(a|b)+
        a.cerraduraSuma();
        
        //creando c
        AFN c =new AFN();
        c.CrearAFNBasico('c');
        //c*
        c.cerraduraSuma();
        
        //(a|b)+c*
        a.concatenar(c);
        
        a.ConvAFNaAFD();
//        //CerraduraEpsilon
//        for(Estado e: a.EdosAFN){
//            System.out.println("Estado del AFN");
//            System.out.println(e.IdEstado);
//            
//            HashSet<Estado> res=a.cerraduraEpsilon(e);
//            System.out.println("Estados de la cerradura");
//            for(Estado Edo: res){
//                System.out.println(Edo.IdEstado);
//            }
//            
//        }
//        
        
        
        
    }
    
}
