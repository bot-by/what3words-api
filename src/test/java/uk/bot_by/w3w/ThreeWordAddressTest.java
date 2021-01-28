package uk.bot_by.w3w;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("fast")
class ThreeWordAddressTest {

	@Test
	void testToString() {
		// given
		ThreeWordAddress threeWordAddress = new ThreeWordAddress(new String[]{"spring", "tops", "issued"});

		// when and then
		assertEquals("spring.tops.issued", threeWordAddress.toString());
	}

}