bits 32
global start

extern exit
import exit msvcrt.dll
extern scanf
import scanf msvcrt.dll
extern printf
import printf msvcrt.dll

segment data use32 class=data
	a		RESD		1
	str1	DB		'%d', 0
	tmp		RESD		6

segment data use32 class=code
	start:

	; a = 3
		MOV		EAX,		3
		MOV		[a],		EAX

	; [tmp + 0] = 2 * 2
		MOV		EBX,		2
		MOV		EAX,		2
		IMUL	EBX
		MOV		[tmp + 0],		EAX
	; [tmp + 4] = [tmp + 0] * 2
		MOV		EBX,		[tmp + 0]
		MOV		EAX,		2
		IMUL	EBX
		MOV		[tmp + 4],		EAX
	; [tmp + 8] = [tmp + 4] + [a]
		MOV		EAX,		[tmp + 4]
		ADD		EAX,		[a]
		MOV		[tmp + 8],		EAX
	; [tmp + 12] = [a] * [a]
		MOV		EBX,		[a]
		MOV		EAX,		[a]
		IMUL	EBX
		MOV		[tmp + 12],		EAX
	; [tmp + 16] = [tmp + 8] - [tmp + 12]
		MOV		EAX,		[tmp + 8]
		SUB		EAX,		[tmp + 12]
		MOV		[tmp + 16],		EAX
	; [tmp + 20] = [tmp + 16] + 5
		MOV		EAX,		[tmp + 16]
		ADD		EAX,		5
		MOV		[tmp + 20],		EAX
	; a = [tmp + 20]
		MOV		EAX,		[tmp + 20]
		MOV		[a],		EAX

	; output statement
		PUSH	DWORD		[a]
		PUSH	DWORD		str1
		CALL	[printf]
		ADD		ESP,		4 * 2

	;exit
		PUSH		DWORD		0
		CALL		[exit]
