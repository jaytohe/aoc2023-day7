import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

public class Part1 {
    public static void main(String[] args) {
        File puzzleInputFile = new File("input.txt");
        try(BufferedReader bfr = new BufferedReader(new FileReader(puzzleInputFile))) {
            String line;
            HandReader hr = new HandReader();
            ArrayList<Hand> hands = new ArrayList<Hand>();
            while((line = bfr.readLine()) != null) {
                String[] splits = line.split(" ");
                String handString = splits[0];
                String bidString = splits[1];
                Hand iterHand = hr.readHand(handString, bidString);
                hands.add(iterHand);
            }

            Collections.sort(hands); //sort hands by type and if needed by cards.

            ListIterator<Hand> it = hands.listIterator();

            long totalWinnings = 0;
            while(it.hasNext()) {
                totalWinnings += (it.nextIndex()+1) * it.next().getBid().getAmount();
            }

            System.out.println("The total winnings are : "+totalWinnings);
        }
        catch(FileNotFoundException fe) {
            fe.printStackTrace();
        }
        catch(IOException ie) {
            ie.printStackTrace();
        }

        catch(CardReadException ce) {
            ce.printStackTrace();
        } 
        catch (HandReadException e) {
            e.printStackTrace();
        } 
        catch (BidReadException e) {
            e.printStackTrace();
        }
    }
    

}
