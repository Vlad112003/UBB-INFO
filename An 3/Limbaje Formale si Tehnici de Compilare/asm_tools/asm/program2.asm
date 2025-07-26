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
	tmp		RESD		0

segment data use32 class=code
	start:

	; a = 4
		MOV		EAX,		4
		MOV		[a],		EAX

	; output statement
		PUSH	DWORD		[a]
		PUSH	DWORD		str1
		CALL	[printf]
		ADD		ESP,		4 * 2

	;exit
		PUSH		DWORD		0
		CALL		[exit]
