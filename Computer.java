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
	private String myPC;
	private String myIR;

	/**
	 * Initializes all the memory to 0, registers to 0 to 32,
	 * and PC, IR to empty strings
	 * Represents the initial state 
	 */
	public Computer() {
		myPC = "";
		myPC = "";
		myIR = "";
		myIR = "";
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
	 * Reads strings from array, checks that they represent valid instructions, and places them in instruction memory.
	 * 
	 * @param instructions the array of instructions (Strings)
	 */
	public void compile(String[] instructions) {
		for (int i = 0; i < instructions.length; i++) {
			
		}
	}
	

	public void execute() {
		String input = "ADD $t1, $t2, $t3";
		Scanner scan = new Scanner(input.replace(",", ""));
		String instr = scan.next();
		String destR = scan.next();
		String shiftAmt = scan.next();
		String funcCode = scan.next(); 
		scan.close();
		System.out.println("Instruction: " + instr);
		System.out.println("Dest reg: " +  destR);
		System.out.println("Shift amt: " + shiftAmt);
		System.out.println("Func code: " + funcCode);
	}
	
	
	/**
	 * Given a register mode instruciton, returns the register numbers for dr, s1, & s2 in an array.
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
	 * Executes the add operation from the String representation of the instruction in IR. 
	 * This is a register mode instruction of the form <ADD $DR, $S1, $S2>
	 */
	public void executeAdd() {
		int[] regArray = parseRegistersRegMode(myIR);
		int sum = myRegisters[regArray[1]].getValue2sComp() + myRegisters[regArray[2]].getValue2sComp();
		myRegisters[regArray[0]].setValue2sComp(sum);
	}
	
	/**
	 * Executes the addu operation from the String representation of the instruction in IR. 
	 * This is a register mode instruction of the form <ADDU $DR, $S1, $S2>
	 */
	public void executeAddU() {
		int[] regArray = parseRegistersRegMode(myIR);
		int sum = myRegisters[regArray[1]].getValue() + myRegisters[regArray[2]].getValue();
		myRegisters[regArray[0]].setValue(sum);
	}
	
	/**
	 * Executes the and operation from the String representation of the instruction in IR. 
	 * This is a register mode instruction of the form <AND $DR, $S1, $S2>
	 */
	public void executeAnd() {
		int[] regArray = parseRegistersRegMode(myIR);
		int sum = myRegisters[regArray[1]].getValue2sComp() + myRegisters[regArray[2]].getValue2sComp();
		myRegisters[regArray[0]].setValue2sComp(sum); // modify from ADD to AND
	}
	
	/**
	 * Executes the or operation from the String representation of the instruction in IR. 
	 * This is a register mode instruction of the form <OR $DR, $S1, $S2>
	 */
	public void executeOr() {
		int[] regArray = parseRegistersRegMode(myIR);
		int sum = myRegisters[regArray[1]].getValue2sComp() + myRegisters[regArray[2]].getValue2sComp();
		myRegisters[regArray[0]].setValue2sComp(sum); // modify from ADD to OR
	}
	
	public void executeORI(String bitstr1, int immed) {
		int dr = myIR.substring(beginIndex);
		int sr1 = myIR.subString(beginIndex, endIndex);
		int sr2 = myIR.substring(beginIndex);
		char str1arr[] = bitstr1.toCharArray();
		BitString bitstr = new BitString();
		bitstr.setValue2sComp(immed);
		char str2arr[] = bitstr.getBits();
		char newStr[] = new char[bitstr1.length()];
		for (int i = 0; i < bitstr1.length(); i++) {
			if (str1arr[i] == str2arr[i]) {
				newStr[i] = 1;
			}
			else {
				newStr[i] = 0;
			}
		}
		myRegisters[dr] = setBits(newStr);
		
	}
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
