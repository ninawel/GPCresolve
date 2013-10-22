/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resolutionequation;

/**
 *
 * @author NiGhThUnTeR
 */
public class CholeskyResolution extends Resolution{

    Matrice oldMatrice;
    Vecteur oldB;
    Matrice C;
    Matrice tC;

    public CholeskyResolution(Matrice M, Vecteur b) {
        this.oldMatrice = M.getCopy();
        this.solution = b.getCopy();
        this.oldB = b.getCopy();
        this.C = Matrice.nulle(M.getSize());
        this.tC = Matrice.nulle(M.getSize());
    }
    
    private void parRemonte() {
        int n = tC.getSize();
        for (int k = n - 1; k >= 0; k--) {
            for (int l = n - 1; l > k; l--) {
                solution.seti(solution.geti(k) - solution.geti(l) * tC.getij(k, l), k);
            }
            solution.seti(solution.geti(k) / tC.getij(k, k), k);
        }
    }

    private void parDescente() {
        int n = C.getSize();
        for (int k = 0; k <= n - 1; k++) {
            for (int l = 0; l < k; l++) {
                solution.seti(solution.geti(k) - solution.geti(l) * C.getij(k, l), k);
            }
            solution.seti(solution.geti(k) / C.getij(k, k), k);
        }
    }

    public boolean Solve() {
        if (!oldMatrice.isSymetric()) {
            return false;
        }
        double s = 0;
        for (int i = 0; i < oldMatrice.getSize(); i++) {
            for (int j = 0; j < i; j++) {
                s = 0;
                for (int k = 0; k < i; k++) {
                    s += C.getij(i, k) * C.getij(j, k);
                }
                C.setij(((oldMatrice.getij(j, i)) - s) / C.getij(j, j), i, j);
            }
            
            s = 0;
            for (int k = 0; k < i; k++) {
                s += C.getij(i, k) * C.getij(i, k);
            }
            if (((oldMatrice.getij(i, i)) - s)<=0) return false;
            C.setij(Math.sqrt((oldMatrice.getij(i, i)) - s), i, i);
        }
        tC=C.transpose();
        this.parDescente();
        this.parRemonte();
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
