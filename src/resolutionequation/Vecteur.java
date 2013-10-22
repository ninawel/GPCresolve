/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resolutionequation;

import java.util.Arrays;

/**
 *
 * @author NiGhThUnTeR
 */
public class Vecteur {
    int n;
    double[] b;

    public Vecteur(int n) {
        this.n = n;
        b = new double[n];
    }

    public double geti(int i) {
        return b[i];
    }

    public void seti(double val, int i) {
        b[i] = val;
    }

    public void Mult(double val) {
        for (int k = 0; k < n; k++) {
            seti(val * geti(k), k);
        }
    }

    public void Permute(int i, int j) {
        double c = geti(i);
        seti(geti(j), i);
        seti(c, j);
    }
    
    public Matrice getPermuteFromVect(){
        Matrice Mret=Matrice.nulle(this.getSize());
        for (int i=0;i<this.getSize();i++){        
            Mret.setij(1,(int) Math.round(this.geti(i)),i);
        }
        
        return Mret;
    }

    public void add(Vecteur V) {
        for (int k = 0; k < n; k++) {
            seti(V.geti(k) + geti(k), k);
        }
    }

    public int getSize() {
        return n;
    }

    public Vecteur getCopy() {
        Vecteur Vret = new Vecteur(n);
        for (int i = 0; i < n; i++) {
            Vret.seti(geti(i), i);
        }
        return Vret;
    }

    @Override
    public String toString() {
        String S = "";
        for (int i = 0; i < getSize(); i++) {
            S += this.geti(i) + "\t";
            S += "\n";
        }
        return S;
    }
    
    public static Vecteur aleaVect(int n) {
        Vecteur vRet = new Vecteur(n);
        for (int i = 0; i < n; i++) {
                vRet.seti(Math.random() * 2, i);
            
        }
        return vRet;
    }

    

    
    
}
