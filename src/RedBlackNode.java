
public class RedBlackNode {
	
	private int value;
	private Color color;
	private RedBlackNode leftChild;
	private RedBlackNode rightChild;
	private RedBlackNode parent;
	
	public RedBlackNode(int value){
		this.value = value;
		this.color=Color.BLACK;
		this.parent=null;
	}
	
	public RedBlackNode(int value, RedBlackNode parent) {
		super();
		this.value = value;
		this.color=Color.RED;
		this.parent = parent;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public RedBlackNode getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(RedBlackNode leftChild) {
		this.leftChild = leftChild;
		if(leftChild!=null){
			leftChild.setParent(this);
		}
	}

	public RedBlackNode getRightChild() {
		return rightChild;
	}

	public void setRightChild(RedBlackNode rightChild) {
		this.rightChild = rightChild;
		if(rightChild!=null){
			rightChild.setParent(this);
		}
		
	}

	public RedBlackNode getParent() {
		return parent;
	}

	public void setParent(RedBlackNode parent) {
		this.parent = parent;
	}
	
	public boolean isLeftChild(){
		
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
	
	public RedBlackNode getUncle(){
		if(this.getParent().getParent()==null){
			return null;
		}
		return this.getParent().getSibling();
		
	}
	
	public void switchColor(){
		if(this.getColor().equals(Color.BLACK)){
			this.setColor(Color.RED);
		}
		else{
			this.setColor(Color.BLACK);
		}
	}
	
	public RedBlackNode getSibling(){
		if(this.getParent()!=null){
			if(this.isLeftChild()){
				return this.getParent().getRightChild();
			}
			else{
				return this.getParent().getLeftChild();
			}
		}
		else{
			return null;
		}
	}
	
	public boolean hasRedChildren(){
		if(this.getLeftChild()!=null && this.getLeftChild().getColor().equals(Color.RED)){
			return true;
		}
		
		if(this.getRightChild()!=null && this.getRightChild().getColor().equals(Color.RED)){
			return true;
		}
		return false;
		
	}
}
