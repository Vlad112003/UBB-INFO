bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
                          extern exit,fopen,fclose,fscanf,printf
import exit msvcrt.dll
import fopen msvcrt.dll
import fscanf msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db -1
    min dd 16
    mod_acces db "r",0
    nume_fisier db "input.txt",0
    descriptor dd -1
    format_citire db "%c",0
    format_afisare db "%c%d",0
    spatiu dd ' '
    
; our code starts here
segment code use32 class=code
    start:
        
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 4*2
        
        mov [descriptor],eax
        cmp eax,0
        je final
        
        repeta:

        push dword a
        push dword format_citire
        push dword [descriptor]
        call [fscanf]
        add esp, 4*3
        
        cmp eax,-1
        je final_repeta ;verific daca mai sunt caractere de citit
        
        cmp byte[a],' '
        jne sari_1      ;sa nu afisam daca nu s-a terminat primul numar
        
        push dword[min]
        push dword[spatiu]
        push dword format_afisare
        call [printf]
        add esp, 4*3
        
        mov dword[min],16
        jmp sari_repeta    ;sa trec la urmatorul caracter, dupa ce actualizez minimul la 16 din nou
        
        sari_1: 
        
        cmp byte[a],'9'
        jle sari_numar
        ja sari_caracter
        
        sari_numar:
        mov bl,byte[a]
        sub bl,'0'
        jmp actualizare
        
        sari_caracter:
        mov bl,byte[a]
        sub bl,'A'
        add bl,10
        
        actualizare:
        mov cl,bl
        mov ebx,0
        mov bl,cl ;fac sa fie 0 in rest in ebx
        cmp dword[min],ebx
        ja mai_mic
        jle sari_repeta
        
        mai_mic:
        mov dword[min],ebx
        
        sari_repeta:
        
        jmp repeta
        
        final_repeta:
        
        ; afisez pe ecran ultimul minim
        push dword[min]
        push dword[spatiu]
        push dword format_afisare
        call [printf]
        add esp, 4*3
        
        
        final:
        push dword[descriptor]
        call fclose
        add esp, 4*1
        
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
