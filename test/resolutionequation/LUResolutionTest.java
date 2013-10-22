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
public class LUResolutionTest {
    final int systemSize = 8;
    final int puissance = 4;
    final double margeAccepte=0.0001;
    LUResolution LU;
    public LUResolutionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        LU = new LUResolution(Matrice.aleaMat(systemSize),Vecteur.aleaVect(systemSize));
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Solve method, of class LUResolution.
     */
  

    /**
     * Test of Solve method, of class LUResolution.
     */
    @Test
    public void testSolve_int() {
        System.out.println("LUSolve("+puissance+")");
        Vecteur expResult = LU.oldB;
        boolean bret = LU.Solve(puissance);
        Vecteur result = new Vecteur(LU.solution.n);
        for (int i=0;i<LU.solution.n;i++){
            result.seti(0, i);
        }
        
        Matrice mPuissance = LU.oldMatrice.puissance(puissance);
        
        for (int i=0;i<LU.solution.n;i++){
            for (int k=0;k<LU.solution.n;k++){
                result.seti(result.geti(i)+mPuissance.getij(i, k)*LU.solution.geti(k), i);
            }
        }
        if (bret) assertArrayEquals(expResult.b, result.b, margeAccepte);
        else assertFalse(bret);
    }
}