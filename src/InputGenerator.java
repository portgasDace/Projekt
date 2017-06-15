import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class InputGenerator {

	public static void main(String [ ] args) 
	{
		BufferedWriter writer = null;
		int i;
		int n=700;
		try
		{
			writer=new BufferedWriter( new FileWriter( "input.txt"));
			Random rg = new Random();
			for(i=0;i<n;i++){
				//rg.nextInt(20*n)
				writer.write( Integer.toString(rg.nextInt(20*n)));
				if(i!=n-1){
					writer.newLine();
				}
			}

		}
		catch ( IOException e)
		{
		}
		finally
		{
			try
			{
				if ( writer != null)
					writer.close( );
			}
			catch ( IOException e)
			{
			}
		}
	}

}
