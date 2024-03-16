#include <vector>
#include <iostream>
#include <exception>
#include <algorithm>
#include <string>
#include <map>
using namespace std;

class Mancare {
private:
	int pret=0;
public:
	virtual ~Mancare() = default;
	virtual string descriere() = 0;
	virtual int getPret() { return pret; }
};

class CuCartof :public Mancare {
private:
	Mancare* m;
public:
	CuCartof(Mancare* m):m{m}{}
	virtual string descriere() override { return m->descriere() + " cu cartof"; }
	virtual int getPret() override { return m->getPret() + 3; }
	virtual ~CuCartof() override { delete m; }
};

class CuSos :public Mancare {
private:
	Mancare* m;
public:
	CuSos(Mancare* m) :m{ m } {}
	virtual string descriere() override { return m->descriere() + " cu sos"; }
	virtual int getPret() override { return m->getPret() + 2; }
	virtual ~CuSos() override { delete m; }
};

class Burger : public Mancare {
private:
	string nume;
public:
	Burger(string nume):nume{nume}{}
	virtual string descriere() override { return nume; }
	virtual int getPret() override {
		if (nume == "McPuisor") return 10;
		if (nume == "BigTasty") return 20;
		if (nume == "Booster") return 30;
	}
};

vector<Mancare*> get_lista()
{
	vector<Mancare*> lista;
	Mancare* el1 = new Burger{ "McPuisor" };
	lista.push_back(el1);
	Mancare* el2 = new CuSos(new CuCartof(new Burger{ "BigTasty" }));
	lista.push_back(el2);
	Mancare* el3 = new CuSos(new Burger{ "Booster" });
	lista.push_back(el3);
	Mancare* el4 = new CuCartof(new Burger{ "Booster" });
	lista.push_back(el4);
	return lista;
}

class Sesiune {
private:
	vector<string>lst;
	string nume;
public:
	Sesiune(string nume) :nume{nume}{};

	Sesiune& operator+(string el) {
		lst.push_back(el);
		return *this;
	}
	auto begin() {
		return lst.begin();
	}
	auto end() {
		return lst.end();
	}
	string get_nume() {
		return nume;
	}

	vector<string>select(string nume) {
		vector<string>new_lst;
		copy_if(lst.begin(), lst.end(), back_inserter(new_lst), [&](string el) {
			return el.find(nume)!=-1;
			});
		return new_lst;
	}
};

class Conferinta {
private:
	vector<Sesiune> lst;
public:
	Sesiune& track(string nume) {
		bool ok = 0;
		for(auto& el:lst)
			if (el.get_nume() == nume) {
				return el;
			}
		lst.push_back(Sesiune{ nume });
		return lst[lst.size() - 1];
	}
};



int main() {
	/*
	auto lista = get_lista();
	sort(lista.begin(), lista.end(), [](Mancare* m1, Mancare* m2) {
		return m1->getPret() < m2->getPret();
		});
	for (auto& el : lista)
	{
		cout << el->descriere() << " " << el->getPret() << endl;
		delete el;
	}

	*/
	Conferinta conf;
	//add 2 attendants to "Artifiial Inteligente" track
	conf.track("Artifiial Inteligente") + "Ion Ion" + "Vasile Aron";
	//add 2 attendants to "Software" track
	Sesiune& s = conf.track("Software");
	s + "Anar Lior" + "Aurora Bran";
	//print all attendants from group "Artifiial Inteligente" track
	for (auto name : conf.track("Artifiial Inteligente")) {
		std::cout << name << ",";
	}
	cout << '\n';
	//find and print all names from Software track that contains "ar"
	vector<string> v = conf.track("Software").select("ar");
	for (auto name : v) { std::cout << name << ","; }
	
	
	return 0;
}