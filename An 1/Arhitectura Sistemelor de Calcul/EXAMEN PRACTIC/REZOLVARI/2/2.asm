bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
extern exit,fopen,fclose,scanf,fprintf
import exit msvcrt.dll
import fopen msvcrt.dll
import fprintf msvcrt.dll
import fclose msvcrt.dll
import scanf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    n dd -1
    m dd -1
    format_citire db '%d',0
    aux dd -1
    doi dw 2
    suma_pare dd 0
    suma_impare dd 0
    mod_acces db 'w',0
    nume_fisier db 'output.txt',0
    descriptor dd -1
    format_afisare db '%d %d %d',0
    linie_noua db '\n'
; our code starts here
segment code use32 class=code
    start:
        
        ;scanf('%d',n)
        push dword n
        push dword format_citire
        call [scanf]
        add esp, 4*2
        
        mov ecx,[n] ;atatea citiri mai am nevoie sa fac in loop
        
        repeta:
        
        mov dword[aux],ecx ; ecx se poate modifica la citire, il salvez in ebx
        
        push dword m
        push dword format_citire
        call [scanf]
        add esp, 4*2
        
        mov eax,[m]
        mov edx,0
        div dword[doi] ;-> edx-rest eax-cat
        
        cmp edx,0
        je par
        
        ;altfel e impar
        mov eax,[m]
        add dword[suma_impare],eax
        jmp sari
        
        par:
        mov eax,[m]
        add dword[suma_pare],eax
        
        sari:
        
        mov ecx,[aux] ;sa fim siguri ca ecx se modifica bine
        
        loop repeta
        
        mov ebx,[suma_pare]
        sub ebx,[suma_impare]
        
        ;afisare in fisier a suma_pare, suma_impare, bl
        ;creez fisierul fopen(nume_fisier,mod_acces)
        
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        
        add esp, 4*2   
        
        mov [descriptor],eax
            
        ;afisare mesaj fprintf(suma_pare,linie_noua,suma_impare,linie_noua,ebx,format,descriptor)
        push ebx
        
        
        push dword[suma_pare]            ;suma_pare e byte
        
        push dword[suma_impare]                 ;suma_impare e byte
        
        push dword format_afisare
        
        push dword [descriptor]
        
        call [fprintf]
        add esp, 4*4
        
        push dword[descriptor]
        call [fclose]
        add esp, 4*1
        
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
