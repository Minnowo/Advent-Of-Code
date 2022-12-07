import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day5 
{
    public static void printTop(char[][] table)
    {
        for(int i = 0, col = 0; i < table.length && col < 9; i++)
        {
            if(table[i][col] != ' ')
            {
                System.out.print(table[i][col]);
                i = 0;
                col++;
            }
        }
        System.out.println();
    }

    public static void printTable(char[][] table)
    {
        for(int i = 0; i < table.length; i++)
        {
            System.out.println(Arrays.toString(table[i]));
        }
    }

    public static char[][] copyTable(char[][] table)
    {
        char[][] newTable = new char[table.length][9];

        for(int i=0; i < table.length; i++)
        for(int j=0; j < table[i].length; j++)
            newTable[i][j] = table[i][j];

        return newTable;
    }

    public static char[][] resizeTable(char[][] table, int increase_by)
    {
        char[][] newTable = new char[table.length + increase_by][9];
                        
        for(int i = 0; i < increase_by; i++)
            Arrays.fill(newTable[i], ' ');

        for(int i = increase_by; i < newTable.length; i++)
        {
            newTable[i] = table[i - increase_by];
            table[i - increase_by] = null;
        }

        return newTable;
    }

    public static void main(String[] args) throws IOException
    {
        Scanner reader = new Scanner(new File("./input.txt"));

        boolean tableParsed = false;

        int lineNumber = 0;

        final char[][] TABLE_OG = new char[9][9];
        char[][] TABLE_PART_A = null;
        char[][] TABLE_PART_B = null;

        final Pattern INSTRUCTION_PARSER = Pattern.compile("^move (\\d+) from (\\d+) to (\\d+)");

        while(reader.hasNextLine())
        {
            final String LINE = reader.nextLine();

            if(!tableParsed && LINE.isBlank())
            {
                tableParsed = true;
                TABLE_PART_A = copyTable(TABLE_OG);
                TABLE_PART_B = copyTable(TABLE_OG);
                continue;
            }

            if(!tableParsed)
            {
                for(int i = 1, k = 0; i < LINE.length(); i += 4, k++)
                {
                    TABLE_OG[lineNumber][k] = LINE.charAt(i);
                }
                lineNumber++;
                continue;
            }

            final Matcher m = INSTRUCTION_PARSER.matcher(LINE);
            
            if(!m.matches())
                throw new IOException("bad input data");

            final int COUNT = Integer.parseInt(m.group(1));
            final int FROM  = Integer.parseInt(m.group(2)) - 1;
            final int TO    = Integer.parseInt(m.group(3)) - 1;

            // ------- part a here -------

            // loop as many times as we have count
            for(int c = 0; c < COUNT; c++)
            {
                // value we're taking
                char from = ' '; 

                for(int i = 0; i < TABLE_PART_A.length; i++)
                {
                    if(TABLE_PART_A[i][FROM] == ' ')
                        continue;

                    from = TABLE_PART_A[i][FROM];
                    TABLE_PART_A[i][FROM] = ' ';
                    break;
                }

                // there is nothing to take
                if(from == ' ')
                    continue;

                for(int i = 0; i < TABLE_PART_A.length; i++)
                {
                    if(TABLE_PART_A[i][TO] == ' ')
                        continue;

                    // ahh, this is really so good
                    // make a new array with more size, and copy pointer from old array into that
                    // then set the pointer in the old array to null
                    if(i == 0)
                    {
                        // resize by 1 and add our value
                        TABLE_PART_A = resizeTable(TABLE_PART_A, 1);

                        TABLE_PART_A[0][TO] = from;

                        break;
                    }

                    // add our value at the first whitespace spot, (ie i-1)
                    TABLE_PART_A[i - 1][TO] = from;
                    break;
                }
            }


            // ------- part b starts here -------

            // counts how many items we're moving
            int count = 0;

            // holds the items we're moving
            char[] move = new char[COUNT];

            // get as many items as we can move
            for(int i = 0; i < TABLE_PART_B.length && count != COUNT; i++)
            {
                if(TABLE_PART_B[i][FROM] == ' ')
                    continue;

                // insert into our move array and inc the count
                move[count++] = TABLE_PART_B[i][FROM];

                // remove from the column we're taking from
                TABLE_PART_B[i][FROM] = ' ';
            }

            // the index of the last actual element (ie not ' ')
            int moveIndex = count - 1;

            for(int i = 0; i < TABLE_PART_B.length; i++)
            {
                if(TABLE_PART_B[i][TO] == ' ')
                    continue;

                // i-1 is the count of ' '
                // so if we are moving more items than blank spots
                // we need more space in the table
                if(count > i - 1)
                {
                    // resize our table
                    TABLE_PART_B = resizeTable(TABLE_PART_B, Math.abs(i - 1 - count));

                    // nice and easy fill since we know the size matches perfectly
                    for(i = 0; i < count; i++)
                    {
                        TABLE_PART_B[i][TO] = move[i];
                    }
                    break;
                }

                // ugly for loop but it doesn't insert in the right spot
                for(; i > 0 && moveIndex >= 0; i--)
                {
                    TABLE_PART_B[i - 1][TO] = move[moveIndex--];
                }
                break;
            }
        }

        printTop(TABLE_PART_A);
        printTop(TABLE_PART_B);
    }    
}
