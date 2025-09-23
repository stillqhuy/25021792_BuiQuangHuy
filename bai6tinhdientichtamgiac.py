import math as mt
print("nhap 3 so a b c")
a,b,c=map(float,input().split())
if (a+b)>c and (a+c)>b and (b+c)>a:
    print("a b c la 3 canh cua mot tam giac")
    p=(a+b+c)/2
    s=mt.sqrt(p*(p-a)*(p-b)*(p-c))
    print(f'dien tich cua tam giac la {s:.1f}')
else:
    print("a b c khong phai la 3 canh cua mot tam giac")
