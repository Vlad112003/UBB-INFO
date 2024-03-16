;sir de la tastatura cu nr de maxim 5 cifre. sa se gaseasca max si min,sa se scrie in fisier, sa se inmulteasca si rezultatul sa se pastreze in mem
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,scanf,printf,fprintf,fopen,fclose               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll
import fopen msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    aux dd -1
    n dd -1
    format_d db '%d',0
    max dd -1
    min dd -1
    format_d_d db '%d %d',0
    mod_acces_w db 'w',0
    nume db 'output.txt',0
    descriptor dd -1

; our code starts here
segment code use32 class=code
    start:
        ;scanf(format,variabile)
        push dword n
        push dword format_d
        call [scanf]
        add esp,4*2
        
        mov eax,dword[n]
        mov dword[min],eax
        mov dword[max],eax
        
        repeta:
            ;scanf(format,variabile)
            push dword n
            push dword format_d
            call [scanf]
            add esp,4*2
            
            cmp dword[n],0
            je final_repeta
            
            mov eax,dword[n]
            cmp eax,dword[min]
            jl minn
            cmp eax,dword[max]
            jg maxx
            jmp repeta
            
            minn:
                mov dword[min],eax
                jmp repeta
            maxx:
                mov dword[max],eax
                jmp repeta
        final_repeta:
        
        ;fopen(nume,mod_acces)
        push dword mod_acces_w
        push dword nume
        call [fopen]
        
        cmp eax,0
        je final_tot
        mov dword[descriptor],eax
        
        ;fprintf(descriptor,format,variabile)
        push dword[min]
        push dword[max]
        push dword format_d_d
        push dword[descriptor]
        call [fprintf]
        add esp,4*4
        
        ;fclose(descriptor)
        push dword[descriptor]
        call [fclose]
        add esp, 4*1
        
        ;maxim de 5 cifre -> incap pe ax si bx
        mov ax,word[min]
        imul word[max]
        ;rezultat pe dx:ax
        push dx
        push ax
        pop dword[aux]
        
        
        final_tot:
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
