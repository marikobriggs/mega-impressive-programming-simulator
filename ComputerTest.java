import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * 
 */

/**
 * Class to test the Computer class's functionality
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
	 * Test method for {@link Computer#compile(java.util.List)}.
	 */
	@Test
	final void testCompile() {
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
	 * Test method for {@link Computer#executeAdd()}.
	 */
	@Test
	final void testExecuteAdd() {
		String inst = "ADD $t1, $t2, $t3";
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
	 * Test method for {@link Computer#executeAddU()}.
	 */
	@Test
	final void testExecuteAddU() {
		String inst = "ADD $t1, $t2, $t3";
		ArrayList<String> instArr = new ArrayList<String>();
		instArr.add(inst);
		BitString[] registers = comp.getRegisters();
		registers[10].setValue(5);
		// put in t2
		registers[11].setValue(6);
		try {
			comp.assemble(instArr);
		} catch (IOException e) {
			fail("Received unexpected IOException");
		}
		comp.execute();

		assertEquals(11, registers[9].getValue());

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
		// put in t2
		registers[12].setValue2sComp(5);

		try {
			comp.assemble(instArr);
		} catch (IOException e) {
			fail("Received unexpected IOException");
		}
		comp.execute();
		assertEquals(7, registers[10].getValue2sComp());
	}

	/**
	 * Test method for {@link Computer#createMap()}.
	 */
	@Test
	final void testCreateMap() {
		fail("Not yet implemented");
	}

}
