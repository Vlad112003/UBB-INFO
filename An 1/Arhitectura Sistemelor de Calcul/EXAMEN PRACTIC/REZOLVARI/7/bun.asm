; se da un sir de qword definit in data segment. elementele sirului sunt in baza 16
;   a)sa se printeze bytes care compun qword urile din sir
;   b)sa se identifice words care au ultima cifra egala cu constanta k definita in segmentul de date. sa se salveze aceste words in sirul d
;   c)sa se identifice dwords care sunt cuprinse in intervalul AAA - FFFF. sa se determine numarul dwords din acest interval. sa se afiseze acest numar in baza 10 in fisierul 'iesire.txt'
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,printf,fopen,fprintf,fclose               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fopen msvcrt.dll
import fprintf msvcrt.dll
import fclose msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s dq 1122334455667788h, 11AA22BB33CC44DDh, 0ABCh
    lens equ $-s
    copie dd -1
    copie1 dd -1
    format1 db '%x ',0
    format2 db '%d',0
    hexa dw 010h ;adica 16
    k dw 2h
    new_line db 10,13,0
    format_extra db '%s'
    words times 20 dw -1
    inceput dd 0AAAh
    final dd 0FFFFh
    nume_fisier db 'iesire.txt',0
    mod_acces db 'w',0
    descriptor dd -1
; our code starts here
segment code use32 class=code
    start:
        ;------------------
        ;a-----------------
        mov ecx, lens ;pt ca lens e cati byte sunt in s
        mov esi,s
        repeta1:
            mov eax,0
            lodsb
            
            mov [copie],ecx
            ;printf(format,variabile)
            push eax
            push dword format1
            call [printf]
            add esp,4*2
            
            mov ecx,[copie]
        loop repeta1
        ;-------------------
        ;b------------------
        mov ecx, lens/2
        mov esi,s
        mov edi,words
        repeta2:
            mov eax,0
            lodsw
            
            mov [copie1],eax
            div word[hexa] ;in dx am restul
            cmp dx,word[k]
            je stocare
            jmp final2
            
            stocare:
                stosw
            
            final2:
        loop repeta2
        ;-------------------
        ;c------------------
        mov ecx, lens/4
        mov esi,s
        mov ebx,0
        repeta3:
            mov eax,0
            lodsd
            
            cmp eax,[inceput]
            jb final3
            cmp eax,[final]
            ja final3
            inc ebx
            
            final3:
        loop repeta3
        
        ;afisare ebx in fisier iesire.txt
        ;fopen(nume_fisier,mod_acces)
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        
        cmp eax,0
        je nu_s_a_deschis
        mov [descriptor],eax
        
        ;fprintf(descriptor,format,variabile)
        push dword ebx
        push dword format1
        push dword[descriptor]
        call [fprintf]
        add esp,4*3
        
        ;fclose(descriptor)
        push dword[descriptor]
        call [fclose]
        add esp,4*1
        
        
        
        nu_s_a_deschis:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
