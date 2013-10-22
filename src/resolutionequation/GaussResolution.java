/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resolutionequation;

/**
 *
 * @author NiGhThUnTeR
 */
public class GaussResolution extends Resolution{
    Matrice oldMatrice;
    Vecteur oldB;
    Matrice U;

    public GaussResolution(Matrice M, Vecteur b) {
        this.oldMatrice=M.getCopy();
        this.U = M.getCopy();
        this.solution = b.getCopy();
        this.oldB=b.getCopy();
    }

    public boolean Solve(){
        // Echelonnement de la matrice avec la methode du pivot partiel
        int n = U.getSize();
        for (int i = 0; i < n; i++) {
            if (U.getij(i, i) == 0) {
                int ligneAdr = -1;
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
            }
            for (int l = i + 1; l < n; l++) {
                Vecteur Vi = U.getLigne(i);
                Vecteur Vl = U.getLigne(l);
                double facteur = Vl.geti(i) / Vi.geti(i);
                Vi.Mult(-facteur);
                Vl.add(Vi);
                U.setLigne(Vl, l);
                solution.seti(solution.geti(l) - facteur * solution.geti(i), l);
                //nawel idea for student use------------------------------------
                //System.in.read();
                //System.out.println(U+"\n\n");
                //nawel idea for student use------------------------------------
            }
        }
        // Méthode par remonté
        for (int k = n - 1; k >= 0; k--) {
            for (int l = n - 1; l > k; l--) {
                solution.seti(solution.geti(k) - solution.geti(l) * U.getij(k, l), k);
            }
            solution.seti(solution.geti(k) / U.getij(k, k), k);
        }
        return true;
    }
    
    public boolean Solve(int n){
        boolean Bret=false;
        while (n>=1){
            Bret=Solve();
            if (Bret==false) return false;
            this.U=this.oldMatrice.getCopy();
            n--;
        }
        return Bret;
    }
    
        public Vecteur getSolution() {
        return solution;
    }
    
    
}
