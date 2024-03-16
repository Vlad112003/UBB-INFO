;Se citesc din fisierul numere.txt mai multe numere (pare si impare). Sa se creeze 2 siruri rezultat N si P astfel: N - doar numere impare si P - doar numere pare. Afisati cele 2 siruri rezultate pe ecran.
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,fscanf,fopen,fclose,printf              ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fscanf msvcrt.dll                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fopen msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    filename db "numere.txt",0
    acces_mode db 'r',0
    descriptor dd -1
    cif dd -1
    format db '%c',0
    format_afisare db '%s',0
    destinatie times 100 db -1

    
    
; our code starts here
segment code use32 class=code
    start:
        mov esi,destinatie
        ;fopen(filename,acces_mode)
        push dword acces_mode
        push dword filename
        call [fopen]
        
        ;vedem daca s-a deschis
        cmp eax,0
        je out_program
        mov dword[descriptor],eax
        
        repeta:
            ;fscanf(descriptor,format,variabila)
            push dword cif
            push dword format
            push dword [descriptor]
            call [fscanf]
            add esp,4*3
            
            cmp eax,-1
            je out_citire
            
            add esi,1
            mov al,byte[cif]
            mov byte[esi],al
            
            jmp repeta
        out_citire:
        
        ;fclose(descriptor)
        push dword [descriptor]
        call [fclose]
        add esp,4*1
        
        add esi,1
        mov byte[esi],0
        
        ;printf(format,variabile)
        push dword destinatie
        push dword format_afisare
        call [printf]
        add esp,4*2
        
        out_program:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
