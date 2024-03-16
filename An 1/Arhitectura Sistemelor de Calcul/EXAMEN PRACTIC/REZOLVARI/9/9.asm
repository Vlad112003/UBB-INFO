;de la tastatura se citeste un nume de fisier input. Se creaza un alt fisier de output in care se pune continutul fisierului de input su toate caracterele mici schimbate cu codul lor ascii
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,gets,printf,fopen,fscanf,fprintf,fclose               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import gets msvcrt.dll
import printf msvcrt.dll                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fopen msvcrt.dll
import fscanf msvcrt.dll
import fprintf msvcrt.dll
import fclose msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s times 100 db -1
    mod_acces_citire db 'r',0
    descriptor1 dd -1
    descriptor2 dd -1
    format_afisare_tastatura db '%s',0
    format_afisare_tastatura1 db '%c',0
    format_afisare_fisier_1 db '%c',0
    format_afisare_fisier_2 db '%d',0
    format_afisare_fisier_3 db '%s',0
    new_line db 10,13,0
    continut_fisier times 100 db -1
    format_citire_fisier db '%c',0
    chr db -1
    mod_acces_output db 'w',0
    fisier_output db 'output.txt',0
; our code starts here
segment code use32 class=code
    start:
        push dword s
        call [gets]

        ;fopen(nume_fisier,mod_acces)
        push dword mod_acces_citire
        push dword s
        call [fopen]
        
        cmp eax,0
        je nu_s_a_deschis_fisierul
        mov [descriptor1],eax
        
        mov edi,continut_fisier
        
        repeta:
            ;fscanf(descriptor,format,variabile)
            push dword chr
            push dword format_citire_fisier
            push dword[descriptor1]
            call [fscanf]
            add esp, 4*3
            
            cmp eax,-1 ;a ajuns la final, nu mai are de citit
            je final_citire_fisier
            
            mov al,byte[chr]
            mov byte[edi],al
            add edi,1
            
            jmp repeta
        final_citire_fisier:
        
        mov byte[edi],0
        ;in continut_fisier acum avem toate literele teoretic
        
        ;fopen(nume_fisier,mod_acces)
        push dword mod_acces_output
        push dword fisier_output
        call [fopen]
        add esp, 4*2
        
        cmp eax,0 ;daca s-a deschis fisierului
        je nu_s_a_deschis_fisierul
        mov [descriptor2],eax
        
        mov edi,continut_fisier
        
        repeta1:
        
            cmp byte[edi],0
            je final_repeta1 ;am treminat toate caracterele
            
            cmp byte[edi],'a'
            jb nu_e
            cmp byte[edi],'z'
            ja nu_e
            
            mov eax,0
            mov al,byte[edi]
                
            push dword eax
            push dword format_afisare_fisier_2
            push dword[descriptor2]
            call [fprintf]
            add esp,4*3
            
            add edi,1
            jmp repeta1
            
            nu_e:
                mov eax,0
                mov al,byte[edi]
                
                push dword eax
                push dword format_afisare_fisier_1
                push dword[descriptor2]
                call [fprintf]
                add esp,4*3
                
            add edi,1
            jmp repeta1
        final_repeta1:
            
        
        
        nu_s_a_deschis_fisierul:
        ;inchidem fisierele fclose(descriptor)
        push dword [descriptor1]
        call [fclose]
        add esp,4
        
        push dword [descriptor2]
        call [fclose]
        add esp,4
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
