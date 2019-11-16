import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;

/**
 * Computer class comprises of memory, registers, and
 * can execute the instructions based on PC and IR 
 * @author mmuppa
 *
 */
public class Computer {

	private final static int MAX_INST_MEMORY = 50;
	private final static int MAX_DATA_MEMORY = 128;
	private final static int MAX_REGISTERS = 32;

	private BitString myRegisters[];
	private BitString myDataMemory[];
	private String myInstMemory[];
	private Map<String, Integer> myRegisterMappings;
	private int myPC;
	private String myIR;
	private Map<String, Integer> myLabelMap;

	/**
	 * Initializes all the memory to 0, registers to 0 to 32,
	 * and PC to 0, IR to empty string
	 * Represents the initial state 
	 */
	public Computer() {
		createRegisterMappings();
		myLabelMap = new HashMap<String, Integer>();
		myPC = 0;
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
	 * Returns the register file for this Computer
	 * @return this Computer's register file
	 */
	public BitString[] getRegisters() {
		return myRegisters;
	}
	
	public BitString[] getDataMemory() {
		// TODO Auto-generated method stub
		return myDataMemory;
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
		
		for (int i = 0; i < myInstMemory.length; i++) {
			myInstMemory[i] = "";
		}
		
		myLabelMap = new HashMap<String, Integer>(); // forget old label mappings, these are new instructions
		if (instructions.size() > myInstMemory.length) {
			throw new IOException("Too many instructions to fit in instruction memory");
		}

		int i = 0;
		for (String inst : instructions) {
			String unparsedInst = inst.toLowerCase().split("#")[0].trim(); // remove comments
			if ("".equals(unparsedInst)) { // no instruction here
				continue; // stop looking here, go to next instruction
			} else if (unparsedInst.contains(":")) { // there's a label
				String label = unparsedInst.split(":")[0].trim();
				myLabelMap.put(label, i); // this label points to the before the instruction about to be processed (pc +1 will happen)
				if (!(unparsedInst.split(":").length == 1)) {
					unparsedInst = unparsedInst.split(":")[1]; // this is now a normal instruction
					for (String s : myRegisterMappings.keySet()) {
						// dollar signs are viewed as ending a line in regex so must escape this interpretation with \\
						unparsedInst = unparsedInst.replaceAll("\\" + s, Matcher.quoteReplacement("$" + myRegisterMappings.get(s)));
					}
					myInstMemory[i] = unparsedInst.toLowerCase().trim();
					i++; // put next instruction at next instruction address
				} else {
					continue; // there was only a label no instruction, go to next instruction
				}

			} else { // this is just a normal instruction
				for (String s : myRegisterMappings.keySet()) {
					// dollar signs are viewed as ending a line in regex so must escape this interpretation with \\
					unparsedInst = unparsedInst.replaceAll("\\" + s, Matcher.quoteReplacement("$" + myRegisterMappings.get(s)));
				}
				myInstMemory[i] = unparsedInst.toLowerCase();
				i++; // put next instruction at next instruction address
			}

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
	public void execute() {
		myPC = 0;



		
		executeWhile: 
		while (true) { 
			
			myIR = myInstMemory[myPC];
			myPC++;
			
			if (myIR == null || myIR.equals("")) {
				myIR = "";
			  	myPC = 0;
				break;
			}
			switch(myIR.split("\\s")[0].toUpperCase()) { // get opcode
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
				  
			   default :    // no opcodes matched! done executing
				  myIR = "";
				  myPC = 0;
			      break executeWhile; 
			}
		}
		myPC = 0;
	}
	

	/**
	 * Given a register mode instruction, returns the register numbers for dr, s1, & s2 in an array.
	 */
	private int[] parseRegistersRegMode(String instr) {
		String modifiedString = instr.replaceAll("[$,]", " ");
		Scanner scanner = new Scanner(modifiedString);
		scanner.next(); // get rid of opcode
	    int[] regArray = new int[3];
	    regArray[0] = scanner.nextInt();
	    regArray[1] = scanner.nextInt();
	    regArray[2] = scanner.nextInt();
	    scanner.close();
		return regArray;
	}
	
	/**
	 * Given a register mode instruction, returns the register numbers for dr, s1, & constant in an array.
	 */
	private int[] parseImmedRegMode(String instr) {
		String modifiedString = instr.replaceAll("[$,]", " ");
		Scanner scanner = new Scanner(modifiedString);
		scanner.next(); // get rid of opcode
	    int[] regArray = new int[3];
	    regArray[0] = Integer.parseInt(scanner.next());
	    regArray[1] = Integer.parseInt(scanner.next());
	    regArray[2] = Integer.parseInt(scanner.next());
	    scanner.close();
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
	public void executeAddu() {
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
		char sr1[] = myRegisters[regArray[1]].getBits();
		char sr2[] = myRegisters[regArray[2]].getBits();
		char newOR[] = new char[sr1.length];
		
		for (int i = 0; i < sr1.length; i++) {
			if (sr1[i] == '1' && sr2[i] == '1') {
				newOR[i] = '1';
			}
			else {
				newOR[i] = '0';
			}
		}
		myRegisters[regArray[0]].setBits(newOR);
	}
	
	/**
	 * Executes the or operation from the String representation of the instruction in IR. 
	 * This is a register mode instruction of the form <OR $DR, $S1, $S2>
	 */
	public void executeOr() {
		int[] regArray = parseRegistersRegMode(myIR);
		char sr1[] = myRegisters[regArray[1]].getBits();
		char sr2[] = myRegisters[regArray[2]].getBits();
		char newOR[] = new char[sr1.length];
		
		for (int i = 0; i < sr1.length; i++) {
			if (sr1[i] == '1' || sr2[i] == '1') {
				newOR[i] = '1';
			}
			else {
				newOR[i] = '0';
			}
		}
		myRegisters[regArray[0]].setBits(newOR);
	}
	
	/**
	 * Executes the ori operation from the String representation of the instruction in IR. 
	 * This is an immediate mode instruction of the form <ORI $DR, $S1, n>
	 */
	private void executeOri() {
		int[] regArray = parseImmedRegMode(myIR);
		char sr1[] = myRegisters[regArray[1]].getBits();
		BitString constant = new BitString();
		constant.setValue2sComp(regArray[2]);
		char sr2[] = constant.getBits();
		
		char newOR[] = new char[sr1.length];
		for (int i = 0; i < sr1.length; i++) {
			if (sr1[i] == '1' || sr2[i] == '1') {
				newOR[i] = '1';
			}
			else {
				newOR[i] = '0';
			}
		}
		myRegisters[regArray[0]].setBits(newOR);
	}
	
	/**
	 * Executes the addi operation from the String representation of the instruction in IR. 
	 * This is an immediate mode instruction of the form <ADDI $DR, $S1, n>
	 */
	private void executeAddi() {
		int[] regArray = parseImmedRegMode(myIR);
		BitString constant = new BitString();
		constant.setValue2sComp(regArray[2]);
		for (int n : regArray) {
		}
		int sum = myRegisters[regArray[1]].getValue2sComp() + constant.getValue2sComp();
		myRegisters[regArray[0]].setValue2sComp(sum);
	}
	
	/**
	 * Executes the addiu operation from the String representation of the instruction in IR. 
	 * This is an immediate mode instruction of the form <ADDIU $DR, $S1, n>
	 */
	private void executeAddiu() {
		int[] regArray = parseImmedRegMode(myIR);
		BitString constant = new BitString();
		constant.setValue(regArray[2]);
		
		int sum = myRegisters[regArray[1]].getValue() + constant.getValue();
		myRegisters[regArray[0]].setValue(sum);
		
	}
	
	/**
	 * Executes the andi operation from the String representation of the instruction in IR. 
	 * This is an immediate mode instruction of the form <ANDI $DR, $S1, n>
	 */
	private void executeAndi() {
		int[] regArray = parseImmedRegMode(myIR);
		char sr1[] = myRegisters[regArray[1]].getBits();
		BitString constant = new BitString();
		constant.setValue2sComp(regArray[2]);
		char sr2[] = constant.getBits();
		
		char newAND[] = new char[sr1.length];
		for (int i = 0; i < sr1.length; i++) {
			if (sr1[i] == '1' && sr2[i] == '1') {
				newAND[i] = '1';
			}
			else {
				newAND[i] = '0';
			}
		}
		myRegisters[regArray[0]].setBits(newAND);
	}
	
	/**
	 * Executes the lw operation from the String representation of the instruction in IR. 
	 * This is an instruction of the form <lw $dr, offset($sr)>, executes $dr = dataMem[offset + R[sr]]
	 */
	private void executeLw() {
		Scanner scanner = new Scanner(myIR.replaceAll("[$,()]", " "));
		scanner.next(); // get rid of opcode
		int destReg = scanner.nextInt();
		int offset = scanner.nextInt();
		int addrReg = scanner.nextInt();
		scanner.close();
		myRegisters[destReg] = myDataMemory[myRegisters[addrReg].getValue() + offset];
	}
	
	/**
	 * Executes the sw operation from the String representation of the instruction in IR. 
	 * This is an instruction of the form <sw $sr, offset($dra)>, executes  dataMem[offset + R[dra]] = $sr
	 */
	private void executeSw() {
		Scanner scanner = new Scanner(myIR.replaceAll("[$,()]", " "));
		scanner.next(); // get rid of opcode
		int valueReg = scanner.nextInt();
		int offset = scanner.nextInt();
		int addrReg = scanner.nextInt();
		scanner.close();
		myDataMemory[myRegisters[addrReg].getValue() + offset] = myRegisters[valueReg];
	}
	
	/**
	 * Executes the beq operation from the String representation of the instruction in IR. 
	 * This is an immediate mode instruction of the form <bne $sr, $sr, label>
	 */
	private void executeBeq() {
		String modifiedString = myIR.replaceAll("[$,]", " ");
		Scanner scanner = new Scanner(modifiedString);
		scanner.next(); // get rid of opcode
	    int num1 = myRegisters[scanner.nextInt()].getValue2sComp();
	    int num2 = myRegisters[scanner.nextInt()].getValue2sComp();
	    String label = scanner.next();
	    scanner.close();
	    if (num1 == num2) {
			myPC = myLabelMap.get(label).intValue();
	    }
	}
	
	/**
	 * Executes the j operation from the String representation of the instruction in IR. 
	 * This is an immediate mode instruction of the form <J label>
	 */
	private void executeBne() {
		String modifiedString = myIR.replaceAll("[$,]", " ");
		Scanner scanner = new Scanner(modifiedString);
		scanner.next(); // get rid of opcode
	    int num1 = myRegisters[scanner.nextInt()].getValue2sComp();
	    int num2 = myRegisters[scanner.nextInt()].getValue2sComp();
	    String label = scanner.next();
	    scanner.close();
	    if (num1 != num2) {
			myPC = myLabelMap.get(label);
	    }
		
	}
	
	/**
	 * Executes the j operation from the String representation of the instruction in IR. 
	 * This is an immediate mode instruction of the form <J label>
	 */
	private void executeJ() {
		String[] instructionAsArray = myIR.split("\\s");
		String label = instructionAsArray[instructionAsArray.length - 1];
		myPC = myLabelMap.get(label);
	}
	
	/**
	 * Executes the jr operation from the String representation of the instruction in IR. 
	 * This is a register mode instruction of the form <J $SR>, performs PC = R[sr].
	 */
	private void executeJr() {
		String[] instructionAsArray = myIR.split("\\$");
		int register = Integer.parseInt(instructionAsArray[instructionAsArray.length - 1]);
		myPC = myRegisters[register].getValue();
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
	    myRegisterMappings.put("$s4", 20);
	    myRegisterMappings.put("$s5", 21);
	    myRegisterMappings.put("$s6", 22);
	    myRegisterMappings.put("$s7", 23);
	    myRegisterMappings.put("$t8", 24);
	    myRegisterMappings.put("$t9", 25);
	    myRegisterMappings.put("$k0", 26);
	    myRegisterMappings.put("$k1", 27);
	    myRegisterMappings.put("$gp", 28);
	    myRegisterMappings.put("$sp", 29);
	    myRegisterMappings.put("$fp", 30);
	    myRegisterMappings.put("$ra", 31);
	}
	
	public Map<String, Integer> getRegisterMappings() {
		return myRegisterMappings;
	}
}
