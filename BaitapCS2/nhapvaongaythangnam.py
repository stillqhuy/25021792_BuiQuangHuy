dd,mm,yy=map(int,input().split("/"))
max=0   

match mm:
        case 2:
            if (yy%4==0 and yy%100!=0) or yy%400==0:
                max=29
            else:
                max=28
        case 1|3|5|7|8|10|12:
            max=31
        case 4|6|9|11:
            max=30
if 1<=dd<=max and 1900<=yy<=2100:
    print("valid")
else:
    print("invalid")

