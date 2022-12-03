import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

public class day1 
{
    public static void main(String[] args) throws FileNotFoundException 
    {
        Scanner reader = new Scanner(new File("./input.txt"));

        int[] maxCalsOnElf = new int[] { 0, 0, 0 };
        int currentElfCals = 0;

        while (reader.hasNextLine()) 
        {
            String line = reader.nextLine();

            if(line.isEmpty())
            {
                if(currentElfCals > maxCalsOnElf[0])
                {
                    // the array is in sorted order, so we need to shift everything
                    maxCalsOnElf[2] = maxCalsOnElf[1];
                    maxCalsOnElf[1] = maxCalsOnElf[0];
                    maxCalsOnElf[0] = currentElfCals;
                }
                else if(currentElfCals > maxCalsOnElf[1])
                {
                    maxCalsOnElf[2] = maxCalsOnElf[1];
                    maxCalsOnElf[1] = currentElfCals;
                }
                else if(currentElfCals > maxCalsOnElf[2])
                {
                    maxCalsOnElf[2] = currentElfCals;
                }

                currentElfCals = 0;
            }
            else 
            {
                try
                {
                    currentElfCals += Integer.parseInt(line);
                }
                catch (NumberFormatException ex) 
                {
                }
            }
        }
        
        System.out.println("Max cals: " + maxCalsOnElf[0]);
        System.out.println("Top 3 Cals: " + (maxCalsOnElf[0] + maxCalsOnElf[1] + maxCalsOnElf[2]));
    }
}