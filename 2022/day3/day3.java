import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class day3 
{
    public static int toScore(int value)
    {
        if('a' <= value && value <= 'z')
        {
            return value - 'a' + 1;
        }

        return value - 'A' + 1 + 26;
    }

    public static void main(String[] args) throws IOException
    {
        Scanner reader = new Scanner(new File("./input.txt"));

        int totalScore = 0;
        int totalScore2 = 0;

        int index = 0;

        final char[][] lines = new char[3][];

        while(reader.hasNextLine())
        {
            String line = reader.nextLine();

            lines[index] = line.toCharArray();

            char[] left  = Arrays.copyOf(lines[index], line.length() / 2);
            char[] right = Arrays.copyOfRange(lines[index], line.length() / 2, line.length());

            index = (index + 1) % 3;

            HashSet<Character> hs = new HashSet<>();

            int value = 0;

            for(int i = 0; i < left.length; i++)
            {
                hs.add(left[i]);
            }

            for(int i = 0; i < left.length; i++)
            {
                if(hs.contains(right[i]))
                {
                    value = (int)right[i];
                    break;
                }
            }

            totalScore += toScore(value);

            if(index != 0)
                continue;

            for(char c : right)
            {
                hs.add(c);
            }

            final char[] fst = lines[0];
            final char[] scd = lines[1];

            // i cannot think of a more efficient way than nested loop ;w;
            for(int i = 0; i < fst.length; i++)
            for(int j = 0; j < scd.length; j++)
            {
                if(fst[i] == scd[j] && hs.contains(fst[i]))
                {
                    value = fst[i];
                    i = fst.length;
                    break;
                }
            }

            totalScore2 += toScore(value);
        }

        System.out.println("Total Sum A: " + totalScore);
        System.out.println("Total Sum B: " + totalScore2);

    }
}
