#include <iostream>
#include <string>
#include <vector>
#include <sstream>
#include <unistd.h>
#include <sys/wait.h>
#include "second.cpp"
#include "first.cpp"


int main() {
    //first();
    second();
    return 0;
}
