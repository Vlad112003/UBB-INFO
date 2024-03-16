;Se citeste o propozitie de la tastatura. Sa se inverseze fiecare cuvant si sa se afiseze pe ecran.
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,printf,scanf,gets               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    
import printf msvcrt.dll
import scanf msvcrt.dll
import gets msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    sursa times 100 db -1
    format_citire db '%s',0
    format_afisare db '%s ',0
    destinatie times 100 db -1
    inv times 100 db -1

; our code starts here
segment code use32 class=code
    start:
        ;scanf(format,variabila)
        push dword sursa
        call [gets]
        add esp, 4*2
        
        mov esi,sursa
        mov edi,destinatie     
        mov ecx,-1
        
        repeta: ;aici iau cate un cuvant pe rand (verific spatiu) si il inversez
            
            cmp byte[esi],0 ;verificam daca am ajuns la final  - conditie de oprire
            je final_repeta
            
            cmp byte[esi],' ' ;verificam daca am terminat de stocat primul cuvant sau nu - am ajuns la spatiu
            jne stocheaza_litera ;daca nu, stocam in continuare litere si trecem mai departe
            je inverseaza ;daca da, inversam cuvantul si il afisam
            
            stocheaza_litera:
                movsb  ;se stocheaza in edi caracterul de la esi
                jmp repeta
                
            inverseaza:
                sub edi,1 ;l-a crescut la ultima litera stocata
                mov ebx,inv
                repeta1:
                    cmp edi,destinatie-1 ;pt ca destinatie o sa fie primul octet - sa vedem daca am copiat toate literele
                    je final_repeta1
                    
                    mov al,byte[edi]
                    mov byte[ebx],al
                    sub edi,1 
                    add ebx,1
                    jmp repeta1
                    
                final_repeta1:
                mov byte[ebx],0 ;resetam
                
                ;printf(format,variabila) - afisam cuvantul inversat din edi
                push dword inv
                push dword format_afisare
                call [printf]
                add esp, 4*2
                
                mov edi,destinatie ; resetam,sa nu mai avem cuvantul deja prelucrat
                add esi,1 ; sa treaca de spatiu
                jmp repeta
                
                
        final_repeta:
        sub edi,1
        mov ebx,inv
        repeta2:
                    cmp edi,destinatie-1 ;pt ca destinatie o sa fie primul octet
                    je final_repeta2
                    mov al,byte[edi]
                    mov byte[ebx],al
                    sub edi,1
                    add ebx,1
                    jmp repeta2
       final_repeta2:
       mov byte[ebx],0
       ;printf(format,variabila) - afisam cuvantul inversat din edi
       push dword inv
       push dword format_afisare
       call [printf]
       add esp, 4*2

    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
