/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

/**
 *
 * @author skip
 */
public class Case {

    private int ligne;
    private int colonne;
    public NatureTerrain nature;
    
    public Case(int l, int c, NatureTerrain nat) {
        this.ligne = l;
        this.colonne = c;
        this.nature = nat;
    }
    
    
}
