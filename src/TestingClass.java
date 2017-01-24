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
		int runsNumber= 20;
		int offset=2 ;
		int sCount=100;
		int dCount = 0;
		int range = 100000;
		ArrayList<Long> resultList = new ArrayList<Long>();
		for(int k=0;k<6;k++){
			resultList.add((long)0);
		}
		for(int i =0;i<runsNumber+offset;i++){
			
			InputGenerator.main(null);
			ArrayList<Long> templist = new ArrayList<>();
			getResult(templist, sCount, dCount, range);
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
		System.out.println("Number of search opeartions in one experiment :"+sCount);
		System.out.println("Number of delete opeartions in one experiment :"+dCount);
		System.out.println("Number of experiments :"+runsNumber);
		System.out.println("Times are given in nano seconds. ");
		System.out.println("AVL Tree insert time: "+resultList.get(0));
		System.out.println("Unbalanced Tree insert time: "+resultList.get(1));
		System.out.println("AVL Tree search time: "+resultList.get(2));
		System.out.println("Unbalanced Tree search time: "+resultList.get(3));
		System.out.println("AVL Tree delete time: "+resultList.get(4));
		System.out.println("Unbalanced Tree delete time: "+resultList.get(5));




	}


	private static  void getResult (ArrayList<Long> list, int sCount, int dCount, int range) throws FileNotFoundException{


		long AVLInsertResultTime=0;
		long AVLSearchResultTime=0;
		long AVLDeleteResultTime=0;
		long UnbInsertResultTime=0;
		long UnbSearchResultTime=0;
		long UnbDeleteResultTime=0;
		int i;


		//insert
		AVLTree avlTree = new AVLTree();
		UnbalancedTree unbTree = new UnbalancedTree();

		//avl
		long  startTimeAVLInsert= System.nanoTime();
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
		AVLInsertResultTime+=System.nanoTime()-startTimeAVLInsert;
		   //unbalaced
		long startTimeUnbInsert= System.nanoTime();
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
		UnbInsertResultTime+=System.nanoTime()-startTimeUnbInsert;

		//Random randomGenerator = new Random();
		//search
		
		for(i=0;i<sCount;i++){
			Random randomGenerator = new Random();
			int rand = randomGenerator.nextInt(range);
			
			
			//avl
			long startTimeAVLSearch= System.nanoTime();
			
			avlTree.search(rand);
			
			
			AVLSearchResultTime += System.nanoTime()-startTimeAVLSearch;
			
			


			//unbalanced
			long startTimeUnbSearch= System.nanoTime();
			unbTree.search(rand);

			UnbSearchResultTime+=System.nanoTime()-startTimeUnbSearch;

		}


		//delete

		for(i=0;i<dCount;i++){
			Random randomGenerator = new Random();
			int rand = randomGenerator.nextInt(range);

			//avl
			long startTimeAVLDelete= System.nanoTime();
			avlTree.delete(rand);

			AVLDeleteResultTime += System.nanoTime()-startTimeAVLDelete;


			//unbalanced
			long startTimeUnbDelete= System.nanoTime();
			unbTree.delete(rand);

			UnbDeleteResultTime+=System.nanoTime()-startTimeUnbDelete;

		}

		//get arithemtic result time
		if(sCount!=0){
			AVLSearchResultTime = AVLSearchResultTime / sCount;
			UnbSearchResultTime = UnbSearchResultTime / sCount;
		}
		if(dCount!=0){
			AVLDeleteResultTime = AVLDeleteResultTime / dCount;
			UnbDeleteResultTime = UnbDeleteResultTime / dCount;
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
