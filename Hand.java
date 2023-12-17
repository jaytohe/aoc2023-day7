class Hand implements Comparable<Hand> {
    //each hand has a strength and five cards associated with it.

    private Card[] cards;
    private CardType cardType;
    private Bid bid;

    public Hand(Card[] cards, CardType cardType, Bid bid) {
        this.cards = cards;
        this.cardType = cardType;
        this.bid = bid;
    }

    public Card[] getCards() {
        return cards;
    }

    public CardType getType() {
        return cardType;
    }

    public Bid getBid() {
        return bid;
    }

    @Override
    public int compareTo(Hand otherHand) {
        int difference;
        difference = cardType.compareTo(otherHand.getType());
        if (difference == 0) {
            for (int i=0; i<5; i++) {
                Card thisHandCard = cards[i];
                Card otherHandCard = otherHand.getCards()[i];

                difference = thisHandCard.compareTo(otherHandCard);

                if (difference != 0) break;
            }
        }

        return difference;
    }
    
}

