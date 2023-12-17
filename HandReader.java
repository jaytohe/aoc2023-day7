import java.util.HashMap;
import java.util.HashSet;


class CardReadException extends Exception {
    public CardReadException(String message) {
        super(message);
    }
}

class HandReadException extends Exception {
    public HandReadException(String message) {
        super(message);
    }
}
    class BidReadException extends Exception {
    public BidReadException(String message) {
        super(message);
    }
}
class HandReader {


    private Card readCard(char c) throws CardReadException{
        switch(c) {
            case 'A':
            return Card.ACE;
            case 'Q':
            return Card.QUEEN;
            case 'K':
            return Card.KING;
            case 'J':
            return Card.JACK;
            case 'T':
            return  Card.TEN;
            case '9':
            return  Card.NINE;
            case '8':
            return  Card.EIGHT;
            case '7':
            return  Card.SEVEN;
            case '6':
            return  Card.SIX;
            case '5':
            return  Card.FIVE;
            case '4':
            return  Card.FOUR;
            case '3':
            return  Card.THREE;
            case '2':
            return  Card.TWO;
            default:
                throw new CardReadException("Failed to read card symbol "+c);

        }
    }

    public Hand readHand(String handString, String bidString) 
    throws HandReadException, CardReadException, BidReadException
    {

        if (handString.length() != 5) {
            throw new HandReadException("Hand "+ handString +" is an invalid hand!");
        }
        final HashMap<Card, Integer> cardOccurences = new HashMap<Card, Integer>();
        Card[] cards = new Card[5];
        for(int i = 0;i<5; i++) {
            char c = handString.charAt(i);

            // read card based on character.
            cards[i] = readCard(c);
            //update the card occurences.
            cardOccurences.put(cards[i], cardOccurences.getOrDefault(cards[i], 0)+1);
        }

        CardType cardType = null;
        if (cardOccurences.size() == 1) {// means we found the same card five times
            cardType = CardType.FIVE_KIND;
        }
        else if (cardOccurences.size() == 2) {
            // 4,1 or 3,2
            //means we found four of the same card and one different
            // OR we found three of the same AND two of the same.
            // So we need to check if it's four kind or full house:
            HashSet<Integer> valuesSet = new HashSet<Integer>(cardOccurences.values());
            if (valuesSet.contains(4)) {
                cardType = CardType.FOUR_KIND;
            } else {
                cardType = CardType.FULL_HOUSE;
            }

        }
        else if (cardOccurences.size() == 3) {
            // 3, 1, 1 or 2,2,1
            // 3, 1, 1 is three kind since three cards share one label
            // 2,2,1 is two-pair: where two cards share one label, two other cards share a second label, and the remaining card has a third label
            HashSet<Integer> valuesSet = new HashSet<Integer>(cardOccurences.values());
            if (valuesSet.contains(3)) {
                cardType = CardType.THREE_KIND;
            } else {
                cardType = CardType.TWO_PAIR;
            }
        }
        else if (cardOccurences.size() == 4) {
            // One-pair: 2,1,1,1
            cardType = CardType.ONE_PAIR;
        }
        
        else {
            cardType = CardType.HIGH_CARD;
        }

        int bidAmount;
        try {
        bidAmount = Integer.parseInt(bidString);
        } catch (NumberFormatException ne) {
            throw new BidReadException("Bid needs to be an integer number!");
        }
        return new Hand(cards, cardType, new Bid(bidAmount));
    }
}
