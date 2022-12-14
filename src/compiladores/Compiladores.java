/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        //c+
        c.cerraduraSuma();
        
        //(a|b)+c+
        a.concatenar(c);
        //se crea un AFN vacio para asignar el token al no vacio
        AFN d =new AFN();
        //por cada AFN que tengamos se repite la union
        d.UnionEspecialANFs(a, 10);
        
        
//        Se convierte el AFN a un AFD creando su tabla en archivo
        a.ConvAFNaAFD("AFD");
        //se crea unAFD para leer la tabla
        AFD e =new AFD();
        e.leerAFDdeArchivo("AFD", 0);//nombre del archivo y id del AFN
        //impresion de la tabla
        System.out.println("TablaAFD = " + e.TablaAFD.get(0).get(256));
        //se crea el ananlizador lexico se le pasa cadena AFD y id
        AnalizLexico analizador = new AnalizLexico("abccccc", "AFD", 0);
        int res=0;
        //se analiza el token
        res=analizador.yylex();
        //imprime el token
        System.out.println("analizador = " + res);
        //imprime el lexema
        System.out.println("analizador = " + analizador.yytext);
        
//        //CerraduraEpsilon
//        for(Estado e: a.EdosAFN){
//            System.out.println("Estado del AFN");
//            System.out.println(e.IdEstado);
//            
//            HashSet<Estado> epsilon=a.cerraduraEpsilon(e);
//            System.out.println("Estados de la cerradura");
//            for(Estado Edo: epsilon){
//                System.out.println(Edo.IdEstado);
//            }
//            HashSet<Estado> mover=a.Mover(epsilon, 'a');
//            System.out.println("Estados de mover");
//            for(Estado Edo: mover){
//                System.out.println(Edo.IdEstado);
//            }
//            HashSet<Estado> ira=a.IrA(epsilon, 'a');
//            System.out.println("Estados de Ir A");
//            for(Estado Edo: ira){
//                System.out.println(Edo.IdEstado);
//            }
//            
//        }
//        
//        
//        
        
    }
    
}
