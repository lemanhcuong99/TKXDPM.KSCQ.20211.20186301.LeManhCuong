import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValidateProductAddressTest {
	
	private RushPlaceOrderController rushPlayOrder;

	@BeforeEach
	void setUp() throws Exception {
		rushPlayOrder = new RushPlaceOrderController();
	}
	
	@Parameterized
	@CsvSource({
		"[ao,quan],[ao,mu],true",
		"[ao,quan],[vay,mu],false",
		"[ao,quan],[ao,mu,quan],true"
	})

	@Test
	public void test(String[] productList, String[] rushPlaceOrderProductList, boolean expected) {
		boolean isValided = rushPlayOrder.validateProduct(productList, rushPlaceOrderProductList);
		assertEquals(expected, isValided);
	}

}
