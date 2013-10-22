/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resolutionequation;

import java.util.ArrayList;

/**
 *
 * @author NiGhThUnTeR
 */
public class LUResolution extends Resolution {
    Matrice oldMatrice;
    Vecteur oldB;
    Matrice L;
    Matrice U;
    Vecteur P; // representation de la matrice permutation


    public LUResolution(Matrice M, Vecteur b) {
        this.oldMatrice = M.getCopy();
        this.solution = b.getCopy();
        this.oldB=b.getCopy();
        this.U = M.getCopy();
        this.L = Matrice.Identity(U.getSize());
        this.P = new Vecteur(U.getSize());
    }

    
    private void parRemonte() {
        int n = U.getSize();
        for (int k = n - 1; k >= 0; k--) {
            for (int l = n - 1; l > k; l--) {
                solution.seti(solution.geti(k) - solution.geti(l) * U.getij(k, l), k);
            }
            solution.seti(solution.geti(k) / U.getij(k, k), k);
        }
    }

    private void parDescente() {
        int n = L.getSize();
        for (int k = 0; k <= n - 1; k++) {
            for (int l = 0; l < k; l++) {
                solution.seti(solution.geti(k) - solution.geti(l) * L.getij(k, l), k);
            }
            solution.seti(solution.geti(k) / L.getij(k, k), k);
        }
    }
    
    private void PermuteB(){
        for (int i=0;i<U.getSize();i++){
            if (i!=P.geti(i)) solution.Permute(i, (int)P.geti(i));
        }
    }

   
    public boolean Solve() {
        // Echelonnement de la matrice
        ArrayList<Vecteur> TraceDePermutations = new ArrayList<Vecteur>();
        
        int n = U.getSize();
        for (int i=0;i<n;i++){
            P.seti(i, i);
        }
        int ligneAdr = 0;
        for (int i = 0; i < n; i++) {
            if (U.getij(i, i) == 0) {
                ligneAdr = -1;
                int k = i + 1;
                while (k < n && ligneAdr == -1) {
                    if (U.getij(k, i) != 0) {
                        ligneAdr = k;
                    } else {
                        k++;
                    }
                }
                if (ligneAdr == -1) {
                    return false;
                }
             

                U.PermuteLigne(i, ligneAdr);
                solution.Permute(i, ligneAdr);
                P.Permute(ligneAdr, i);
                
            }
            else ligneAdr=i;
            Vecteur V=new Vecteur(2);
            V.seti(i, 0);
            V.seti(ligneAdr, 1);
            TraceDePermutations.add(V.getCopy());
            
            for (int l = i + 1; l < n; l++) {
                Vecteur Vi = U.getLigne(i);
                Vecteur Vl = U.getLigne(l);
                double facteur = Vl.geti(i) / Vi.geti(i);
                Vi.Mult(-facteur);
                Vl.add(Vi);
                U.setLigne(Vl, l);
                L.setij(facteur, l, i);
                solution.seti(solution.geti(l) - facteur * solution.geti(i), l);
            }
        }
  
        //ajuster les Permutations pour regler les E'
        for (int i=0;i<n-2;i++){
            for (int j=i+1;j<n-1;j++){
                L.PermuteElement((int) Math.round(TraceDePermutations.get(j).geti(0)), i, (int) Math.round(TraceDePermutations.get(j).geti(1)), i);
            }   
        }
        //--------------------------------------------
        parRemonte();
        
        return true;
    }

    public boolean Solve(int n) {
        boolean Bret = false;
        Bret = Solve();
        if (Bret == false) {
            return false;
        }
        n = n - 1;
        while (n >= 1) {
            PermuteB();
            parDescente();
            parRemonte();
            n = n - 1;
        }

        return true;
    }
    
           public Vecteur getSolution() {
        return solution;
    }
    
    
}
