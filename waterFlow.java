import java.io.*;
import java.util.*;
import java.util.Arrays;

public class waterFlow
{
   public static class Node
   {		
		boolean Listed = false;
		boolean Explored = false;
		int pathcost;
		int edgetime;
		String name;
   }
   
   public static class solution
   {
	   String goalnode;
	   int timetaken;
	   
   }
	
   public static class Edge
	{
		String From;
		String To;
		int cost;
		int offtimes;
		String[] timingsA;
		int[] timings;	
	}
	
	public static class psort implements Comparator<Node>
   {
	   public int compare(Node A , Node B)
	   {
		   if(A.pathcost == B.pathcost)
		   {
			   return A.name.compareTo(B.name);
		   }
		   else
		   {
		   return A.pathcost-B.pathcost;
		   }
	   }
   }
	
	public static class Nsort implements Comparator<Node>
	   {
		   public int compare(Node A , Node B)
		   {
			   return A.name.compareTo(B.name);
		   }
	   }
	
	public static Comparator<Edge> edgeComparator = new Comparator<Edge>() {
		public int compare(Edge E1, Edge E2) {
			return E1.cost - E2.cost;
		}
	};
	
	public static int min(int a, int b)
	{
		if(a<b)return a;
		else return b;
	}
	
	public static Node[] Goals; 
	public static Node Source = new Node();
	public static Node[] IntNodes;
	public static int infinity = 9999999;
	
	public static Edge createedge(String x)
	{
		Edge n = new Edge();
		String[] cur = x.split(" ");
		n.From = cur[0];
		n.To = cur[1];
		n.cost = Integer.parseInt(cur[2]);
		n.offtimes = Integer.parseInt(cur[3]);
		
		int count = 0;
	    if(n.offtimes > 0)
		{    
	        String[] currArray = new String[n.offtimes];
				for(int i=0;i<n.offtimes;i++)
			{
				currArray[i] = cur[i+4];
			}
			n.timingsA = currArray;
		}
		if(n.timingsA != null)
		{
		for(int u=0;u<n.timingsA.length;u++)
		{
			String ctime = n.timingsA[u];
			String[] ctarray = ctime.split("-");
			int p1 = Integer.parseInt(ctarray[0]);
			int p2 = Integer.parseInt(ctarray[1]);
			count = count + p2 - p1 + 1;
		}
		}
		
		n.timings = new int[count];
		
	    int r=0;
	    if(n.timingsA != null)
	    {
	    	
	    for(int i=0;i<n.timingsA.length;i++)
	    {
	    	String currtimings = n.timingsA[i];
			String[] carray = currtimings.split("-");
			int a = Integer.parseInt(carray[0]);
			int b = Integer.parseInt(carray[1]);
			
	    	while(a<=b)
	    	{
	    		n.timings[r] = a;
	    		a++;
	    		r++;
	    	}
	    }
	    }
	    
	    Arrays.sort(n.timings);
		   
		return n;
	}
	
		
/*---------------------------------------------------------------------------------------------------*/		
   
		public static solution bfs(Edge[] edges)
		{
		    solution cursol = new solution();
		    		    
		    LinkedList <Node> Openlist = new LinkedList<Node>();//the frontier
		    
		    Node[] AllNodes = new Node[Goals.length + IntNodes.length];
			System.arraycopy(Goals, 0, AllNodes, 0, Goals.length);
			System.arraycopy(IntNodes, 0, AllNodes, Goals.length, IntNodes.length);
		    
		    
		   	Nsort Np = new Nsort();
		    PriorityQueue <Node> tmp = new PriorityQueue <Node>(10, Np);
		    
		    Source.Listed = true;
		    
		    for(int i =0;i<edges.length;i++)
		    {
		    	if(edges[i].From.equals(Source.name))
		    	{
		    		for(int j=0;j<AllNodes.length;j++)
		    		{
		    			if(AllNodes[j].name.equals(edges[i].To))
		    			{
		    				AllNodes[j].pathcost = Source.pathcost + 1;
		    				AllNodes[j].Listed = true;
		    				tmp.offer(AllNodes[j]);
		    			}
		    		}
		    		
		    	}
		    }
		    
		    Node ff = new Node();
		    while(!tmp.isEmpty())
			   {
				ff = tmp.poll();
				Openlist.addLast(ff);   
		       }
		   
		    while(!Openlist.isEmpty())
		   {
			   Node curr = new Node();
			   curr = Openlist.poll();
			   	   
			   for(int i=0;i<Goals.length;i++)
			   {
				   if(curr.name.equals(Goals[i].name))
				   {
					   cursol.goalnode = curr.name;
					   curr.pathcost = curr.pathcost % 24;
					   cursol.timetaken = curr.pathcost;
					   return cursol;
				   }
			   }
			   curr.Explored = true;
			  	   			   
			  for(int f=0;f<edges.length;f++)
			   {
				   if(edges[f].From.equals(curr.name))
				   {
					   for(int h=0;h<AllNodes.length;h++)
					   {
						   if(AllNodes[h].name.equals(edges[f].To))
						   {
							 
								   if(AllNodes[h].Explored != true)
								   {
							          if(AllNodes[h].Listed != true)
							          {
							        	  AllNodes[h].Listed = true;
							        	  AllNodes[h].pathcost = curr.pathcost + 1;
								      	  tmp.offer(AllNodes[h]);
							          }
								   }
							   
						   }
					   }					   
					   
				   }
			   }
			   			   
			   while(!tmp.isEmpty())
			   {
				   Openlist.addLast(tmp.poll());
				  
			   }
			   
			   
			   
			   
		   }		     
			 cursol.goalnode = "None";
			 cursol.timetaken = -1;
			 return cursol;
			 
		}
        
/*--------------------------------------------------------------------------------------------------------*/		

                
		public static void removeelement(Deque<Node> A, Node N)
	{
		Node[] X = new Node[A.size()];
		int i=0;
		while(!A.isEmpty())
		{
			X[i] = A.pop();
			i++;
		}
		for(int p=0;p<X.length;p++)
		{
			if(X[p].name.equals(N.name))
			{
				X[p] = null;
			}
		}
		for(int p=X.length-1; p>=0;p--)
		{
			if(X[p] != null)
			{
				A.push(X[p]);
			}
		}
		
	}      
		
		public static solution dfs(Edge[] edges)
		{
		    solution cursol = new solution();
		    		    
		    Deque <Node> Openlist = new ArrayDeque<Node>();//the frontier
		    Deque <Node> tmpstack = new ArrayDeque<Node>();	 
		    
		    Node[] AllNodes = new Node[Goals.length + IntNodes.length];
			System.arraycopy(Goals, 0, AllNodes, 0, Goals.length);
			System.arraycopy(IntNodes, 0, AllNodes, Goals.length, IntNodes.length);
			
		   	Nsort Np = new Nsort();
		    PriorityQueue <Node> tmp = new PriorityQueue <Node>(10, Np);
		    
		    Source.Listed = true;
		    
		    for(int i =0;i<edges.length;i++)
		    {
		    	if(edges[i].From.equals(Source.name))
		    	{
		    		for(int j=0;j<AllNodes.length;j++)
		    		{
		    			if(AllNodes[j].name.equals(edges[i].To))
		    			{
		    				AllNodes[j].pathcost = Source.pathcost + 1;
		    				AllNodes[j].Listed = true;
		    				tmp.offer(AllNodes[j]);
		    			}
		    		}
		    		
		    	}
		    }
		    
		   
		    
		    Node ff = new Node();
		    while(!tmp.isEmpty())
			   {
				ff = tmp.poll();
				tmpstack.push(ff);
				
				}
			while(!tmpstack.isEmpty())
			   {
				ff = tmpstack.pop();
				Openlist.push(ff);				  
				}	
			 
					   
		   while(!Openlist.isEmpty())
		   {
			   Node curr = new Node();
			   curr = Openlist.pop();
			   
			   			   	   
			   for(int i=0;i<Goals.length;i++)
			   {
				   if(curr.name.equals(Goals[i].name))
				   {
					   cursol.goalnode = curr.name;
					   curr.pathcost = curr.pathcost % 24;
					   cursol.timetaken = curr.pathcost;
					   return cursol;
				   }
			   }
			   curr.Explored = true;
			  	   			   
			  for(int f=0;f<edges.length;f++)
			   {
				   if(edges[f].From.equals(curr.name))
				   {
					   for(int h=0;h<AllNodes.length;h++)
					   {
						   if(AllNodes[h].name.equals(edges[f].To))
						   {
							 
								   if(AllNodes[h].Explored != true)
								   {
							          if(AllNodes[h].Listed != true)
							          {
							        	  AllNodes[h].Listed = true;
							        	  AllNodes[h].pathcost = curr.pathcost + 1;
								      	  tmp.offer(AllNodes[h]);
							          }
							          else
							          {
							        	  AllNodes[h].pathcost = curr.pathcost + 1;
							        	  removeelement(Openlist,AllNodes[h]);
										  tmp.offer(AllNodes[h]);
										  
							          }
								   }
							   
						   }
					   }
					   
				   }
			   }
			   			   
			   //sort and append tmp queue to Openlist queue
			   while(!tmp.isEmpty())
			   {
				   tmpstack.push(tmp.poll());
				 
			   }
			   while(!tmpstack.isEmpty())
			   {
				   Openlist.push(tmpstack.pop());
				 
			   }
		   
		   }		     
			 cursol.goalnode = "None";
			 cursol.timetaken = -1;
			 return cursol;
			 
		}
        
        
        
/*-------------------------------------------------------------------------------------------------*/
        
		public static solution ucs(Edge[] ed, int startt)
		{
			solution usol = new solution();
			
			psort pp = new psort();
			PriorityQueue <Node> OpenList = new PriorityQueue <Node>(20, pp);
			Source.Listed = true;
			Source.pathcost = 0;			
			Source.edgetime = startt;
			
			OpenList.add(Source);// Add the source to the frontier
			
			int start = 0; //initialize the timer
			
			//Append both types of nodes into one
			Node[] AllNodes = new Node[Goals.length + IntNodes.length];
			System.arraycopy(Goals, 0, AllNodes, 0, Goals.length);
			System.arraycopy(IntNodes, 0, AllNodes, Goals.length, IntNodes.length);
								
			while(!OpenList.isEmpty())
			{
						
				Node curr = new Node();
				curr = OpenList.poll();// Pop the front most node from the frontier
				curr.Explored = true;//mark it as explored and listed
				curr.Listed = true;
				
			    start = curr.edgetime; // set the timer 
				start = start % 24; // reset to 0 if 24
							
				for(int l=0;l<Goals.length;l++) // if the current node is a Goal node, terminate
				{
					if(curr.name.equals(Goals[l].name))
					{
						usol.goalnode = curr.name;
						usol.timetaken = start;
						return usol;
					}
				}
										
				for(int j=0; j<ed.length;j++) // for each edge, the array of edges is sorted by cost
			{
					if(ed[j].From.equals(curr.name)) //if current node is the parent
				{
						if(ed[j].offtimes > 0) // if the edges has off times
					{
							if((Arrays.binarySearch(ed[j].timings,start))< 0) // check whether pipe is open currently
						{					
								for(int h=0;h < AllNodes.length; h++) // check the node at the other end of the pipe
							{
									if(AllNodes[h].name.equals(ed[j].To))
								{
										if(AllNodes[h].Explored == false) // if it has already not been explored, prevents cycles.
									{
											if(AllNodes[h].Listed == false) // check if it is in the frontier list
										{
												AllNodes[h].Listed = true; // list it in frontier
												AllNodes[h].pathcost = curr.pathcost + ed[j].cost;
												AllNodes[h].edgetime = ed[j].cost + start;
												OpenList.add(AllNodes[h]);
										}
										   else //if it is currently in the frontier list
											{
											   if(AllNodes[h].pathcost > curr.pathcost + ed[j].cost) // check which path is cheapest
												{
													AllNodes[h].pathcost = curr.pathcost + ed[j].cost;
													AllNodes[h].edgetime = ed[j].cost + start;	
												}
																																		
											}	
												
									}
										
										
								}		
									
							}
								
						}
					}
					
					else // if there is no off times for the pipe
				{						  
							  
						for(int h=0;h < AllNodes.length; h++)
					{
									
							if(AllNodes[h].name.equals(ed[j].To))
						{
								if(AllNodes[h].Explored == false)
								{
										if(AllNodes[h].Listed == false)
										{
												AllNodes[h].Listed = true;
												AllNodes[h].pathcost = curr.pathcost + ed[j].cost;
												AllNodes[h].edgetime = ed[j].cost + start;
												OpenList.add(AllNodes[h]);
												
											
										}
										else
										{
											if(AllNodes[h].pathcost > curr.pathcost + ed[j].cost)
												{
													AllNodes[h].pathcost = curr.pathcost + ed[j].cost;
													AllNodes[h].edgetime = ed[j].cost + start;	
												}									
										}	
												
								}
																				
						}
									
									
					}
								
				}//end of else for edgs with no timings
							  		
			}
				
		}
				  
				
											
	}
		//if no remaining nodes in the frontier, return "none" as the result
			usol.goalnode = "None";
			usol.timetaken = -1;
			return usol;
}
/*-------------------------------------------------------------------------------*/        
   
   
   public static void main(String [] args) {

        if(!args[0].equals("-i"))
		{
			System.out.println(" Not a valid command. The first argument should be '-i' followed by the inputFile name");
			System.exit(0);
		}
		String fileName = args[1]; // file name from the 1st command line argument
        String line = null; // one line at a time
		String s = "";
		String current;
		int i,j;
		String outputfileName = "output.txt";
	
        try {
                FileReader fileReader = new FileReader(fileName);
                LineNumberReader linereader = new LineNumberReader(fileReader);
				FileWriter fileWriter = new FileWriter(outputfileName);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				
				line = linereader.readLine();
				String a = line;
				int b = Integer.parseInt(a);
				
				for( int count=1; count<=b; count++)
			{
                String Algo = linereader.readLine();
				s = linereader.readLine();//Source
				//Node Source = new Node();
				Source.name = s;
				Source.pathcost = 0;
			
				String goals = linereader.readLine();
				String[] goalnodes = goals.split(" ");//given Goal Nodes
				Goals = new Node[goalnodes.length];
					for(i=0;i<goalnodes.length;i++)
					{
						Goals[i] = new Node();
						Goals[i].name = goalnodes[i];
						Goals[i].pathcost = infinity;			
					}
				
				String internal = linereader.readLine();
				String[] internalnodes = internal.split(" ");// internal nodes of the tree
				IntNodes = new Node[internalnodes.length];
					for(i=0;i<internalnodes.length;i++)
					{
						IntNodes[i] = new Node();
						IntNodes[i].name = internalnodes[i];
						IntNodes[i].pathcost = infinity;			
					}
				
				String pipes = linereader.readLine();
				int p = Integer.parseInt(pipes); // no.of edges in the graph
				
				Edge[] e = new Edge[p];//array for holding the edges
				
				for(j=0;j<p;j++)
				{
					current = linereader.readLine();
					e[j] = createedge(current);
					
				}
				
				String starttime = linereader.readLine();
				int startT = Integer.parseInt(starttime);
				String xm = linereader.readLine();
																	
				if(Algo.equals("BFS"))
				{
					solution bsol = new solution();
					Arrays.sort(e,edgeComparator);
				    bsol = bfs(e);
				    if(bsol.timetaken < 0)
				    {
				    	bufferedWriter.write(bsol.goalnode);
						bufferedWriter.newLine();	
				    }
				    else
				    {
				    
				    bsol.timetaken += startT;
				    bsol.timetaken = bsol.timetaken % 24;
					bufferedWriter.write(bsol.goalnode + " " + bsol.timetaken);
					bufferedWriter.newLine();
				    }
					
					
				}
				
				if(Algo.equals("DFS"))
				{
					
					solution dsol = new solution();
					dsol = dfs(e);
					
				    if(dsol.timetaken < 0)
				    {
				    	bufferedWriter.write(dsol.goalnode);
						bufferedWriter.newLine();	
				    }
				    else
				    {
				    dsol.timetaken += startT;
				    dsol.timetaken = dsol.timetaken % 24;
					bufferedWriter.write(dsol.goalnode + " " + dsol.timetaken);
					bufferedWriter.newLine();
				    }
				
				} 
				
				
				if(Algo.equals("UCS"))
				{
					solution n = new solution();
					Arrays.sort(e,edgeComparator);
					n = ucs(e,startT);	
					
					if(n.timetaken < 0)
				    {
				    	bufferedWriter.write(n.goalnode);
						bufferedWriter.newLine();	
				    }
					else
					{					
					bufferedWriter.write(n.goalnode + " " + n.timetaken);
					bufferedWriter.newLine();
					}
				
				} 
			}		
              linereader.close();  
			  bufferedWriter.close();
          }
			
		catch(FileNotFoundException ex) 
		{
            System.out.println("Unrecognised file '" + fileName + "'");                
        }
        catch(IOException ex) 
		{
            System.out.println("Error reading file '" + fileName + "'");                   
        }		

}
}