;se citesc cuvinte de la tastatura, sa se creeze un fisier cu numele primului .txt si restul sa se afiseze in acest fisier cu literele inversate
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,gets,printf,fopen,fprintf,fclose               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import gets msvcrt.dll                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import printf msvcrt.dll
import fopen msvcrt.dll
import fprintf msvcrt.dll
import fclose msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    sir times 100 db -1
    cuvinte times 100 db -1
    format_s db '%s',0
    format_c db '%c',0
    mod_acces db 'w',0
    descriptor dd -1
    new_line db 10,13,0
    spatiu dd ' '
; our code starts here
segment code use32 class=code
    start:
        push dword sir
        call [gets]
        add esp,4
        
        mov esi,sir
        mov edi,cuvinte
        
        repeta:
            lodsb
            cmp al,' '
            je creaza_fisier
            stosb
        jmp repeta
        
        creaza_fisier:
        mov dword[edi],'.txt'
        add edi,3
        ;fopen(nume,mod_acces)
        add edi,1
        mov byte[edi],0
        push dword mod_acces
        push dword cuvinte
        call [fopen]
        add esp,4*2
        
        cmp eax,0
        je final_tot
        mov [descriptor],eax
        ;---------------pana aici merge si creaza fisierul
        ;esi e fix dupa primul spatiu
        
        mov edi,cuvinte
        ;pun invers cuvintele ramase
        repeta1:
            lodsb
            cmp al,0
            je final_repeta1 ;nu mai sunt cuvinte
            cmp al,' '
            je afiseaza
            
            ;daca nu e spatiu si nu e la final, stocam litera 
            stosb
            jmp repeta1
        final_repeta1:
        jmp final_aproape_tot
        
        afiseaza:
            ;resetam cuvinte dupa ce afisam
            repeta2:
                dec edi
                cmp edi,cuvinte-1
                je gata_repeta2
                
                mov eax,0
                mov al,byte[edi]
                push dword eax
                push dword format_c
                push dword[descriptor]
                call [fprintf]
                add esp,4*2
            jmp repeta2
            gata_repeta2:
            push dword [spatiu]
            push dword format_c
            push dword[descriptor]
            call [fprintf]
            add esp,4*2
            mov edi,cuvinte
        jmp repeta1
        
        
        final_aproape_tot:
        repeta3:
                dec edi
                cmp edi,cuvinte-1
                je gata_repeta3
                
                mov eax,0
                mov al,byte[edi]
                push dword eax
                push dword format_c
                push dword[descriptor]
                call [fprintf]
                add esp,4*2
            jmp repeta3
            gata_repeta3:
        ;fclose(decsriptor)
        push dword[descriptor]
        call [fclose]
        final_tot:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
