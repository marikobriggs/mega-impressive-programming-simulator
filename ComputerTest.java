
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * 
 */

/**
 * @author mercedeschea
 *
 */
class ComputerTest {

	static Computer comp;
	static BitString bits;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		comp = new Computer();
		bits = new BitString();
	}

	/**
	 * Test method for {@link Computer#Computer()}.
	 */
	@Test
	final void testComputer() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link Computer#getRegisters()}.
	 */
	@Test
	final void testGetRegisters() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link Computer#assemble(java.util.List)}.
	 */
	@Test
	final void testAssemble() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link Computer#execute()}.
	 */
	@Test
	final void testExecute() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link Computer#parseRegistersRegMode()}.
	 */
	@Test
	final void testParseRegistersRegMode() {
		 String inst = "ADD $t1, $t2, $t3";
		 ArrayList<String> instArr = new ArrayList<String>();
		 instArr.add(inst.replaceAll("[$,]", " "));
		 try {
		 comp.assemble(instArr);
		 } catch (IOException e) {
		 fail("Received unexpected IOException");
		 }
		 comp.execute();
		 int[] regModeArr = new int[3];
		 for (int i = 0; i < inst.length(); i++) {
		 regModeArr[i] = Integer.parseInt(instArr.get(i));
		 }
		 assertArrayEquals(new int[] { 9, 10, 11 }, regModeArr);
	}

	/**
	 * Test method for {@link Computer#testparseImmedRegMode()}.
	 */
	@Test
	final void testParseImmedRegMode() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link Computer#executeAdd()}.
	 */
	@Test
	final void testExecuteAdd() {
		String inst = "ADD $t1, $t2, $t3";
		ArrayList<String> instArr = new ArrayList<String>();
		instArr.add(inst);
		BitString[] registers = comp.getRegisters();
		// put in t1
		registers[10].setValue2sComp(5);
		// put in t2
		registers[11].setValue2sComp(6);
		try {
			comp.assemble(instArr);
		} catch (IOException e) {
			fail("Received unexpected IOException");
		}
		comp.execute();

		assertEquals(11, registers[9].getValue2sComp());
	}

	/**
	 * Test method for {@link Computer#executeAddU()}.
	 */
	@Test
	final void testExecuteAddU() {
		String inst = "ADDU $t1, $t2, $t3";
		ArrayList<String> instArr = new ArrayList<String>();
		instArr.add(inst);
		BitString[] registers = comp.getRegisters();
		registers[10].setValue(5);
		registers[11].setValue(56);
		try {
			comp.assemble(instArr);
		} catch (IOException e) {
			fail("Received unexpected IOException");
		}
		comp.execute();

		assertEquals(61, registers[9].getValue()); 
		
	}

	/**
	 * Test method for {@link Computer#executeAnd()}.
	 */
	@Test
	final void testExecuteAnd() {
		String inst = "AND $10, $11, $12";
		ArrayList<String> instArr = new ArrayList<String>();
		instArr.add(inst);
		BitString[] registers = comp.getRegisters();

		registers[11].setValue(5);
		// put in t2
		registers[12].setValue(6);

		try {
			comp.assemble(instArr);
		} catch (IOException e) {
			fail("Received unexpected IOException");
		}
		comp.execute();
		assertEquals(4, registers[10].getValue());
	}

	/**
	 * Test method for {@link Computer#executeOr()}.
	 */
	@Test
	final void testExecuteOr() {
		String inst = "OR $10, $11, $12";
		ArrayList<String> instArr = new ArrayList<String>();
		instArr.add(inst);
		BitString[] registers = comp.getRegisters();

		registers[11].setValue(5);
		// put in t2
		registers[12].setValue(6);

		try {
			comp.assemble(instArr);
		} catch (IOException e) {
			fail("Received unexpected IOException");
		}
		comp.execute();
		assertEquals(7, registers[10].getValue());
	}

	/**
	 * Test method for {@link Computer#executeORI(java.lang.String, int)}.
	 */
	@Test
	final void testExecuteORI() {
		String inst = "ORI $10, $11, 5";
		ArrayList<String> instArr = new ArrayList<String>();
		instArr.add(inst);
		BitString[] registers = comp.getRegisters();
		
		registers[11].setValue2sComp(3);

		try {
			comp.assemble(instArr);
		} catch (IOException e) {
			fail("Received unexpected IOException");
		}
		comp.execute();
		assertEquals(7, registers[10].getValue2sComp());
	}

	/**
	 * Test method for {@link Computer#executeAddiu()}.
	 */
	@Test
	final void testExecuteAddiu() {
		String inst = "ADD $t1, $t2, 6";
		ArrayList<String> instArr = new ArrayList<String>();
		instArr.add(inst);
		BitString[] registers = comp.getRegisters();
		registers[10].setValue2sComp(5);
		// put in t2
		registers[11].setValue2sComp(6);
		try {
			comp.assemble(instArr);
		} catch (IOException e) {
			fail("Received unexpected IOException");
		}
		comp.execute();

		assertEquals(11, registers[9].getValue2sComp());
	}

	/**
	 * Test method for {@link Computer#executeAndi()}.
	 */
	@Test
	final void testExecuteAndi() {
		String inst = "AND $10, $11, 6";
		ArrayList<String> instArr = new ArrayList<String>();
		instArr.add(inst);
		BitString[] registers = comp.getRegisters();

		registers[11].setValue(5);
		// put in t2
		registers[12].setValue(6);

		try {
			comp.assemble(instArr);
		} catch (IOException e) {
			fail("Received unexpected IOException");
		}
		comp.execute();
		assertEquals(4, registers[10].getValue());
	}

	/**
	 * Test method for {@link Computer#executeLw()}.
	 */
	@Test
	final void testExecuteLw() { // will test lw by loading from address 25 into $t1
		
		// set R[9] = 0, R[10] = 18
		BitString[] registers = comp.getRegisters();
		registers[9].setValue2sComp(0); // set $t1 to 0
		registers[10].setValue2sComp(18); // set $t2 to 18
		
		// set M[25] = 81
		BitString[] memory = comp.getDataMemory();
		BitString eightyOneBitString = new BitString(); // data to be loaded
		eightyOneBitString.setValue2sComp(81);
		memory[25] = eightyOneBitString;
		
		String lwInst = "LW $t1, 7($t2)"; // lw with address in t2 offset by 7
		ArrayList<String> instArr = new ArrayList<String>();
		instArr.add(lwInst);

		try {
			comp.assemble(instArr);
		} catch (IOException e) {
			fail("Received unexpected IOException");
		}
		comp.execute();

		assertEquals(81, registers[9].getValue()); // make sure t1 got the loaded value (81)
	}

	/**
	 * Test method for {@link Computer#executeSw()}.
	 */
	@Test
	final void testExecuteSw() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link Computer#executeBeq()}.
	 */
	@Test
	final void testExecuteBeq() {
		 ArrayList<String> instArr = new ArrayList<String>();
		 instArr.add("BEQ $10, $11, LABEL");
		 instArr.add("ADDI $10, $11, 10");
		 instArr.add("LABEL: ADDI $10, $11, 1");
		 BitString[] registers = comp.getRegisters();
		 // set equal
		 registers[10].setValue(0);
		 registers[11].setValue(0);
		 try {
		 comp.assemble(instArr);
		 } catch (IOException e) {
		 fail("Received unexpected IOException");
		 }
		 comp.execute();
		 assertEquals(1, registers[10].getValue());

	}

	/**
	 * Test method for {@link Computer#executeBne()}.
	 */
	@Test
	final void testExecuteBne() {
		 ArrayList<String> instArr = new ArrayList<String>();
		 instArr.add("BNE $10, $11, LABEL");
		 instArr.add("ADDI $10, $11, 10");
		 instArr.add("LABEL: ADDI $10, $11, 1");
		 BitString[] registers = comp.getRegisters();
		 // set equal
		 registers[10].setValue(0);
		 registers[11].setValue(1);
		 try {
		 comp.assemble(instArr);
		 } catch (IOException e) {
		 fail("Received unexpected IOException");
		 }
		 comp.execute();
		 assertEquals(2, registers[10].getValue());
	}

	/**
	 * Test method for {@link Computer#executeJ()}.
	 */
	@Test
	final void testExecuteJ() {
		 ArrayList<String> instArr = new ArrayList<String>();
		 instArr.add("J LABEL");
		 instArr.add("ADDI $10, $11, 10");
		 instArr.add("LABEL: ADDI $10, $11, 1");
		 BitString[] registers = comp.getRegisters();
		 // t1
		 registers[10].setValue(0);
		 registers[11].setValue(0);
		 try {
		 comp.assemble(instArr);
		 } catch (IOException e) {
		 fail("Received unexpected IOException");
		 }
		 comp.execute();
		 assertEquals(1, registers[10].getValue());
	}

	/**
	 * Test method for {@link Computer#executeJr()}.
	 */
	@Test
	final void testExecuteJr() {
		String inst = "JR $T1";
		ArrayList<String> instArr = new ArrayList<String>();
		instArr.add(inst);
		BitString[] registers = comp.getRegisters();
		// t1
		registers[9].setValue(12);

		try {
			comp.assemble(instArr);
		} catch (IOException e) {
			fail("Received unexpected IOException");
		}
		comp.execute();
		assertEquals(12, registers[9].getValue());
	}

	/**
	 * Test method for {@link Computer#createRegisterMappings()}.
	 */
	@Test
	final void testCreateRegisterMappings() {
		assertEquals(2, comp.getRegisterMappings().get("$v0").intValue());
		assertEquals(12, comp.getRegisterMappings().get("$t4").intValue());
		assertEquals(26, comp.getRegisterMappings().get("$k0").intValue());
	}

	/**
	 * Test method for {@link Computer#getRegisterMappings()}.
	 */
	@Test
	final void testGetRegisterMappings() {
		assertEquals(2, comp.getRegisterMappings().get("$v0").intValue());
		assertEquals(12, comp.getRegisterMappings().get("$t4").intValue());
		assertEquals(26, comp.getRegisterMappings().get("$k0").intValue());
	}

}