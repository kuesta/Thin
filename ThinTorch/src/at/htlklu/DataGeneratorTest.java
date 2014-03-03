package at.htlklu;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataGeneratorTest implements RefVars {

	static DataGenerator dg;
	static int veId = 1;
	static int ledId = 1;
	static int red = 64;
	static int green = 64;
	static int blue = 64;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dg = new DataGenerator(veId, ledId, red, green, blue);
	}

	@Test
	public void testGetData() {
		System.out.println("testGetData\n==========================");
		System.out.println("Manual:\t"
				+ Integer.parseInt("10001100000010000001000000", 2)
				+ "\t10001100000010000001000000");
		System.out.println("gen:\t" + dg.getData() + "\t"
				+ Integer.toBinaryString(dg.getData()));
		assertEquals(Integer.parseInt("10001100000010000001000000", 2),
				dg.getData());

	}

	@Test
	public void testGetErrorMaskNumber() {
		
		dg.getData();
		int error=dg.getErrorMaskNumber();
		System.out.println(error);
		
		if(error==(error&ERRORMASK_NO_ERROR)){
			System.out.println("Success");
		}
		if(ERRORMASK_NOT_VALID_VE_ID==(error&ERRORMASK_NOT_VALID_VE_ID)){
			System.out.println("Error: invalid ve id");
		}
		if(ERRORMASK_NOT_VALID_LED_ID==(error&ERRORMASK_NOT_VALID_LED_ID)){
			System.out.println("Error: invalid Led id");
		}
		if(ERRORMASK_NOT_VALID_RED_INPUT==(error&ERRORMASK_NOT_VALID_RED_INPUT)){
			System.out.println("Error: invalid red input");
		}
		if(ERRORMASK_NOT_VALID_GREEN_INPUT==(error&ERRORMASK_NOT_VALID_GREEN_INPUT)){
			System.out.println("Error: invalid green input");
		}
		if(ERRORMASK_NOT_VALID_BLUE_INPUT==(error&ERRORMASK_NOT_VALID_BLUE_INPUT)){
			System.out.println("Error: invalid blue input");
		}
		
	}

}
