package sieger.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class KnockOutMappingTest {

	@Test
	void testGetKeyByValue() {
		KnockOutMapping komapping = new KnockOutMapping(2);
		komapping.mapGameToKOBracket(1,"game");
		assertTrue(komapping.getKeyByValue("game") == 1);
	}

}
