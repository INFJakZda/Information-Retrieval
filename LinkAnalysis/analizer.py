import numpy as np

#L1  = [0, 1, 1, 0, 0, 0, 0, 0, 0, 0]
L1  = [0, 1, 1, 0, 1, 0, 0, 0, 0, 0]
L2  = [1, 0, 0, 1, 0, 0, 0, 0, 0, 0]
#L3  = [0, 1, 0, 0, 0, 0, 0, 0, 0, 0]
L3  = [0, 1, 0, 0, 0, 0, 1, 0, 0, 0]
L4  = [0, 1, 1, 0, 0, 0, 0, 0, 0, 0]
L5  = [0, 0, 0, 0, 0, 1, 1, 0, 0, 0]
L6  = [0, 0, 0, 0, 0, 0, 1, 1, 0, 0]
L7  = [0, 0, 0, 0, 1, 1, 1, 1, 1, 1]
L8  = [0, 0, 0, 0, 0, 0, 1, 0, 1, 0]
L9  = [0, 0, 0, 0, 0, 0, 1, 0, 0, 1]
L10 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]

L = np.array([L1, L2, L3, L4, L5, L6, L7, L8, L9, L10])

ITERATIONS = 100

### DONE 1: Compute stochastic matrix M
def getM(L):
    M = np.zeros([10, 10], dtype=float)
    # number of outgoing links
    c = np.zeros([10], dtype=int)
    itx = 0
    ity = 0
    for row in L:
        for edge in row:
            if edge == 1:
                c[itx] += 1
        ity = 0
        for edge in row:
            if c[itx] > 0:
                M[ity][itx] = edge / c[itx]
            ity += 1
        itx += 1
    return M
    
print("Matrix L (indices)")
#print(L)    

M = getM(L)

print("Matrix M (stochastic matrix)")
print(M)

### TODO 2: compute pagerank with damping factor q = 0.15
### Then, sort and print: (page index (first index = 1 add +1) : pagerank)
### (use regular array + sort method + lambda function)
print("PAGERANK")

q = 0.15

pr = np.zeros([10], dtype=float)
pr = [1/10 for ele in pr]
for t in range(0, ITERATIONS):
    pr_tmp = pr
    for it in range(0, 10):
        #pr_tmp[it] = q + ((1 - q) * sum([pr[i] * M[it][i] for i in range(0, 10)]))
        sum_pr = q
        sum_tmp = 0
        for Mij in M[it]:
            sum_tmp += Mij * pr[it]
        sum_pr += (1 - q) * sum_tmp
        pr_tmp[it] = sum_pr
    pr = pr_tmp
print(pr)
print(sum(pr))
print(pr/sum(pr))

    
### TODO 3: compute trustrank with damping factor q = 0.15
### Documents that are good = 1, 2 (indexes = 0, 1)
### Then, sort and print: (page index (first index = 1, add +1) : trustrank)
### (use regular array + sort method + lambda function)
print("TRUSTRANK (DOCUMENTS 1 AND 2 ARE GOOD)")

q = 0.15

d = np.zeros([10], dtype=float)

tr = [v for v in d]
    
### TODO 4: Repeat TODO 3 but remove the connections 3->7 and 1->5 (indexes: 2->6, 0->4) 
### before computing trustrank