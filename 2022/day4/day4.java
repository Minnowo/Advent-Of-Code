import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day4 
{
    public static void main(String[] args) throws IOException
    {
        Scanner reader = new Scanner(new File("./input.txt"));

        // regex that matches a-b,x-y where a, b, x, y are any number
        final Pattern PAIRED_PAIR_MATCHER = Pattern.compile("^(\\d+)-(\\d+),(\\d+)-(\\d+)$");

        int pairOverlapCount1 = 0;
        int pairOverlapCount2 = 0;

        while(reader.hasNextLine())
        {
            final String LINE = reader.nextLine();

            final Matcher m = PAIRED_PAIR_MATCHER.matcher(LINE);
            
            if(!m.matches())
                throw new IOException("no regex match");

            final int A = Integer.parseInt(m.group(1)); 
            final int B = Integer.parseInt(m.group(2)) ;

            final int X = Integer.parseInt(m.group(3));
            final int Y = Integer.parseInt(m.group(4));

            // range a-b contains x-y 
            // or
            // range x-y contains a-b
            if(A <= X && B >= Y ||
               X <= A && Y >= B)
            {
                pairOverlapCount1++;
                pairOverlapCount2++;
            }

            // any of the upper or lower bounds ==
            // then there exists an overlap
            else if(A == X || A == Y ||
                    B == X || B == Y)
            {
                pairOverlapCount2++;
            }

            // range a-b contains x
            // or
            // range x-y contains a
            else if(A < X && X < B ||
                    X < A && A < Y)
            {
                pairOverlapCount2++;
            }
        }

        System.out.println("Pair Overlap: " + pairOverlapCount1);
        System.out.println("Pair Overlap: " + pairOverlapCount2);
    }    
}
