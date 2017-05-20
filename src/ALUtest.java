import static org.junit.Assert.*;

import org.junit.Test;

public class ALUtest {
	String dot=".";
	char dotC='.';
	@Test
	public void testIntegerRepresentation() {
		assertEquals("00011", ALU.integerRepresentation("3", 5));
	}

	@Test
	public void testFloatRepresentation() {
		assertEquals("00111111111000000000000000000000", ALU.floatRepresentation("1.75",8,23));
		assertEquals("10111111111000000000000000000000", ALU.floatRepresentation("-1.75",8,23));
		assertEquals("01111111100000000000000000000000", ALU.floatRepresentation("+Inf",8,23));
	}

	@Test
	public void testIeee754() {
		assertEquals("00111111111000000000000000000000", ALU.floatRepresentation("1.75",8,23));
		assertEquals("10111111111000000000000000000000", ALU.floatRepresentation("-1.75",8,23));
		assertEquals("01111111100000000000000000000000", ALU.floatRepresentation("+Inf",8,23));
	}

	@Test
	public void testIntegerTrueValue() {
		assertEquals("-2", ALU.integerTrueValue("1110"));
		assertEquals("-8", ALU.integerTrueValue("1000"));
		assertEquals("2", ALU.integerTrueValue("0010"));
	}

	@Test
	public void testFloatTrueValue() {
		assertEquals("3.0", ALU.floatTrueValue("01000000010000000000", 8, 11));
		assertEquals("2.5", ALU.floatTrueValue("01000000001000000000", 8, 11));
		assertEquals("NaN", ALU.floatTrueValue("01111111101000000000", 8, 11));
	}

	@Test
	public void testNegation() {
		assertEquals("1111", ALU.negation("0000"));
		assertEquals("0111", ALU.negation("1000"));
		assertEquals("0000", ALU.negation("1111"));
	}

	@Test
	public void testLeftShift() {
		assertEquals("0010", ALU.leftShift("0001",1));
		assertEquals("0010", ALU.leftShift("1001",1));
		assertEquals("1110", ALU.leftShift("1111",1));
		assertEquals("0000", ALU.leftShift("0000",1));
	}

	@Test
	public void testLogRightShift() {
		assertEquals("0000", ALU.logRightShift("0001",1));
		assertEquals("0111", ALU.logRightShift("1111",1));
		assertEquals("0001", ALU.logRightShift("0110",2));
	}

	@Test
	public void testAriRightShift() {
		assertEquals("1111", ALU.ariRightShift("1111",1));
		assertEquals("1111", ALU.ariRightShift("1000",3));
		assertEquals("0001", ALU.ariRightShift("0100",2));
	}

	@Test
	public void testFullAdder() {
		assertEquals("10", ALU.fullAdder('1','1','0'));
	}

	@Test
	public void testClaAdder() {
		assertEquals("01111", ALU.claAdder("1110","0001",'0'));
		assertEquals("01011", ALU.claAdder("1001", "0001", '1'));
		assertEquals("10011", ALU.claAdder("1001", "1001", '1'));
	}

	@Test
	public void testOneAdder() {
		assertEquals("01111", ALU.oneAdder("1110"));
		assertEquals("010000", ALU.oneAdder("01111"));
		assertEquals("100000", ALU.oneAdder("11111"));
	}

	@Test
	public void testAdder() {
		assertEquals("000000111", ALU.adder("0100", "0011", '0', 8));
		assertEquals("111111110", ALU.adder("1110", "11", '1', 8));
		assertEquals("000001000", ALU.adder("0100", "0011", '1', 8));
	}

	@Test
	public void testIntegerAddition() {
		assertEquals("000000111", ALU.integerAddition("0100", "0011", 8));
		assertEquals("111111101", ALU.integerAddition("1110", "11", 8));
	}
    //有问题.......
	
	
	
	
	
	
	
	
	
	@Test
	public void testIntegerSubtraction() {
		assertEquals("011111111", ALU.integerSubtraction("1110", "11", 8));
		assertEquals("111111101", ALU.integerSubtraction("1110", "01", 8));
	}

	@Test
	public void testIntegerMultiplication() {
		assertEquals("011111110", ALU.integerMultiplication("1110", "01", 8));
		assertEquals("000001000", ALU.integerMultiplication("0100", "0010", 8));
	}

	@Test
	public void testIntegerDivision() {
		assertEquals("01111110011111111", ALU.integerDivision("10111", "00010", 8));
		assertEquals("01111110100000000", ALU.integerDivision("1010", "00010", 8));
		assertEquals("01111110000000000", ALU.integerDivision("11000", "00010", 8));
		assertEquals("00000010000000000", ALU.integerDivision("01000", "00010", 8));
		assertEquals("00000010000000001", ALU.integerDivision("01001", "00010", 8));
		assertEquals("00000000100000001", ALU.integerDivision("011", "00010", 8));
	}

	@Test
	public void testSignedAddition() {
		assertEquals("0111111011", ALU.signedAddition("10111", "00010", 8));
		assertEquals("0100000011", ALU.signedAddition("10001", "10010", 8));
	}
	//不是很懂究竟返回原码还是补码
	
	
	
	
	
	

	@Test
	public void testFloatAddition() {
		fail("Not yet implemented");
	}

	@Test
	public void testFloatSubtraction() {
		fail("Not yet implemented");
	}

	@Test
	public void testFloatMultiplication() {
		fail("Not yet implemented");
	}

	@Test
	public void testFloatDivision() {
		fail("Not yet implemented");
	}

}
