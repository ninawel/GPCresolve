/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resolutionequation;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author NiGhThUnTeR
 */
public class CholeskyResolutionTest {

    final int systemSize = 5;
    final int puissance = 2;
    final double margeAccepte=0.0001;
    CholeskyResolution Chol;

    public CholeskyResolutionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        Matrice M = Matrice.aleaMat(systemSize);
        M=Matrice.mult(M, M.transpose());
        Vecteur B = Vecteur.aleaVect(systemSize);
        Chol = new CholeskyResolution(M,B);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of Solve method, of class CholeskyResolution.
     */
    @Test
    public void testSolve() {
        System.out.println("Solve");
        Vecteur expResult = Chol.oldB;
        boolean bret = Chol.Solve();
        Vecteur result = new Vecteur(Chol.solution.n);
        for (int i=0;i<Chol.solution.n;i++){
            result.seti(0, i);
        }
        for (int i=0;i<Chol.solution.n;i++){
            for (int k=0;k<Chol.solution.n;k++){
                result.seti(result.geti(i)+Chol.oldMatrice.getij(i, k)*Chol.solution.geti(k), i);
            }
        }
        if (bret) assertArrayEquals(expResult.b, result.b, margeAccepte);
        else assertFalse(bret);
        
        
    }

    /**
     * Test of Solve method, of class CholeskyResolution.
     */
    @Test
    public void testSolve_int() {
        System.out.println("Solve("+puissance+")");
        Vecteur expResult = Chol.oldB;
        boolean bret = Chol.Solve(puissance);
        Vecteur result = new Vecteur(Chol.solution.n);
        for (int i=0;i<Chol.solution.n;i++){
            result.seti(0, i);
        }
        
        Matrice mPuissance = Chol.oldMatrice.puissance(puissance);
        
        for (int i=0;i<Chol.solution.n;i++){
            for (int k=0;k<Chol.solution.n;k++){
                result.seti(result.geti(i)+mPuissance.getij(i, k)*Chol.solution.geti(k), i);
            }
        }
        if (bret) assertArrayEquals(expResult.b, result.b, margeAccepte);
        else assertFalse(bret);
    }
}