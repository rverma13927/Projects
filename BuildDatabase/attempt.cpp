#include<iostream>
#include<fstream>
using namespace std;

void write(){
    int a;
    string b;
    cin>>a>>b;
    
    fstream filename;
    filename.open("simple1.db",ios::app);
    
    if(!filename){
        cout<<"File not opened";
    }else{
      filename<<a<<" "<<b<<endl;
      filename.close();
    }
}

void show(){

    fstream filename;
    filename.open("simple1.db",ios::in);
    
    if(!filename){
        cout<<"File not opened";
    }else{
      char x;                     
        while (filename.get(x)) {                       
            cout<<x;                  
        }
      filename.close();
    }


}
int main(){
  // we want to support insert : id name
  // show --show all record
  // save to file after insertion: simple.db
  //load from the file while starting
  
  while(1){
      cout<<"db>>";
      string a;
      cin>>a;
      if(a=="exit"){
        break;
      }
      
      if(a=="insert"){
            write();
      }else if(a=="show"){
          show();
      }
  }  
}
