# Mega Impressive Programming Simulator

This project is a MIPS simulator created in Java with a GUI created in Java Swing. The GUI allows users to enter their MIPS code in a text area as well as allowing registers and memory to be viewed and modified. 

## Contents 
  - [Project Decisions and Considerations](#project-decisions-and-considerations)
  - [How to Run the Program](#how-to-run-the-program)
    - [How to Enter Values into Register/Memory](#how-to-enter-values-into-registermemory)
    - [How to Format Instructions](#how-to-format-instructions)
  - [Not Handled by Our Simulator](#not-handled-by-our-simulator)
    - [Incorrect Code](#incorrect-code)
    - [Zero](#zero)
  - [Examples of Working Code](#examples-of-working-code)
  - [Tests](#tests)
  - [UML Diagram](#uml-diagram)
  - [Built With](#built-with)
  - [Authors](#authors)

## Project Decisions and Considerations 

* Our program works with assembly level langauge 
* Spaces and commas are ignored
* Input is taken through the GUI and stored in instruction memory 
  * Instruction memory is 50 
* Memory exists in the form of data memory/instruction memory 
  * Data memory is visualized by the GUI
* 32 registers that are visualized by the GUI 
* Instructions are executed all at once 


## How to Run the Program 
*The main method is in the GUI.* 

Once you have downloaded the component files and started up the GUI, 
1. Enter MIPS code into the main text box. Formatting is flexible and is described in more detail [below.](###How-to-format-instructions)
2. Enter values into register/memory text boxes as desired. Further instruction on how to do this [follows.](###How-to-Enter-Values-into-Register/Memory)
3. Click the ``Assemble`` button. 
4. Click the ``Run`` button. 

### How to Enter Values into Register/Memory 

1. Click the appropriate register/memory 
2. Select and delete all of the present text 
3. Enter the desired **decimal** value and press Enter to convert to binary 

### How to Format Instructions 
Multiple instruction formats are valid and will run within the Mega Impressive Programming Simulator; however, the preferred method of input format is *generally* the same format as MARS. 

* Case is ignored 
* Registers can be entered with or without dollar signs 
* Registers can be entered by their number or by register
* Both commas and spaces are handled 
* ``.data`` and ``.text`` are **not** handled 
* 32 bit offset as opposed to byte offset, **unlike** MARS 

[Click here](#examples-of-working-code) to jump to examples of proper code. 

## Not Handled by Our Simulator
### Incorrect Code 
* The Mega Impressive Programming Simulator is not equipped to deal with bad code -- behavior under these conditions is unpredictable. Hopefully your code works! 

An example of some code that won't work: 
```
addi $t1, $t2, $t3
```
```
 $t1, $t2, $t3 
```
### Zero 
The ``$zero`` register is 0 and will remain 0 until changed; however,  **it can be changed** and is not hardcoded to be 0. 

## Examples of Working Code 
The following code shows what kinds of instructions and formats are taken by our simulator. 

* The offset used in ``lw`` and ``sw`` is represented in single units as opposed to what you might see in MARS, as shown below: 

In MARS
```
sw $s2, 4($t4)
lw $s0, 8($t2)
```
In our simulator, the **equivalent** instructions are 
```
sw $s2, 1($t4)
lw $s0, 2($t2)
```

Essentially, we divide by four. 

Other examples: 

```
ADDI $t0, $zero, 5
ADDI $t1, $zero, 3
ADD $t3, $t0, $t1

ADDI $t4, $zero, 81
ADDI $t5, $zero, 6
OR $t6, $t4, $t5

AND $s0, $t3, $t6
```

which outputs a result of ``0``.
```
ADDI $t5, $zero, 5
ADDI $t4, $zero, 5
BNE $t4, $t5, aLabel: 

ADDI $t5, $zero, 6
ADDI $t4, $zero, 11
BEQ $t4, $t5, aLabel:

ADDI $t1, $zero, -81



ADDI $t2, $zero, 5
OR $t0, $t1, $t2
aLabel:
```
which outputs a result of ``-81``. 

## Tests

JUnit tests were created for the Computer class and can be found under ``ComputerTest.java``. 

## UML Diagram 
![UML Diagram](MIPS_UML.png)
## Built With

* Java
* Java Swing

## Authors

* [Thaddaeus Hug](https://github.com/tadhug)
* [Mercedes Chea](https://github.com/mercedeschea)
* [Mariko Briggs](https://github.com/marikobriggs)
