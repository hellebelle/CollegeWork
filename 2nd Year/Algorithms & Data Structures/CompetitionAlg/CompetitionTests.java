import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
/*	@author: Anna Maliakal 17324430
 *  Q1. Justify the choice of the data structures used in CompetitionDijkstra and
 *     CompetitionFloydWarshall
 *  A1.A vertex class was created in CompetitionDijkstra to keep track of the connected vertices and therefore keep track of the edges 
 *	   that belonged to each vertex. This was also an easy way to keep track of the weights and if each vertex was settled or not.
 *     For CompetitionFloydWarshall no specific data structure was created however a 2d array was used to keep track of the weights.

 *  Q2. Explain theoretical differences in the performance of Dijkstra and Floyd-Warshall algorithms
 *  in the given problem. Also explain how would their relative performance be affected by the
 *  density of the graph. Which would you choose in which set of circumstances and why?
 *  A2. Dijkstra's algorithm is a single-source shortest path algorithm. The algorithm computes the shortest path from a chose source node
 *		to every other node in the graph. Floyd-Warshalls algorithm is used when any of all the nodes can be a source,so you want the shortest distance to reach any destination node from any source node.
 *      This only fails when there are negative cycles.
 *		Floyd-Warshall runs in O(V3) time.
 *		Dijkstra’s Algorithm runs in O(E log V) time.

 */
public class CompetitionTests {
    @Test
    public void testDijkstraConstructor(){
    	CompetitionDijkstra dijkstra = new CompetitionDijkstra("tinyEWD.txt", 50, 80, 60);
    	assertEquals(38, dijkstra.timeRequiredforCompetition());
    }

    @Test
    public void testFWConstructor(){
    	CompetitionFloydWarshall floydWarshall = new CompetitionFloydWarshall("tinyEWD.txt", 50, 80, 60);
    	assertEquals(38, floydWarshall.timeRequiredforCompetition());
    }
    
    @Test
    public void testNegativeWalker(){
    	CompetitionDijkstra dijkstra = new CompetitionDijkstra("tinyEWD.txt", -2, 10, 20);
    	assertEquals(-1, dijkstra.timeRequiredforCompetition());
    	CompetitionFloydWarshall floydWarshall = new CompetitionFloydWarshall("tinyEWD.txt", -2, 10, 20);
    	assertEquals(-1, floydWarshall.timeRequiredforCompetition());
    }
    
    @Test
    public void testEmpty(){
    	CompetitionDijkstra dijkstra = new CompetitionDijkstra("inputA.txt", 50, 80, 60);
    	assertEquals(-1, dijkstra.timeRequiredforCompetition());
    	CompetitionFloydWarshall floydWarshall = new CompetitionFloydWarshall("inputA.txt", 50, 80, 60);
    	assertEquals(-1, floydWarshall.timeRequiredforCompetition());
    }
    
    @Test
    public void testSingleNode(){
    	CompetitionDijkstra dijkstra = new CompetitionDijkstra("inputB.txt", 50, 80, 60);
    	assertEquals(-1, dijkstra.timeRequiredforCompetition());
    	CompetitionFloydWarshall floydWarshall = new CompetitionFloydWarshall("inputB.txt", 50, 80, 60);
    	assertEquals(-1, floydWarshall.timeRequiredforCompetition());
    }
    
    @Test
    public void testNoFile(){
    	CompetitionDijkstra dijkstra = new CompetitionDijkstra("", 50, 80, 60);
    	assertEquals(-1, dijkstra.timeRequiredforCompetition());
    	CompetitionFloydWarshall floydWarshall = new CompetitionFloydWarshall("", 50, 80, 60);
    	assertEquals(-1, floydWarshall.timeRequiredforCompetition());
    }

    @Test
    public void testDisconnectedGraph(){
    	CompetitionDijkstra dijkstra = new CompetitionDijkstra("inputC.txt", 50, 80, 60);
    	assertEquals(-1, dijkstra.timeRequiredforCompetition());
    	CompetitionFloydWarshall floydWarshall = new CompetitionFloydWarshall("inputC.txt", 50, 80, 60);
    	assertEquals(-1, floydWarshall.timeRequiredforCompetition());
    }
}