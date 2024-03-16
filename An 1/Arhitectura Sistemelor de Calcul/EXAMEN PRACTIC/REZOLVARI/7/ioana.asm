bits 32
global start        
extern exit, printf, fprintf, fopen, fclose
import exit msvcrt.dll 
import printf msvcrt.dll
import fprintf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
segment data use32 class=data
    sir dq 0ABCh, 498700000AB3h, 1320ACE54Fh
    lens equ $-sir
    format_afisare1 db 'a)Bytes care compun quadword sunt: ', 0
    format_afisare_bytes db '%x ', 0
    
    
    k dw 2
    words times 12 db 0
    zece dw 10
    
    nrdwords db -1
    numefisier db 'iesire.txt', 0
    mod_acces db 'w', 0
    descriptor dd 0
    capat1 dd 0AAAh
    capat2 dd 0FFFFh
    format_afisare2 db '%d', 0
    
    

; our code starts here
segment code use32 class=code
    start:
        ; se da un sir de qword definit in data segment. elementele sirului sunt in baza 16
        ;   a)sa se printeze bytes care compun qword urile din sir
        ;   b)sa se identifice words care au ultima cifra egala cu constanta k definita in segmentul de date. sa se salveze aceste words in sirul d
        ;   c)sa se identifice dwords care sunt cuprinse in intervalul AAA - FFFF. sa se determine numarul dwords din acest interval. sa se afiseze acest numar in baza 10 in fisierul 'iesire.txt'
        mov esi, sir
        push format_afisare1
        call [printf]
        add esp, 4*1
        mov ecx, lens

        repeta:
            mov eax, 0
            lodsb
            xchg ecx, ebx
            push eax
            push format_afisare_bytes
            call [printf]
            add esp, 4*2
            xchg ecx, ebx
            loop repeta
            
            
        mov esi, sir
        mov edi, words
        mov ecx, lens/2
        
        repeta1:
            mov eax, 0
            lodsw
            mov ebx, eax
            div word[zece]
            cmp dx, word[k]
            jne finalrepeta1
            mov ax, bx
            stosw
            finalrepeta1:
            loop repeta1
            
        mov esi, sir
        mov ecx, lens/4
        mov ebx, 0
        repeta2:
            lodsd
            cmp eax, [capat1]
            jb finalrepeta2
            cmp eax, [capat2]
            ja finalrepeta2
            inc ebx
            finalrepeta2:
            loop repeta2
            
        push mod_acces
        push numefisier
        call [fopen]
        add esp, 4*2
        
        cmp eax, 0
        je final
        mov [descriptor], eax
        
        push ebx
        push format_afisare2
        push dword[descriptor]
        call [fprintf]
        add esp, 4*3
        
        push dword[descriptor]
        call [fclose]
        add esp, 4*1
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
