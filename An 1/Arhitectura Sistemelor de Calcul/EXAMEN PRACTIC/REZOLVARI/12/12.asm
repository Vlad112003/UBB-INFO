;se citesc 3 numere din fisier. sa se afiseze inmultirea lor pe ecran
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start       

; declare external functions needed by our program
extern exit,fopen,fclose,printf,fscanf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll   
import fscanf msvcrt.dll                       ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    mod_acces db 'r',0
    nume db 'input.txt',0
    descriptor dd -1
    format db '%d %d %d'
    format_d db '%d'
    a dd -1
    b dd -1
    c dd -1
    aux dd -1
; our code starts here
segment code use32 class=code
    start:
        ;fopen(nume,mod_acces)
        push dword mod_acces
        push dword nume
        call [fopen]
        add esp,4*2
        
        cmp eax,0
        je final_tot
        mov [descriptor],eax
        
        ;fscanf(descriptor,format,variabile)
        push dword c
        push dword b
        push dword a
        push dword format
        push dword[descriptor]
        call [fscanf]
        add esp,4*5
        
        mov eax,dword[a]
        mov ebx,dword[b]
        mov ecx,dword[c]
        mul bl ;->rezultat in ax
        mul cx ;rezultat in dx ax
        
        push dx
        push ax
        pop dword[aux]
        
        ;printf(format,variabile)
        push dword[aux]
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
