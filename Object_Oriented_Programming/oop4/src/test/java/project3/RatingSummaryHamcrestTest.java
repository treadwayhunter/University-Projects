package project3;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;



public class RatingSummaryHamcrestTest {

    // hamcrest matchers
    // assertThat(Obj o, Matcher m)
    /**
     * is - decorator to improve readability
     * allOf - matches if all matchers match
     * anyOf - matches if any matchers match
     * not - matches if the warpped matcher doesn't match and vice versa
     * equalTo - test object equality using Object equals
     * hasToString - test Object.toString
     * instanceOf - test type
     * notNullValue, nullValue - test for null
     * sameInstance - test object identity
     * hasEntry, hasKey, hasValue - test a map contains an entry, kety or value
     * hasItem, hasItems - test a collection contains elements
     * hasItemInArray - test an array contains an element
     * closeTo - test floats values are close to a given value
     * greaterThan, greaterThanOrEqualTo, lessThan, lessThanOrEqualTo
     * equalToIgnoringCase - test string equality
     * equalToIgnoringWhiteSpace - test string equality ignoring differences in runs of whitespace
     * containsString, endsWith, startsWith - test string matching
     */

    public RatingSummary rs; // use BeforeEach
    public RatingSummary rs2;
	@BeforeEach
	void setUp() {
		rs = new RatingSummary("A1EE2E3N7PW666", 2, 2.5f, 3.5f, 2.5f, 2.5f);
	}

    @Test
    void testListElementIsFloat() {
        assertThat(rs.getList().get(0), instanceOf(Float.class));
    }

    @Test
    void testListContainsFloat() {
        assertThat(rs.getList(), hasItem(2.5f));
    }

    @Test
    void testNodeIDIsString() {
        assertThat(rs.getNodeID(), instanceOf(String.class));
    }

    @Test
    void testDegreeIsLong() {
        assertThat(rs.getDegree(), instanceOf(long.class));
    }

    @Test
    void testListIsList() {
        assertThat(rs.getList(), instanceOf(List.class));
    }

    @Test
    void testEquals() {
        assertThat(rs.equals(rs), is(true));
    }

    @Test
    void testNotEquals() {
        assertThat(rs.equals("Test"), is(false));
    }

    @Test 
    void setDegreeTest() {
        AbstractRatingSummary s = new RatingSummary("IDTEST", 10);
        s.setDegree(5);
        assertThat(s.getDegree(), is(5L));
    }

    @Test
    void setDegreeWithListTest() {
        List<Rating> rawList = new ArrayList<>();
		rawList.add(new Rating("A1EE2E3N7PW666", "B000GFDAUG", 5));
		rawList.add(new Rating("AGZ8SM1BGK3CK","B000GFDAUG",4));
		rawList.add(new Rating("AQNPK1Q7HIAP3","B000GOYLNC",3));
		rawList.add(new Rating("AQNPK1Q7HIAP3","B007427XS4",2));
		rawList.add(new Rating("A1EE2E3N7PW666","B007427XS4",5));

		//rs2 = new RatingSummary("test", rawList);
        AbstractRatingSummary s = new RatingSummary("IDTEST", 10);
        s.setDegree(rawList);

        assertThat(s.getDegree(), is(0L));
    }

}
