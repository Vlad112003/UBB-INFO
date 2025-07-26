bits 32
global start

extern exit
import exit msvcrt.dll
extern scanf
import scanf msvcrt.dll
extern printf
import printf msvcrt.dll

segment data use32 class=data
	x		RESD		1
	y		RESD		1
	z		RESD		1
	str1	DB		'%d', 0
	str2	DB		'%d', 0
	str3	DB		'%d', 0
	tmp		RESD		2

segment data use32 class=code
	start:



	; input statement
		PUSH	DWORD		x
		PUSH	DWORD		str1
		CALL	[scanf]
		ADD		ESP,		4 * 2

	; input statement
		PUSH	DWORD		y
		PUSH	DWORD		str2
		CALL	[scanf]
		ADD		ESP,		4 * 2

	; z = 0
		MOV		EAX,		0
		MOV		[z],		EAX

	; [tmp + 0] = [z] + [x]
		MOV		EAX,		[z]
		ADD		EAX,		[x]
		MOV		[tmp + 0],		EAX
	; z = [tmp + 0]
		MOV		EAX,		[tmp + 0]
		MOV		[z],		EAX

	; [tmp + 4] = [z] + [y]
		MOV		EAX,		[z]
		ADD		EAX,		[y]
		MOV		[tmp + 4],		EAX
	; z = [tmp + 4]
		MOV		EAX,		[tmp + 4]
		MOV		[z],		EAX

	; output statement
		PUSH	DWORD		[z]
		PUSH	DWORD		str3
		CALL	[printf]
		ADD		ESP,		4 * 2

	;exit
		PUSH		DWORD		0
		CALL		[exit]
