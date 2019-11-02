public class Simulator {

	public static void main(String[] args) {

		Computer comp;

		/************************************** */
		/** The next two variables - program and programSize - */
		/** allow someone using the simulator (such as a grader) */
		/** to decide what program will be simulated. */
		/** The simulation must load and execute */
		/** instructions found in the "program" array. */
		/** For grading purposes, it must be possible for me to */
		/**
		 * - paste in a different set of binary strings to replace the existing
		 * ones
		 */
		/** - recompile your program without further changes */
		/** and see the simulator load and execute the new program. */
		/** Your grade will depend largely on how well that works. */
		/************************************** */

		String program[] = { "0010000000000111", "0010001000000111",
				"0001010000000001", "0000010000000011", "1111000000100001",
				"0001000000111111", "0000111111111011", "1111000000100101",
				"0000000000111001", "1111111111010000" };

		/*
		 * This is the assembly program that was compiled into the binary
		 * program shown above. 
		 * 		.ORIG x3000
		 * 
		 * 		LD R0 START 
		 * 		LD R1 END 
		 * TOP 	ADD R2 R0 R1 
		 * 		BRZ DONE 
		 * 		OUT 
		 * 		ADD R0 R0 -1
		 * 		BRNZP TOP 
		 * DONE HALT
		 * 
		 * START .FILL x39 
		 * END .FILL x-30
		 * 
		 * 		.END
		 */
		comp = new Computer();
		comp.display();

		/* TO DO: load the instructions in the "program" array */

		/* Next 3 lines are a test of NOT */
		/* Once you are confident that single instructions work, you will */
		/* want to replace this with code that loads all the instructions */
		/* from the array shown above. */
		BitString notInstr = new BitString();
		notInstr.setBits("1001100101111111".toCharArray());
		comp.loadWord(0, notInstr);

		/* execute the program */
		/* During execution, the only output to the screen should be */
		/* the result of executing OUT. */
		comp.execute();

		/* shows final configuration of computer */
		comp.display();
	}

}
