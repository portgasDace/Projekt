

public class Debug {
	public static void main(String [ ] args)  
	{
		
	int n=10;
	SplayTree rbTree=new SplayTree();
	
	//for(int j=1; j<n+1;j++){
		//rbTree.insert(j);
	//}
	rbTree.insert(5);
	rbTree.insert(3);
	//rbTree.test(rbTree.getTreeRoot(), 0);
	rbTree.insert(7);
	//rbTree.test(rbTree.getTreeRoot(), 0);
    rbTree.insert(11);
    
	rbTree.insert(2);
	
	rbTree.insert(9);
	rbTree.insert(4);
	rbTree.test(rbTree.getTreeRoot(), 0);
	
	
	
	}
}
