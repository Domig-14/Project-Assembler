         LDBA 0x0001, i
         STBA 0xA000, d

         BR main

pr_Name: LDBA 0x0044, i ; D
         STBA 0xFC16, d
         LDBA 0x006F, i ; o
         STBA 0xFC16, d
         LDBA 0x006D, i ; m
         STBA 0xFC16, d
         LDBA 0x0069, i ; i
         STBA 0xFC16, d
         LDBA 0x006E, i ; n
         STBA 0xFC16, d
         LDBA 0x0069, i ; i
         STBA 0xFC16, d
         LDBA 0x0063, i ; c
         STBA 0xFC16, d
         LDBA 0x006B, i ; k
         STBA 0xFC16, d

         LDBA 0x0020, i ; [SPACE]
         STBA 0xFC16, d

         LDBA 0x0053, i ; S
         STBA 0xFC16, d
         LDBA 0x006D, i ; m
         STBA 0xFC16, d
         LDBA 0x0069, i ; i
         STBA 0xFC16, d
         LDBA 0x0074, i ; t
         STBA 0xFC16, d
         LDBA 0x0068, i ; h
         STBA 0xFC16, d

         LDBA 0x000A, i
         STBA 0xFC16, d
         
         LDBA 0xA000, d
         ADDA 0x0001, i
         STBA 0xA000, d

main:    LDBA 0xA000, d
         CPBA 0x0003, i
         
         BRNE frPrint
         BREQ end_loop

frPrint: CALL pr_Name


end_loop:STOP
.END