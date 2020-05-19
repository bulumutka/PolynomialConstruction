import numpy as np
import sympy as sp


def find_polynomial(vector):
    n = vector[0]
    m = vector[1]
    # First term R'(T)
    sub_graph_number = int(vector[2])
    index = 3
    first_term = 0
    for num in range(sub_graph_number):
        edges_number = int(vector[index])
        index += 1
        edges_weights = [vector[index + j] * 2 for j in range(edges_number)]
        index += edges_number
        vertex_number = int(vector[index])
        index += 1
        for _ in range(vertex_number):
            sub = vector[index]
            index += 1
            args_number = int(vector[index])
            index += 1
            args = [vector[index + j] for j in range(args_number)]
            index += args_number
            sum1 = sum([bernoulli_barns(args[i], edges_weights) for i in range(args_number)])
            first_term += sum1 * sub
    # Second term R''(T)
    sub_graph_number = int(vector[index])
    index += 1
    second_term = 0
    for _ in range(sub_graph_number):
        edges_number = int(vector[index])
        index += 1
        edges_weights = [vector[index + j] * 2 for j in range(edges_number)]
        index += edges_number
        vertex_number = int(vector[index])
        index += 1
        for _ in range(vertex_number):
            j_weight = vector[index] * 2
            index += 1
            args_number = int(vector[index])
            index += 1
            args = [vector[index + j] for j in range(args_number)]
            index += args_number
            edges_weights.remove(j_weight)
            sum1 = sum([bernoulli_barns(args[i], edges_weights) for i in range(args_number)])
            edges_weights.append(j_weight)
            second_term += sum1
    assert (index == len(vector))
    return first_term + second_term


def bernoulli_barns(S, t):
    T = sp.symbols('T')
    k = len(t)
    expr = 1 / (np.prod(t))
    expr *= (T + S) ** k / sp.factorial(k) - 0.5 * sum(t) * (T + S) ** (k - 1) / sp.factorial(k - 1)
    return sp.expand(expr)


def double_sum(a):
    sum1 = 0
    for i in range(len(a)):
        for j in range(i + 1, len(a)):
            sum1 += a[i] * a[j]
    return sum1


def unpack_vector(pathname):
    return find_polynomial(read_vector(pathname))


def read_vector(pathname):
    vector = []
    with open(pathname, 'r') as reader:
        vector = [float(line) for line in reader]
    return vector


print(unpack_vector('data.txt'))