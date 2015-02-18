public class Point {
	double x, y;
	int id, cluster;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point() {
		x = 0;
		y = 0;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getCluster() {
		return cluster;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setID(int id) {
		this.id = id;
	}

	public void setCluster(int cluster) {
		this.cluster = cluster;
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}