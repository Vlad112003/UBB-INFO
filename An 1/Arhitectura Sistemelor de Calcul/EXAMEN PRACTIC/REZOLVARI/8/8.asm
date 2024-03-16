;Citesc string tastatura, creem fisier string.txt, adaugam in fisier prima data majusculele apoi minusculele invers de cum apar in sir
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,gets,printf,fopen,fprintf,fclose              ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll
import gets msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fopen msvcrt.dll
import fprintf msvcrt.dll
import fclose msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s times 100 db -1
    s1 times 100 db -1
    format_afisare db '%s',0
    mod_acces db 'w',0 
    sir_mic times 100 db -1
    sir_mare times 100 db -1
    new_line db 10,13,0
    cnt_mare dd 0
    cnt_mic dd 0
    format_afisare1 db '%d ',0
    format db '%c',0
    descriptor dd -1
    copie dd -1
; our code starts here
segment code use32 class=code
    start:
        push dword s
        call [gets]
        add esp,4*1
        
        
        mov esi,s
        mov edi,s1
        sub esi,1
        repeta:
            add esi,1
            mov al,byte[esi]
            mov byte[edi],al
            add edi,1
            cmp byte[esi],0
            je adauga
            jne repeta
            
            adauga:
                mov dword[esi],'.txt'       
        
        add esi,4
        mov byte[esi],0; sa afiseze bine cu %s
        
        ;fopen(nume_fisier,mod_acces)
        push dword mod_acces
        push dword s
        call [fopen]
        add esp,4*2
        
        mov [descriptor],eax
        
        
        mov esi,s1
        sub esi,1
        mov edi,sir_mic
        mov ebx,sir_mare
        repeta1:
            add esi,1
            cmp byte[esi],0
            je final_repeta1
            
            cmp byte[esi],'a'
            jb verifica_litera_mare
            cmp byte[esi],'z'
            ja repeta1
            
            ;daca a ajuns aici inseamna ca e litera mica
            mov al,byte[esi]
            mov byte[edi],al
            add edi,1
            add byte[cnt_mic],1
            jmp repeta1
            
            verifica_litera_mare:
                cmp byte[esi],'A'
                jb repeta1
                cmp byte[esi],'z'
                ja repeta1
                
            ;daca a ajuns aici e litera mare
            mov al,byte[esi]
            mov byte[ebx],al
            add ebx,1
            add byte[cnt_mare],1
            jmp repeta1
                
        final_repeta1:
        
        ;acum am in sir_mic literele mici si in sir_mare literele mari
        
        ;fprintf(descriptor,format,variabile)
        mov ecx,[cnt_mare]
        mov ebx,sir_mare
        repeta3:
            cmp ecx,0
            je final_repeta3
            
            mov eax,0
            mov al,byte[ebx+ecx-1]
            
            mov [copie],ecx
            push dword eax
            push dword format
            push dword[descriptor]
            call [fprintf]
            add esp,4*3
            
            mov ecx,[copie]
            dec ecx
            jmp repeta3
        final_repeta3:
        
        mov ecx,[cnt_mic]
        mov edi,sir_mic
        repeta2:
            cmp ecx,0
            je final_repeta2
            
            mov eax,0
            mov al,byte[edi+ecx-1]
            
            mov [copie],ecx
            push dword eax
            push dword format
            push dword[descriptor]
            call [fprintf]
            add esp,4*3
            
            mov ecx,[copie]
            dec ecx
            jmp repeta2
        final_repeta2:
        
        ;fclose(descriptor)
        push dword[descriptor]
        call [fclose]
        add esp,4*1

    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
