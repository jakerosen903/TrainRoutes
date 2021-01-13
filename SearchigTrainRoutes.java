import java.util.*;
import java.io.*;

public class SearchigTrainRoutes
{
   public static void main(String[] args) throws FileNotFoundException
   {
      Long start = System.currentTimeMillis();
      
      FindPath find = new FindPath("Ciudad Juarez", "Montreal"); 
      Long end = System.currentTimeMillis(); 
     // System.out.println(end - start);
      
   }
}
class FindPath
{

   public static HashMap<String, Integer> cities = new HashMap();
   
   public static ArrayList<Node> nodes = new ArrayList<>(); 
   
   public static HashMap<Integer, Node> nodesMap = new HashMap<>();
   
   public int goal = 0;
   
   public static Node goalNode = null; 
   
   public FindPath(String c1, String c2) throws FileNotFoundException
   {
   
      Scanner scan = new Scanner(new File("rrNodes.txt"));
      
      File file = new File("rrNodeCity.txt");
      
      
      
      while(scan.hasNextLine())
      {
      
         int i = scan.nextInt(); 
      
         Node node = new Node(i);
         nodes.add(node);
         nodesMap.put(i, node);  
      
      
         node.setCoordinates(scan.nextDouble(), scan.nextDouble());  //possibly y coordinate x cooridnate
      
         scan.nextLine();
      
      
      }
      
      cities = createCityMap(file);
      
      
      int city1 = cities.get(c1);
      int city2 = cities.get(c2);
      goal = city2; 
      
      goalNode = nodesMap.get(city2);
      
      long start = System.currentTimeMillis();
      genGraph(nodes.get(0));
      long end = System.currentTimeMillis();
      
      Double time = (end - start) / 1000.0; 
      System.out.println("\nTime to generate the data structure " + time + " seconds");
      
      start = System.currentTimeMillis();
      dijSearch(nodesMap.get(city1)); 
      end = System.currentTimeMillis();
      
      time = (end - start) / 1000.0; 
      
      System.out.println("\nFound by Dijkstra: " + nodesMap.get(city2).getDistance() + " in " + time + " seconds");

      start = System.currentTimeMillis();
      aSearch(nodesMap.get(city1));
      end = System.currentTimeMillis();
      
      time = (end - start) / 1000.0; 
   
      System.out.println("\nFound by ASearch: " + nodesMap.get(city2).getDistance() + " in " + time + " seconds\n");
   
      System.out.println(nodes.get(30).toString() + " " + nodes.get(30).getXCord() + " "+ nodes.get(30).getYCord());
   
   }
   
   public HashMap<String, Integer> createCityMap(File file) throws FileNotFoundException
   {
   
      Scanner scan = new Scanner(file);
      
      HashMap<String, Integer> cityMap = new HashMap();
   
      int num = 0; 
      String name = "";
   
      while(scan.hasNext())
      {
      
         num = scan.nextInt(); 
         name = scan.nextLine(); 
         name = name.trim();
         
         cityMap.put(name, num); 
         
         nodesMap.get(num).setName(name); 
      
      }
      
      return cityMap; 
   
   }
 
   public ArrayList<Node> genGraph(Node n) throws FileNotFoundException
   {
      HashSet<Integer> explored = new HashSet<>();
      Scanner scan = new Scanner(new File("rrEdges.txt"));
   
      int edge1 = 0; 
      int edge2 = 0; 
   
      while(scan.hasNextInt())
      { 
      
         edge1 = scan.nextInt();
         edge2 = scan.nextInt(); 
      
         nodesMap.get(edge1).addConnection(edge2); 
         nodesMap.get(edge2).addConnection(edge1);              
      
      }
   
      return nodes; 
   
   }
   
   public Node dijSearch(Node n)
   {
   
      n.setDistance(0); 
      PriorityQueue<Node> queue = new PriorityQueue<>(new dijComp());
      queue.add(n); 
      
      Node current = null;
      
      while(queue.size() > 0)
      {
         current = queue.poll(); 
      
         ArrayList<Node> children = current.getChildren();
         children = adjustDistance(children, current, 0);
         
         queue.addAll(children);
      
      
      }
      
      return current; 
   }
   
   public Node aSearch(Node n)
   {
      
      n.setDistance(0); 
      //set distance to 0 so that it can search from the "n" node
      PriorityQueue<Node> queue = new PriorityQueue<>(new aComp());
      
      //a* comparator
      queue.add(n); 
      
      Node current = null;
      
      while(queue.size() > 0)
      {
         current = queue.poll(); 
         ArrayList<Node> children = current.getChildren();
         children = adjustDistance(children, current, 1); 
      
         queue.addAll(children);
      
      
      }
      
      return null;  
   
   }

   public static Node findNode(int find)
   {
   
      for(Node n : nodes)
      {
      
      
         if(n.getID() == find)
            return n; 
      
      }
   
      return null; 
   }
   
   public ArrayList<Node> adjustDistance(ArrayList<Node> c, Node n, int search)
   {
      ArrayList<Node> newChildren = new ArrayList<Node>(); 
      //Dij
      if(search == 0)
      {
         for(Node child: c)
         {
            if(child.getID() == goal)
            {
            
            //   System.out.println("found"); 
            }
         //compares previous distance to an updated distance of the predecessor + distance between them
            if(child.getDistance() > n.getConnections().get(child) + n.getDistance())
            {
               child.setDistance(n.getConnections().get(child) + n.getDistance()); 
               child.setPred(n); 
               newChildren.add(child);
            }

         }
      }
      if(search == 1)
      {
      
      for(Node child: c)
         {
            if(child.getID() == goal)
            {
            
               //System.out.println("found"); 
            }
         //compares previous distance to an updated distance of the predecessor + distance between them
         //It is the same as djikstra except it also factors in distance to goal node
            if(child.getDistance() + child.getDistanceGoal() > n.getConnections().get(child) + n.getDistance() + n.getDistanceGoal())
            {
            
               child.setDistance(n.getConnections().get(child) + n.getDistance()); 
               child.setPred(n); 
               newChildren.add(child);
            }
         }
      }
   
      return newChildren;
   }

}


class Node
{

   int id = 0; 
   ArrayList<Character> listData = new ArrayList<>();
   String cityName = null;
   
   HashMap<Node, Double> connections = new HashMap<>(); 
   
   private Node pred = null; 
    
   private ArrayList<Node> children = new ArrayList<>(); 
   
   private double distance = Double.MAX_VALUE; 
   
   private double xCord = 0;
   private double yCord = 0;
   
   private double goalDistance = 0; 
   
   public Node(int idNum) 
   {
   
      this.id = idNum;
      
      if(FindPath.cities.containsKey(id))
         cityName = "" + FindPath.cities.get(id);
      else
         cityName = "" + idNum; 
         
      if(distance > 0)
      {
      
        // goalDistance = findDistance(FindPath.goalNode);
      
      }
            
   }

   public double getDistance()
   {
   
      return distance; 
   
   }
   
   public void setDistance(double d)
   {
   
      distance = d; 
   
   }
   public void addConnection(int i)
   {
   
      Node n = FindPath.nodesMap.get(i);
   
      double d = findDistance(n);
    
      connections.put(n, d);
      children.add(n); 
   
   }
   public HashMap<Node, Double> getConnections()
   {
   
      return connections; 
   
   }
      
   public ArrayList<Node> getChildren()
   {
   
      return children; 
   
   }
   public int getID()
   {
   
      return id; 
   
   }
   
   public void setName(String s)
   {
      cityName = s; 
   
   }
   public void setCoordinates(double x, double y)
   {
   
      this.xCord = x; 
      this.yCord = y; 
   
   }
 
   public void setPred(Node n)
   {
   
      pred = n;
   
   }
   
   public Node getPred()
   {
   
      return pred;
   
   }
   public void setDistance(int i) {
   
      this.distance = i;
      
   }
   
   public double getXCord()
   {
   
      return xCord;
   
   }
   
   public double getYCord()
   {
   
      return yCord;
   
   }
   
   public double findDistance(Node n)
   {//null pointer because it searches for a goal node not yet created
   
      return GreatCircle.getDistance(xCord, yCord, n.getXCord(), n.getYCord());
   
   }
 
   public double getDistanceGoal()
   {
   
      return findDistance(FindPath.goalNode); 
       
   }
   public String toString()
   {
      
         
      return cityName;
   
   }
   
   

}
class dijComp implements Comparator<Node>
{
   
   public int compare(Node n1, Node n2)
   {
   
      return (int) (n1.getDistance() - n2.getDistance()); 
    
   }
}

class aComp implements Comparator<Node>
{


   public int compare(Node n1, Node n2)
   {
   
      return (int)((n1.getDistance() + n1.getDistanceGoal()) - (n2.getDistance() + n2.getDistanceGoal())); 
   
   }

}