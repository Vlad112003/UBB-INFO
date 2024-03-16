;Se citeste de la tastatura un cuvant (sir de caractere de maxim 20 de caractere) si un numar reprezentat pe un octet. 
;Daca numarul este impar se cere criptarea cuvantului prin adunarea la fiecare caracter a numarului citit modulo 20. 
;Daca numarul este par se cere criptarea cuvantului prin adaugarea dupa fiecare consoana a gruparii "p"+consoana. Se cere afisarea cuvantului criptat
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,scanf,printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    format_citire db '%s %d',0
    s times 20 db -1
    nr dd -1
    doi db 2
    rezultat times 20 db -1
    format_afisare db '%s'
    douazeci db 20
    cod db -1
    format db '%s',0
    format_c db '%c',0
    new_line db 10,13,0
    copie dd -1

; our code starts here
segment code use32 class=code
    start:
        ;scanf(format,variabile)
        push dword nr
        push dword s
        push dword format_citire
        call [scanf]
        
        
        ;verificare paritate
        mov eax,dword[nr]
        div byte[doi] ;ah-rest
        cmp ah,0
        je par
        jne impar
        
        impar:
            mov eax,dword[nr]
            div byte[douazeci] ;ah-rest
            mov byte[cod],ah
            mov esi,s
            repeta:
                mov eax,0
                lodsb
                cmp al,0
                je final ;inseamna ca ma terminat de criptat
                add al,byte[cod]
                
                ;afisare caracter format
                push dword eax
                push dword format_c
                call [printf]
                add esp,4*2
                
                jmp repeta
        
        
        par:
            mov esi,s
            repeta1:
                mov eax,0
                lodsb
                cmp al,0
                je final
                
                cmp al,'a'
                je vocala
                cmp al,'e'
                je vocala
                cmp al,'i'
                je vocala
                cmp al,'o'
                je vocala
                cmp al,'u'
                je vocala ;adica sare peste modificare
                
                consoana:
                    mov [copie],eax
                    push dword eax
                    push dword format_c
                    call [printf]
                    add esp,4*2
                    
                    mov ebx,'p'
                    push dword ebx
                    push dword format_c
                    call [printf]
                    add esp,4*2
                    
                    mov eax,[copie]
                    push dword eax
                    push dword format_c
                    call [printf]
                    add esp,4*2
                
                jmp repeta1
                
                vocala:
                    push dword eax
                    push dword format_c
                    call [printf]
                    add esp,4*2
            
                jmp repeta1
        final:

        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
