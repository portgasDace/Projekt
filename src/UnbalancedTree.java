
public class UnbalancedTree {

	public UnbalancedTree(){}
	
	public UnbalancedNode treeRoot;

	public UnbalancedNode getTreeRoot() {
		return treeRoot;
	}

	public void setTreeRoot(UnbalancedNode treeRoot) {
		this.treeRoot = treeRoot;
	}

	private void insert(int value, UnbalancedNode node){

		int nodeValue=node.getValue();
		if(nodeValue >= value){
			if(node.getLeftChild()!= null){
				insert(value, node.getLeftChild());
			}
			else{
				node.setLeftChild(new UnbalancedNode(value, node));
			}
		}
		else{
			if(node.getRightChild()!=null){
				insert(value, node.getRightChild());
			}
			else{
				node.setRightChild(new UnbalancedNode(value, node));
			}
		}
	}
	private void delete(int value, UnbalancedNode node){
		if(node.getValue()<value){
			if(node.getLeftChild()!=null){
				delete(value, node.getLeftChild());
			}
			else{
				//greska, ne postoji cvor koji se treba izbrisati;
			}
		}
		else if(node.getValue()>value){
			if(node.getRightChild()!=null){
				delete(value, node.getRightChild());
			}
			else{
				//greska, ne postoji cvor koji se treba izbrisati;
			}
		}
		else{
			// no children
			if(node.getLeftChild()==null && node.getRightChild() ==null){
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
				UnbalancedNode switchNode = node.getLeftChild();
				while(switchNode.getRightChild()!=null){
					switchNode=switchNode.getRightChild();
				}
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
		}
	}

	private UnbalancedNode search (int value, UnbalancedNode node){

		if(node.getValue()==value){
			return node;
		}
		else if(node.getValue()>=value){
			if(node.getRightChild()!=null){
				return search(value, node.getRightChild());
			}
			else{
				//greska, node ne postoji
				return null;
			}
		}
		else{
			if(node.getLeftChild()!=null){
				return search(value, node.getLeftChild());
			}
			else{
				//greska, node ne postoji
				return null;
			}
		}
	}

	public void insert (int value){
		if(getTreeRoot()!=null){
			insert(value, getTreeRoot());
		}
		else{
			setTreeRoot(new UnbalancedNode(value));
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

	public UnbalancedNode search (int value){
		if(getTreeRoot()!=null){
			return search(value, getTreeRoot());
		}
		else{
			return null;
		}
	}
	public int size (UnbalancedNode node, int i){


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
	public void test (UnbalancedNode node, int i){
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
