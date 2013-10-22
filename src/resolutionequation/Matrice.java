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
public class Matrice {

    private int n;
    private double[][] M;

    public Matrice(int n) {
        this.n = n;
        this.M = new double[n][n];
    }

    public static Matrice Identity(int n) {
        Matrice I = new Matrice(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    I.setij(1., i, j);
                } else {
                    I.setij(0., i, j);
                }
            }
        }
        return I;
    }
    
    public static Matrice nulle(int n) {
        Matrice I = new Matrice(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                I.setij(0, i, j);
            }
        }
        return I;
    }

    public double getij(int i, int j) {
        if (i < n && j < n && i >= 0 && j >= 0) {
            return M[i][j];
        }
        return 0.;
    }

    public void setij(double val, int i, int j) {
        if (i < n && j < n) {
            this.M[i][j] = val;
        }
    }

    public int getSize() {
        return n;
    }

    public Matrice SousMatrice(int d) {
        Matrice Mret = new Matrice(d);
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                Mret.setij(this.getij(i, j), i, j);
            }
        }
        return Mret;
    }

    public void PermuteLigne(int i, int j) {
        Vecteur Bi = new Vecteur(n);
        Vecteur Bj = new Vecteur(n);
        Bi = this.getLigne(i);
        Bj = this.getLigne(j);
        setLigne(Bi, j);
        setLigne(Bj, i);
    }
    
    public void PermuteElement(int i1,int j1,int i2,int j2){
        double c = this.getij(i2, j2);
        this.setij(getij(i1, j1), i2, j2);
        this.setij(c,i1,j1);
    }
    
    

    public Vecteur getLigne(int i) {
        Vecteur B = new Vecteur(n);
        for (int k = 0; k < n; k++) {
            B.seti(getij(i, k), k);
        }
        return B;
    }

    public void setLigne(Vecteur V, int i) {
        for (int k = 0; k < n; k++) {
            this.setij(V.geti(k), i, k);
        }
    }

    public Matrice getCopy() {
        Matrice Mret = new Matrice(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Mret.setij(getij(i, j), i, j);
            }
        }
        return Mret;
    }

    public static Matrice mult(Matrice A, Matrice B) {
        int t = A.getSize();
        Matrice Mret = new Matrice(t);
        for (int k = 0; k < t; k++) {
            for (int l = 0; l < t; l++) {
                Mret.setij(0., k, l);
                for (int i = 0; i < t; i++) {
                    Mret.setij(Mret.getij(k, l) + A.getij(k, i) * B.getij(i, l), k, l);
                }
            }
        }
        return Mret;
    }

    public Matrice puissance(int n) {
        Matrice Mret = this.getCopy();
        if (n <= 0) {
            return Identity(this.getSize());
        } else {
            while (n > 1) {
                Mret = mult(Mret, this);
                n--;
            }
        }
        return Mret;
    }

    public boolean equals(Matrice M) {
        if (!Arrays.deepEquals(this.M, M.M)) {
            return false;
        }
        return true;
    }

    public Matrice transpose() {
        Matrice Mret = new Matrice(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Mret.setij(getij(j, i), i, j);
            }
        }
        return Mret;
    }

    public boolean isSymetric() {
        if (this.equals(this.transpose())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String S = "";
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                S += this.getij(i, j) + "\t";
            }
            S += "\n";
        }
        return S;
    }

    public static Matrice aleaMat(int n) {
        Matrice mRet = new Matrice(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mRet.setij(Math.random() * 2, i, j);
            }
        }
        return mRet;
    }
}
