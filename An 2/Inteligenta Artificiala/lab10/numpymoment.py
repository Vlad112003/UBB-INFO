import numpy as np


def simple_sum(list):
    sum = float(0)
    for item in list:
        sum = sum + item
    return sum

def my_sum(list, axis):
    if axis==None:
        return simple_sum(list)
    else:
        sum = [axis, simple_sum(list)]
        return sum

# print(sum([1,2,3], None))
#
# print (sum([1,2,3], 0))

#numpy argmin returns the index of the minimum value along the specified axis
#a = np.arange(6).reshape(2,3) + 10
# a
# array([[10, 11, 12],
#        [13, 14, 15]])
# np.argmin(a)
# 0
# np.argmin(a, axis=0)
# array([0, 0, 0])
# np.argmin(a, axis=1)
# array([0, 0])

def argmin(lst, axis=None):
    if axis is None:
        # Flatten the list and return the index of the minimum value
        return min(range(len(lst)), key=lst.__getitem__)
    else:
        # Return the index of the minimum value along the specified axis
        return [min(range(len(sub_lst)), key=sub_lst.__getitem__) for sub_lst in zip(*lst)]

#numpy mean returns the average of the array elements along the specified axis

# def mean(lst, axis=None):
#     if axis is None:
#         return sum(sum(lst, [])) / len(sum(lst, []))
#     return [sum(x)/len(x) for x in zip(*lst)]
#
# print(mean([1,2,3], None))
# print(mean([1,2,3], 0))

#numpy isnan returns a boolean array indicating whether each element is NaN or not

def isnan(lst):
    return [x!=x for x in lst]

print(isnan([np.log(-1.), np.nan, 3]))

listutza = []

print(type(listutza) is list)

#numpy not_equal returns an element-wise comparison of two arrays

def not_equal(lst1, lst2):
    return [x!=y for x, y in zip(lst1, lst2)]
