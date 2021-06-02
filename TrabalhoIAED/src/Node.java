
public class Node {
	private Node dad;
	private Node left;
	private Node right;
	private TweetRegistry tr;
	
	public Node (TweetRegistry tr) {
		this.tr = tr;
		dad = null;
		left = null;
		right = null;
	}

	public Node getDad() {
		return dad;
	}

	public void setDad(Node dad) {
		this.dad = dad;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public TweetRegistry getTr() {
		return tr;
	}

	public void setTr(TweetRegistry tr) {
		this.tr = tr;
	}
	
	
}
