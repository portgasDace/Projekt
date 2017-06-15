
public class SplayNode {
	
	private int value;
	private SplayNode leftChild;
	private SplayNode rightChild;
	private SplayNode parent;
	
	public SplayNode(int value){
		this.value = value;
		
	}
	public SplayNode(int value, SplayNode parent) {
		super();
		this.value = value;
		this.parent = parent;
	}
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public SplayNode getLeftChild() {
		return leftChild;
	}
	public void setLeftChild(SplayNode leftChild) {
		this.leftChild = leftChild;
		if(leftChild!=null){
			leftChild.setParent(this);
		}
	}
	public SplayNode getRightChild() {
		return rightChild;
	}
	public void setRightChild(SplayNode rightChild) {
		this.rightChild = rightChild;
		if(rightChild!=null){
			rightChild.setParent(this);
		}
	}
	public SplayNode getParent() {
		return parent;
	}
	public void setParent(SplayNode parent) {
	this.parent = parent;
	}
	
	public boolean hasGrandParent(){
		if(this.getParent().getParent()!=null){
			return true;
		}
		else{
			return false;
		}
	}
	
	public SplayNode getGrandParent(){
		if(this.hasGrandParent()){
			return this.getParent().getParent();
		}
		else{
			return null;
		}
	}
	
	public boolean isLeftChild() {

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
}
