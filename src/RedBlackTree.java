public class RedBlackTree {
	
	private RedBlackNode treeRoot;
	RedBlackTree(){}
	
	public RedBlackNode getTreeRoot() {
		return treeRoot;
	}

	public void setTreeRoot(RedBlackNode treeRoot) {
		this.treeRoot = treeRoot;
	}
	
	public void insert(int value){
		if(getTreeRoot()!=null){
			insert(value, getTreeRoot());
		}
		else{
			setTreeRoot(new RedBlackNode(value));
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
	
	public RedBlackNode search (int value){
		
		if(getTreeRoot()!=null){
			
			return search(value, getTreeRoot());
			
			
		}
		else{
			return null;
		}
		
	}
	
	private void delete(int value, RedBlackNode node){

		//standard delete
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
			
			boolean isLeaf = false;
			boolean isLeft=false;
			RedBlackNode parent = null;
			Color color =null;
			// no children
			if(node.getLeftChild()==null && node.getRightChild() ==null){
				if(node.getParent()!=null){
					if(node.isLeftChild()){
						node.getParent().setLeftChild(null);
					}
					else{
						node.getParent().setRightChild(null);
					}
					isLeaf=true;
					isLeft=false;
					parent=node.getParent();
					color=Color.BLACK;
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
					}
					else{
						node.getParent().setRightChild(node.getLeftChild());
					}
					isLeaf=false;
					isLeft=true;
					parent=node.getParent();
					color=node.getColor();
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
					}
					else{
						node.getParent().setRightChild(node.getRightChild());
					}
					parent=node.getParent();
					color=node.getColor();
					isLeft=false;
					isLeaf=false;
				}
				else{
					this.setTreeRoot(node.getRightChild());
					node.getRightChild().setParent(null);
				}
			}
			//both children
			else{
				//even if node is root no need to handle differently, since only value will change!
				RedBlackNode switchNode = node.getLeftChild();
				while(switchNode.getRightChild()!=null){
					switchNode=switchNode.getRightChild();
				}
				parent=switchNode.getParent();
				color=switchNode.getColor();
				if(switchNode.getLeftChild()!=null){
					isLeaf=false;
					isLeft=true;
				}
				else{
					isLeaf=true;
					isLeft=false;
				}
				if(switchNode.getLeftChild()!=null){
					if(switchNode.isLeftChild()){
						switchNode.getParent().setLeftChild(switchNode.getLeftChild());
					}
					else{
						switchNode.getParent().setRightChild(switchNode.getLeftChild());
					}
					switchNode.getLeftChild().setParent(switchNode.getParent());
				}
				node.setValue(switchNode.getValue());
			}
			if(parent!=null){
				relocorAfterDelete(parent, isLeaf, isLeft, color);
			}
			

			

		}
		
	}
	
	private void relocorAfterDelete(RedBlackNode parent, boolean isLeaf, boolean isLeft, Color color){
		
		boolean isUtemp=false;
		RedBlackNode u = null;
		if(isLeft && parent.getLeftChild()!=null){
			u=parent.getLeftChild();
		}
		else if(!isLeaf && parent.getRightChild()!=null){
			u=parent.getRightChild();
		}
		else{
			u= new RedBlackNode(0, parent);
			isUtemp=true;
			if(isLeft){
				parent.setLeftChild(u);
			}
			else{
				parent.setRightChild(u);
			}
			
		}
		if(color.equals(Color.RED) || u!= null && u.getColor().equals(Color.RED)){
			u.setColor(Color.RED);
			return;
		}
		
		u.setColor(Color.DOUBLEBLACK);
		doubleBlack(u);
		if(isUtemp){
			if(u.isLeftChild()){
				u.getParent().setLeftChild(null);
			}
			else{
				u.getParent().setRightChild(null);
			}
		}
		
	}
	private void doubleBlack(RedBlackNode u){
		RedBlackNode s = u.getSibling();
		if(s==null || s.getColor().equals(Color.BLACK)){
			//handle s==null;
			//moguce je da s niakda nece biti null, ako bude pucalo handlat ovdje;
			if( s !=null && s.hasRedChildren()){
				//slucaj 3.2 a
				u.setColor(Color.BLACK);
				if(s.isLeftChild()){
					if(s.getLeftChild()!=null && s.getLeftChild().getColor().equals(Color.RED)){
						rotate(1, s);
					}
					/*
					 * if(node.isLeftChild() && parent.isLeftChild()){
				//LL = 1
				parent.switchColor();
				parent.getParent().switchColor();
				rotate(2, parent.getParent());
				
			}
			else if(!node.isLeftChild() && parent.isLeftChild()){
				//LR 
				rotate(1, parent);
				node.switchColor();
				node.getParent().switchColor();
				rotate(2, node.getParent());
			}
			else if(node.isLeftChild() && !parent.isLeftChild()){
				//RL
				rotate(2, parent);
				node.switchColor();
				node.getParent().switchColor();
				rotate(1, node.getParent());
			}
			else {
				//RR
				parent.switchColor();
				parent.getParent().switchColor();
				rotate(1, parent.getParent());
			}
					 */
					else{
						rotate(1, s);
						rotate(2, s.getParent());
					}
				}
				else{
					if(s.getRightChild()!=null && s.getRightChild().getColor().equals(Color.RED)){
						rotate(2,s);
					}
					else{
						rotate(2, s);
						rotate(1, s.getParent());
					}
				}
			}
			else if (s!=null && s.getColor().equals(Color.RED)){
				//3.2 b
				s.setColor(Color.RED);
				if(s.getParent().getColor().equals(Color.BLACK)){
					s.getParent().setColor(Color.DOUBLEBLACK);
					doubleBlack(s.getParent());
				}
				else{
					s.getParent().setColor(Color.BLACK);
				}
				
			}
			else{
				if(u.getParent().getColor().equals(Color.BLACK)){
					u.getParent().setColor(Color.DOUBLEBLACK);
					doubleBlack(u.getParent());
				}
				else{
					u.getParent().setColor(Color.BLACK);
				}
			}
		}
		else{
			//slucaj 3.2c
			s.switchColor();
			s.getParent().switchColor();
			if(s.isLeftChild()){
				rotate(1,s);
			}
			else{
				rotate(2,s);
			}
			doubleBlack(u);
		}
	}
	private void insert(int value , RedBlackNode node){
		RedBlackNode newNode =null;
		int nodeValue=node.getValue();
		if(nodeValue>=value){
			if(node.getLeftChild()!=null){
				insert(value, node.getLeftChild());
				return;
			}
			else{
				newNode=new RedBlackNode(value, node);
				node.setLeftChild(newNode);
			}
		}
		else{
			if(node.getRightChild()!=null){
				insert(value, node.getRightChild());
				return;
			}
			else{
				newNode=new RedBlackNode(value, node);
				node.setRightChild(newNode);
			}
		}
		recolorAndRotate(newNode);
		
		
		
	}
	
	private RedBlackNode search (int value, RedBlackNode node){
		
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
	
	private void recolorAndRotate(RedBlackNode node){
		
		if(node.equals(treeRoot)){
			node.setColor(Color.BLACK);
			return;
		}
		
		if(!node.getParent().getColor().equals(Color.RED)){
			return;		
		}
		
		if(node.getUncle()!= null && node.getUncle().getColor().equals(Color.RED)){
			//System.out.println(node.getUncle().getValue());
			RedBlackNode grandParent =node.getParent().getParent();
			node.getUncle().setColor(Color.BLACK);
			node.getParent().setColor(Color.BLACK);
			if(grandParent!=null && grandParent.getParent()!= null){
				grandParent.setColor(Color.RED);
				recolorAndRotate(grandParent);
			}
		}
		else{
			RedBlackNode parent = node.getParent();
			//ipak sam moram raditi logiku s obzirom na polzaj jer avl koristi balance kojeg rbtree nema
			//relativno lagan switch case,ne zabopraviti recolor prije rotacija
			//tusam
			if(node.isLeftChild() && parent.isLeftChild()){
				//LL = 1
				parent.switchColor();
				parent.getParent().switchColor();
				rotate(2, parent.getParent());
				
			}
			else if(!node.isLeftChild() && parent.isLeftChild()){
				//LR 
				rotate(1, parent);
				node.switchColor();
				node.getParent().switchColor();
				rotate(2, node.getParent());
			}
			else if(node.isLeftChild() && !parent.isLeftChild()){
				//RL
				rotate(2, parent);
				node.switchColor();
				node.getParent().switchColor();
				rotate(1, node.getParent());
			}
			else {
				//RR
				parent.switchColor();
				parent.getParent().switchColor();
				rotate(1, parent.getParent());
			}
		}
		this.getTreeRoot().setColor(Color.BLACK);
	}
	
	//1 = rotate left, 2 = rotate right, 3 = double left rotation, 4 = double right rotation;
		private void rotate(int rotationType, RedBlackNode node){

			RedBlackNode current =node;
			switch(rotationType){
			case 1: 
				//node.switchColor();
				//node.getParent().switchColor();
				RedBlackNode rightChild= node.getRightChild();
				
				if(node.getParent()!=null){
					RedBlackNode parent = node.getParent();
					//1korak
					if(node.isLeftChild()){
						parent.setLeftChild(rightChild);
					}
					else{
						parent.setRightChild(rightChild);
					}
					if(parent!=null && rightChild!=null){
						rightChild.setParent(parent);
					}
					
				}
				else{
					//1korak
					this.treeRoot=rightChild;
					rightChild.setParent(null);
				}
				//2korak
				if(rightChild!=null && rightChild.getLeftChild()!=null){
					rightChild.getLeftChild().setParent(current);
					current.setRightChild(rightChild.getLeftChild());
				}
				else{
					current.setRightChild(null);
				}
				//3korak
				if(rightChild!=null){
					rightChild.setLeftChild(current);
					
				}
				current.setParent(rightChild);
				break;
			case 2 : 

				//node.switchColor();
				//node.getParent().switchColor();
				RedBlackNode leftChild=node.getLeftChild();
				
				if(node.getParent()!=null){
					RedBlackNode parent = node.getParent();
					//1korak
					if(node.isLeftChild()){
						parent.setLeftChild(leftChild);
					}
					else{
						parent.setRightChild(leftChild);
					}
					if(parent!=null && leftChild!=null){
						leftChild.setParent(parent);
					}
					
				}
				else{
					//1korak
					this.treeRoot=leftChild;
					leftChild.setParent(null);
				}
				//2korak
				if(leftChild!=null && leftChild.getRightChild()!=null){
					leftChild.getRightChild().setParent(current);
					current.setLeftChild(leftChild.getRightChild());
				}
				else{
					current.setLeftChild(null);
				}
				//3korak
				if(leftChild!=null){
					leftChild.setRightChild(current);
					
				}
				current.setParent(leftChild);
				break;
			case 3 : 
				node.switchColor();
				node.getParent().switchColor();
				rotate(2, node);
				rotate(1, node);
				break;
			case 4 : 
				node.switchColor();
				node.getParent().switchColor();
				rotate(1, node);
				rotate(2, node.getParent());
				break;
			}
		}
		
		public void test (RedBlackNode node, int i){
			for(int n=0; n<i;n++){
				System.out.print(" ");
			}
			System.out.println(Integer.toString(node.getValue())+" "+node.getColor());
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
