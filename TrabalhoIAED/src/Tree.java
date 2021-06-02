import java.util.Arrays;

public class Tree {
	
	private Node root;
	
	public Tree() {
		root = null;
	}
	
	public void insertTr(TweetRegistry tr) {
		if (root == null) {
			root = new Node (tr);
		}
		else {
			insertOrdn(root,tr);
		}
	}
	
	public void insertOrdn(Node actual,TweetRegistry tr) {
		if (actual != null) {
			if (Arrays.compare(tr.usuario, actual.getTr().usuario) < 0) {
				if (actual.getLeft() == null) {
					Node aux = new Node (tr);
					actual.setLeft(aux);
					actual.getLeft().setDad(actual);
				}
				else {
					insertOrdn(actual.getLeft(),tr);
				}
			
			}
			else {
				if (actual.getRight() == null) {
					Node aux = new Node(tr);
					actual.setRight(aux);
					actual.getRight().setDad(actual);
				}
				else {
					insertOrdn(actual.getRight(),tr);
				}
			}
		}
	}
	
	public void central() {
		inFix (root);
	}
	
	public void inFix(Node root) {
		if (root==null) {
			
			return;
		}
		
		inFix(root.getLeft());
		System.out.println(root.getTr().usuario);
		inFix(root.getRight());
	}
	
	public Node pesqRec (String user) {
		Node res = pesqArvRec(root, user);
		return res;
	}
	
	public Node pesqArvRec(Node root, String user) {
		Node res = null;
		String userRunning;
		
		if (root != null) {
			userRunning = new String(root.getTr().usuario);
			if (userRunning.equalsIgnoreCase(user)) {
				res = root;
			}
			else {
				if (userRunning.compareToIgnoreCase(user) < 0) {
					res = pesqArvRec(root.getLeft(),user);
				}
				else {
					res = pesqArvRec(root.getRight(),user);
				}
			}
		}
		else {
			System.out.println("Root null");
		}
		return res;
	}
}
