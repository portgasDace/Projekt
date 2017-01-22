
public class AVLNode {

	private int value;
	private int height;
	private AVLNode leftChild;
	private AVLNode rightChild;
	private AVLNode parent;

	//constructor for tree root
	public AVLNode(int value){

		this.value=value;
		this.height=1;
		this.parent=null;
	}

	//constructor for child nodes
	public AVLNode(int value, AVLNode parent){

		this.value=value;
		this.height=1;
		this.parent=parent;
	}

	//left -  right;
	public int getNodeBalance()
	{
		AVLNode node=this;
		int heightLeft ;
		int heightRight ;
		if(node.getLeftChild()!=null){
			heightLeft=node.getLeftChild().getHeight();
		}
		else{
			heightLeft=0;
		}
		if(node.getRightChild()!=null){
			heightRight=node.getRightChild().getHeight();
		}
		else{
			heightRight=0;
		}
		return heightLeft-heightRight;
	}
	
	public boolean isNodeLeftChild() {

		if(this.getParent()==null){
			return false;
		}
		if(getParent().getLeftChild()!=null && getParent().getLeftChild().equals(this)){
			return true;
		}
		else {
			return false;
		}
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public AVLNode getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(AVLNode leftChild) {
		this.leftChild = leftChild;
	}

	public AVLNode getRightChild() {
		return rightChild;
	}

	public void setRightChild(AVLNode rightChild) {
		this.rightChild = rightChild;
	}

	public AVLNode getParent() {
		return parent;
	}

	public void setParent(AVLNode parent) {
		this.parent = parent;
	}

	
}
