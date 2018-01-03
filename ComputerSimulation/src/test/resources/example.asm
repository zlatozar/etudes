;
; This program tests the Pythagorean relation on the values stored at
; X, Y, and Z. The external procedure 'Square' is used to calculate
; the square of a value and is entered one word past its head. The
; symbol 'Good' goes on the map only.
;
           .DEF Pythagoras
           .REF Square
           .MAP Good
;
;

Add         FAR,2    1           ; Add register 1 to register 2 and
            BCRR,0   *15         ; returns via register 15.

Pythagoras  L,1      X           ; Square X.
            BAL, 15  Square+4    ; Into register 1.
            LR,2     1           ; Save it in register 2.
            L,1      Y           ; Do the same for Y
            BAL,15   Square+4    ; and add it in
            BAL,15   Add         ; to the sum.
            L,1      Z           ; Now square Z, subtract
            BAL,15   Square+4    ; the running sum, and test
            FSR,1    2           ; for zero.
            BCS,1    Good
            SVC,7    False       ; Print 'F' and quit.
            SVC,0    0
Good        SVC,7    True        ; Print 'T' and exit via
            BCRR,0   *14         ; register 14.

True        DSC      'T'         ; Define a character constant.
False       DSC      'F'
X           DSF      3.          ; Define a real constant.
Y           DSF      4.
Z           DSF      5.

            .END      Pythagoras
