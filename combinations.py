'''
Author: Gray Meeks
File: combinations.py

Description:

combinations.py features a dynamic programming
solution to calculate binomial coefficients.
'''

# Prints an "array"
def print_array(arr):
    for i in arr:
        for j in i:
            print("{} ".format(j), end='')
        print()
    return

# Returns m choose n with optional tracing
# that prints the before-and-after arrays.
def combinations(m,n, trace=False):
    if (n > m):
        return 0
    L = []
    # Add empty row zero
    row_zero = []
    for i in range(0, n + 1):
        row_zero.append(-1)
    L.append(row_zero)
    # Populate array according to base cases
    for curr_m in range(1, m+1):
        # Insert row with empty zero value
        L.append([-1])
        for curr_n in range(1, n+1):
            # Base case 1: if n=1, matrix value is m
            if (curr_n == 1):
                L[curr_m].append(curr_m)
            # Base case 2: m=n, matrix value is 1
            elif (curr_n == curr_m):
                L[curr_m].append(1)
            # Invalid case if n>m
            elif (curr_n > curr_m):
                L[curr_m].append(-1)
            else:
                L[curr_m].append(0)
    if (trace):
        print_array(L)
    # Calculate values based on previously calculated answers
    for curr_m in range(1, m+1):
        for curr_n in range(1, n+1):
            if (L[curr_m][curr_n] == 0):
                foo = L[curr_m - 1][curr_n]
                bar = L[curr_m - 1][curr_n - 1]
                L[curr_m][curr_n] = foo + bar
    if (trace):
        print_array(L)
    return L[m][n]


x = input("Please enter total items: ")
y = input("Please the length of each combination: ")
ans = combinations(int(x),int(y))
print("\n{} choose {} results in {} combinations".format(x,y,ans))
            
