/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge Ortega
 */
public class AFD {
    List<List<Integer>> TablaAFD=new ArrayList<List<Integer>>();//lista de listas
    public AFD(){
        
    }
    public AFD leerAFDdeArchivo(String file, int id){
        AFD afd=new AFD();
        File archivo =null;
        FileReader fr=null;
        BufferedReader br =null;
        Stack<String> lineas= new Stack<String>();
        Stack<String> lineasAux= new Stack<String>();
        String linea;

        try {
            archivo = new File (file + ".txt");
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);
            int columna=0;
            //lee linea a linea y la pone en una pila
            while((linea=br.readLine())!=null){
                lineasAux.push(linea);               
            }
            //se pone en el orden orignial
            while(lineasAux.size()!=0){
                lineas.push(lineasAux.pop());
            }
            //se lee la pila
            while(lineas.size()!=0){
                this.TablaAFD.add(new ArrayList<Integer>());
                linea=lineas.pop();
                for(int tam=0, cont=0; cont<=256; cont++){  
                    tam=linea.length();
                    //Se agrega un elemento hasta donde encuentre un espacio
                    this.TablaAFD.get(columna).add(parseInt(linea.substring(0, linea.indexOf(" "))));//agregarFila
                    //la cadena se recorta hasta donde encontro el estacio para la siguiente iteracion
                    linea=linea.substring(linea.indexOf(" ")+1, tam);
                }
                //se agrega otra columna
                columna++;
            }  
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AFD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AFD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return afd;
    }
}
