;5.	Se citeste de la tastatura un sir de caractere (litere mici si litere mari, cifre, caractere speciale, etc). Sa se formeze un sir nou doar cu literele mici si un sir nou doar cu literele mari. Sa se afiseze cele 2 siruri rezultate pe ecran.
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,gets,printf              ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import gets msvcrt.dll                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import printf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s times 100 db -1
    format_afisare db '%s',0
    sirul_mic times 100 db -1
    sirul_mare times 100 db -1
    new_line db 10,13,0
; our code starts here
segment code use32 class=code
    start:
        ;scanf(format,variabile)
        push dword s
        call [gets]
        add esp,4*1
        
        mov esi,s
        sub esi,1 ;ca il cresc de fiecare data in loop la inceput
        mov ebx,sirul_mic
        mov edi,sirul_mare
        repeta:
            add esi,1
            cmp byte[esi],0 ;verific daca am ajuns la final
            je final_repeta
            
            ;verific litera mica
            cmp byte[esi],'a'
            jb verifica_litera_mare
            cmp byte[esi],'z'
            ja repeta
            jbe sir_mic
            
            verifica_litera_mare:
                cmp byte[esi],'A'
                jb repeta
                cmp byte[esi],'Z'
                ja repeta
                jbe sir_mare
            
            sir_mic:
                mov al,byte[esi]
                mov byte[ebx],al
                add ebx,1
            
            jmp sari_siruri
            
            sir_mare:
                mov al,byte[esi]
                mov byte[edi],al
                add edi,1
                
            sari_siruri:
            jmp repeta
                
        final_repeta:
        
        mov byte[edi],0
        mov byte[ebx],0
        
        ;printf(format,variabile)
        push dword sirul_mic
        push dword format_afisare
        call [printf]
        add esp, 4*2
        
        push dword new_line
        push dword format_afisare
        call [printf]
        add esp, 4*2
        
        push dword sirul_mare
        push dword format_afisare
        call [printf]
        add esp, 4*2
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
