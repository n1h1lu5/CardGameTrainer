package domain.decks;

import org.junit.Test;

public class CardTest {

    @Test
    public void canCreateACard() {
        new Card(1, Card.Type.CLOVER);
    }

    @Test
    public void aNecessaryCheckInOrderToHave100PercentCodeCoverage_junitStrangeBehaviorRegardingEnums() {
        Card.Type.valueOf("CLOVER");
    }
}
