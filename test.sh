#!/bin/bash


function test() { # do another way
    let "total+=1"

    test_name=$1
    params=$2
    expected_status=$3
    output=$(./run.sh $params)
    actual_status=$?

    if [ $actual_status -eq $expected_status ]
    then
    echo -e "\033[32m$test_name passed \033[0m"
    let "success+=1"
    else
    echo -e "\033[31m$test_name failed \033[0m"
    fi

    echo -e "app.jar $params \nexpected: $expected_status \nactual: $actual_status \n"
}

total=0
success=0

test "T1.1" "" 1
test "T1.2" "-h" 0 #было один 1 до подключения kotlinx.cli
test "T1.3" "-q"  1 #было один 0 до подключения kotlinx.cli
test "Т1.4" "12345"  1 #было один 0 до подключения kotlinx.cli
test "T1.5" "-res A.B 12345"  1 #было один 0 до подключения kotlinx.cli
test "T2.1" "-login vasya -pass 123"  0
test "T2.2" "-login VASYA -pass 123" 2
test "T2.3" "-login asd -pass 123"  3
test "T2.4" "-login admin -pass 123"  4
test "T3.1" "-login vasya -pass 123 -res A -role READ"  0
test "T3.2" "-login vasya -pass 123 -res A -role DELETE"  5
test "T3.3" "-login vasya -pass 123 -res A -role WRITE"  6
test "T3.4" "-login vasya -pass 123 -res A.B -role READ"  0
test "T3.5" "-login vasya -pass 0000 -res A.B -role DELETE"  4
test "T3.6" "-login admin -pass admin -res A -role EXECUTE"  6
test "T3.7" "-login admin -pass admin -res A.A -role WRITE"  6
test "T4.1" "-login vasya -pass 123 -res A -role READ -ds 2020-03-10 -de 2020-03-11 -vol 100"  0
test "T4.2" "-login vasya -pass 123 -res A -role READ -ds 2020.03.10 -de 2020.03.11 -vol 100"  7
test "T4.3" "-login vasya -pass 123 -res A -role READ -ds 2020-03-10 -de 2020-03-11 -vol aaaa"  7
test "T4.4" "-login vasya -pass 123 -res A -role EXECUTE -ds 01.02.3012 -de 01.02.2030 -vol aaa"  6
test "T4.5" "-login vasya -pass 123 -res A -role READ -ds 2020-03-11 -de 2020-03-10 -vol 100"  7

if [ $success -eq $total ]
    then
    echo -e "\033[32m================== ${total} passed ==================\033[0m"
    exit 0
else
    echo -e "\033[31m================== $((total-success)) failed, ${success} passed ==================\033[0m"
    exit 1
fi
