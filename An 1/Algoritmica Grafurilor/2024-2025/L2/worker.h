#include<fstream>
#include<iostream>
#include<queue>
#include<vector>
#include<string.h>
using namespace std;
#define dim 100
class probleme
{
    private:
        void p1(int s,int f);
        void p2();
        void p3();
        void p4(int v);
        void p5();
    protected:
        void citire_orientat_mtr(char* file,int**a,int &n);
        void citire_orientat_lst(char* file,vector<int> E[],int &n);
        void citire_orientat_char(char*file,char**a,int& n);
        void afisare_mtr(int** a,int n);
        void afisare_char(char** a,int n);
        void afisare_lst(vector<int> E[],int n);
    public:
        void meniu();
        void start();
};
