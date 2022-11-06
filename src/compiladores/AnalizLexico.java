/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores;

import java.util.Stack;

/**
 *
 * @author Jorge Ortega
 */
public class AnalizLexico {
    int token, edoActual, edoTransicion;
    String cadenaSigma;
    public  String yytext; //yytext
    boolean pasoPorEdoAcept;
    int iniLexema,finLexema, indiceCaracterActual;
    char caracterActual;
    Stack<Integer> pila= new Stack<Integer>();
    AFD automataAFD;
    Especiales c= new Especiales();
    
    public AnalizLexico(){
        cadenaSigma="";
        pasoPorEdoAcept=false;
        finLexema=-1;
        iniLexema=finLexema;
        indiceCaracterActual=-1;
        token=-1;
        pila.clear();
        automataAFD=null;
        
    }
    public AnalizLexico(String sigma, String fileAFD, int idAFD){
        automataAFD=new AFD();
        cadenaSigma=sigma;
        pasoPorEdoAcept=false;
        iniLexema=0;
        finLexema=-1;
        indiceCaracterActual=0;
        token=-1;
        pila.clear();
        automataAFD.leerAFDdeArchivo(fileAFD, idAFD);
    }
    public AnalizLexico(String sigma, String fileAFD){
        automataAFD=new AFD();
        cadenaSigma=sigma;
        pasoPorEdoAcept=false;
        iniLexema=0;
        finLexema=-1;
        indiceCaracterActual=0;
        token=-1;
        pila.clear();
        automataAFD.leerAFDdeArchivo(fileAFD, -1);
                
    }
    public AnalizLexico(String fileAFD, int idAFD){
        automataAFD=new AFD();
        cadenaSigma="";
        pasoPorEdoAcept=false;
        iniLexema=0;
        finLexema=-1;
        indiceCaracterActual=0;
        token=-1;
        pila.clear();
        automataAFD.leerAFDdeArchivo(fileAFD, idAFD);
                
    }
    public AnalizLexico(String sigma, AFD AutFD){
        
        cadenaSigma=sigma;
        pasoPorEdoAcept=false;
        iniLexema=0;
        finLexema=-1;
        indiceCaracterActual=0;
        token=-1;
        pila.clear();
        automataAFD=AutFD;
                
    }
    
    public ClassEstadoAnalizLexico getEdoAnalizLexico(){
        ClassEstadoAnalizLexico EdoActualAnaliz=new ClassEstadoAnalizLexico();
        EdoActualAnaliz.caracterActual=caracterActual;
        EdoActualAnaliz.edoActual=edoActual;
        EdoActualAnaliz.edoTransicion=edoTransicion;
        EdoActualAnaliz.finLexema=finLexema;
        EdoActualAnaliz.indiceCaracterActual=indiceCaracterActual;
        EdoActualAnaliz.iniLexema=iniLexema;
        EdoActualAnaliz.lexema=yytext;
        EdoActualAnaliz.pasoPorEdoAcept=pasoPorEdoAcept;
        EdoActualAnaliz.pila=pila;
        EdoActualAnaliz.token=token;
        return EdoActualAnaliz;
    }
    
    public boolean SetEdoAnalizLexico(ClassEstadoAnalizLexico e){
        caracterActual=e.caracterActual;
        edoActual=e.edoActual;
        edoTransicion=e.edoTransicion;
        finLexema=e.finLexema;
        indiceCaracterActual=e.indiceCaracterActual;
        iniLexema=e.iniLexema;
        yytext=e.lexema;
        pasoPorEdoAcept=e.pasoPorEdoAcept;
        token=e.token;
        pila=e.pila;
        return true;
    }
    
    public void setSigma(String sigma){
        cadenaSigma=sigma;
        pasoPorEdoAcept=false;
        iniLexema=0;
        finLexema=-1;
        indiceCaracterActual=0;
        token=-1;
        pila.clear();
        
    }
    
    public String cadenaXAnaliz(){
        return cadenaSigma.substring(indiceCaracterActual, indiceCaracterActual+(cadenaSigma.length() - indiceCaracterActual));
    }
    
    public int yylex(){
        while (true){
            pila.push(indiceCaracterActual);
            if(indiceCaracterActual>=cadenaSigma.length()){
                yytext="";
                return c.FIN;
            }
            iniLexema=indiceCaracterActual;
            edoActual=0;
            pasoPorEdoAcept=false;
            finLexema=-1;
            token=-1;
            while(indiceCaracterActual<cadenaSigma.length()){
                caracterActual=cadenaSigma.charAt(indiceCaracterActual);
                edoTransicion=automataAFD.TablaAFD.get(edoActual).get(caracterActual);//columna fila
                if(edoTransicion != -1){
                    if(automataAFD.TablaAFD.get(edoTransicion).get(256) != -1){
                        pasoPorEdoAcept=true;
                        token=automataAFD.TablaAFD.get(edoTransicion).get(256);
                        finLexema=indiceCaracterActual;
                    }
                    indiceCaracterActual++;
                    edoActual=edoTransicion;
                    continue;
                }
                break;
            }//no existe una transicion
            if(!pasoPorEdoAcept){
                indiceCaracterActual=iniLexema+1;
                yytext=cadenaSigma.substring(iniLexema, iniLexema+1);
                token=c.getERORR();
                return token;
            }
            //no hay transicion pero si tenemos edo de aceptacion
            yytext=cadenaSigma.substring(iniLexema, (finLexema+1));//iniLexema+(finLexema-iniLexema+1)
            indiceCaracterActual=finLexema+1;
            if(token==c.getOMITIR()){//para los espacios  o tabuladores
                continue;
            }else{
                return token;
            }
        }
    }
    
    public boolean UndoToken(){
        if(pila.size() ==0){
            return false;
            
        }
        indiceCaracterActual=pila.pop();
        return true;
    }
    
    
}
