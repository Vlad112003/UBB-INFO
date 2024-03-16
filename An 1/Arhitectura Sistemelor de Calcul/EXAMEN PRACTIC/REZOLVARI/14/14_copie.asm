;Se da fișierul ala "preufung.txt", câte conține un text de maxim 200 caractere, conține litere mici, mari, spatii și puncte. 
;Afiseaza pe ecran pe randuri diferite cuvintele cu nr impar de litere și sa se înlocuiască litera din mijloc cu spatiu
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,fopen,fscanf,fclose,printf,fprintf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fopen msvcrt.dll                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fscanf msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll
import fprintf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    descriptor dd -1
    mod_acces db 'r',0
    nume db 'input.txt',0
    chr db -1
    format_c db '%c',0
    format_s db '%s',0
    sir times 100 db -1
    cuvant times 100 db -1
    new_line db 10,13,0
    cnt db 0
    doi db 2

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
        
        mov edi,sir
        repeta:
            ;fscanf(descriptor,format,variabile)
            push dword chr
            push dword format_c
            push dword[descriptor]
            call [fscanf]
            add esp,4*3
            
            cmp eax,-1
            je final_repeta
            
            mov al,byte[chr]
            stosb
            jmp repeta
            
        final_repeta:
        ;-------------------------------------------
        ;pana aici merge----------------------------
        
        mov esi,sir
        mov edi,cuvant
        
        repeta1:
            lodsb
            cmp al,0
            je final_repeta1
            
            cmp al,' '
            je verifica
            cmp al,'.'
            je verifica
            
            stosb ;daca nu e spatiu,stocheaza in continuare literele cuvantului si creste contorul pt litere
            mov eax,0
            mov al,byte[cnt]
            inc al
            mov byte[cnt],al
            jmp repeta1
            
            verifica:
                mov eax,0
                mov al,byte[cnt]
                cmp al,0
                je sari_afisare ;in caz ca a fost . spatiu sau invers si atunci sa nu fie 0/2
                div byte[doi]
                cmp ah,0
                je sari_afisare ;daca cuv are nr par de litere,nu il afisez
            afisare:
                    mov ax,0
                    mov al,byte[cnt]
                    div byte[doi]

                    movzx eax,al
                    
                    mov byte[cuvant+eax],' '
                    mov byte[edi],0
                    ;printf(format,variabile)
                    push dword cuvant
                    push dword format_s
                    call [printf]
                    add esp,4*2

                    ;reactualizare edi si contor pt urmatorul cuvant
                    mov edi,cuvant
                    mov al,0
                    mov byte[cnt],al
                    ;afisare de linie noua
                    push dword new_line
                    push dword format_s
                    call [printf]
                    add esp,4*2
                    jmp repeta1
            sari_afisare:
                    mov edi,cuvant
                    mov al,0
                    mov byte[cnt],al
            jmp repeta1
        
        final_repeta1:
        
        ;fclose(descriptor)
        push dword[descriptor]
        call [fclose]
        add esp,4*1
        
        final_tot:
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
