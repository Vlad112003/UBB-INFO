;Se citeste o propozitie de la tastatura. Sa se numere literele din fiecare cuvant si sa se afiseze aceste numere pe ecran.
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,gets,printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import gets msvcrt.dll; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import printf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    format_afisare db '%d ',0
    sursa times 100 db -1
    destinatie times 100 db -1
    contor dd 0
; our code starts here
segment code use32 class=code
    start:
        
        push dword sursa
        call [gets]
        add esp,4*1
        
        mov esi,sursa
        mov edi,destinatie
        
        repeta:
            cmp byte[esi],0
            je final_repeta
            
            cmp byte[esi],' '
            jne stocheaza
            je numara
            
            stocheaza:
                movsb ;mut la adresa edi litera de la adresa esi
                jmp repeta
                
            numara:
                sub edi,1 ;ultimul movsb a crescut pozitia
                repeta1:
                    cmp edi,destinatie-1 ;sa vedem daca am numarat toate literele
                    je final_repeta1
                    
                    add byte[contor],1
                    sub edi,1
                    jmp repeta1
                    
                final_repeta1:
                
                ;afisez literele citite
                ;printf(format,variabila)
                push dword [contor]
                push dword format_afisare
                call [printf]
                add esp, 4*2
                
                mov byte[contor], 0 ;resetez
                mov edi,destinatie ;resetez
                add esi,1 ;trec peste spatiu
                jmp repeta
            
        final_repeta:
        
        
        sub edi,1 ;ultimul movsb a crescut pozitia
        repeta2:
              cmp edi,destinatie-1 ;sa vedem daca am numarat toate literele
              je final_repeta2
                    
              add byte[contor],1
              sub edi,1
              jmp repeta2
                    
        final_repeta2:
                
                ;afisez literele citite
                ;printf(format,variabila)
        push dword [contor]
        push dword format_afisare
        call [printf]
        add esp,4*2
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
