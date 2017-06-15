
public class AVLTree {

	private AVLNode treeRoot;

	public AVLTree (){}

	private void insert(int value, AVLNode node){

		AVLNode parent =null;
		int nodeValue=node.getValue();
		int rotationType;
		boolean isLeftChild=false;
		if(nodeValue>=value){
			if(node.getLeftChild()!=null){
				insert(value, node.getLeftChild());
				return;
			}
			else{
				node.setLeftChild(new AVLNode(value, node));
			}
		}
		else{
			if(node.getRightChild()!=null){
				insert(value, node.getRightChild());
				return;
			}
			else{
				node.setRightChild(new AVLNode(value, node));
			}
		}
		// test test(getTreeRoot(), 1);
		updateHeightAfterInsert(node);
		//petlja rekurzivno ide prema vrhu i breaka ili kada se dogodi rotacija(kod inserta je uvijek max 1 rotacija!) ili kada dode do roota
		while(true){
			if(node.getParent()!=null){
				parent = node.getParent();
				isLeftChild=node.isNodeLeftChild();
			}
			rotationType=checkAndRotate(node);
			if(rotationType==1){
				updateHeightAfterSingleRotation(node.getParent());
				break;
			}
			else if(rotationType==2){
				if(node.getParent()!=null){
					parent=node.getParent();
					isLeftChild=node.isNodeLeftChild();
					if(isLeftChild){
						updateHeightAfterDoubleRotation(parent.getLeftChild());
					}
					else{
						updateHeightAfterDoubleRotation(parent.getRightChild());
					}
				}
				break;
			}
			else{
				if(node.getParent()==null){
					break;
				}
				else{
					node=node.getParent();
				}
			}
		}
	}

	private void delete(int value, AVLNode node){

		
		
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
			int nodeType=0;
			AVLNode parent=null;
			// no children
			if(node.getLeftChild()==null && node.getRightChild() ==null){
				nodeType=1;
				if(node.getParent()!=null){
					parent=node.getParent();
				}
				if(node.getParent()!=null){
					if(node.isNodeLeftChild()){
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

				nodeType=2;
				if(node.getParent()!=null){
					parent=node.getParent();
				}
				if(node.getParent()!=null){
					if(node.isNodeLeftChild()){
						node.getParent().setLeftChild(node.getLeftChild());
					}
					else{
						node.getParent().setRightChild(node.getLeftChild());
					}
				}
				else{
					this.setTreeRoot(node.getLeftChild());
					node.getLeftChild().setParent(null);
				}
			}
			//only right child
			else if(node.getLeftChild()==null && node.getRightChild() !=null){

				nodeType=2;
				if(node.getParent()!=null){
					parent=node.getParent();
				}
				if(node.getParent()!=null){
					if(node.isNodeLeftChild()){
						node.getParent().setLeftChild(node.getRightChild());
					}
					else{
						node.getParent().setRightChild(node.getRightChild());
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
				AVLNode switchNode = node.getLeftChild();
				nodeType=3;
				while(switchNode.getRightChild()!=null){
					switchNode=switchNode.getRightChild();
				}
				parent=switchNode.getParent();
				if(switchNode.getLeftChild()!=null){
					if(switchNode.isNodeLeftChild()){
						switchNode.getParent().setLeftChild(switchNode.getLeftChild());
					}
					else{
						switchNode.getParent().setRightChild(switchNode.getLeftChild());
					}
					switchNode.getLeftChild().setParent(switchNode.getParent());
				}
				node.setValue(switchNode.getValue());
			}

			//update height 
			if(nodeType!=0){
				updateHeightAfterInsert(parent);
			}
			//rotate
			
			rotateAfterDelete(treeRoot);

		}
	}


	private AVLNode search (int value, AVLNode node){

		
		
		
		
		if(node.getValue()==value){
			return node;
		}
		else if(node.getValue()>=value){
			if(node.getLeftChild()!=null){
				return search(value, node.getLeftChild());
			}
			else{
				//greska, node ne postoji
				return null;
			}
		}
		else{
			if(node.getRightChild()!=null){
				return search(value, node.getRightChild());
			}
			else{
				//greska, node ne postoji
				return null;
			}
		}
		

	}
	//0 = no roation, 1 = single rotation, 2 = double rotation
	private int checkAndRotate(AVLNode node){
		if(node.getNodeBalance()>=2){
			if(node.getLeftChild().getNodeBalance()>0){
				rotate(2, node);
				return 1;
			}
			else{
				rotate(4, node);
				return 2;
			}
		}
		else if(node.getNodeBalance()<=-2){
			if(node.getRightChild().getNodeBalance()<0){
				rotate(1, node);
				return 1;
			}
			else{
				rotate(3, node);
				return 2;
			}
		}
		else{
			return 0;
		}
	}

	private void updateHeightAfterInsert(AVLNode node){

		int heightLeft ;
		int heightRight ;
		int maxHeight;
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
		if(heightLeft>heightRight){
			maxHeight=heightLeft;
		}
		else{
			maxHeight=heightRight;
		}
		if(maxHeight==node.getHeight()-1){
			return;
		}
		else{
			node.setHeight(maxHeight+1);
			if(node.getParent()!=null){
				updateHeightAfterInsert(node.getParent());
			}
		}
	}

	private void updateHeightAfterSingleRotation(AVLNode node){

		updateHeightAfterDoubleRotation(node);
		//ukoliko je insert lancano povecao parent nodove mozda ih treba smanjiti
		if(node.getParent()!=null){
			updateHeightRevert(node.getParent());
		}

	}

	private void updateHeightAfterDoubleRotation(AVLNode node){

		if(node.getLeftChild()==null&&node.getRightChild()==null){
			node.setHeight(1);
			return;
		}
		if(node.getLeftChild()!=null){
			updateHeightAfterDoubleRotation(node.getLeftChild());
		}
		if(node.getRightChild()!=null){
			updateHeightAfterDoubleRotation(node.getRightChild());
		}
		int heightLeft ;
		int heightRight ;
		int maxHeight;
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
		if(heightLeft>heightRight){
			maxHeight=heightLeft;
		}
		else{
			maxHeight=heightRight;
		}
		node.setHeight(maxHeight+1);
		return;

	}

	private void updateHeightRevert(AVLNode node){

		int heightLeft ;
		int heightRight ;
		int maxHeight;
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
		if(heightLeft>heightRight){
			maxHeight=heightLeft;
		}
		else{
			maxHeight=heightRight;
		}
		if(node.getHeight()>maxHeight+1){
			node.setHeight(maxHeight+1);
			if(node.getParent()!=null){
				updateHeightRevert(node.getParent());
			}
		}
		else{
			return;
		}
	}


	private void rotateAfterDelete(AVLNode node){

		/*AVLNode parent=null;
		int rotationType=checkAndRotate(node);
		boolean isLeftChild = node.isNodeLeftChild();

		if(node.getRightChild()!=null){
			rotateAfterDelete(node.getRightChild());
		}
		if(node.getLeftChild()!=null){
			rotateAfterDelete(node.getLeftChild());
		}
		if(node.getParent()!=null){
			parent = node.getParent();
		}
		if(rotationType==1){
			updateHeightAfterSingleRotation(node);
		}
		else if(rotationType==2){
			if(node.getParent()!=null){
				if(isLeftChild){
					updateHeightAfterDoubleRotation(parent.getLeftChild());
				}
				else{
					updateHeightAfterDoubleRotation(parent.getRightChild());
				}
			}
			else{
				updateHeightAfterDoubleRotation(treeRoot);
			}
		}*/
		while(true){
			int rotationType=0;
			AVLNode parent=null;
			boolean isLeftChild;
			if(node.getParent()!=null){
				parent = node.getParent();
				isLeftChild=node.isNodeLeftChild();
			}
			rotationType=checkAndRotate(node);
			if(rotationType==1){
				updateHeightAfterSingleRotation(node.getParent());
				
			}
			else if(rotationType==2){
				if(node.getParent()!=null){
					parent=node.getParent();
					isLeftChild=node.isNodeLeftChild();
					if(isLeftChild){
						updateHeightAfterDoubleRotation(parent.getLeftChild());
					}
					else{
						updateHeightAfterDoubleRotation(parent.getRightChild());
					}
				}
				
			}
			else{
				if(node.getParent()==null){
					break;
				}
				else{
					node=node.getParent();
				}
			}
		}
	}

	//1 = rotate left, 2 = rotate right, 3 = double left rotation, 4 = double right rotation;
	private void rotate(int rotationType, AVLNode node){

		AVLNode current =node;
		switch(rotationType){
		case 1: 

			AVLNode rightChild= node.getRightChild();
			if(node.getParent()!=null){
				AVLNode parent = node.getParent();
				//1korak
				if(node.isNodeLeftChild()){
					parent.setLeftChild(rightChild);
				}
				else{
					parent.setRightChild(rightChild);
				}
				rightChild.setParent(parent);
			}
			else{
				//1korak
				this.treeRoot=rightChild;
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

			AVLNode leftChild=node.getLeftChild();
			if(node.getParent()!=null){
				AVLNode parent = node.getParent();
				//1korak
				if(node.isNodeLeftChild()){
					parent.setLeftChild(leftChild);
				}
				else{
					parent.setRightChild(leftChild);
				}
				leftChild.setParent(parent);
			}
			else{
				//1korak
				this.treeRoot=leftChild;
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

	public void insert (int value){
		if(getTreeRoot()!=null){
			insert(value, getTreeRoot());
		}
		else{
			setTreeRoot(new AVLNode(value));
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

	public AVLNode search (int value){
		
		if(getTreeRoot()!=null){
			
			return search(value, getTreeRoot());
			
			
		}
		else{
			return null;
		}
		
	}

	public AVLNode getTreeRoot() {
		return treeRoot;
	}

	public void setTreeRoot(AVLNode treeRoot) {
		this.treeRoot = treeRoot;
	}
	public void test (AVLNode node, int i){
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
public int size (AVLNode node, int i){
		
		if(node.getLeftChild()!=null){
			int n= size(node.getLeftChild(),0);
			i=i+n;
		}
		if(node.getRightChild()!=null){
			int n = size(node.getRightChild(), 0);
			i=i+n;
		}
		i++;
		return i ;
		
	}
}
