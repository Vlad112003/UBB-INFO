bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s dq 1122334455667788h, 11AA22BB33CC44DDh
    lens equ ($-s)/8
    format_afisare db '%x ',0
    copie dd -1
    copie1 dd -1
    aux dw -1
    k dw 2h
    hexa db 10h
    sir_words times lens*4 dw -1

; our code starts here
segment code use32 class=code
    start:
    
    ;-------------------------------------
    ;a
        mov esi,s
        mov ecx,lens 
        CLD
        
        repeta:
        
            mov [copie],ecx
            mov ecx,8 
            repeta1:
                mov eax,0
                mov al,[esi+ecx-1] ;incarca in al ultimul byte din primul qword(little endian) 
                ;esi+7 =11
                ;esi+6=22
                ;esi+5=33
                ;esi+4=44
                ;esi+3=55
                ;esi+2=66
                ;esi+1=77
                ;esi+0=88
                
                mov [copie1],ecx ;se strica ecx la push/call
                
                push dword eax
                push dword format_afisare
                call [printf]
                add esp,4*2
                
                mov ecx,[copie1]
                
            loop repeta1
            mov ecx,[copie]  
            add esi,8 ;trec de primul qword
        
        loop repeta
        
    ;-------------------------------------
    ; b) Sa se extraga din fiecare quadword acele word-uri care au ultima cifra egala cu o constanta k, definita in data segment
        mov ebx,0
        mov esi,s
        mov edi,sir_words
        repet:
        inc ebx
        mov ecx,8
        repet2:
            sub ecx,2
            ;ax=[esi+ecx]
            mov ax,[esi+ecx]
            mov [aux],ax
            mov dx,0
            div word[hexa]
            cmp dx,[k]
            jne ultima_cifra_nu_e_k
            mov ax,[aux]
            mov [edi],ax
            add edi,2
            ultima_cifra_nu_e_k:
            
        cmp ecx,0
        jne repet2
        add esi,8
        cmp ebx,lens
        jne repet
        
        
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
;Se da un sir de qwords in data segment in baza 16.
;a) Sa se printeze pe ecran sirul byte-ilor care compun qword-ul
;b) Sa se extraga din fiecare qword acele word-uri care au ultima cifra egala cu o constanta k definita in data segment.
;c) Sa se extraga dword-urile a caror valoare este in intervalul AAAh -> FFFh. Sa se printeze in fisier nr. dword-urilor extrase.