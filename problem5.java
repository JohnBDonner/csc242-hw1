import java.io.*;

public class problem5 {
	public static void main (String [] args) {

		int k = 3;
		LinkedList dataset = new LinkedList();
		Point[] kPoints = new Point[k];

		// read the textfile and drop that data into usable objects
		dataset = read("A.txt");

	    // Presumably got the data... now time for some fun

		// For number of k clusters, establishes two random points for each kmean
	    for (int i = 0; i < k; i++) {
	    	double x = Math.random() * 2.09;
	    	double y = Math.random() * 1.49;
	    	kPoints[i] = new Point(x, y);
	    	kPoints[i].setCluster(i+1);
	    }

		System.out.println("cluster "+kPoints[0].cluster+" center: " + kPoints[0]);
		System.out.println("cluster "+kPoints[1].cluster+" center: " + kPoints[1]);
		System.out.println("cluster "+kPoints[2].cluster+" center: " + kPoints[2]);
	    // prints out all the points (with clusters)
	    /*
	    for (int i = 0; i < dataset.length(); i++) {
	    	System.out.println(dataset.extract(i).toString() + " assigned to cluster " + dataset.extract(i).cluster);
	    }
	    */
	    // Assign each point to closest cluster
	    kmeans(dataset, kPoints);
	    // Prints what clusters all the points were assigned to on first iteration
	   	/*
	    for (int i = 0; i < dataset.length(); i++) {
	    	System.out.println(dataset.extract(i).toString() + " assigned to cluster " + dataset.extract(i).cluster);
	    }
	    */

	    // test my average point function
	    System.out.println("cluster "+kPoints[0].cluster+" center: " + kPoints[0]);
		System.out.println("cluster "+kPoints[1].cluster+" center: " + kPoints[1]);
		System.out.println("cluster "+kPoints[2].cluster+" center: " + kPoints[2]);
	    newMean(dataset, kPoints);
	    System.out.println("cluster "+kPoints[0].cluster+" center: " + kPoints[0]);
		System.out.println("cluster "+kPoints[1].cluster+" center: " + kPoints[1]);
		System.out.println("cluster "+kPoints[2].cluster+" center: " + kPoints[2]);
	}

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

	public static double S(LinkedList data, Point[] k) {
		double s;
		double s_x = 0;
		double s_y = 0;
		double sumx;
		double sumy;
		for (int i = 0; i < k.length; i++) {
			sum = 0;
			for (int j = 0; j < data.length(); j++) {
				sumx += Math.abs(data.extract(j).getX()-k[i].getX());
				sumy += Math.abs(data.extract(j).getY()-k[i].getY());
			}
			s_x += sumx;
			s_y += sumy;
		}
		retu
	}

	public static double eDist(Point x1, Point x2) {
		double a = Math.pow(x1.getX()-x2.getX(), 2);
		double b = Math.pow(x1.getY()-x2.getY(), 2);
		return Math.sqrt(a+b);
	}

	public static Point[] newMean(LinkedList data, Point[] k) {
		double n;
		double sumx, sumy;
		for (int i = 0; i < k.length; i++) {
			n = 0;
			sumx = 0;
			sumy = 0;
			Point mp = k[i];
			for (int j = 0; j < data.length(); j++) {
				Point p = data.extract(j);
				if (p.getCluster() == mp.getCluster()) {
					// System.out.println("point cluster "+p.getCluster()+"\t\tk cluster "+mp.getCluster());
					n++;
					sumx += p.getX();
					sumy += p.getY();
					
					//System.out.println("k["+i+"] = \n\tn: "+n+"\n\tsumx: "+sumx+"\n\tsumy: "+sumy);
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