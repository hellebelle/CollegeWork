import time 

reg_win_depth = 0
max_depth = 0
overflow_n = 0
underflow_n = 0
proc_calls = 0
filled_reg = 2
registerSet = 0

def ackermann(x, y):
    global reg_win_depth
    global proc_calls 
   
    proc_calls +=1  
    reg_win_depth +=1
    overflow()                  #register window overflow can only occur on a call
   
    if x == 0:
        result = y + 1
        reg_win_depth -= 1
        underflow()             #register window underflow can only occur on a return
        return result
    elif y == 0:
        result = ackermann(x - 1, 1)
        reg_win_depth -=1
        underflow()
        return result
    else:
        result = ackermann(x-1, ackermann(x, y-1))
        reg_win_depth -=1
        underflow()
        return result

def overflow():
    global reg_win_depth
    global max_depth
    global registerSet
    global overflow_n
    global filled_reg 

    if (reg_win_depth > max_depth):
        max_depth = reg_win_depth
    
    if(filled_reg < registerSet):
        filled_reg+=1

    else:
        overflow_n+=1


def underflow():
    global filled_reg
    global underflow_n

    if filled_reg > 2 :
        filled_reg-=1
    else:
        underflow_n-=1

if __name__ == "__main__":
    #print(ackermann(3, 6))
    """ global registerSet
    global proc_calls
    global max_depth
    global overflow_n
    global underflow_n
 """
    total_time = 0
    registerSet = int(input("Enter number of Register Sets\n"))
    if registerSet == 6 or registerSet == 8 or registerSet == 16:
        print("Register Set = " + str(registerSet))
        print("Calculating ackermann(3,6)...")
        for x in range(1001):
            t = time.process_time()
            ackermann(3,6)
            total_time += time.process_time() - t
        total_time = total_time/1000
        
        print(
            "Procedural Calls: " + str(proc_calls) + "\n"
            "Maximum Register Window Depth: " + str(max_depth) + "\n"
            "Register Window Overflows: " + str(overflow_n) + "\n"
            "Register Window Underflows: " + str(underflow_n) + "\n"
            "Average time elapsed: " + str(total_time) + "\n"
        )
    else:
        print("ERROR!! PLEASE INPUT VALID REGISTER SET (6, 8, 16)")