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
public class ClassEstadoAnalizLexico {
    char caracterActual;
    int edoActual;
    int edoTransicion;
    int finLexema;
    int iniLexema;
    int indiceCaracterActual;
    String lexema;
    boolean pasoPorEdoAcept;
    int token;
    Stack<Integer> pila = new Stack<Integer>();
}
