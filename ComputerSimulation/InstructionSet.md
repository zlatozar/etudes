## Instruction description

In this section we describe each of the instructions. The first line of a description is a short 
summary, giving the instruction name, its format (```RR```, ```RS```, ```IM```, ```CH```), its opcode in hexadecimal, its
assembly language format, and the possible condition code bits affected. A word description
follows the summary.
 
NOTE: In assembly language, the indirect bit is set by writing an asterisk before the address field, as in:
```
LN,R1 *A,R2
```
The code for the **CCR** effect is ```O``` (Overflow), ```L``` (Less than), ```G``` (Greater than),
```E``` (Equal) , and ```None``` (indicating that the **CCR** is unaffected).

 - [LR](#lr)

### LR

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|   Load Register   |   RR   |     00      |    ```LR, R1 R2```       |    G L E     |

The register R1 is loaded with the word at the effective address. The load value is compared
with zero and the G, L, or E bit of the CCR set as appropriate. If the effective address does
not fall on a word boundary, a _word-addressing exception_ occurs.

NOTE: In a comparison to set the CCR, the final result is assumed to hold if the first operand mentioned is on the left of
      the relation and the second on the right. That is, if the result is less than, it means that the first operand is less than
      the second.

### L

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|       Load        |   RS   | 20   |   ```L, R1 A, R2```              |    G L E     |

This instruction operates in the same way as the Load Register instruction except that the
effective address is calculated by using the ```register-and-storage``` addressing algorithm.

### LI

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|     Load Immediate              |   IM     |     40        |       ```LI, R1 I```            |    G L E        |

This instruction operates like the Load Register instruction except that the loaded value is
the immediate operand I with its sign bit extended left 12 bits. No exceptions can occur.

### LC

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|      Load Character             |  CH    |    60         |      ```LC, R1 A, R2```               |      G E      |

Register R1 is cleared to zero, and the character at the effective address is loaded into bits
24 through 31. The loaded value is compared to zero and either the G or E bit of the CCR
set.

### LNR
 
| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|    Load Negative Register               |   RR     |    01         |       ```LNR, R1 R2```     |     O G L E       |

The register R1 is loaded with the two's complement of the word at the effective address.
The loaded result is compared to zero to set the CCR. If overflow occurs, only the O bit of
the CCR is set. A word-addressing exception may occur.

### LN

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|      Load Negative             |   RS     |    21         |       ```LN, R1 A, R2```            |     O G L E        |

This instruction operates in the same way as Load Negative Register except that the 
effective address is calculated by the register-and-storage addressing algorithm.

### LNI

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|    Load Negative Immediate               |    IM    |     41        |    ```LNI, R1 I```   |      GLE      |
 
The value loaded into register R1 is the 32-bit two's complement of the 20-bit two's 
complement value I. Overflow cannot occur. The CCR is set by comparing the loaded value with
zero.

### LNC

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|       Load Negative Character            |    CH      |      61       |     ```LNC, R1 A, R2```   |    LE         |
 
The character at the effective address is extended leftward 24 bits with zeros and the 
resulting word complemented and loaded into register R1. Overflow cannot occur. The loaded
value is compared with zero to set the CCR.

### STR

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|       Store Register            |    RR    |   02       |     ```STR, R1 R2```              |     GLE        |
   
The value in R1 is stored in the word at the effective address. The stored value is compared
to zero to set the CCR. A word-addressing exception may occur.

### ST

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|       Store            |   RS     |     22         |      ```ST, R1 A, R2```              |    GLE        |
  
This instruction operates in the same way as the Store Register instruction with the effective
address calculated by the register-and-storage addressing algorithm.

### STC

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|      Store Character             |   CH     |    62         |      ```STC, R1 A, R2```             |    GE        |

Bits 24 through 31 are stored in the character at the effective address. The stored value is
compared to zero to set the CCR.

### SWAPR

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|       Swap Register            |   RR     |     03        |   ```SWAPR, R1 R2```                |    GLE        |

    
The word in register R1 is exchanged with the word at the effective address. The CCR is
set by comparing the value moved into register R1 with zero. A word-addressing exception
may occur.

### SWAP

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|       Swap            |    RS    |     23        |     ```SWAP, R1 A, R2```              |     GLE       |
    
This instruction operates in the same way as the Swap Register instruction with the 
effective address calculated by the ```register-and-storage``` algorithm.

### SWAPC

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|  Swap Character                 |  CH      |    63         |     ```SWAPC, R1 A, R2```              |   GE         |
    
Bits 24 through 31 are exchanged with the character at the effective address. The CCR is
set by comparing the character loaded into the register with zero. Bits 0 through 23 of
register R1 are not affected.

### ANDR

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|  ```AND``` Register                 |  RR      |      04       |     ```ANDR, R1 R2```              |    GLE        |
    
The logical and of the word in R1 and the word at the effective address is formed and
loaded into register R1. Bit G of the CCR is set if the final value in R1 is all ones, bit L
is set if the result if mixed zeros and ones, and bit E is set if the result is all zeros. A word-
addressing exception may occur.

### AND

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|   ```AND```                |  RS      |    24         |    ```AND, R1 A, R2```               |    GLE        |
  
This instruction operates like the And Register except that the register-and-storage 
addressing algorithm is used to calculate the effective address.

### ANDI

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|    ```AND``` Immediate               |  IM      |  44           |   ```ANDI, R1 I```                |     LE       |

The logical and of the word in register R1 and the 20-bit immediate value I extended on the
left with 12 zero bits is stored in R1. The CCR is set in the same way as the And Register
instruction.

### ANDC

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|   ```AND``` Character               |   CH     |   64          |   ```ANDC, R1 A, R2```                |  GLE          |

The character at the effective address is anded with bits 24 through 31 of register R1 and
the result is replaced in bits 24 through 31 of R1. Bits 0 through 23 of R1 are not affected.
The CCR is set in the same way as the And Register instruction.

### ORR

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|    ```OR``` Register               | RR       |   05          |    ```ORR, R1 R2```               |    GLE        |
    
This instruction operates in the same way as the And Register with logical or replacing
logical and.

### ORI

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|     ```OR``` Immediate              | IM       |    45         |   ```ORI, R1 I```                 |    GLE        |

This instruction operates in the same way as And Immediate with logical and replaced by
logical or.

#### ORC

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|  ```OR``` Character                 | CH       |   65          |    ```ORC, R1 A, R2```               |     GLE       |

This instruction operates in the same way as And Character with logical and replaced by
logical or.

### XORR

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|   Exclusive ```OR``` Register                | RR       |   06          |    ```XORR, R1 R2```               |  GLE          |
    
This instruction operates in the same way as And Register with logical and replaced by
logical exclusive or.

### XOR

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|  Exclusive ```OR```                 |  RS      | 26            |  ```XOR, R1 A, R2```                 |       GLE         |

This instruction operates in the same way as And with logical and replaced by logical
exclusive or.

### XORI

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|   Exclusive OR Immediate                | IM       |  46           |    ```XORI, R1 I```               |    GLE        |

This instruction operates in the same way as And Immediate with logical and replaced by
logical exclusive or.

### XORC

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|  Exclusive OR Character                 | CH       |    66         |   ```XORC, R1 A, R2```                |  GLE          |

This instruction operates in the same as And Character with logical and replaced by logical
exclusive or.

### NOTR

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|  ```NOT``` Register                  |  RR      |   07          |    ```NOTR, R1 R2```               |  GLE          |
    
This instruction operates in the same way as And Register with logical and replaced by
logical complement of the second operand, the original value in register R1 being ignored.

### NOT

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| ```NOT```         |   RS   |  27         |```NOT, R1 A, R2```|   GLE      |

This instruction operates in the same way as And with logical and replaced by logical 
complement of the second operand, the original value in register R1 being ignored.

### NOTI

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|  NOT Immediate                 | IM       |   47          | ```NOTI, R1 I```   |  GLE          |

This instruction operates in the same way as And Immediate with logical and replaced by
logical complement of the extended immediate value, the original value in register R1 being
ignored.

### NOTC

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|   NOT Character                |  CH      |   67          |   ```NOTC, R1 A, R2```                |    GLE        |

This instruction operates in the same way as And Character with logical and replaced by
logical complement of the second operand, the original value of bits 24 through 31 of
register R1 being ignored.

### BCSR

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|Branch Conditions Set Register| RR       |  08           |   ```BCSR ,M1 R2```               |   None         |

If the logical and of the contents of the CCR and the 4-bit logical mask M1 is nonzero,
the contents of the ILC are replaced by the effective address.

### BCS

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|Branch Conditions Set                   | RS       |  28           |   ```BCS, M1 A, R2```                 |  None          |
  
This instruction operates in the same way as Branch Conditions Set Register with the 
effective address calculated by the register-and-storage addressing algorithm.

### BCRR

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|Branch Conditions Reset Register                   | RR       | 09            |  ```BCRR, M1 R2```                 |     None       |
    
If the logical and of the contents of the CCR and the 4-bit logical mask M1 is zero, the 
contents of the ILC are replaced by the effective address.

### BCR

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|Branch Condition Reset                   |  RS      |  29           | ```BCR, M1 A, R2```                  |   None         |
    
This instruction operates in the same way as Branch Conditions Reset Register with the
effective address calculated by the register-and-storage addressing algorithm.

### BALR

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|Branch and Link Register                   | RR       |    0A         |  ```BALR, R1 R2```                 |    None        |

The current contents of the ILC are loaded into register R1 and the effective address is
loaded into the ILC. If the indirect bit is not on, the effective address is register designator
R2 multiplied by 4.

### BAL

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|Branch and Link                   | RS       |   2A          |   ```BAL, R1 A, R2```                |   None         |

The current contents of the ILC are stored in register R1 and the ILC is loaded with the
effective address of the instruction.

### SACR

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|Save Condition Register                   |  RR      |   0B          |    ```SACR, M1 R2```               |   None         |

If the logical and of the CCR and the 4-bit mask field M1 is nonzero, a word of all one bits
is stored in the effective address; otherwise a word of all zeros is stored. A word-addressing
exception may occur.

### SAC

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|Save Condition                   |  RS      |   2B          |    ```SAC, M1 A, R2```               |            |

This instruction operates in the same way as the Store Conditions Register 
instruction with the effective address calculated by the register-and-storage addressing
algorithm.

### SACC

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|Save Condition Character                   | CH       |   6B          | ```SACC, M1 A, R2```                  |   None         |
   
If the logical and of the CCR and the 4 bit mask field Ml is nonzero, a character of all one
bits is stored at the effective address; otherwise a character of all zero bits is stored.

### CR

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Compare Register                  | RR       |     0C        |   ```CR, R1 R2```                 |     GLE       |
   
The results of an algebraic comparison between the contents of register R1 and the word at
the effective address are used to set the G, L, or E bits of the CCR as appropriate.
A word-addressing exception may occur.

### C

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|  Compare                 |  RS      |  2C           |    ```C, R1 A, R2```               |    GLE         |
   
This instruction operates the same way as Compare Register except that the effective 
address is calculated by the register-and-storage addressing algorithm.

#### CI

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Compare Immediate                  | IM       |   4C          |     ``` CI, R1, I```             |  GLE          |
    
The 32-bit value in register R1 is compared algebraically with the 32-bit value constructed
by propagating the immediate operand's sign bit leftward 12 bits, and the result is used to
set the G, L, or E bit of the CCR as appropriate.

### CC

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|  Compare Character                 |  CH      |  6C           |     ```CC, R1 A, R2```              | GLE           |
 
Bits 24 through 31 of register R1 are compared as an 8-bit positive integer with the 
character at the effective address, and the result is used to set the G, L, or E bit of the CCR as
appropriate.

### CCS

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Compare Character String                  | RR       |  0E           |    ```CCS, M1 R2```               |    GLE        |
    
Register designator R2 names a register pair R2 and (R2+1) mod 16 (the second register
will be called R2+1 throughout). The pair R2 and R2+1 should contain a string descriptor
doubleword, with a character address A1 in bits 16 through 31 of register R2, a length L in
bits 0 through 15 of register R2+1, and a character address A2 in bits 16 through 31 of
register R2+1. To begin execution, Al, A2, and L are moved to internal registers, the CCR is
set to zero, and the E bit of the CCR is set to one. A loop is started.

- First, if L is zero, bits 0 through 15 of both registers are set to zero, bits 16 to 31 of R2
  are set to the internal value of Al, bits 16 through 31 of R2+1 are set to the internal
  value of A2, and the instruction terminates.

- Second, the character of Al is compared as an 8-bit integer to the character at A2 and the
  result used to set the appropriate bits of the CCR.

- Third, if the E bit of the CCR is not one, bits 0 through 15 of register R2 are set to
  zero, bits 16 through 31 of R2 to the internal value of Al, bits 0 through 15 of R2+1
  to the internal value of L, bits 16 through 31 of R2+1 to the internal value of A2, and
  the instruction terminates.

- Finally, L is decremented by 1, A1 is incremented by the mask M1 interpreted as a 4-bit
  two's complement integer, and A2 is incremented by 1, and the loop returns to the
  first step.

### MCS

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Move Character String                  | RR       |   0F          |    ```MCS, M1 R2```               | None           |

Registers R2 and (R2+1) mod 16 contain a string descriptor doubleword as described in
Compare Character String. The L, Al, and A2 fields are loaded into internal registers. A
loop is begun.

- First, if L is zero, bits 0 through 15 of registers R2 and R2+1 are set to zero, bits 16
  through 31 of R2 to A1, bits 16 through 31 of R2+1 to A2, and the instruction terminates.

- Second, the character at location A1 is stored at character location A2.

- Third, L is decremented by 1 and A2 is incremented by 1.

- Finally, A1 is incremented by the mask M1 interpreted as a 4-bit two's complement
  integer and the loop returns to its first step.

### SVC

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Supervisor Call                  |  RS      |  2E           |  ```SVC, R1 A, R2```                 |   None         |

Program execution is interrupted and a call made to a controlling supervisor program.

### EX

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|  Execute                 | RS       |  2F           |  ```EX, R1 A, R2```                 |  None          |

The instruction at the effective address is executed. The effects of the subject instruction
become the effects of the Execute instruction. If the effective address is not even, an
_execute address exception_ occurs. Execute instructions may be nested to any depth. Note
that the ILC is changed only if explicitly modified by the subject instruction.

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|Load Address                   |  RS      |    4E         |      ```LA, R1 A, R2```             |    None        |
    
Register R1 is loaded with the instruction's effective address.

### LM

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Load Multiple                  |RS        |   6E          | ```LM, R1 A, R2```                  |     None       |

Registers R1 through R2 are loaded from consecutive words in memory, beginning at the
effective address (the effective address is calculated by assuming that the index register
designator is zero). If R2 is less than R1, registers R1 through 15 and 0 through R2 are
loaded. A word-addressing exception may occur.

### STM

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Store Multiple                  | RS       |   6F          |   ```STM, R1 A, R2```                |   None         |

Registers R1 through R2 are stored into consecutive words of memory, beginning at the
effective address (the effective address is calculated by assuming the index register 
designator is zero). If R2 is less than R1, registers R1 through 15 and 0 through R2 are stored.
A word-addressing exception may occur.

### AR

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|Add Register                   |  RR      |  10           |     ```AR, R1 R2```              |   OGLE         |
   
The word in R1 is added to the word at the effective address and the result is placed in R1.
The sum is compared to zero to set the CCR. If overflow occurs, only the O bit of the CCR
is set. A word-addressing exception may occur.

### A

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Add                  |   RS     |   30          | ```A, R1 A, R2```                  |   OGLE         |
    
This instruction operates in the same way as Add Register with the effective address 
calculated by the register-and-storage addressing algorithm.

### AI

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Add Immediate                  |  IM      |   50          |    AI,R1 I               |   OGLE         |

    
The 20-bit two's complement immediate operand I is added to the value in register R1 and
the sum stored in R1. The sum is compared to zero to set the CCR. If overflow occurs, only
the O bit of the CCR is set.

### AC

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|Add Character                   |   CH     |  70           |  ```AC, R1 A, R2```                 |   OGLE         |

   
The character at the effective address is extended 24 bits to the left with zeros and added to
the value in register R1 with the result loaded into R1. The sum is compared to zero to set
the CCR. If overflow occurs, only the O bit of the CCR is set.

### SR

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|  Subtract Register                 | RR       |   11          |    ```SR, R1 R2```               |   OGLE         |

    
The word at the effective address (the subtrahend) is subtracted from the value in register
R1 (the minuend) and the difference is stored in R1. The difference is compared to zero to
set the CCR. If overflow occurs, only the O bit of the CCR is set. A word-addressing 
exception may occur.

### S

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|  Subtract                 |   RS     |    31         |    ```S, R1 A, R2```               |    OGLE        |

    
This instruction operates the same way as Subtract Register with the effective address
calculated by the register-and-storage addressing algorithm.

### SI

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|  Subtract Immediate                 |   IM     |   51          |    ```SI, R1 I```               |    OGLE        |
    
The 20-bit two's complement integer immediate operand I (the subtrahend) is subtracted
from the value in register R1 (the minuend) and the result stored in register R1. The difference
is compared to zero to set the CCR. If overflow occurs, only the O bit of the CCR is set.

### SC

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Subtract Character                  |   CH     |    71         |    ```SC, R1 A, R2```               |   OGLE         |
   
The character at the effective address (the subtrahend), treated as a positive integer by
extension 24 bits leftward with zeros, is subtracted from the value in register R1 (the
minuend) and the result stored in R1. The difference is compared to zero to set the CCR.
If overflow occurs, only the O bit of the CCR is set.

### RSR

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Reverse Subtract Register                  |  RR      |    12         |    ```RSR, R1 R2```               |    OGLE        |
    
This instruction operates the same way as the Subtract Register instruction except that the
roles of the minuend and the subtrahend are reversed.

NOTE: In all the reversed instructions, although the roles of the two operand values are interchanged, the result is still
      stored in the same place.

### RS

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Reverse Subtract                  | RS       |  32           |    ```RS, R1 A, R2```               |  OGLE          |
    
This instruction operates the same way as Subtract except that the roles of the minuend
and the subtrahend are reversed.

### RSI

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Reverse Subtract Immediate                  |  IM      |   52          |     ```RSI, R1 I```              |  OGLE          |
    
This instruction operates the same way as Subtract Immediate except that the roles of the
minuend and the subtrahend are reversed.

#### RSC

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Reverse Subtract Character                  |    CH    |   72          |   ```RSC, R1 A, R2```                |    OGLE        |
    
This instruction operates the same way as the Subtract Character with the roles of the
minuend and the subtrahend reversed.

### MR

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Multiply Register                  | RR       |    13         |    ```MR, R1 R2```               |     OGLE       |
    
The value in register R1 and the word at the effective address are multiplied and the low-
order 32 bits of the product are stored in register R1. The result in register R1 is compared
to zero to set the CCR. If overflow occurs, only the **O** bit of the CCR is set. A word-
addressing exception may occur.

### M

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|  Multiply                 |  RS      |    33         |   ```M, R1 A, R2```                |     OGLE       |
    
This instruction operates the same way as Multiply Register except that the effective 
address is calculated by the register-and-storage addressing algorithm.

### MI

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Multiply Immediate                  |  IM      |   53          |   ```MI,R1 I```                |    OGLE        |
    
The low 32 bits of the product of the value in register R1 and the 20-bit immediate value I
are stored in register R1. The product in register R1 is compared to zero to set the CCR. If
overflow occurs, only the O bit of the CCR is set.

### MC

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|  Multiply Character                 |  CH      |   73          |        ```MC,R1 A,R2```          |  OGLE          |
    
The low 32 bits of the product of the value in register R1 and the positive 8-bit integer
in the character at the effective address are stored in register R1. The value in register R1
is compared to zero to set the CCR. If overflow occurs, only the **O** bit of the CCR is set.

### DR

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|   Divide Register                | RR       |  14           |   ```DR,R1 R2```                |    OGLE        |
    
The value in register R1 (the _dividend_) is divided by the word at the effective address (the
_divisor_) and the quotient is stored in register R1. The quotient is selected so that the 
remainder is nonnegative. The quotient is compared to zero to set the CCR. If overflow
occurs, only the O bit of the CCR is set. A word-addressing exception may occur. If
the divisor is zero, the _zero divisor exception_ occurs and register R1 is unchanged.

### DI

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|   Divide Immediate                |   IM     |   54          |    ```DI,R1 I```               |   OGLE         |
    
The value in register R1 (the dividend) is divided by the 20-bit two's complement integer
immediate value I (the divisor) and the quotient is stored in register R1. The quotient is
selected so that the remainder is non negative. The quotient is compared to zero to set the
CCR. If overflow occurs, only the O bit of the CCR is set. If the divisor is zero, the zero
divisor exception occurs and the register R1 is unchanged.

### DC

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|  Divide Character                 |   CH     | 74            |      ```DC,R1 A,R2```            |     GLE       |
 
The value in register R1 (the dividend) is divided by the positive 8-bit integer at the 
effective address (the divisor) and the quotient is stored in register R1. The quotient is selected
so that the remainder is non negative. The quotient is compared to zero to set the CCR. If
the divisor is zero, the zero divisor exception occurs and register R1 is unchanged. Overflow
is not possible.

### RDR

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Reverse Divide Register                  |   RR     |     15        |   ``` RDR,R1 R2```                |    OGLE        |
   
This instruction operates the same way as Divide Register except that the roles of the
dividend and divisor are reversed.

### RD

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Reverse Divide                  | RS       |     35        |   ```RD,R1 A,R2 ```                |     OGLE       |

This instruction operates the same way as Divide except that the roles of the dividend and
the divisor are reversed.

### RDI

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|Reverse Divide Immediate                   |  IM      |  55           |  ```RDI,R1 I ```                 |   GLE         |
   
This instruction operates the same way as Divide Immediate except that the roles of the
dividend and the divisor are reversed. Overflow is not possible.

### RDC

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Reverse Divide Character                  | CH       |   75          |       ```RDC,R1 A,R2```            |    GLE        |
    
This instruction operates the same way as Divide Character except that the roles of the
dividend and the divisor are reversed.

### REMR

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Remainder Register                  |   RR     |       16      |     ```REMR,R1 R2```              |   GE         |
    
The value in register R1 (the dividend) is divided by the word at the effective address (the
divisor) and the non negative remainder is stored in register R1. The remainder is 
compared to zero to set the CCR. A word-addressing exception may occur. If the divisor is zero,
the zero divisor exception occurs and register R1 is unchanged.

### REM

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|   Remainder                |  RS      |    36         |     ```REM,R1 A,R2```              |   GE         |
   
This instruction operates the same way as Remainder Register except that the effective
address is calculated by the register-and-storage addressing algorithm.

### REMI

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Remainder Immediate                  | IM       |  56           |    ```REMI,R1 I```               |   GE         |
    
The value in register R1 (the dividend) is divided by the 20-bit two's complement value I
(the divisor) and the non negative remainder is stored in register R1. The remainder is 
compared to zero to set the CCR. If the divisor is zero, the zero divisor exception occurs and
register R1 is unchanged.

### REMC

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|  Remainder Character                 |   CH     |  76           |    ```REMC,R1 A,R2```               |   GE         |
    
The value in register R1 (the dividend) is divided by the 8-bit positive integer (the divisor)
at the effective address and the nonnegative remainder is stored in register R1. The 
remainder is compared to zero to set the CCR. If the divisor is zero, the zero divisor exception
occurs and register R1 is unchanged.

### RREMR

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|  Reverse Remainder Register                 | RR       |   07          |     ```RREMR,R1 R2```              |    GE        |
    
This instruction operates the same way as Remainder Register except that the roles of
dividend and divisor are reversed.

### RREM

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|  Reverse Remainder                 |  RS      |   37          |   ```RREM,R1 A,R2```                |    GE        |
    
This instruction operates the same way as Remainder except that the roles of dividend and
divisor are reversed.

### RREMI

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Reverse Remainder Immediate                  |  IM      |    57         |    ```RREMI,R1 I```               |     GE       |
   
This instruction is the same as Remainder Immediate except that the roles of dividend and
divisor are reversed.

### RREMC

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
| Reverse Remainder Character                  |  CH      |   77          |    ```RREMC,R1 A,R2```               |    GE        |
    
This instruction is the same as Remainder Character except that the roles of dividend and
divisor are reversed.

####

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |


###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

####

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |


###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

####

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |


###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |

###

| ```Instruction name```  | ```Format``` | ```OpCode(hex)``` | ```Assembly language``` | ```CCR effect``` |
|:-----------------:|:------:|:-----------:|:-----------------:|:----------:|
|                   |        |             |                   |            |
