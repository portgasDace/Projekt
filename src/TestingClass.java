import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class TestingClass {


	//insert podatke iz filea u sva stabla, biljeziti vrijeme! napraviti to n puta i zbrajati vremena
	//vrijeme inserta  = ukupno vrijeme/n
	//int x = random(range), search x ! (sve puta n, biljezi)
	//vrijeme search = ukupno/n
	//int x = random (range) ;
	//analogno
	//spremi podatke u nekakvu statisktu
	//ovo cemo raditi za razlicite input fileove

	public static void main(String [ ] args) throws FileNotFoundException 
	{
		int runsNumber= 10;
		int offset=1 ;
		int range = 700;
		int count=10;
		ArrayList<Long> resultList = new ArrayList<Long>();
		for(int k=0;k<12;k++){
			resultList.add((long)0);
		}
		for(int i =0;i<runsNumber+offset;i++){
			System.out.println(i);
			InputGenerator.main(null);
			ArrayList<Long> templist = new ArrayList<>();
			getResult(templist, count , range);
			if(i>=offset){
				for(int j=0;j<12;j++){
					resultList.set(j, resultList.get(j)+templist.get(j)); 
				}
			}
			templist.clear();
		}
		for(int j=0;j<12;j++){
			resultList.set(j, resultList.get(j)/runsNumber); 
		}
		System.out.println("Experiment stats :");
		System.out.println("Number of tree nodes :"+range);
		System.out.println("Number of search, insert and delete opeartions in one experiment :"+count);
		System.out.println("Number of experiments :"+runsNumber);
		System.out.println("Times are given in nano seconds. ");
		System.out.println("AVL Tree insert time: "+resultList.get(0));
		System.out.println("Unbalanced Tree insert time: "+resultList.get(1));
		System.out.println("Red Black Tree insert time: "+resultList.get(2));
		System.out.println("Splay Tree insert time: "+resultList.get(3));
		System.out.println("AVL Tree search time: "+resultList.get(4));
		System.out.println("Unbalanced Tree search time: "+resultList.get(5));
		System.out.println("Red Black Tree search time: "+resultList.get(6));
		System.out.println("Splay Tree search time: "+resultList.get(7));
		System.out.println("AVL Tree delete time: "+resultList.get(8));
		System.out.println("Unbalanced Tree delete time: "+resultList.get(9));
		System.out.println("Red Black Tree delete time: "+resultList.get(10));
		System.out.println("Splay Tree delete time: "+resultList.get(11));




	}


	private static  void getResult (ArrayList<Long> list, int count,  int range) throws FileNotFoundException{


		long AVLInsertResultTime=0;
		long AVLSearchResultTime=0;
		long AVLDeleteResultTime=0;
		long UnbInsertResultTime=0;
		long UnbSearchResultTime=0;
		long UnbDeleteResultTime=0;
		long RBInsertResultTime=0;
		long RBSearchResultTime=0;
		long RBDeleteResultTime=0;
		long SplayInsertResultTime=0;
		long SplaySearchResultTime=0;
		long SplayDeleteResultTime=0;
		
		ArrayList<Integer> nodeList = new ArrayList<>();
		int i;

		AVLTree avlTree = new AVLTree();
		UnbalancedTree unbTree = new UnbalancedTree();
		RedBlackTree rbTree = new RedBlackTree();
		SplayTree splayTree = new SplayTree();

		//avl init
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader("input.txt"));
		try {
			String line = br.readLine();
			while(line!=null){

				avlTree.insert(Integer.parseInt(line));

				line=br.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//avlTree.test(avlTree.getTreeRoot(), 0);
		//unbalaced init
		@SuppressWarnings("resource")
		BufferedReader br2 = new BufferedReader(new FileReader("input.txt"));
		try {
			String line = br2.readLine();
			while(line!=null){

				unbTree.insert(Integer.parseInt(line));

				line=br2.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//rbtree
		@SuppressWarnings("resource")
		BufferedReader br3 = new BufferedReader(new FileReader("input.txt"));
		try {
			String line = br3.readLine();
			while(line!=null){
				
				rbTree.insert(Integer.parseInt(line));

				line=br3.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
		//splay init
		@SuppressWarnings("resource")
		BufferedReader br4 = new BufferedReader(new FileReader("input.txt"));
		try {
			String line = br4.readLine();
			while(line!=null){

				splayTree.insert(Integer.parseInt(line));

				line=br4.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//init list of random nodes
		for(i=0;i<count;i++){
			Random randomGenerator = new Random();
			int rand = randomGenerator.nextInt(range);
			nodeList.add(rand);
		}

		
		//unbalanced insert
		long  startTimeUnbInsert= System.nanoTime();
		for(i=0;i<count;i++){
			unbTree.insert(nodeList.get(i));
		}
		UnbInsertResultTime+=System.nanoTime()-startTimeUnbInsert;	

		//unbalanced search
		long  startTimeUnbSearch= System.nanoTime();
		for(int j=0; j<200; j++){
			for(i=0;i<count;i++){
				unbTree.search(nodeList.get(j%3));
			}
		}
		
		UnbSearchResultTime+=System.nanoTime()-startTimeUnbSearch;	

		//unbalanced delete
		long  startTimeUnbDelete= System.nanoTime();
		for(i=0;i<count;i++){
			unbTree.delete(nodeList.get(i));
		}
		UnbDeleteResultTime+=System.nanoTime()-startTimeUnbDelete;
		
		
		//redblack search
				long  startTimeRBSearch= System.nanoTime();
				for(int j=0; j<200; j++){
					for(i=0;i<count;i++){
						rbTree.search(nodeList.get(j%3));
					}
				}
				
				RBSearchResultTime+=System.nanoTime()-startTimeRBSearch;	

		//redblack insert
		long  startTimeRBInsert= System.nanoTime();
		for(i=0;i<count;i++){
		rbTree.insert(nodeList.get(i));
		}
		RBInsertResultTime+=System.nanoTime()-startTimeRBInsert;	

		
		//redblack delete
		long  startTimeRBDelete= System.nanoTime();
		for(i=0;i<count;i++){
			rbTree.delete(nodeList.get(i));
		}
		RBDeleteResultTime+=System.nanoTime()-startTimeRBDelete;
		
		//splay insert
		long  startTimeSplayInsert= System.nanoTime();
		for(i=0;i<count;i++){
			splayTree.insert(nodeList.get(i));
		}
		SplayInsertResultTime+=System.nanoTime()-startTimeSplayInsert;	

		//splay search
		long  startTimeSplaySearch= System.nanoTime();
		for(int j=0; j<200; j++){
			for(i=0;i<count;i++){
				splayTree.search(nodeList.get(j%3));
			}
		}
		
		SplaySearchResultTime+=System.nanoTime()-startTimeSplaySearch;	

		//splay delete
		long  startTimeSplayDelete= System.nanoTime();
		for(i=0;i<count;i++){
			splayTree.delete(nodeList.get(i));
		}
		SplayDeleteResultTime+=System.nanoTime()-startTimeSplayDelete;
		
		//avl insert
				long  startTimeAVLInsert= System.nanoTime();
				for(i=0;i<count;i++){
					avlTree.insert(nodeList.get(i));
				}
				AVLInsertResultTime+=System.nanoTime()-startTimeAVLInsert;	

				

				//avl delete
				long  startTimeAVLDelete= System.nanoTime();
				for(i=0;i<count;i++){
					avlTree.delete(nodeList.get(i));
				}
				AVLDeleteResultTime+=System.nanoTime()-startTimeAVLDelete;	
				
				//avl search
				long  startTimeAVLSearch= System.nanoTime();
				for(int j=0; j<200; j++){
					for(i=0;i<count;i++){
						avlTree.search(nodeList.get(j%3));
					}
				}
				
				AVLSearchResultTime+=System.nanoTime()-startTimeAVLSearch;	



		//get arithemtic result time
		if(count!=0){
			AVLSearchResultTime = AVLSearchResultTime / (200*count);
			UnbSearchResultTime = UnbSearchResultTime / (200*count);
			RBSearchResultTime = RBSearchResultTime / (200*count);
			SplaySearchResultTime = SplaySearchResultTime / (200*count);
			AVLDeleteResultTime = AVLDeleteResultTime / count;
			UnbDeleteResultTime = UnbDeleteResultTime / count;
			RBDeleteResultTime = RBDeleteResultTime / count;
			SplayDeleteResultTime = SplayDeleteResultTime / count;
			AVLInsertResultTime = AVLInsertResultTime / count;
			UnbInsertResultTime = UnbInsertResultTime / count;
			RBInsertResultTime =RBInsertResultTime / count;
			SplayInsertResultTime = SplayInsertResultTime / count;
			
		}


		//list init
		list.clear();
		for(int k=0;k<12;k++){
			list.add((long)0);
		}

		//update list;
		list.set(0, AVLInsertResultTime);
		list.set(1, UnbInsertResultTime);
		list.set(2, RBInsertResultTime);
		list.set(3, SplayInsertResultTime);
		list.set(4, AVLSearchResultTime);
		list.set(5, UnbSearchResultTime);
		list.set(6, RBSearchResultTime);
		list.set(7, SplaySearchResultTime);
		list.set(8, AVLDeleteResultTime);
		list.set(9, UnbDeleteResultTime);
		list.set(10, RBDeleteResultTime);
		list.set(11, SplayDeleteResultTime);

		return ;

	}
}
