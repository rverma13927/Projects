#include<iostream>
#include <vector>
#include<fstream>
#include<vector>
#include <sstream>
#include "readAndWrite.cpp"

using namespace std;

int main()
{
  string input;
  while (1)
  {
    cout << "ter>> ";
    getline(cin, input);

    if (input == "exit")
      break;

    string s;
    stringstream ss(input);
    vector<string> v;
    while (getline(ss, s, ' ')){
      // store token string in the vector
      v.push_back(s);
    }
    // cout<<v.size()<<endl;
    ReadAndWriteFile rd;
    for (int i = 0; i < v.size(); i++){
      cout<< v[i] << endl;
      rd.write(v[i]);
    }
    
    cout<<"Content of file: \n";
    rd.read();
    cout<<endl;
  }
}
