/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge Ortega
 */




public class AFN {    
    
    Especiales c = new Especiales();
    
    public static HashSet<AFN> ConjDeAFNs = new HashSet<AFN>();
    Estado EdoIni;
    HashSet<Estado> EdosAFN = new HashSet<Estado>();
    HashSet<Estado> EdosAcept = new HashSet<Estado>();
    HashSet<Character> Alfabeto = new HashSet<Character>();
    boolean SeAgregoAFNUnionLexico;
    public int IdAFN;

    public AFN() {
        IdAFN = 0;
        EdoIni = null;
        EdosAFN.clear();
        EdosAcept.clear();
        Alfabeto.clear();
        SeAgregoAFNUnionLexico = false;

    }

    public AFN CrearAFNBasico(char s) {
        Transicion t;
        Estado e1, e2;
        e1 = new Estado();
        e2 = new Estado();
        t = new Transicion(s, e2);
        e1.Trans.add(t);
        e2.EdoAcept = true;
        Alfabeto.add(s);
        EdoIni = e1;
        EdosAFN.add(e1);
        EdosAFN.add(e2);
        EdosAcept.add(e2);
        SeAgregoAFNUnionLexico = false;
        return this;
    }

    public AFN CrearAFNBasico(char s1, char s2) {
        if (s1 <= s2) {
            Transicion t;
            Estado e1, e2;
            e1 = new Estado();
            e2 = new Estado();
            t = new Transicion(s1, s2, e2);
            e1.Trans.add(t);
            e2.EdoAcept = true;
            for (int i = s1; i <= s2; i++) {
                Alfabeto.add((char) i);
            }
            EdoIni = e1;
            EdosAFN.add(e1);
            EdosAFN.add(e2);
            EdosAcept.add(e2);
            SeAgregoAFNUnionLexico = false;
            return this;
        }else{
            return null;
        }
        
    }
    public AFN unirAFN(AFN f2){
        //nuevo estado inicial
        Estado e1=new Estado();
        //nuevo estado final
        Estado e2=new Estado();
        //el 07 en ASCII se tomara como epsilon
        //se recorren los estados inicales a e1, con tranciciones epsilon
        Transicion t1=new Transicion(c.EPSILON, this.EdoIni);
        Transicion t2=new Transicion(c.EPSILON, f2.EdoIni);
        e1.Trans.add(t1);
        e1.Trans.add(t2);
        //e1 se hace estado inicial
        this.EdoIni=e1;
        // se hacen las traciciones de los estados de aceptacion es e2 con tranciciones epsilon
        for(Estado e: this.EdosAcept){
            e.Trans.add(new Transicion(c.EPSILON, e2));
            e.EdoAcept=false;
        }
        for(Estado e: f2.EdosAcept){
            e.Trans.add(new Transicion(c.EPSILON, e2));
            e.EdoAcept=false;
        }
        //e2 se hace estado de aceptacion
        e2.EdoAcept=true;
        //se borran los estados de aceptacion que tenia
        this.EdosAcept.clear();
        //se agrega e2 como estado de aceptacion
        this.EdosAcept.add(e2);
        //se agrega el alfabeto del segundo AFN
        for(char e: f2.Alfabeto){
            this.Alfabeto.add(e);
        }
        //Se agrengan los estados del segundo AFN
        for(Estado e: f2.EdosAFN){
            this.EdosAFN.add(e);
        }
        //se agrega e1 y e2 a los estados del AFN
        this.EdosAFN.add(e1);
        this.EdosAFN.add(e2);
        return this;
    }
    
    public AFN concatenar(AFN f2){
        //no se crean nuevos estados
        //estado inicial es el estado de primero
        //Se agrengan los estados del segundo AFN
        for(Estado e: f2.EdosAFN){
            if(e.IdEstado!=f2.EdoIni.IdEstado){
                this.EdosAFN.add(e);
            } else{//los estados de aceptacion del primer AFN es el inicial del segundo
                for(Estado x: this.EdosAcept){
                    x.EdoAcept=f2.EdoIni.EdoAcept;
                    x.IdEstado=f2.EdoIni.IdEstado;
                    x.Token=f2.EdoIni.Token;
                    x.Trans=f2.EdoIni.Trans;
                }
            }         
        }
        
        //se renuevan los estados de aceptacion
        this.EdosAcept.clear();
        for(Estado e: f2.EdosAcept){
            this.EdosAcept.add(e);
        }
        //se agrega el alfabeto del segundo AFN
        for(char e: f2.Alfabeto){
            this.Alfabeto.add(e);
        }
        
        
        return this;
    }
    
    public AFN cerraduraSuma(){
        //nuevo estado inicial
        Estado e1=new Estado();
        //nuevo estado final
        Estado e2=new Estado();
        
        // se hacen las traciciones de los estados de aceptacion es e2 con tranciciones epsilon
        //se hace la tracicion al estado inicial con epsilon
        for(Estado e: this.EdosAcept){
            e.Trans.add(new Transicion(c.EPSILON, e2));
            e.Trans.add(new Transicion(c.EPSILON, this.EdoIni));
            e.EdoAcept=false;
        }
        
        //el 07 en ASCII se tomara como epsilon
        //se recorre el estado inical a e1, con tranciciones epsilon
        Transicion t1=new Transicion(c.EPSILON, this.EdoIni);
        e1.Trans.add(t1);
        //e1 se hace estado inicial
        this.EdoIni=e1;
        
        //e2 se hace estado de aceptacion
        e2.EdoAcept=true;
        //se borran los estados de aceptacion que tenia
        this.EdosAcept.clear();
        //se agrega e2 como estado de aceptacion
        this.EdosAcept.add(e2);
        //se agrega e1 y e2 a los estados del AFN
        this.EdosAFN.add(e1);
        this.EdosAFN.add(e2);
        return this;
    }
    public AFN cerraduraAsterisco(){
        //nuevo estado inicial
        Estado e1=new Estado();
        //nuevo estado final
        Estado e2=new Estado();
        
        
        // se hacen las traciciones de los estados de aceptacion es e2 con tranciciones epsilon
        //se hace la tracicion al estado inicial con epsilon
        for(Estado e: this.EdosAcept){
            e.Trans.add(new Transicion(c.EPSILON, e2));
            e.Trans.add(new Transicion(c.EPSILON, this.EdoIni));
            e.EdoAcept=false;
        }
        
        //el 07 en ASCII se tomara como epsilon
        //se recorre el estado inical a e1, con tranciciones epsilon
        Transicion t1=new Transicion(c.EPSILON, this.EdoIni);
        e1.Trans.add(t1);
        //e1 se hace estado inicial
        this.EdoIni=e1;
        
        //e2 se hace estado de aceptacion
        e2.EdoAcept=true;
        //se borran los estados de aceptacion que tenia
        this.EdosAcept.clear();
        //se agrega e2 como estado de aceptacion
        this.EdosAcept.add(e2);
        //agregar trancicion del estado inicial al final
        e1.Trans.add(new Transicion(c.EPSILON, e2));
        //se agrega e1 y e2 a los estados del AFN
        this.EdosAFN.add(e1);
        this.EdosAFN.add(e2);
        
        
        return this;
    }
    
    public AFN opcional(AFN f2){
        //nuevo estado inicial
        Estado e1=new Estado();
        //nuevo estado final
        Estado e2=new Estado();
        
        // se hacen las traciciones de los estados de aceptacion es e2 con tranciciones epsilon
        for(Estado e: this.EdosAcept){
            e.Trans.add(new Transicion(c.EPSILON, e2));
            e.EdoAcept=false;
        }
        
        //el 07 en ASCII se tomara como epsilon
        //se recorre el estado inical a e1, con tranciciones epsilon
        Transicion t1=new Transicion(c.EPSILON, this.EdoIni);
        e1.Trans.add(t1);
        //e1 se hace estado inicial
        this.EdoIni=e1;
        
        //e2 se hace estado de aceptacion
        e2.EdoAcept=true;
        //se borran los estados de aceptacion que tenia
        this.EdosAcept.clear();
        //se agrega e2 como estado de aceptacion
        this.EdosAcept.add(e2);
        //agregar trancicion del estado inicial al final
        e1.Trans.add(new Transicion(c.EPSILON, e2));
        //se agrega e1 y e2 a los estados del AFN
        this.EdosAFN.add(e1);
        this.EdosAFN.add(e2);
        
        
        return this;
    }
    
    public HashSet<Estado> cerraduraEpsilon(Estado e){
        HashSet<Estado> R= new HashSet<Estado>();
        Stack<Estado> S= new Stack<Estado>();      
        Estado aux;
        Estado Edo;
        R.clear();
        S.clear();
        S.push(e);
        
        while(!(S.isEmpty())){
            aux=S.pop();
            R.add(aux);          
            for(Transicion t: aux.Trans){
                if((Edo=t.GetEdoTrans(c.EPSILON))!= null)
                    if(!(R.contains(Edo)))
                        S.push(Edo);              
            }
        }
        return R;
    }
    
    public HashSet<Estado> Mover(HashSet<Estado> Edos, char Simb){
        HashSet <Estado> C= new HashSet<Estado>();
        Estado Aux;
        C.clear();
        for(Estado Edo: Edos){
            for(Transicion t: Edo.Trans){
                Aux=t.GetEdoTrans(Simb);
                if(Aux !=null)
                    C.add(Aux);
            }
            
        }
        return C;
    }
    
    public HashSet<Estado> IrA(HashSet<Estado> Edos, char Simb){
        HashSet <Estado> C= new HashSet<Estado>();
        C.clear();
        for(Estado Edo: Mover(Edos, Simb)){
            C.addAll(cerraduraEpsilon(Edo));
        }
        
        return C;
    }
    
    
    public void ConvAFNaAFD(String archTabla){
        int CardAlfabeto, NumEdosAFD;
        int i , j, r ;
        char[] arrAlfabeto;
        ConjIJ Ij, Ik;
        boolean existe;

        HashSet<Estado> ConjAux = new HashSet<Estado>();
        HashSet<ConjIJ> EdosAFD = new HashSet<ConjIJ>();
        Queue<ConjIJ> EdosSinAnalizar = new LinkedList<ConjIJ>();
        
        EdosAFD.clear();
        EdosSinAnalizar.clear();
        CardAlfabeto=Alfabeto.size();
        
        arrAlfabeto=new char [CardAlfabeto];
        i=0;
        
        for(char c: Alfabeto){
            arrAlfabeto[i++]=c;
        }
        
        j=0;// este es el contador de los estados
        Ij= new ConjIJ(CardAlfabeto);
        Ij.conjIJ=cerraduraEpsilon(this.EdoIni);
        Ij.j=j;
        
        EdosAFD.add(Ij);
        EdosSinAnalizar.add(Ij);
        j++;
        
        while(EdosSinAnalizar.size() !=0 ){
            Ij=EdosSinAnalizar.remove();
            for(char c: arrAlfabeto){
                Ik=new ConjIJ(CardAlfabeto);
                Ik.conjIJ=IrA(Ij.conjIJ, c);
                if(Ik.conjIJ.size() == 0 )//si entra no hubo transiciones
                    continue;
                existe=false;
                for(ConjIJ I: EdosAFD){
                    if(I.conjIJ.equals(Ik.conjIJ)){
                        existe=true;
                        r= indiceCaracter(arrAlfabeto, c);
                        Ij.TransicionesAFD[r]=I.j;
                        break;
                        
                    }
                }
                if (!existe) {
                    Ik.j=j;
                    r= indiceCaracter(arrAlfabeto, c);
                    Ij.TransicionesAFD[r]=Ik.j;
                    EdosAFD.add(Ik);
                    EdosSinAnalizar.add(Ik);
                    j++;
                }
            }
        }
        NumEdosAFD = j;
        //tabla
        //List<List<Integer>> TablaAFD=new ArrayList<List<Integer>>();
        //contadores y token
        int contador=0,tkn=0;
        //escritura de la tabla
        File file = new File(archTabla + ".txt");
        FileWriter fichero = null;
        BufferedWriter pw = null;
        //File archivo = new File (archTabla + ".txt");
//        FileReader fr = new FileReader (archivo);
//        BufferedReader br = new BufferedReader(fr);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fichero = new FileWriter(file);
            pw = new BufferedWriter(fichero);
            int b=0;
            for(ConjIJ con: EdosAFD){ 
                for(int a=0; a<=256; a++){ 
                     
                        if(a<=255){
                            contador=0;
                            tkn=0;
    //                        System.out.println(IrA(con.conjIJ, (char)a));
    //                        System.out.println(con.conjIJ);
                            HashSet<Estado> ira= new HashSet<Estado>();
                            ira=IrA(con.conjIJ, (char)a);
                            for(ConjIJ sj: EdosAFD){
                                
                                if(ira.equals(sj.conjIJ)){
//                                    System.out.println(IrA(con.conjIJ, (char)a));
//                                    System.out.println(con.conjIJ);
                                    //System.out.println("a = " + a);
                                    pw.write("+"+tkn+" ");
                                    //pw.write(tkn+" ");
                                    contador=1;
                                }
                                tkn++;//par ver en que Sj esta
                            }
                            if(contador==0){
                                pw.write("-1 ");
                            }

                        }else{
                            contador=0;
                            tkn=0;
                            for(Estado x: con.conjIJ){ 
                                for(Estado y: this.EdosAcept){ 
                                    if(y.IdEstado==x.IdEstado){//se encuentra el estado de aceptacion entre los Sj y el AFN
                                        tkn=x.Token;
                                        contador++;
                                        //System.out.println("y.IdEstado = " + y.IdEstado);
                                        //System.out.println("x.IdEstado = " + x.IdEstado);
                                    }
                                }
                            }
    //                        System.out.println("contador = " + contador);
                            if(contador==1){
                                pw.write("+"+tkn + " ");
                            }else{
                                pw.write("-1 ");
                            }
                        }       
                    
                    
                }
                b++;
                System.out.println(b);
                pw.write("\n");
            }
            
        } catch (IOException ex) {
            Logger.getLogger(AFN.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
               pw.close();
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
        
        
        
    }
    
    public int indiceCaracter(char [] pajar, char aguja){
            int encontro = -1;
            for(int i = 0; i < pajar.length; i++){
                if(pajar[i] == aguja){
                   encontro = i;
                   return i;
                }
            }
            return  -1;
    }
    public void UnionEspecialANFs(AFN f, int token){
        Estado e;
        if(!this.SeAgregoAFNUnionLexico){
            this.EdosAFN.clear();
            this.Alfabeto.clear();
            e=new Estado();
            e.Trans.add(new Transicion(c.EPSILON, f.EdoIni));
            this.EdoIni=e;
            this.EdosAFN.add(e);
            this.SeAgregoAFNUnionLexico=true;
        }else{
            this.EdoIni.Trans.add(new Transicion(c.EPSILON, f.EdoIni));
        }
        //aginacion de token al segundo AFN
        for(Estado EdoAcep: f.EdosAcept){
            EdoAcep.Token=token;
        }
        for(char c: f.Alfabeto){
            this.Alfabeto.add(c);
        }
        for(Estado c: f.EdosAFN){
            this.EdosAFN.add(c);
        }
        for(Estado c: f.EdosAcept){
            this.EdosAcept.add(c);
        }
        
  
    }
        
    
        


}
