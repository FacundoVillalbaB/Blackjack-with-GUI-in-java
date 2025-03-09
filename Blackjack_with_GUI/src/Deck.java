import java.util.ArrayList;//import the necessary libraries 
import java.util.Collections;
import java.util.List;
public class Deck{//create a class deck 
    private List<Card> cards;//create a list to store all the objects card
    public Deck(int number_of_decks){//give the number of deck as a parameter
        cards=new ArrayList<>();//initialize the array to 0
        create(number_of_decks);//create the amount of deck that we want
        shuffle();//shuffle all the deck to then  pop
    }
    
    
    private void create(int number_of_decks){//create a function that will put all the objects of card in the array
        for (int deck=0;deck<number_of_decks;deck++){//for all the deck that user select
            for (Card.Suit suit : Card.Suit.values()) {//for every Suit values in the class
                for(Card.Rank rank : Card.Rank.values()){//and for every rank
                    cards.add(new Card(rank,suit));//generate a card in the deck
                }
            }
        }
    }
    
    public void shuffle() {
        Collections.shuffle(cards);//use the collection library to shuffle
    }

    public Card drawCard() {//create a function to draw the cards
        if (!cards.isEmpty()) {//if cards isn't empty
            return cards.remove(0);  //we will return a card and then eliminate from the object
        } else {
            System.out.println("Deck is empty!");//if the deck is empty then print this
            return null;  //return null if deck is empty
        }
    }

}