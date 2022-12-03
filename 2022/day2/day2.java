import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class day2 
{
    // what your opponent will play 
    static final char ROCK_1     = 'A';
    static final char PAPER_1    = 'B';
    static final char SCISSORS_1 = 'C';

    // what you should play
    static final char ROCK_2     = 'X';
    static final char PAPER_2    = 'Y';
    static final char SCISSORS_2 = 'Z';

    // how the round should end
    static final int YOU_NEED_LOSE = 0;
    static final int YOU_NEED_DRAW = 1;
    static final int YOU_NEED_WIN  = 2;

    public static void main(String[] args) throws FileNotFoundException 
    {
        Scanner reader = new Scanner(new File("./input.txt"));

        int yourPointsPartA = 0;
        int yourPointsPartB = 0;

        while(reader.hasNextLine())
        {
            String line = reader.nextLine();

            // 0 = rock
            // 1 = paper
            // 2 = scissors
            int opponentPlays = line.charAt(0) - 'A';
            int youPlay       = line.charAt(2) - 'A' - ('X' - 'A');

            yourPointsPartA += youPlay + 1;

            // draw
            if(youPlay == opponentPlays)
            {
                yourPointsPartA += 3;
            }
            // you lose
            else if((youPlay + 1) % 3 == opponentPlays)
            {

            }
            // you win
            else 
            {
                yourPointsPartA += 6;
            }


            if(youPlay == YOU_NEED_LOSE)
            {
                youPlay = (opponentPlays - 1) % 3;

                // turns out java % is remaindor not mod, so it can return negative
                if(youPlay < 0)
                {
                    youPlay += 3;
                }

                yourPointsPartB += youPlay + 0 + 1;
            }
            else if(youPlay == YOU_NEED_DRAW)
            {
                youPlay = opponentPlays;

                yourPointsPartB += youPlay + 3 + 1;
            }
            else if(youPlay == YOU_NEED_WIN)
            {
                youPlay = (opponentPlays + 1) % 3;

                yourPointsPartB += youPlay + 6 + 1;
            }
        }

        System.out.println(yourPointsPartA);
        System.out.println(yourPointsPartB);
    }    
}
