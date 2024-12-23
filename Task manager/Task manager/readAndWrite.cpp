#include <iostream>
#include <fstream>
using namespace std;
class ReadAndWriteFile
{
public:
  void read()
  {

    string s;

    ifstream file("savetask.txt");
    while (getline(file, s))
    {
      // Output the text from the file
      cout << s;
    }
  }

  void write(string s)
  {
    fstream f;
    f.open("savetask.txt", ios::app);
    f <<s<<endl;
    f.close();
  }
};