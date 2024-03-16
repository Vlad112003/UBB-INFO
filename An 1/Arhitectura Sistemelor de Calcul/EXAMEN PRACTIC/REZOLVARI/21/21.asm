;Sa se citeasca de la tastatura un nume de fisier si un numar. 
;Sa se citeasca din fisierul dat cuvintele separate prin spatii si sa se afiseze in consola cuvintele care sunt pe pozitiile multipli de numarul citit de la tastatura. 
;Afisati si numarul cuvintelor care nu au fost tiparite
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,scanf,printf,gets,fopen,fscanf,fclose               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import scanf msvcrt.dll 
import printf msvcrt.dll                         ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import gets msvcrt.dll
import fopen msvcrt.dll
import fscanf msvcrt.dll
import fclose msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    format_s db '%s',0
    format_s_spatiu db '%s ',0
    format_c db '%c',0
    format_d db '%d',0
    format_s_d db '%s %d',0
    s times 100 db -1
    n dd -1
    new_line db 10,13,0
    mod_acces_r db 'r',0
    descriptor dd -1
    chr db -1
    cuvant times 100 db -1
    cnt db 0
    cnt_neafis db 0

; our code starts here
segment code use32 class=code
    start:
        ;scanf(format,variabile)
        push dword s
        push dword format_s
        call [scanf]
        add esp,4*3
        
        push dword n
        push dword format_d
        call [scanf]
        add esp,4*3
        
        ;fopen(nume,mod_acces)
        push dword mod_acces_r
        push dword s
        call [fopen]
        
        cmp eax,0
        je final_tot
        mov [descriptor],eax
        
        mov edi,cuvant
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
            cmp al,' '
            je verificare
            
            stosb
            jmp repeta
        final_repeta:
            jmp sari_verificare
            
        verificare:
            ;INC CONTORUL DE CUVINTE DACA A AJUNS LA SPATIU
            mov byte[edi],0;pun 0 la final edi ca sa afiseze bine cu %s
            mov eax,0
            mov al,byte[cnt]
            inc al 
            mov byte[cnt],al
            ;verific al multiplu de n adica al/n da rest 0

            mov ebx,dword[n] ;in bl avem acum pe n de fapt
            
            div bl;in ah restul
            cmp ah,0
            je afiseaza
            ;daca nu afiseaza, reseteaza cuvantul si creste cnt_neafis
            mov edi,cuvant
            mov al,byte[cnt_neafis]
            inc al
            mov byte[cnt_neafis],al
            jmp repeta
            
            afiseaza:
            ;printf(format,variabile)
            push dword cuvant
            push dword format_s_spatiu
            call [printf]
            add esp,4*3
            mov edi,cuvant
            jmp repeta
            
          
        sari_verificare:    
        ;SA NU UITI DE ULTIMUL CUVANT
        mov byte[edi],0;pun 0 la final edi ca sa afiseze bine cu %s
        mov eax,0
        mov al,byte[cnt]
        inc al 
        mov byte[cnt],al
        ;verific al multiplu de n adica al/n da rest 0

       mov ebx,dword[n] ;in bl avem acum pe n de fapt
            
       div bl;in ah restul
       cmp ah,0
       je afiseaza1
       
       jmp nu_afisa
            
       afiseaza1:
            ;printf(format,variabile)
            push dword cuvant
            push dword format_s_spatiu
            call [printf]
            add esp,4*3
            mov edi,cuvant
            jmp sari_nu_afisa
            
        nu_afisa:
        mov al,byte[cnt_neafis]
        inc al
        mov byte[cnt_neafis],al
        
        sari_nu_afisa:
        
        ;afisez cnt_neafis
        mov ebx,0
        mov bl,byte[cnt_neafis]
        
        push ebx
        push dword format_d
        call [printf]
        add esp,4*2
        
        ;fclose(descriptor)
        push dword[descriptor]
        call [fclose]
        add esp,4*1
        final_tot:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
