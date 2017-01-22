public class UnbalancedNode {

	private int value;
	private UnbalancedNode leftChild;
	private UnbalancedNode rightChild;
	private UnbalancedNode parent;

	public UnbalancedNode(int value){

		this.value=value;
		this.parent=null;
	}

	public UnbalancedNode(int value, UnbalancedNode parent){

		this.value=value;
		this.parent=parent;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public UnbalancedNode getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(UnbalancedNode leftChild) {
		this.leftChild = leftChild;
	}

	public UnbalancedNode getRightChild() {
		return rightChild;
	}

	public void setRightChild(UnbalancedNode rightChild) {
		this.rightChild = rightChild;
	}

	public UnbalancedNode getParent() {
		return parent;
	}

	public void setParent(UnbalancedNode parent) {
		this.parent = parent;
	}

	public boolean isNodeLeftChild() {

		if(this.getParent()==null){
			return false;
		}
		if(getParent().getLeftChild().equals(this)){
			return true;
		}
		else {
			return false;
		}
	}
}
