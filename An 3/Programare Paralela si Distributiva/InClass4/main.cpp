#include <iostream>
#include <omp.h>

using namespace std;

const int MAX = 20;
int a[MAX], b[MAX], m[MAX][MAX], c[MAX];

int produsScalar(int a[MAX], int b[MAX]) {  // suma produselor de pe aceeasi pozitie
    int s = 0;

    for (int i = 0; i < MAX; i++) {
        s += a[i] * b[i];
    }

    return s;
}

int produsScalarParallel(int a[MAX], int b[MAX]) {
    int sum = 0;
    int partial_sum = 0;

    #pragma omp parallel firstprivate(partial_sum) num_threads(16)
    {
        // public - variabilele definite in afara sectiunii paralele sunt publice (ex. sum e public)
        // private - se creeaza o copie locala threadului, nu se copiaza valoarea
        // firstprivate - se creeaza o copie locala threadului, si se pastreaza valoarea
        // lessprivate - la finalul executiei se stocheaza in variabila globala valoare ultimului thread care a modificat-o
        #pragma omp for  // by default: schedule(static)
        for (int i = 0; i < MAX; i++) {
            partial_sum += a[i] * b[i];
        }

        #pragma omp critical  // se aduna sumele partiale la suma finala
        {
            sum += partial_sum;
        }
    }

    return sum;
}

int produsScalarParallel2(int a[MAX], int b[MAX]) {
    int sum = 0;

    #pragma omp parallel for reduction(+:sum)
    // reduction(+ operatie aritmetica de reducing, sum variabila in care se va face reducerea)
    // face acelasi lucru ca produsScalarParallel
    for (int i = 0; i < MAX; i++) {
        sum += a[i] * b[i];
    }

    return sum;
}

void addVectors(int a[MAX], int b[MAX], int c[MAX])
{
    for (int i = 0; i < MAX; i++) {
        c[i] = a[i] + b[i];
    }
}

void multiplyVectors(int a[MAX], int b[MAX], int c[MAX])
{
    for (int i = 0; i < MAX; i++) {
        c[i] = a[i] * b[i];
    }
}

int main()
{
    omp_set_num_threads(4); // seteaza parametrul numarului de threaduri, ! nu se creeaza threadurile !

    cout << "Thread num1: " << omp_get_num_threads() << endl;

    #pragma omp parallel num_threads(16) // incepe paralelismul, codul dintre {} va fi rulat in paralel, se creeaza 16 threaduri, chiar daca variavila globala e setata la 4, se ia variabila locala
    {
        // +  threadurile nu sunt distruse la final


        cout << "Thread num2: " << omp_get_num_threads() << endl;

        #pragma omp critical  // directiva care permite executarea unei instructiuni de cate un thread pe rand
        {
            // omp single - un singur thread va executa instructiunile
            cout << "Hello from: " << omp_get_thread_num() << endl;
            cout << "Thread num3: " << omp_get_num_threads() << endl;
        }

        cout << "Thread num4: " << omp_get_num_threads() << endl;

        #pragma omp for schedule(static, 2) nowait
        // static - spatiul se imparte mereu la fel
        // dynamic - threadurile proceseaza date pe idea primul venit, primul servit
        // fara 2 - spatiul se imparte neechitabil, restul merge la ultimul thread
        // 2 - se creeaza chunkuri de dimensiune 2, care se distribuie dupa threadurilor
        // by default threadurile se asteapta unul pe altul, nowait scoate bariera responsabila pentru asta
        for (int i = 0; i < MAX; i++) {
            a[i] = omp_get_thread_num();
            b[i] = i;
        }

        #pragma omp for schedule(static, 2) collapse(2) // collapse(2) - linearizeaza primele 2 for-uri si dupa le paralelizeaza, daca nu il aveam doar primul for se paralelizeaza by default
        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                m[i][j] = omp_get_thread_num();
            }
        }
    } // ! threadurile nu se distrug, raman intr-un thread pool

    for (int i = 0; i < MAX; i++) {
        cout << a[i] << " ";
    }

    cout << endl;

    for (int i = 0; i < MAX; i++) {
        for (int j = 0; j < MAX; j++) {
            cout << m[i][j] << " ";
        }
        cout << endl;
    }

    cout << produsScalar(a, b) << endl;
    cout << produsScalarParallel(a, b) << endl;
    cout << produsScalarParallel2(a, b) << endl;

    #pragma omp parallel num_threads(8)
    {
        #pragma omp sections
        // urmeaza mai multe sectiuni
        // un section = un thread
        // desi avem 8 threaduri, numai 3 vor fi folosite, deoarece avem 3 sectiuni
        // daca aveam mai multe sectiuni, se executau numai p sectiuni (p - nr threaduri)
        {
            // vectorul c se va suprascrie in cazul asta
            #pragma omp section
            {
                addVectors(a, b, c);
                #pragma omp critical
                {
                    cout << "Section add: " << omp_get_thread_num() << endl;
                }
            }
            #pragma omp section
            {
                multiplyVectors(a, b, c);
                #pragma omp critical
                {
                    cout << "Section multiply: " << omp_get_thread_num() << endl;
                }
            }
            #pragma omp section
            {
                produsScalar(a, b);
                #pragma omp critical
                {
                    cout << "Section produs: " << omp_get_thread_num() << endl;
                }
            }
        }
    }

    #pragma omp single
    {
        cout << "Doar eu execut: " << omp_get_thread_num() << endl;
    }

    return 0;
}