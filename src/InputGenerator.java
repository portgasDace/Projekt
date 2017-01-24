import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class InputGenerator {

	public static void main(String [ ] args) 
	{
		BufferedWriter writer = null;
		int i;
		int n=100000;
		try
		{
			writer=new BufferedWriter( new FileWriter( "input.txt"));
			Random rg = new Random();
			for(i=0;i<n;i++){
				writer.write( Integer.toString(rg.nextInt(n)));
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
