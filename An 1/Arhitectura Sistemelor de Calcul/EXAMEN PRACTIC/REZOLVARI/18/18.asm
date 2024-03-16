;se citeste un sir din input.txt. sa se afiseze invers
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,fscanf,fopen,fclose,printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fopen msvcrt.dll                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fclose msvcrt.dll
import printf msvcrt.dll
import fscanf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    mod_acces_r db 'r',0
    nume db 'input.txt',0
    descriptor dd -1
    chr db -1
    format_c db '%c',0
    format_s db '%s',0
    sir times 100 db -1

; our code starts here
segment code use32 class=code
    start:
        ;fopen(nume,mod_acces)
        push dword mod_acces_r
        push dword nume
        call [fopen]
        add esp, 4*2
        
        cmp eax,0
        je final_tot
        mov dword[descriptor],eax
      
        mov edi,sir
        repeta:
            ;fscanf(descriptor,format,variabile)
            push dword chr
            push dword format_c
            push dword[descriptor]
            call [fscanf]
            add esp,4*3
            
            cmp eax,-1
            je final_repeta
            
            mov al,byte[chr]
            mov byte[edi],al
            inc edi
            jmp repeta
        final_repeta:
        
        ;afisare invers
        repeta1:
            dec edi
            cmp edi,sir-1
            je final_repeta1
            
            mov eax,0
            mov al,byte[edi]
            push eax
            push dword format_c
            call [printf]
            add esp,4*2
            
            jmp repeta1
        final_repeta1:
        
        
        ;fcolse(descriptor)
        push dword[descriptor]
        call [fclose]
        add esp,4*1
        final_tot:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
