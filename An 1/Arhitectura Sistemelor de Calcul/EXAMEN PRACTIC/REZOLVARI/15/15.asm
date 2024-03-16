;sa citesti un nume de fisier de la tastatura, sa deschizi fisieru, sa numeri cate caractere is in fisier, si apoi sa pui nr ala la capatu fisierului
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,gets,fopen,fclose,fscanf,fprintf,printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fclose msvcrt.dll
import fopen msvcrt.dll
import gets msvcrt.dll
import fscanf msvcrt.dll                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fprintf msvcrt.dll
import printf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s times 100 db -1
    mod_acces_r db 'r',0
    mod_acces_a db 'a',0
    descriptor dd -1
    chr db -1
    format_c db '%c',0
    format_d db '%d',0
    cnt db 0

; our code starts here
segment code use32 class=code
    start:
        ;gets(s)
        push dword s
        call [gets]
        add esp,4*1
        
        ;fopen(nume,mod_acces)
        push dword mod_acces_r
        push dword s
        call [fopen]
        
        cmp eax,0
        je final_tot
        mov [descriptor],eax
        
        repeta:
            ;fscanf(descriptor,format,variabile)
            push dword chr
            push dword format_c
            push dword[descriptor]
            call [fscanf]
            add esp,4*3
            
            cmp eax,-1
            je final_repeta
            
            ;crestem cnt de caractere
            mov al,byte[cnt]
            inc al
            mov byte[cnt],al
            jmp repeta
        final_repeta:
        
        ;fclose(descriptor)
        push dword[descriptor]
        call [fclose]
        
        ;fopen(nume,mod_acces)
        push dword mod_acces_a
        push dword s
        call [fopen]
        
        cmp eax,0
        je final_tot
        mov [descriptor],eax
        
        ;fprintf(descriptor,format,variabile)
        mov eax,0
        mov al,byte[cnt]
        
        push dword eax
        push dword format_d
        push dword[descriptor]
        call [fprintf]
        add esp,4*3
        

        final_tot:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
