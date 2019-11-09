import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Computer class comprises of memory, registers, and
 * can execute the instructions based on PC and IR 
 * @author mmuppa
 *
 */
public class Computer {

	private final static int MAX_INST_MEMORY = 50;
	private final static int MAX_DATA_MEMORY = 50;
	private final static int MAX_REGISTERS = 32;

	private BitString myRegisters[];
	private BitString myDataMemory[];
	private String myInstMemory[];
	private Map<String, Integer> myRegisterMappings;
	private int myPC;
	private int myIR;

	/**
	 * Initializes all the memory to 0, registers to 0 to 32,
	 * and PC, IR to empty strings
	 * Represents the initial state 
	 */
	public Computer() {
		createRegisterMappings();
		myPC = 0;
		myIR = 0;
		myRegisters = new BitString[MAX_REGISTERS];
		for (int i = 0; i < MAX_REGISTERS; i++) {
			myRegisters[i] = new BitString();
			myRegisters[i].setValue(i);
		}

		myDataMemory = new BitString[MAX_DATA_MEMORY];
		myInstMemory = new String[MAX_INST_MEMORY];
		for (int i = 0; i < MAX_DATA_MEMORY; i++) {
			myDataMemory[i] = new BitString();
			myDataMemory[i].setValue(0);
		}
	}
	
	/**
	 * Returns the register file for this Computer
	 * @return this Computer's register file
	 */
	BitString[] getRegisters() {
		return myRegisters;
	}
	
	/**
	 * Reads strings from array, checks that they represent valid instructions, and places them in instruction memory.
	 * 
	 * Note that assembling is being simply simulated and the instructions are not actually being assembled to machine code (bits).
	 * 
	 * @param instructions the array of instructions (Strings)
	 * @throws IOException when instructions don't fit in memory
	 */
	public void assemble(List<String> instructions) throws IOException {
		if (instructions.size() > myInstMemory.length) {
			throw new IOException("Too many instructions to fit in instruction memory");
		}
		for (int i = 0; i < instructions.size(); i++) {
			myInstMemory[i] = instructions.get(i);
		}
	}
	
	/**
	 * Execute the instructions in the instruction memory. 
	 * At this stage, can assume that the instructions have register markings of the form $n for 0 <= n <= 31,
	 * as this is done at "assemble" time.
	 * 
	 * Additionally, opcodes will be all uppercase and instruction tokens will be comma and space separated.
	 * e.g. "ADDI $1, $15, -5"
	 * @return the output of the execution
	 */
	public List<String> execute() {
		
		List<String> output = new ArrayList<String>();
		
		for (int i = 0; i < myInstMemory.length; i++) {
			switch(getOpcode(myInstMemory[i]))
			{
			   case "ADD" :
			      executeAdd();
			      break; 
			   
			   case "ADDU" :
			      executeAddu();
			      break;
			      
			   case "AND" :
				  executeAnd();
				  break;
			      
			   case "OR" :
				  executeOr();
				  break;
				  
			   case "ADDI" :
				  executeAddi();
				  break;
				  
			   case "ADDIU" :
				  executeAddiu();
				  break;
				  
			   case "ANDI" :
				  executeAndi();
				  break;
				  
			   case "ORI" :
				  executeOri();
				  break;
				  
			   case "LW" :
				  executeLw();
				  break;
				  
			   case "SW" :
				  executeSw();
				  break;
				  
			   case "BEQ" :
			      executeBeq();
				  break;
				  
			   case "BNE" :
			      executeBne();
				  break;
				  
			   case "J" :
			      executeJ();
				  break;
				  
			   case "JR" :
				  executeJr();
				  break;
				  
			   default :    // no opcodes matched!
			      // Statements
			}
		}

		
		
		return output;
//		String input = "ADD $t1, $t2, $t3";
//		Scanner scan = new Scanner(input.replace(",", ""));
//		String instr = scan.next();
//		String destR = scan.next();
//		String shiftAmt = scan.next();
//		String funcCode = scan.next(); 
//		scan.close();
//		System.out.println("Instruction: " + instr);
//		System.out.println("Dest reg: " +  destR);
//		System.out.println("Shift amt: " + shiftAmt);
//		System.out.println("Func code: " + funcCode);
	}
	

	/**
	 * Given a register mode instruction, returns the register numbers for dr, s1, & s2 in an array.
	 */
	private int[] parseRegistersRegMode(String instr) {
	    String noSpaceString = instr.replaceAll(" ", "");
	    Scanner s = new Scanner(noSpaceString);
	    s.useDelimiter("[,$]");
	    s.next(); // get rid of ADD at start of string
	    int[] regArray = new int[3];
	    regArray[0] = Integer.parseInt(s.next());
	    regArray[1] = Integer.parseInt(s.next());
	    regArray[2] = Integer.parseInt(s.next());
	    s.close();
		return regArray;
	}
	
	/**
	 * Given a register mode instruction, returns the register numbers for dr, s1, & constant in an array.
	 */
	private int[] parseImmedRegMode(String instr) {
		String noSpaceString = instr.replaceAll(" ", "");
	    Scanner s = new Scanner(noSpaceString);
	    s.useDelimiter("[,$]");
	    s.next(); // get rid of ADD at start of string
	    int[] regArray = new int[3];
	    regArray[0] = Integer.parseInt(s.next());
	    regArray[1] = Integer.parseInt(s.next());
	    regArray[2] = Integer.parseInt(s.next());
	    s.close();
		return regArray;
	}
	/**
	 * Executes the add operation from the String representation of the instruction in IR. 
	 * This is a register mode instruction of the form <ADD $DR, $S1, $S2>
	 */
	public void executeAdd() {
		int[] regArray = parseRegistersRegMode(myInstMemory[myIR]);
		int sum = myRegisters[regArray[1]].getValue2sComp() + myRegisters[regArray[2]].getValue2sComp();
		myRegisters[regArray[0]].setValue2sComp(sum);
	}
	
	/**
	 * Executes the addu operation from the String representation of the instruction in IR. 
	 * This is a register mode instruction of the form <ADDU $DR, $S1, $S2>
	 */
	public void executeAddu() {
		int[] regArray = parseRegistersRegMode(myInstMemory[myIR]);
		int sum = myRegisters[regArray[1]].getValue() + myRegisters[regArray[2]].getValue();
		myRegisters[regArray[0]].setValue(sum);
	}
	
	/**
	 * Executes the and operation from the String representation of the instruction in IR. 
	 * This is a register mode instruction of the form <AND $DR, $S1, $S2>
	 */
	public void executeAnd() {
		int[] regArray = parseRegistersRegMode(myInstMemory[myIR]);
		int sum = myRegisters[regArray[1]].getValue2sComp() + myRegisters[regArray[2]].getValue2sComp();
		myRegisters[regArray[0]].setValue2sComp(sum); // modify from ADD to AND
	}
	
	/**
	 * Executes the or operation from the String representation of the instruction in IR. 
	 * This is a register mode instruction of the form <OR $DR, $S1, $S2>
	 */
	public void executeOr() {
		int[] regArray = parseRegistersRegMode(myInstMemory[myIR]);
		char sr1[] = myRegisters[regArray[1]].getBits();
		char sr2[] = myRegisters[regArray[2]].getBits();
		char newOR[] = new char[sr1.length];
		
		for (int i = 0; i < sr1.length; i++) {
			if (sr1[i] == sr2[i]) {
				newOR[i] = 1;
			}
			else {
				newOR[i] = 0;
			}
		}
		
		myRegisters[regArray[0]].setBits(newOR); // modify from ADD to OR
	}
	
	private void executeOri() {
		int[] regArray = parseImmedRegMode(myInstMemory[myIR]);
		
	}
	
	private void executeJr() {
		// TODO Auto-generated method stub
		
	}

	private void executeJ() {
		// TODO Auto-generated method stub
		
	}

	private void executeBne() {
		// TODO Auto-generated method stub
		
	}

	private void executeBeq() {
		// TODO Auto-generated method stub
		
	}

	private void executeSw() {
		// TODO Auto-generated method stub
		
	}

	private void executeLw() {
		// TODO Auto-generated method stub
		
	}

	private void executeAndi() {
		// TODO Auto-generated method stub
		
	}

	private void executeAddiu() {
		// TODO Auto-generated method stub
		
	}

	private void executeAddi() {
		// TODO Auto-generated method stub
		
	}
	
	private void createRegisterMappings() { 
		myRegisterMappings = new HashMap<String, Integer>();
		myRegisterMappings.put("$zero", 0);
		myRegisterMappings.put("$at", 1);
		myRegisterMappings.put("$v0", 2);
		myRegisterMappings.put("$v1", 3);
		myRegisterMappings.put("$a0", 4);
	    myRegisterMappings.put("$a1", 5);
	    myRegisterMappings.put("$a2", 6);
	    myRegisterMappings.put("$a3", 7);
	    myRegisterMappings.put("$t0", 8);
	    myRegisterMappings.put("$t1", 9);
	    myRegisterMappings.put("$t2", 10);
	    myRegisterMappings.put("$t3", 11);
	    myRegisterMappings.put("$t4", 12);
	    myRegisterMappings.put("$t5", 13);
	    myRegisterMappings.put("$t6", 14);
	    myRegisterMappings.put("$t7", 15);
	    myRegisterMappings.put("$s0", 16);
	    myRegisterMappings.put("$s1", 17);
	    myRegisterMappings.put("$s2", 18);
	    myRegisterMappings.put("$s3", 19);
	    myRegisterMappings.put("$t2", 20);
	    myRegisterMappings.put("$t3", 21);
	    myRegisterMappings.put("$t4", 22);
	    myRegisterMappings.put("$t5", 23);
	    myRegisterMappings.put("$t6", 24);
	    myRegisterMappings.put("$t7", 25);
	    myRegisterMappings.put("$k0", 26);
	    myRegisterMappings.put("$k1", 27);
	    myRegisterMappings.put("$gp", 28);
	    myRegisterMappings.put("$sp", 29);
	    myRegisterMappings.put("$fp", 30);
	    myRegisterMappings.put("$ra", 31);
	}
	
	/**
	 * Given an instruction String, returns the String containing just the opcode (name of instruction)
	 * @param string full, original instruction
	 * @return opcode name of instruction
	 */
	private String getOpcode(String instr) {
		Scanner s = new Scanner(instr);
		s.useDelimiter(" ");
		String opcode = s.next();
		s.close();
		return opcode;
	}
//		regArray[1].
//		
//		char str1arr[] = bitstr1.toCharArray();
//		BitString bitstr = new BitString();
//		bitstr.setValue2sComp(immed);
//		char str2arr[] = bitstr.getBits();
//		char newStr[] = new char[bitstr1.length()];
//		for (int i = 0; i < bitstr1.length(); i++) {
//			if (str1arr[i] == str2arr[i]) {
//				newStr[i] = 1;
//			}
//			else {
//				newStr[i] = 0;
//			}
//		}
//		myRegisters[dr] = setBits(newStr);
		
//		int[] regArray = parseRegistersRegMode(myIR);
//		
//		char sr1[] = myRegisters[regArray[1]].getBits();
//		char sr2[] = myRegisters[regArray[2]].getBits();
//		char newOR[] = new char[sr1.length];
//		
//		for (int i = 0; i < sr1.length; i++) {
//			if (sr1[i] == sr2[i]) {
//				newOR[i] = 1;
//			}
//			else {
//				newOR[i] = 0;
//			}
//		}
//		
//		myRegisters[regArray[0]].setBits(newOR);
		
//	/**
//	 * Loads a 16 bit word into memory at the given address. 
//	 * @param address memory address
//	 * @param word data or instruction or address to be loaded into memory
//	 */
//	public void loadWord(int address, BitString word) {
//		if (address < 0 || address >= MAX_DATA_MEMORY) {
//			throw new IllegalArgumentException("Invalid address");
//		}
//		myDataMemory[address] = word;
//	}
//
//	// TODO - Set CC (remove this after implementing)
//	/**
//	 * Performs not operation by using the data from the register based on bits[7:9] 
//	 * and inverting and storing in the register based on bits[4:6]
//	 */
//	public void executeNot() {
//		BitString destBS = myIR.substring(4, 3);
//		BitString sourceBS = myIR.substring(7, 3);
//		myRegisters[destBS.getValue()] = myRegisters[sourceBS.getValue()].copy();
//		myRegisters[destBS.getValue()].invert();
//	}

//	/**
//	 * This method will execute all the instructions starting at address 0 
//	 * till HALT instruction is encountered. 
//	 */
//	public void execute() {
//		BitString opCodeStr;
//		int opCode;
//
//		while (true) {
//			// Fetch the instruction
//			myIR = myInstMemory[myPC.getValue()];
//			myPC.addOne();
//
//			// Decode the instruction's first 4 bits 
//			// to figure out the opcode
//			opCodeStr = myIR.substring(0, 4);
//			opCode = opCodeStr.getValue();
//
//			// What instruction is this?
//			if (opCode == 9) { // NOT
//				executeNot();
//				return;
//			}
//			// TODO - Others
//		}
//	}

//	/**
//	 * Displays the computer's state
//	 */
//	public void display() {
//		System.out.print("\nPC ");
//		myPC.display(true);
//		System.out.print("   ");
//
//		System.out.print("IR ");
//		myPC.display(true);
//		System.out.print("   ");
//
//		System.out.print("CC ");
//		myCC.display(true);
//		System.out.println("   ");
//
//		for (int i = 0; i < MAX_REGISTERS; i++) {
//			System.out.printf("R%d ", i);
//			myRegisters[i].display(true);
//			if (i % 3 == 2) {
//				System.out.println();
//			} else {
//				System.out.print("   ");
//			}
//		}
//		System.out.println();
//
//		for (int i = 0; i < MAX_MEMORY; i++) {
//			System.out.printf("%3d ", i);
//			myMemory[i].display(true);
//			if (i % 3 == 2) {
//				System.out.println();
//			} else {
//				System.out.print("   ");
//			}
//		}
//		System.out.println();
//
//	}
	

}
