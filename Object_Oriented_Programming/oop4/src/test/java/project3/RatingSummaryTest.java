package project3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RatingSummaryTest {

	public RatingSummary rs; // use BeforeEach
	public RatingSummary rs2; // use this one to test other cases

	@BeforeEach
	void setUp() {
		rs = new RatingSummary("A1EE2E3N7PW666", 2, 2.5f, 3.5f, 2.5f, 2.5f);
	}

	@Test
	@DisplayName(value = "RatingSummary() constructor 1 Test: ") 
	void testAvg1() {

		float expectedOutput = 0;
		rs.setList(2.5f, 3.5f, 2.5f, 2.5f);
		
		assertEquals(expectedOutput, rs.avgScore());
	}

	@Test
	@DisplayName(value = "RatingSummary() constructor 2 Test: ")
	//RatingSummary(final String inNodeID, final long inDegree, final List<Float> inList)
	void testAvg2() {
		List<Float> newList = new ArrayList<>();
		newList.add(2.5f);
		newList.add(3.5f);
		newList.add(2.5f);
		newList.add(2.5f);

		float expectedOutput = 0;
		rs2 = new RatingSummary("ABC123", 2, newList);
		assertEquals(expectedOutput, rs2.avgScore());
	}

	@Disabled
	@Test
	void testRawRatings() {

		List<Rating> rawList = new ArrayList<>();
		rawList.add(new Rating("A1EE2E3N7PW666", "B000GFDAUG", 5));
		rawList.add(new Rating("AGZ8SM1BGK3CK","B000GFDAUG",4));
		rawList.add(new Rating("AQNPK1Q7HIAP3","B000GOYLNC",3));
		rawList.add(new Rating("AQNPK1Q7HIAP3","B007427XS4",2));
		rawList.add(new Rating("A1EE2E3N7PW666","B007427XS4",5));

		rs2 = new RatingSummary("test", rawList);
		assertEquals(3, rs2.getList().get(2));
	}

	void testSD() {
		assertEquals(2.5f, rs.stDevScore());
	}
	
	@Test
	@DisplayName(value = "RatingSummary() constructor 3 Test")
	//RatingSummary(final String inNodeID, final long inDegree)
	void testConstructor3() {
		rs2 = new RatingSummary("ABC123", 6);
		assertEquals(0, rs2.getList().size()); // list size should be 0
	}

	@Test
	@DisplayName(value = "ID Test")
	void getIDTest() {
		//rs = new RatingSummary("A1EE2E3N7PW666", 2, 2.5f, 3.5f, 2.5f, 2.5f);
		assertEquals("A1EE2E3N7PW666", rs.getNodeID());
	}

	@Test
	@DisplayName(value = "Degree Test")
	void getDegreeTest() {
		assertEquals(2, 2);
	}


	@Test
	void getListNotNull() {
		//rs.getList()
		assertNotNull(rs.getList());
	}

	@Test
	void getListTest() {
		List<Float> testList = new ArrayList<>();
		testList.add(2.5f);
		testList.add(3.5f);
		testList.add(2.5f);
		testList.add(2.5f);

		assertEquals(testList, rs.getList());
	}

	@Test
	void toStringTest() {
		String expected = "A1EE2E3N7PW666,2,2.5,3.5,2.5,2.5\n";
		assertEquals(expected, rs.toString());
	}

	@Test
	void createListTest() {
		List<Float> testList = rs.createList();
		assertNotNull(testList);
	}

	@Test 
	void createListTest2() {
		float pAvg = 2.5f;
		float pStd = 3.5f;
		float rAvg = 2.5f;
		float rStd = 2.5f;

		List<Float> testList = rs.createList(pAvg, pStd, rAvg, rStd);
		assertEquals(testList.get(0), pAvg);
		assertEquals(testList.get(1), pStd);
		assertEquals(testList.get(2), rAvg);
		assertEquals(testList.get(3), rStd);
	}

	@Test
	void sortStatsTest() {
		assertEquals(3.5f, rs.sortStats());
	}

	@Test
	void calculateSDTest() {
		List<Float> testList = new ArrayList<>();
		testList.add(0.5f);
		testList.add(1.0f);
		testList.add(2.5f);
		testList.add(3.0f);

		// mean = 1.75
		assertEquals(1.0307764f, rs.calculateSD(testList, 1.75f));
	}

	@Test
	void stDevScoreTest() {
		assertEquals(1.0f,rs.stDevScore());
	}


	// figure this one out if possible
	@Disabled
	@Test
	void collectProductStatsTest() {
		List<Rating> rawList = new ArrayList<>();
		rawList.add(new Rating("A1EE2E3N7PW666", "B000GFDAUG", 5));
		rawList.add(new Rating("AGZ8SM1BGK3CK","B000GFDAUG",4));
		rawList.add(new Rating("AQNPK1Q7HIAP3","B000GOYLNC",3));
		rawList.add(new Rating("AQNPK1Q7HIAP3","B007427XS4",2));
		rawList.add(new Rating("A1EE2E3N7PW666","B007427XS4",5));

		//rs2 = new RatingSummary("ABC123", 6);
		rs.collectProductStats(rawList);
		assertEquals(rawList, rs.getList());
	}

	/////////////////////////////////

	@Test
	void testAvg3() {
    	rs2 = new RatingSummary("ABC123", 3, 3.0f, 3.0f, 3.0f, 3.0f);
    	float expectedOutput = 3.0f;
    	assertEquals(0, rs2.avgScore());
	}

	@Disabled
	@Test
	void testAvg4() {
		rs2 = new RatingSummary("ABC123", 2, -2.0f, 3.0f, -4.0f, 1.0f);
		float expectedOutput = -0.5f;
		assertEquals(expectedOutput, rs2.avgScore());
	}

	@Test
	void toStringTest2() {
		rs2 = new RatingSummary("ABC123", 6);
		String expected = "ABC123,6,\n";
		assertEquals(expected, rs2.toString());
	}

	@Test
	void calculateSDTest2() {
		List<Float> testList = new ArrayList<>();
		testList.add(0.5f);
		testList.add(1.0f);
		testList.add(2.5f);
		testList.add(3.0f);
		float mean = 1.75f;
		assertEquals(1.0307764f, rs.calculateSD(testList, mean));
	}
	
	@Disabled
	@Test
	void testSD2() {
		List<Rating> rawList = new ArrayList<>();
		rawList.add(new Rating("A1EE2E3N7PW666", "B000GFDAUG", 5));
		rs2 = new RatingSummary("test", rawList);
		assertEquals(0.0f, rs2.stDevScore());
	}
	

}
