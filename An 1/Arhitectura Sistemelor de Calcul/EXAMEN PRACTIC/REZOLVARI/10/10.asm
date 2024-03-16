;Se citeste de la tastatura m,n apoi m numere zecimale, pe linii diferite. Sa se afiseze nr cu cel putin n cifre pare in output.txt.
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,scanf,printf,gets,fopen,fprintf,fclose               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import scanf msvcrt.dll                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import printf msvcrt.dll
import gets msvcrt.dll
import fopen msvcrt.dll
import fprintf msvcrt.dll
import fclose msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    m dd -1
    n dd -1
    nr times 15 db -1
    nr_bun times 15 db -1
    format_m_n db '%d %d',0
    format_nr db '%s',0
    format_nr1 db '%s ',0
    new_line db 10,13,0 
    zero db 0
    unu db 1
    doi db 2
    ok db 0
    copie dd -1
    copie1 dd -1
    asterix db '*'
    sir_bun times 100 db -1
    nume db 'output.txt',0
    mod_acces db 'w',0
    descriptor dd -1

; our code starts here
segment code use32 class=code
    start:
        ;fopen(nume,mod_acces)
        push dword mod_acces
        push dword nume
        call [fopen]
        
        cmp eax,0
        je final_tot
        mov [descriptor],eax
        ;scanf(format,variabile)
        push dword n
        push dword m
        push dword format_m_n
        call [scanf]
        add esp,4*3
        
        mov ecx,dword[m]
        add ecx,1
        repeta:
            mov [copie],ecx
            ;gets(nr)
            push dword nr
            call [gets]
            add esp,4*1    
            mov esi,nr
            mov edi,nr_bun
            mov bl,byte[zero]
            mov byte[ok],bl
            repeta_nr:        
                lodsb
                cmp al,0
                je final_repeta_nr
                stosb ;in edi il pune daca nu s-a ajuns la final
                
                movzx ax,al
                div byte[doi]; in ah restul
                cmp ah,0
                jne impar
                
                add byte[ok],1
             
                impar:
                jmp repeta_nr
            final_repeta_nr:
            
            mov bl,byte[ok]
            cmp bl,byte[n]
            jne nu_afisa
            mov byte[edi],0
            ;frintf(descriptor,format,variabile)
            push dword nr_bun
            push dword format_nr1
            push dword[descriptor]
            call [fprintf]
            add esp,4*3
            
            nu_afisa:

            mov ecx,[copie]
        loop repeta
        ;fclose(descriptor)
        push dword[descriptor]
        call [fclose]
        add esp,4
        final_tot:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
