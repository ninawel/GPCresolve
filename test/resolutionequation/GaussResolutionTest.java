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
public class GaussResolutionTest {
    final int systemSize = 7;
    final int puissance = 4;
    final double margeAccepte=0.0001;
    GaussResolution G;
    
    public GaussResolutionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        G = new GaussResolution(Matrice.aleaMat(systemSize),Vecteur.aleaVect(systemSize));
       
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Solve method, of class GaussResolution.
     */
    @Test
    public void testSolve() {
        System.out.println("Solve");
        Vecteur expResult = G.oldB;
        boolean bret = G.Solve();
        Vecteur result = new Vecteur(G.solution.n);
        for (int i=0;i<G.solution.n;i++){
            result.seti(0, i);
        }
        for (int i=0;i<G.solution.n;i++){
            for (int k=0;k<G.solution.n;k++){
                result.seti(result.geti(i)+G.oldMatrice.getij(i, k)*G.solution.geti(k), i);
            }
        }
        if (bret) assertArrayEquals(expResult.b, result.b, margeAccepte);
        else assertFalse(bret);
        
        
    }

    /**
     * Test of Solve method, of class GaussResolution.
     */
    @Test
    public void testSolve_int() {
        System.out.println("Solve("+puissance+")");
        Vecteur expResult = G.oldB;
        boolean bret = G.Solve(puissance);
        Vecteur result = new Vecteur(G.solution.n);
        for (int i=0;i<G.solution.n;i++){
            result.seti(0, i);
        }
        
        Matrice mPuissance = G.oldMatrice.puissance(puissance);
        
        for (int i=0;i<G.solution.n;i++){
            for (int k=0;k<G.solution.n;k++){
                result.seti(result.geti(i)+mPuissance.getij(i, k)*G.solution.geti(k), i);
            }
        }
        if (bret) assertArrayEquals(expResult.b, result.b, margeAccepte);
        else assertFalse(bret);
    }
    
}