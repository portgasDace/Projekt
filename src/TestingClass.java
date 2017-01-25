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
		int range = 2000000;
		int count=range/1000;
		ArrayList<Long> resultList = new ArrayList<Long>();
		for(int k=0;k<6;k++){
			resultList.add((long)0);
		}
		for(int i =0;i<runsNumber+offset;i++){
			System.out.println(i);
			InputGenerator.main(null);
			ArrayList<Long> templist = new ArrayList<>();
			getResult(templist, count , range);
			if(i>=offset){
				for(int j=0;j<6;j++){
					resultList.set(j, resultList.get(j)+templist.get(j)); 
				}
			}
			templist.clear();
		}
		for(int j=0;j<6;j++){
			resultList.set(j, resultList.get(j)/runsNumber); 
		}
		System.out.println("Experiment stats :");
		System.out.println("Number of tree nodes :"+range);
		System.out.println("Number of search, insert and delete opeartions in one experiment :"+count);
		System.out.println("Number of experiments :"+runsNumber);
		System.out.println("Times are given in nano seconds. ");
		System.out.println("AVL Tree insert time: "+resultList.get(0));
		System.out.println("Unbalanced Tree insert time: "+resultList.get(1));
		System.out.println("AVL Tree search time: "+resultList.get(2));
		System.out.println("Unbalanced Tree search time: "+resultList.get(3));
		System.out.println("AVL Tree delete time: "+resultList.get(4));
		System.out.println("Unbalanced Tree delete time: "+resultList.get(5));




	}


	private static  void getResult (ArrayList<Long> list, int count,  int range) throws FileNotFoundException{


		long AVLInsertResultTime=0;
		long AVLSearchResultTime=0;
		long AVLDeleteResultTime=0;
		long UnbInsertResultTime=0;
		long UnbSearchResultTime=0;
		long UnbDeleteResultTime=0;
		ArrayList<Integer> nodeList = new ArrayList<>();
		int i;

		AVLTree avlTree = new AVLTree();
		UnbalancedTree unbTree = new UnbalancedTree();

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

		//init list of random nodes
		for(i=0;i<count;i++){
			Random randomGenerator = new Random();
			int rand = randomGenerator.nextInt(range);
			nodeList.add(rand);
		}

		//avl insert
		long  startTimeAVLInsert= System.nanoTime();
		for(i=0;i<count;i++){
			avlTree.insert(nodeList.get(i));
		}
		AVLInsertResultTime+=System.nanoTime()-startTimeAVLInsert;	

		//avl search
		long  startTimeAVLSearch= System.nanoTime();
		for(i=0;i<count;i++){
			avlTree.search(nodeList.get(i));
		}
		AVLSearchResultTime+=System.nanoTime()-startTimeAVLSearch;	

		//avl delete
		long  startTimeAVLDelete= System.nanoTime();
		for(i=0;i<count;i++){
			avlTree.delete(nodeList.get(i));
		}
		AVLDeleteResultTime+=System.nanoTime()-startTimeAVLDelete;			


		//unbalanced insert
		long  startTimeUnbInsert= System.nanoTime();
		for(i=0;i<count;i++){
			unbTree.insert(nodeList.get(i));
		}
		UnbInsertResultTime+=System.nanoTime()-startTimeUnbInsert;	

		//unbalanced search
		long  startTimeUnbSearch= System.nanoTime();
		for(i=0;i<count;i++){
			unbTree.search(nodeList.get(i));
		}
		UnbSearchResultTime+=System.nanoTime()-startTimeUnbSearch;	

		//unbalanced delete
		long  startTimeUnbDelete= System.nanoTime();
		for(i=0;i<count;i++){
			unbTree.delete(nodeList.get(i));
		}
		UnbDeleteResultTime+=System.nanoTime()-startTimeUnbDelete;

		//get arithemtic result time
		if(count!=0){
			AVLSearchResultTime = AVLSearchResultTime / count;
			UnbSearchResultTime = UnbSearchResultTime / count;
			AVLDeleteResultTime = AVLDeleteResultTime / count;
			UnbDeleteResultTime = UnbDeleteResultTime / count;
			AVLInsertResultTime = AVLInsertResultTime / count;
			UnbInsertResultTime = UnbInsertResultTime / count;
		}


		//list init
		list.clear();
		for(int k=0;k<6;k++){
			list.add((long)0);
		}

		//update list;
		list.set(0, AVLInsertResultTime);
		list.set(1, UnbInsertResultTime);
		list.set(2, AVLSearchResultTime);
		list.set(3, UnbSearchResultTime);
		list.set(4, AVLDeleteResultTime);
		list.set(5, UnbDeleteResultTime);

		return ;

	}
}
