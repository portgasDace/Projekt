public class SplayTree {
	
	private SplayNode treeRoot;

	public SplayTree(){}
	public SplayTree(SplayNode treeRoot) {
		super();
		this.treeRoot = treeRoot;
	}
	
	public SplayNode getTreeRoot() {
		return treeRoot;
	}
	public void setTreeRoot(SplayNode treeRoot) {
		this.treeRoot = treeRoot;
	}
	
	public void insert(int value){
		if(getTreeRoot()!=null){
			insert(value, getTreeRoot());
		}
		else{
			setTreeRoot(new SplayNode(value));
		}
	}
	

	public void delete (int value){
		if(getTreeRoot()!=null){
			delete(value, getTreeRoot());
		}
		else{
			return;
		}
	}
	
	public SplayNode search (int value){
		if(getTreeRoot()!=null){
			return search(value, getTreeRoot());
		}
		else{
			return null;
		}
	}
	
	private void insert(int value, SplayNode treeRoot){
		SplayNode node=this.search(value, treeRoot);
		if(node==null){
			if(value<this.getTreeRoot().getValue()){
				treeRoot=this.getTreeRoot();
				SplayNode newNode = new SplayNode(value);
				newNode.setRightChild(treeRoot);
				treeRoot.setParent(newNode);
				newNode.setLeftChild(treeRoot.getLeftChild());
				if(treeRoot.getLeftChild()!=null){
					treeRoot.getLeftChild().setParent(newNode);
				}
				treeRoot.setLeftChild(null);
				this.setTreeRoot(newNode);
				
			}
			else{
				//this.test(this.getTreeRoot(), 0);
				//System.out.println("A");
				treeRoot=this.getTreeRoot();
				SplayNode newNode = new SplayNode(value);
				newNode.setLeftChild(treeRoot);
				treeRoot.setParent(newNode);
				newNode.setRightChild(treeRoot.getRightChild());
				if(treeRoot.getRightChild()!=null){
					treeRoot.getRightChild().setParent(newNode);
				}
				treeRoot.setRightChild(null);
				this.setTreeRoot(newNode);
			}
			
		}
		else{
			this.setTreeRoot(node);
		}
	}
	
	private SplayNode search (int value, SplayNode node){
		
		if(node.getValue()==value){
			rotateAfterSearch(node);
			return node;
		}
		else if(node.getValue()>=value){
			if(node.getLeftChild()!=null){
				return search(value, node.getLeftChild());
			}
			else{
				//greska, node ne postoji
				rotateAfterSearch(node);
				return null;
			}
		}
		else{
			if(node.getRightChild()!=null){
				return search(value, node.getRightChild());
			}
			else{
				//greska, node ne postoji
				rotateAfterSearch(node);
				return null;
			}
		}
	}
	
	private void delete(int value, SplayNode node){

		if(node.getValue()>value){
			if(node.getLeftChild()!=null){
				delete(value, node.getLeftChild());
			}
			else{
				//greska, ne postoji cvor koji se treba izbrisati;
				return;
			}
		}
		else if(node.getValue()<value){
			if(node.getRightChild()!=null){
				delete(value, node.getRightChild());
			}
			else{
				//greska, ne postoji cvor koji se treba izbrisati;
				return;
			}
		}
		else{
			// no children
			if(node.getLeftChild()==null && node.getRightChild() ==null){
				if(node.getParent()!=null){
					if(node.isLeftChild()){
						node.getParent().setLeftChild(null);
					}
					else{
						node.getParent().setRightChild(null);
					}
				}
				else{
					setTreeRoot(null);
				}
			}
			//only left child
			else if(node.getLeftChild()!=null && node.getRightChild() ==null){

				if(node.getParent()!=null){
					if(node.isLeftChild()){
						node.getParent().setLeftChild(node.getLeftChild());
						node.getLeftChild().setParent(node.getParent());
					}
					else{
						node.getParent().setRightChild(node.getLeftChild());
						node.getLeftChild().setParent(node.getParent());
					}
				}
				else{
					this.setTreeRoot(node.getLeftChild());
					node.getLeftChild().setParent(null);
				}
			}
			//only right child
			else if(node.getLeftChild()==null && node.getRightChild() !=null){

				if(node.getParent()!=null){
					if(node.isLeftChild()){
						node.getParent().setLeftChild(node.getRightChild());
						node.getRightChild().setParent(node.getParent());
					}
					else{
						node.getParent().setRightChild(node.getRightChild());
						node.getRightChild().setParent(node.getParent());
					}
				}
				else{
					this.setTreeRoot(node.getRightChild());
					node.getRightChild().setParent(null);
				}
			}
			//both children
			else{
				//even if node is root no need to handle differently, since only value will change!
				SplayNode switchNode = node.getLeftChild();
				while(switchNode.getRightChild()!=null){
					switchNode=switchNode.getRightChild();
				}
				if(switchNode.isLeftChild()){
					switchNode.getParent().setLeftChild(switchNode.getLeftChild());
				}
				else{
					switchNode.getParent().setRightChild(switchNode.getLeftChild());
				}
				if(switchNode.getLeftChild()!=null){
					
					switchNode.getLeftChild().setParent(switchNode.getParent());
				}
				
				node.setValue(switchNode.getValue());
			}

			

		}
	}

	
	private void rotateAfterSearch(SplayNode node){
		while(node.getParent()!=null){
			if(node.hasGrandParent()){
				//rotate zig zag, 4 cases
				SplayNode p = node.getParent();
				SplayNode g = node.getGrandParent();
				if(g==null){
					System.out.println("B");
				}
				
				if(node.isLeftChild()){
					if(p.isLeftChild()){
						rotate(2, g);
						rotate(2,p);
					}
					else{
						rotate(2,p);
						rotate(1,g);
					}
				}
				else{
					if(p.isLeftChild()){
						rotate(1,p);
						rotate(2,g);
					}
					else{
						rotate(1,g);
						rotate(1,p);
					}
				}
				
			}
			else{
				//zig or zag, current node is child of root
				SplayNode p = node.getParent();
				if(node.isLeftChild()){
					rotate(2,p);
				}
				else{
					rotate(1,p);
				}
			}
		}	
		this.setTreeRoot(node);
		
	}
	
	//1 = rotate left, 2 = rotate right, 3 = double left rotation, 4 = double right rotation;
		private void rotate(int rotationType, SplayNode node){

			SplayNode current =node;
			switch(rotationType){
			case 1: 

				SplayNode rightChild= node.getRightChild();
				if(node.getParent()!=null){
					SplayNode parent = node.getParent();
					//1korak
					if(node.isLeftChild()){
						parent.setLeftChild(rightChild);
					}
					else{
						parent.setRightChild(rightChild);
					}
					rightChild.setParent(parent);
				}
				else{
					//1korak
					this.setTreeRoot(rightChild);
					rightChild.setParent(null);
				}
				//2korak
				if(rightChild.getLeftChild()!=null){
					rightChild.getLeftChild().setParent(current);
					current.setRightChild(rightChild.getLeftChild());
				}
				else{
					current.setRightChild(null);
				}
				//3korak
				rightChild.setLeftChild(current);
				current.setParent(rightChild);
				break;
			case 2 : 

				SplayNode leftChild=node.getLeftChild();
				if(node.getParent()!=null){
					SplayNode parent = node.getParent();
					//1korak
					if(node.isLeftChild()){
						parent.setLeftChild(leftChild);
					}
					else{
						parent.setRightChild(leftChild);
					}
					leftChild.setParent(parent);
				}
				else{
					//1korak
					this.setTreeRoot(leftChild);
					leftChild.setParent(null);
				}
				//2korak
				if(leftChild.getRightChild()!=null){
					leftChild.getRightChild().setParent(current);
					current.setLeftChild(leftChild.getRightChild());
				}
				else{
					current.setLeftChild(null);
				}
				//3korak
				leftChild.setRightChild(current);
				current.setParent(leftChild);
				break;
			case 3 : 
				rotate(2, node.getRightChild());
				rotate(1, node);
				break;
			case 4 : 
				rotate(1, node.getLeftChild());
				rotate(2, node);
				break;
			}
		}
		
		public void test (SplayNode node, int i){
			for(int n=0; n<i;n++){
				System.out.print(" ");
			}
			System.out.println(node.getValue());
			if(node.getLeftChild()==null && node.getRightChild()==null){
				return;
			}
			if(node.getRightChild()!=null){
				test(node.getRightChild(), i+1);
			}
			else{
				for(int n=0; n<=i;n++){
					System.out.print(" ");
				}
				System.out.println("r:null");
			}
			if(node.getLeftChild()!=null){
				test(node.getLeftChild(), i+1);
			}
			else{
				for(int n=0; n<=i;n++){
					System.out.print(" ");
				}
				System.out.println("l:null");
			}
		}
}
