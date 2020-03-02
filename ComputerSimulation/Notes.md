## Kinds of instructions

### Structure of the instruction

If the _indirect bit_ is set we have indirect addressing.
The _operation code_ or _ops code_ are should be validated by assembler.

`R1` (8-11 bits) – is a register or mask.
`R2` (12-15 bits) - index register designator (kind of pointer) or register

`address` - address in the memory. Most commonly label address is set here.

### Register to Register (RR)

This is the easy part - `R2` is always a register. We have two cases depending from the indirect bit

#### Direct addressing
If it is `0` we deal with two registers. Example:
```assembler
LR, 2 1    * Move contenet of register 1 in regiter 2
```

#### Indirect register addressing
If it is `1` means that R2 should be _pointer_(address) to the real data (second operand). Assembler parse it and set
address in R2. When the opcode is 'interpreted' the _address field_ is filled with the current content of the
address location. Example:
```assembler
L, 0  *15    * Load the content of the memory address stored at register 15 to register 0.
```
Register indirect addressing means that the location of an operand is held in a register.
It is also called _indexed addressing_ or _base addressing_.

Register indirect addressing mode requires three read operations to access an operand.

- Read the instruction to find the pointer register
- Read the pointer register to find the oprand address
- Read memory at the operand address to find the operand

### Register and Storage (RS)

#### Indirect bit is 0, R2 is not set and address field is set
In this case we work with register and data situated in that address (in most cases it is a label). Example:
```assembler
BAL, 15 Add    * 'Add' is a lable
```

#### Indirect bit is 0, R2 is set and address field is set
It is used to define offset. Works like previous case except that we add the value of R2 to the address in
address field. Example:
```assembler
BAL, 15 Square + 4   * 'Square' is a lable(address) and the 4 is the offset and assembler place it in R2
```

__Indirect addressing_ may be used for code or data. It can make implementation of
pointers, references, or handles much easier, and can also make it easier to
call subroutines which are not otherwise addressable. Indirect addressing does
carry a performance penalty due to the extra memory access involved.

#### Indirect bit is 1, R2 is not set and address field is set
Kind of _pointer to pointer_. Address filed points to the pointer(situated) in memory. From that address we
obtain second operand.

![Indirect Addressing Mode](images/indirect-addressing-mode.png)

#### Indirect bit is 1, R2 is set and address field is set
Same as previous one except that R2 adds offset. Could be used to work with two dimensional arrays.

![Displacement Addressing Mode](images/displacement-addressing-mode.png)

## Example Assembler Program

```
* This program tests the Pythagorean relation on the values stored at
* X, Y, and Z. The external procedure Square (result in reg. 1)
* is used to calculate the square of a value and is entered one word
* past its head (offset**. The symbol Good goes on the map only.
*
* NOTE: That's why we do + 4 to find the real start addres of Square
* NOTE2: ICL = Program Counter

   DEF Pythagoras

   REF Square  * Linker will handle this
   MAP Good    * ??

* Like a procedure
* With given values in registers 1 and 2 returns the sum in reg 2
Add         FAR,  2  1    * Sum reg 1 and reg 2 (the result goes in reg 2)
            BCRR, 0  *15  * Procedure return.
                          * If ICL is 0 we return to the address that reg 15 contains

* pow(Х) + pow(Y) = pow(Z)
Pythagoras  L,   1   X           * Load what we have in X. Then the
            BAL, 15  Square + 4  * content of the current ICL is moved to reg 15
                                 * and the address of Square is placed in ICL. It's kind of
                                 * calling a procedure.

            LR,  2   1           * The result from reg 1 (Square procedure place result there)
                                 * we move to reg 2

            L,   1   Y           * For Y we do the same. Reg 1 contains the result.
            BAL, 15  Square + 4  * What we achieve: pow(X) in reg 2 and
                                 *                  pow(Y) in reg 1

            BAL, 15  Add         * Having this we can sum them
                                 * and the result(pow(X) + pow(Y)) goes in reg 2

            L,   1   Z           * For Z we do the same and result is in reg 1
            BAL, 15  Square + 4

            FSR, 1   2           * reg 2 (pow(X) + pow(Y)) we compare with reg 1 (pow(Z))
            BCS, 1   Good        * Branching condition. If they are the same go to lable Good,
            SVC, 7   False       * else make interrupt with code 7 ('F' is displayed)
            SVC, 0   0           * and exit. For codes see: Exceptions and supervisor calls

Good        SVC, 7   True        * Interrupt with code 7 ('T' is displayed)
            BCRR,0   *14         * and exit with "success" - so 0 in reg 14
                                 * The programmer who use Pythagoras must know that
                                 * the result is in reg 14

* Assembler specific not opscodes

True        DSC       'T'      * DSC - defines char
False       DSC       'F'

X           DSF       3.	   * DSF - defines float
Y           DSF       4.
Z           DSF       5.

            END       Pythagoras

```

Note that the assembler syntax is not defined in book.
