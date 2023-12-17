.section .rodata
digitln:
   .asciz "%d\n"

.section .text

.globl println
println:
    push %rbp
    mov %rsp, %rbp

    mov $digitln, %rdi
    movq $1, %rax
    xor %rdx, %rdx

check_stack_alignment_println:
    movq %rsp, %rax      # Move stack pointer to %rax
    andq $15, %rax       # Perform bitwise "and" with 15 (binary 1111)
    testq %rax, %rax
    jz aligned_println
    xor %rax, %rax
    push %rax

aligned_println:
    call printf

    cmp $0, %rax
    jne return_println
    pop %rax
return_println:
    mov %rbp, %rsp
    pop %rbp
    ret