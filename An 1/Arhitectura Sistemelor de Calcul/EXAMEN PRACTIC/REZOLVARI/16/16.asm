; se citesc nr din fisier, sa se faca suma nr mai mici decat 8 si sa se afiseze pe ecran
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,fopen,fclose,fscanf,printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fclose msvcrt.dll                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fopen msvcrt.dll
import fscanf msvcrt.dll
import printf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    mod_acces_r db 'r',0
    nume db 'input.txt',0
    descriptor dd -1
    nr dd -1
    format_d db '%d',0
    opt dw 8
    suma dw 0
; our code starts here
segment code use32 class=code
    start:
        ;fopen(nume,mod_acces)
        push dword mod_acces_r
        push dword nume
        call [fopen]
        add esp,4*2
        
        cmp eax,0
        je final_tot
        mov dword[descriptor],eax
        
        repeta:
            ;fscanf(descriptor,format,variabile)
            push dword nr
            push dword format_d
            push dword[descriptor]
            call [fscanf]
            add esp,4*3
            
            cmp eax,-1
            je final_repeta
            
            mov eax,dword[nr]
            cmp ax,word[opt]
            jae nu_aduna
            
            mov bx,word[suma]
            add bx,ax
            mov word[suma],bx
            
            nu_aduna:
            jmp repeta
        final_repeta:
        
        ;afisare suma pe ecran
        mov ebx,0
        mov bx,word[suma]
        
        push ebx
        push dword format_d
        call [printf]
        add esp,4*2
        
        ;fclose(descriptor)
        push dword[descriptor]
        call [fclose]
        add esp,4*1
        
        final_tot:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
