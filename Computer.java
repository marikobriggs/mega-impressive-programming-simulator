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
