bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,printf,fopen,fclose,fprintf
import exit msvcrt.dll    
import fclose msvcrt.dll    
import fprintf msvcrt.dll    
import fopen msvcrt.dll    
import printf msvcrt.dll    


; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; Se da un sir de quadword in data segment in baza 16.
    ; a) Sa se printeze pe ecran sirul bite-urilor care compun quadword-ul
    ; b) Sa se extraga din fiecare quadword acele word-uri care au ultima cifra egala cu o constanta k, definita in data segment
    ; c) sa se extraga double-word-urile a caror valoare este in intervalul [AAAh, FFFh] Sa se printeze in fisier numarul double-word-urilor extrase
cnt dd 0
a dq 1122334455667788h,00000AAA00000FFFh,1234123400000BCDh
lena equ ($-a)/8
aa db -1
sir_word times lena*4 dw 0
format_afisare db '%s',0
doi dd 2
spatiu db ' ',0
k dw 2h
hexa dw 10h
aux dw 0
sir times 8*4+1 db 0
fisier db 'text.txt',0
mod_acces db 'w',0
const1 equ 0xAAA
const2 equ 0xFFF
descriptor dd 0
format_digit db '%d',0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        ; c) sa se extraga double-word-urile a caror valoare este in intervalul [AAAh, FFFh] Sa se printeze in fisier numarul double-word-urilor extrase
        mov ebx,0
        mov edi,a
        rpt:
        inc ebx
        
        ;primul dublu cuvant
        mov eax,[edi+4]
        
        cmp eax,const1
        jb sari
        cmp eax,const2
        ja sari
        add dword[cnt],1
        sari:
        
        ;al doilea dublu cuvant
        mov eax,[edi]
        
        cmp eax,const1
        jb sari2
        cmp eax,const2
        ja sari2
        add dword[cnt],1
        sari2:
        ;AFISARE
        add edi,8
        cmp ebx,lena
        jne rpt
        ;fopen(fisier,mod_acces)
        push dword mod_acces
        push dword fisier
        call [fopen]
        add esp,4*2
        mov [descriptor],eax
        ;fscanf(descriptor,format_digit,cnt)
        push dword[cnt]
        push dword format_digit
        push dword [descriptor]
        call [fprintf]
        add esp,4*3
        ;fclose(descriptor)
        push dword [descriptor]
        call [fclose]
        add esp,4
        
        ; b) Sa se extraga din fiecare quadword acele word-uri care au ultima cifra egala cu o constanta k, definita in data segment
        mov ebx,0
        mov esi,a
        mov edi,sir_word
        repet:
        inc ebx
        mov ecx,8
        repet2:
            sub ecx,2
            ;ax=[esi+ecx]
            mov ax,[esi+ecx]
            mov [aux],ax
            mov dx,0
            div word[hexa]
            cmp dx,[k]
            jne ultima_cifra_nu_e_k
            mov ax,[aux]
            mov [edi],ax
            add edi,2
            ultima_cifra_nu_e_k:
            
        cmp ecx,0
        jne repet2
        add esi,8
        cmp ebx,lena
        jne repet
        
        ; a) Sa se printeze pe ecran sirul bite-urilor care compun quadword-ul
        mov esi, a
        mov ebx,0
        
        repeta:
        ;prima parte din cuvant
        inc ebx
        mov eax,[esi+4]
        
        ;Convertire
        mov edi,sir
        add edi,8*4
        dec edi
        ;edi->ultima pozitie din sir
        mov ecx,8*4
        
        repeta2:
        mov edx,0
        div dword[doi]
        add dl,'0'
        mov [edi],dl
        dec edi
        loop repeta2
        
        
        ;printf(format_afisare,sir)
        push dword sir
        push format_afisare
        call [printf]
        add esp,4*2
        
        ;a doua parte din cuvant
        mov eax,[esi]
        
        ;Convertire
        mov edi,sir
        add edi,8*4
        dec edi
        ;edi->ultima pozitie din sir
        mov ecx,8*4
        
        repeta3:
        mov edx,0
        div dword[doi]
        add dl,'0'
        mov [edi],dl
        dec edi
        loop repeta3
        
        
        ;printf(format_afisare,sir)
        push dword sir
        push format_afisare
        call [printf]
        add esp,4*2
        
        ;prinf(' ')
        push dword spatiu
        call [printf]
        add esp,4
        
        add esi,8
        cmp ebx,lena
        
        jne repeta
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
