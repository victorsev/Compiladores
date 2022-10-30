/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores;

import java.util.HashSet;
import java.util.Stack;

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
        //los estados de aceptacion del primer AFN es el inicial del segundo
        for(Estado e: this.EdosAcept){
            e=f2.EdoIni;
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
        //Se agrengan los estados del segundo AFN
        for(Estado e: f2.EdosAFN){
            if(e.IdEstado!=f2.EdoIni.IdEstado){
                this.EdosAFN.add(e);
            }          
        }
        
        
        return this;
    }
    
    public AFN cerraduraSuma(){
        //nuevo estado inicial
        Estado e1=new Estado();
        //nuevo estado final
        Estado e2=new Estado();
        //el 07 en ASCII se tomara como epsilon
        //se recorre el estado inical a e1, con tranciciones epsilon
        Transicion t1=new Transicion(c.EPSILON, this.EdoIni);
        e1.Trans.add(t1);
        //e1 se hace estado inicial
        this.EdoIni=e1;
        // se hacen las traciciones de los estados de aceptacion es e2 con tranciciones epsilon
        //se hace la tracicion al estado inicial con epsilon
        for(Estado e: this.EdosAcept){
            e.Trans.add(new Transicion(c.EPSILON, e2));
            e.Trans.add(new Transicion(c.EPSILON, this.EdoIni));
            e.EdoAcept=false;
        }
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
        //el 07 en ASCII se tomara como epsilon
        //se recorre el estado inical a e1, con tranciciones epsilon
        Transicion t1=new Transicion(c.EPSILON, this.EdoIni);
        e1.Trans.add(t1);
        //e1 se hace estado inicial
        this.EdoIni=e1;
        // se hacen las traciciones de los estados de aceptacion es e2 con tranciciones epsilon
        //se hace la tracicion al estado inicial con epsilon
        for(Estado e: this.EdosAcept){
            e.Trans.add(new Transicion(c.EPSILON, e2));
            e.Trans.add(new Transicion(c.EPSILON, this.EdoIni));
            e.EdoAcept=false;
        }
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
        //el 07 en ASCII se tomara como epsilon
        //se recorre el estado inical a e1, con tranciciones epsilon
        Transicion t1=new Transicion(c.EPSILON, this.EdoIni);
        e1.Trans.add(t1);
        //e1 se hace estado inicial
        this.EdoIni=e1;
        // se hacen las traciciones de los estados de aceptacion es e2 con tranciciones epsilon
        for(Estado e: this.EdosAcept){
            e.Trans.add(new Transicion(c.EPSILON, e2));
            e.EdoAcept=false;
        }
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
}