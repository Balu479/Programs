//Project: ShortestPathFinderApp
//Name: Balakrishna Uppathi
import java.util.*;
public class ShortestPathFinderApp {
	AdjacencyList aL;
	int visitCount;
	public static void main(String args[])
	{
		ShortestPathFinderApp sPFA=new ShortestPathFinderApp();
		if(args.length==2) {
			String file=args[0];
			String rootNode=args[1];
			sPFA.aL = new AdjacencyList(file);
			Node node=new Node(rootNode);
			sPFA.setUpCosts(node);
		}
		else
			System.out.println("Oops...Please Provide the fileName and rootNode");
	}
	public void setUpCosts(Node node)
	{
		for (Node node1 : aL.adjacency.keySet())
		{
			if(node1.getAddr().equals(node.getAddr())) {
				node1.setCost(0);
				node1.setParent(node);
			}
			else {
				node1.setCost(Integer.MAX_VALUE);
				node1.setParent(null);	
			}
		}
		executeAlgo(node);
	}	
	public void executeAlgo(Node node)
	{
		for (Node node1 : aL.adjacency.keySet())
		{
			if(!node1.getVisitStatus()) {
				List<Edge> list = aL.adjacency.get(node1);
				for (ListIterator<Edge> it = list.listIterator(); it.hasNext();)
				{
					Edge edge = it.next();
					if(edge.start.getAddr().equals(node.getAddr()))
					{
						edge.end.setParent(edge.start);
						int newCost=edge.start.getCost()+edge.weight;
						if(newCost<edge.end.getCost())
							edge.end.setCost(newCost);
						edge.start.setVisitStatus(true);
					}
				}
			}
		}	
		findMinCostNode(node);
	}
	public void findMinCostNode(Node node)
	{
		
		visitCount++;
		int minDist=Integer.MAX_VALUE;Node minNode=null;
		for (Node node1 : aL.adjacency.keySet())
		{
			if(!node1.getVisitStatus()) {
				
				if(node1.getCost()<=minDist) {
					minDist=node1.getCost();
					minNode=node1;
					}
				}
			else
				if(visitCount>=aL.nodeMap.size()) {
					printRoutingTable(node);
				}
		}
		executeAlgo(minNode);
		
	}
	public void printRoutingTable(Node node) {
		System.out.println("Destination\tCost\tNextHop");
		System.out.println("-----------\t----\t-------");
		int minNextHop=Integer.MAX_VALUE; Node nextHop=null;
		for (Node node1 : aL.adjacency.keySet())
		{
			if((node1.getCost()<=minNextHop) && (node1.getCost()!=0)) {
				minNextHop=node1.getCost();
				nextHop=node1;
			}
		}
		for (Node node1 : aL.adjacency.keySet())
		{
			if(((node1.getCost())==0) || (node1.getCost()==minNextHop))
				System.out.println("    "+node1.getAddr()+"\t\t  "+node1.getCost()+"\t  "+"-");
			else
				System.out.println("    "+node1.getAddr()+"\t\t  "+node1.getCost()+"\t  "+nextHop.getAddr());
				
		}	
		System.exit(0);
	}
}
