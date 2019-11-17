/*
 * Mega Impressive Programming Simulator
 * TCSS 372 - Computer Architecture
 * 11-16-2019
 * By:
 * Mariko Briggs
 * Mercedes Chea
 * Thaddaeus Hug
 */

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
	final void testComputer() { // (-81 | 5  ), and test not branching
		ArrayList<String> instArr = new ArrayList<String>();
		instArr.add("ADDI $t5, $zero, 5");
		instArr.add("ADDI $t4, $zero, 5");
		instArr.add("BNE $t4, $t5, aLabel"); // won't branch, are equal
		
		instArr.add("ADDI $t5, $zero, 6");
		instArr.add("ADDI $t4, $zero, 11");
		instArr.add("BEQ $t4, $t5, aLabel"); // won't branch, 6 != 11
		
		instArr.add("ADDI $t1, $zero, -81");
		instArr.add("");
		instArr.add("");
		instArr.add(""); // simulate empty lines
		instArr.add("ADDI $t2, $zero, 5");
		instArr.add("OR $t0, $t1, $t2");
		instArr.add("aLabel:");
		
		try {
			comp.assemble(instArr);
		} catch (IOException e) {
			fail("Received unexpected IOException");
		}
		comp.execute();
		
		BitString[] registers = comp.getRegisters();
		assertEquals(-81 | 5, registers[8].getValue2sComp());
		assertEquals(6, registers[13].getValue2sComp());
		assertEquals(11, registers[12].getValue2sComp());
	}

	/**
	 * Test method for {@link Computer#getRegisters()}.
	 */
	@Test
	final void testGetRegisters() {
		String inst = "ADDI $t1, $zero, $-127"; // $t1 should have -127
		ArrayList<String> instArr = new ArrayList<String>();
		instArr.add(inst);
		try {
			comp.assemble(instArr);
		} catch (IOException e) {
			fail("Received unexpected IOException");
		}
		comp.execute();

		BitString[] registers = comp.getRegisters();
		assertEquals(-127, registers[9].getValue2sComp());
	}

	/**
	 * Test method for {@link Computer#assemble(java.util.List)}.
	 */
	@Test
	final void testAssemble() {
		Assertions.assertThrows(IOException.class, () -> {
			List<String> instArray = new ArrayList<String>();
			for (int i = 0; i < 100; i++) {
				instArray.add(""); // simulate 100 instructions
			}
			comp.assemble(instArray);
			comp.execute();
		});
	}
	
	
	/**
	 * Test method for {@link Computer#execute()}.
	 */
	@Test
	final void testExecute() { // perform (5 + 3) & (81 | 6)
		ArrayList<String> instArr = new ArrayList<String>();
		instArr.add("ADDI $t0, $zero, 5");
		instArr.add("ADDI $t1, $zero, 3");
		instArr.add("ADD $t3, $t0, $t1");
		
		instArr.add("ADDI $t4, $zero, 81");
		instArr.add("ADDI $t5, $zero, 6");
		instArr.add("OR $t6, $t4, $t5");
		
		instArr.add("AND $s0, $t3, $t6");
		
		try {
			comp.assemble(instArr);
		} catch (IOException e) {
			fail("Received unexpected IOException");
		}
		comp.execute();
		
		BitString[] registers = comp.getRegisters();
		assertEquals((5 + 3) & (81 | 6), registers[16].getValue2sComp());
	}
	
	
	/**
	 * Test method for {@link Computer#assemble(java.util.List)}.
	 */
	@Test
	final void testExecuteGarbage() {
			List<String> instArray = new ArrayList<String>();
			instArray.add("ADDalsdknf $52lk $asldkn 5infld");
			instArray.add("ADDalsdknf $52lk $asldkn 5infld");
			instArray.add("ADDalsdknf $52lk $asldkn 5infld");
			instArray.add("ADDalsdknf $52lk $asldkn 5infld");
			try {
				comp.assemble(instArray);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			comp.execute();
	}

	/**
	 * Test method for {@link Computer#parseRegistersRegMode()}.
	 */
	@Test
	final void testParseRegistersRegMode() { 
			try {
				Method parseRegistersRegMode = Computer.class.getDeclaredMethod("parseRegistersRegMode",  String.class);
				parseRegistersRegMode.setAccessible(true);
				int[] regArray;
				regArray = (int[]) parseRegistersRegMode.invoke(comp, "ADD $9, $10, $11");
				assertArrayEquals(new int[] {9, 10, 11}, regArray);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				fail("didn't successfully operate the parseRegistersRegMode method");
			}
	}

	/**
	 * Test method for {@link Computer#testparseImmedRegMode()}.
	 */
	@Test
	final void testParseImmedRegMode() {
		try {
			Method parseImmedRegMode = Computer.class.getDeclaredMethod("parseImmedRegMode",  String.class);
			parseImmedRegMode.setAccessible(true);
			int[] regArray;
			regArray = (int[]) parseImmedRegMode.invoke(comp, "ADD $9, $10, -81");
			assertArrayEquals(new int[] {9, 10, -81}, regArray);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail("didn't successfully operate the parseRegistersRegMode method");
		}
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
		String inst = "ADDIU $t1, $t2, 6";
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
		String inst = "ANDI $10, $11, 6";
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
	final void testExecuteSw() { // will test sw by storing the value 72 from register $s2 into address 53

		// offset = 25 + $t4 = 28 = 53
		BitString[] registers = comp.getRegisters();
		registers[12].setValue2sComp(28); // set $t4 to 28

		// set $s2 to 72
		registers[18].setValue2sComp(72); // data to be stored

		// set M[53] = 0
		BitString[] memory = comp.getDataMemory();
		BitString zeroBitString = new BitString();
		zeroBitString.setValue2sComp(0);
		memory[53] = zeroBitString;

		String swInst = "SW $s2, 25($t4)"; // sw into M[53] using $t4 = 28 offset by 25
		ArrayList<String> instArr = new ArrayList<String>();
		instArr.add(swInst);

		try {
			comp.assemble(instArr);
		} catch (IOException e) {
			fail("Received unexpected IOException");
		}
		comp.execute();

		assertEquals(72, registers[18].getValue()); // make sure $s2 got the loaded value (72)
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
		instArr.add("BNE $10, $11, LABEL"); // will branch, not equal
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