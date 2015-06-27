package participant;

import java.util.ArrayList;
import java.util.List;

public class Player {

	public List<Integer> getHand() {
		List<Integer> hand = new ArrayList<Integer>(); 
		
		return hand;
	}

	public void receiveCard(int card) {
	}

	public int decideBet() {
		return 1;
	}

	public void receiveGains(int capture) {
		
	}

	public void loseBet(Integer capture) {
		
	}
	
}
