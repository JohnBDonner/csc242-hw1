import java.io.*;

public class problem6 {
	public static void main (String [] args) {
		// initialize new LinkedList to store dataset
		LinkedList dataset = new LinkedList();
		LinkedList clusters = new LinkedList();

		// read the textfile and drop that data into usable objects
		dataset = read("B.txt");

		// Return length of dataset and use it to create array of k clusters, where k is each
		// point in the dataset
		int k = dataset.length();
		for (int i=0; i<dataset.length(); i++) {
			double x = dataset.extract(i).getX();
			double y = dataset.extract(i).getY();
			clusters.insert(new Point(x, y));
			clusters.extract(i).setCluster(i+1);
			dataset.extract(i).setCluster(i+1);
		}

	    /*---- Presumably got the data... now time for some fun ----*/
	    
	    // Check if my shit is working
	    /* WORKS
	    for (int i=0; i<kPoints.length; i++) {
	    	System.out.println("Point "+(i+1)+" is "+kPoints[i].toString()+" within cluster "+kPoints[i].getCluster());
	    }
	    */ 

	    // Find closest points and begin clustering
	    // findClosestPair(dataset, clusters);
	    closestPair(clusters);
	    
	    
	}

	// algorithm for hierarchical clustering
	public static void closestPair(LinkedList k) {
		int n = 0;
		int dID = 0;
		double leastDist = 5;
		Point p1 = new Point();
		Point p2 = new Point();
		while (n < 10) {
			n++;
			for (int i=0; i<k.length(); i++) {
				Point x1 = new Point(k.extract(i).getX(), k.extract(i).getY());
				for (int j=0; j<k.length(); j++) {
					Point x2 = new Point(k.extract(j).getX(), k.extract(j).getY());
					// Check if the points are the same. Kind of sketchy, but there are no unique
					// points in the dataset. 
					// if (x1.getX() != x2.getX() && x1.getY() != x2.getY()) {
						// Checks if it is the smallest distance between the points.
						if (eDist(x1, x2) < leastDist && eDist(x1, x2) > 0) {
							System.out.println(k.extract(j).toString()+" ("+j+") belongs to cluster "+k.extract(j).getCluster());
							leastDist = eDist(x1, x2);
							// resets leastDist to new distance and creates new points.
							p1 = k.extract(i);
							p2 = k.extract(j);
							dID = i;
						}
					// }
				}
			}
			// Set the new point to the same cluster as the closest cluster.
			p2.setCluster(p1.getCluster());
			k.delete(dID);
			System.out.println("Delete point "+dID);
			for (int i=0; i<k.length(); i++) {
	    		System.out.println("Point "+(i+1)+" is "+k.extract(i).toString()+" within cluster "+k.extract(i).getCluster());
	    	}
			// Re-establish the center point for every cluster
			newAve(k);
			System.out.println("Iteration "+n);
			
			for (int i=0; i<k.length(); i++) {
	    		System.out.println("Point "+(i+1)+" is "+k.extract(i).toString()+" within cluster "+k.extract(i).getCluster());
	    	}
			
		}
		

	}

	public static LinkedList newAve(LinkedList k) {
		double n;
		double sumx, sumy;
		for (int i = 0; i < k.length(); i++) {
			n = 0;
			sumx = 0;
			sumy = 0;
			Point mp = k.extract(i);
			for (int j = 0; j < k.length(); j++) {
				Point p = k.extract(j);
				System.out.println("i "+i+" and j "+j);
				if (p.getCluster() == mp.getCluster()) {
					// System.out.println("point cluster "+p.getCluster()+"\t\tk cluster "+mp.getCluster());
					n++;
					sumx += p.getX();
					sumy += p.getY();
					
					System.out.println("k["+j+"] = \tn: "+n+"\tsumx: "+sumx+"\tsumy: "+sumy);
				}
			}
			//System.out.println("k["+i+"] = \n\t"+k[i].toString());
			//System.out.println("k["+i+"] = \tn: "+n+"\tsumx: "+sumx+"\tsumy: "+sumy);
			k.extract(i).setX(sumx/n);
			k.extract(i).setY(sumy/n);
			// System.out.println("k["+i+"] = \t"+k[i].toString());
		}
		return k;
	}

	// algorithm for hierarchical clustering
	public static void findClosestPair(LinkedList data, Point[] k) {
		int n = 0;
		double leastDist = 5;
		Point p1 = new Point();
		Point p2 = new Point();
		while (n < 10) {
			n++;
			for (int i=0; i<k.length; i++) {
				Point x1 = new Point(k[i].getX(), k[i].getY());
				for (int j=0; j<k.length; j++) {
					Point x2 = new Point(k[j].getX(), k[j].getY());
					// Check if the points are the same. Kind of sketchy, but there are no unique
					// points in the dataset. 
					// if (x1.getX() != x2.getX() && x1.getY() != x2.getY()) {
						// Checks if it is the smallest distance between the points.
						if (eDist(x1, x2) < leastDist && eDist(x1, x2) > 0) {
							// System.out.println(data.extract(j).toString()+" ("+j+") belongs to cluster "+data.extract(j).getCluster());
							leastDist = eDist(x1, x2);
							// resets leastDist to new distance and creates new points.
							p1 = k[i];
							p2 = k[j];
						}
					// }
				}
			}
			// Set the new point to the same cluster as the closest cluster.
			p2.setCluster(p1.getCluster());
			// Re-establish the center point for every cluster
			newMean(data, k);
			System.out.println("Iteration "+n);
			
			for (int i=0; i<k.length; i++) {
	    		System.out.println("Point "+(i+1)+" is "+k[i].toString()+" within cluster "+k[i].getCluster());
	    	}
			
		}
		

	}

	// Method to find the euclidian distance between two points
	public static double eDist(Point x1, Point x2) {
		double a = Math.pow(x1.getX()-x2.getX(), 2);
		double b = Math.pow(x1.getY()-x2.getY(), 2);
		return Math.sqrt(a+b);
	}

	// Finds a the new average point between a group of points that are assigned to a cluster 'k'
	// *left over from problem 5
	public static Point[] newMean(LinkedList data, Point[] k) {
		double n;
		double sumx, sumy;
		for (int i = 0; i < k.length; i++) {
			n = 0;
			sumx = 0;
			sumy = 0;
			Point mp = k[i];
			for (int j = 0; j < k.length; j++) {
				Point p = k[j];
				if (p.getCluster() == mp.getCluster()) {
					// System.out.println("point cluster "+p.getCluster()+"\t\tk cluster "+mp.getCluster());
					n++;
					sumx += p.getX();
					sumy += p.getY();
					
					System.out.println("k["+j+"] = \tn: "+n+"\tsumx: "+sumx+"\tsumy: "+sumy);
				}
			}
			//System.out.println("k["+i+"] = \n\t"+k[i].toString());
			//System.out.println("k["+i+"] = \tn: "+n+"\tsumx: "+sumx+"\tsumy: "+sumy);
			k[i].setX(sumx/n);
			k[i].setY(sumy/n);
			// System.out.println("k["+i+"] = \t"+k[i].toString());
		}
		return k;
	}

	// Prints all of the points and the clusters they are assigned to.
	// Printing formatted to easily copy and paste into google spreadsheet via
	// "paste special > paste values only"
	public static void printClusters(LinkedList data, Point[] k) {
		String tab = "";
		for (int i = 0; i < k.length; i++) {
			// print cluster number
			//System.out.println("\t-\t\tCluster " + k[i].getCluster() + "\t\t-");
			tab += "\t";
			System.out.println("\n"+k[i].getX()+tab+k[i].getY()+"\n");
			// print x values
			//System.out.println("xxxxxxxx\tX values:\txxxxxxxx");
			tab += "\t";
			for (int j = 0; j < data.length(); j++) {
				Point p = data.extract(j);
				if (p.cluster == k[i].cluster) {
					System.out.println(String.valueOf(p.getX())+tab+String.valueOf(p.getY()));
				}
			}
			// print y values
			/*
			System.out.println("yyyyyyyy\tY values:\tyyyyyyyy");
			for (int j = 0; j < data.length(); j++) {
				Point p = data.extract(j);
				if (p.cluster == k[i].cluster) {
					System.out.println(String.valueOf(p.getY()));
				}
			}
			*/
		}
	}

	// kmeans algorithm leftover from problem 5
	public static void kmeans(LinkedList data, Point[] k) {
		boolean changed = true;
		int n = 0;
		while (changed && n<20) {
			n++;
			changed = false;
			for (int i = 0; i < data.length(); i++) {
				Point p = data.extract(i);
		    	double dist = 2.0;
		    	int c = 0;
		    	for (int j = 0; j < k.length; j++) {
		    		if (eDist(p, k[j]) < dist) {
		    			dist = eDist(p, k[j]);
		    			c = k[j].getCluster();
		    		}
		    	}
		    	if (p.getCluster() != c && !changed) {
		    		changed = true;
		    		//System.out.println(changed);
		    	}
		    	p.setCluster(c);
		    }
		    System.out.println("Iteration "+n);
		    printClusters(data, k);
		    newMean(data, k);
		    // System.out.println("cluster "+k[0].cluster+" center: " + k[0]);
			// System.out.println("cluster "+k[1].cluster+" center: " + k[1]);
		}
		System.out.println(n+" iterations");
	}

	// Reads the .txt file and breaks each line into an 'x' and 'y' coordinate, storing them
	// in a 'Point' object within a LinkedList
	public static LinkedList read(String file) {
		LinkedList dataset = new LinkedList();
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
	        StringBuilder sb = new StringBuilder();
	        String line = "";// = br.readLine();

	        int n = 0;
	        // iterate through the data and store it in a linked list
	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	            if (line != null) {
	            	String[] stringSeparated = line.split("\\s+");
	            	Point point = new Point(Double.parseDouble(stringSeparated[0]), Double.parseDouble(stringSeparated[1]));
	            	dataset.insert(point);
	            	n++;
	            	// System.out.println("line " + n + ": " + point.toString());
	            }
	        }
			//String everything = sb.toString();
	    } catch(IOException e) {
	    	e.printStackTrace();
	    }
	    return dataset;
	}
}