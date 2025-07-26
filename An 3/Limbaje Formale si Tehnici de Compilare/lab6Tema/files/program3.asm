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
	b		RESD		1
	c		RESD		1
	squares		RESD		1
	str1	DB		'%d', 0
	tmp		RESD		4

segment data use32 class=code
	start:

	; a = 3
		MOV		EAX,		3
		MOV		[a],		EAX


	; b = 4
		MOV		EAX,		4
		MOV		[b],		EAX


	; c = 25
		MOV		EAX,		25
		MOV		[c],		EAX


	; squares = 0
		MOV		EAX,		0
		MOV		[squares],		EAX

	; [tmp + 0] = [a] * [a]
		MOV		EBX,		[a]
		MOV		EAX,		[a]
		IMUL	EBX
		MOV		[tmp + 0],		EAX
	; a = [tmp + 0]
		MOV		EAX,		[tmp + 0]
		MOV		[a],		EAX

	; [tmp + 4] = [squares] + [a]
		MOV		EAX,		[squares]
		ADD		EAX,		[a]
		MOV		[tmp + 4],		EAX
	; squares = [tmp + 4]
		MOV		EAX,		[tmp + 4]
		MOV		[squares],		EAX

	; [tmp + 8] = [b] * [b]
		MOV		EBX,		[b]
		MOV		EAX,		[b]
		IMUL	EBX
		MOV		[tmp + 8],		EAX
	; b = [tmp + 8]
		MOV		EAX,		[tmp + 8]
		MOV		[b],		EAX

	; [tmp + 12] = [squares] + [b]
		MOV		EAX,		[squares]
		ADD		EAX,		[b]
		MOV		[tmp + 12],		EAX
	; squares = [tmp + 12]
		MOV		EAX,		[tmp + 12]
		MOV		[squares],		EAX

	; output statement
		PUSH	DWORD		[squares]
		PUSH	DWORD		str1
		CALL	[printf]
		ADD		ESP,		4 * 2

	;exit
		PUSH		DWORD		0
		CALL		[exit]
