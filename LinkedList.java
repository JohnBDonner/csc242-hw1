public class LinkedList {
	Node first;
	int n;
	
	public LinkedList(){
		first = new Node();
		first.data = null;
		first.next = null;
	}
	
	// n^3 time
	public void insert(Point x){
		if (!lookup(x)){
			Node newNode = new Node();
			newNode.data = x;
			if (!isEmpty()) {
				n++;
				newNode.id = n-1;
				newNode.next = first;
			}
			else {
				n = 1;
				newNode.id = 0;
				newNode.next = null;
			}
			first = newNode;
		}
	}
	
	// n^2 time
	public void delete(Point x){
		Node current = first, previous = first;
		while (current != null){
			if (current.data == x && current == first){
				first = current.next;
				return;
			} else if (current.data == x && current != first){
				previous.next = current.next;
				return;
			}
			previous = current;
			current = current.next;
		}
	}

	public void delete(int x){
		Node current = first, previous = first;
		while (current != null){
			if (current.id == x && current == first){
				first = current.next;
				n--;
				return;
			} else if (current.id == x && current != first){
				previous.next = previous.next.next;
				n--;
				return;
			}
			previous = current;
			current = current.next;
		}
	}
	
	// n^2 time
	public boolean lookup(Point x){
		Node current = first;
		while (current != null){
			if (current.data == x)
				return true;
			current = current.next;
		}
		return false;
	}

	// n time
	public boolean lookup(int id){
		Node current = first;
		while (current != null){
			if (current.id == id)
				return true;
			current = current.next;
		}
		return false;
	}

	// n time
	public Point extract(int id) {
		Node current = first;
		while (current != null){
			if (current.id == id)
				return current.data;
			current = current.next;
		}
		return null;
	}
	
	// c time
	public boolean isEmpty(){
		if (first == null) return true;
		else return false;
	}
	
	// n time
	public void printList(){
		Node current = first;
		while (current.data != null){
			System.out.println(current.data);
			current = current.next;
		}
	}

	public int length() {
		int l = 0;
		Node current = first;
		while (current.data != null){
			l++;
			current = current.next;
		}
		return l;
	}
}