import numpy as np
data = np.loadtxt("data.txt", delimiter=", ", dtype=np.float32, skiprows=0)
print(data)