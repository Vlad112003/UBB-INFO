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
    doi db 2
    sir_par times 100 db -1
    sir_impar times 100 db -1
    new_line db 10,13,0
    
    
; our code starts here
segment code use32 class=code
    start:
        mov esi,destinatie
        mov edi,sir_par
        mov ebx,sir_impar
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
            
            cmp byte[esi],' '
            jne stocheaza
            je verifica
            
            stocheaza:
                add esi,1
                mov al,byte[cif]
                mov byte[esi],al
                jmp repeta
            
            verifica:
                sub byte[esi],'0'
                movzx ax,byte[esi]
                add byte[esi],'0'
                div byte[doi]
                cmp ah,0
                je par
                jne impar
                
                par:
                    repeta1:
                        cmp esi,destinatie-1
                        je final_repeta1
                        mov al,byte[esi]
                        mov byte[edi],al
                        add edi,1
                        sub esi,1
                        jmp repeta1
                        final_repeta1:
                jmp repeta
                impar:
                    repeta2:
                        cmp esi,destinatie-1
                        je final_repeta2
                        mov al,byte[esi]
                        mov byte[ebx],al
                        add ebx,1
                        sub esi,1
                        jmp repeta2
                        final_repeta2:                    
            
            jmp repeta
        out_citire:
        
        ;mai trebuie sa pun ultimul numar din fisier in sirul lui
        
        ;fclose(descriptor)
        push dword [descriptor]
        call [fclose]
        add esp,4*1
        
        ;pregatesc de afisare cu %s
        add edi,1
        mov byte[edi],0
        
        add ebx,1
        mov byte[ebx],0
        
        ;printf(format,variabile)
        push dword sir_par
        push dword format_afisare
        call [printf]
        add esp,4*2
        
        push dword new_line
        push dword format_afisare
        call [printf]
        add esp,4*2
        
        push dword sir_impar
        push dword format_afisare
        call [printf]
        add esp,4*2
        
        out_program:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
