#include <vector>
#include <iostream>
#include <string>

using namespace std;

class Participant {
public:
	virtual void tipareste() = 0;
	virtual bool eVoluntar() { return true; };
	virtual ~Participant()=default;
};

class Personal :public Participant {
private:
	string nume;
public:
	Personal(string nume) :nume{ nume }{}
	virtual void tipareste() override {
		cout << nume;
	}
	virtual ~Personal() override = default;
};

class Administrator : public Personal {
public:
	Administrator(string nume) :Personal{nume}{}
	virtual void tipareste() override {
		Personal::tipareste();
		cout << " Administrator";
	}
	virtual ~Administrator() override = default;
};

class Director : public Personal {
public:
	Director(string nume) :Personal{ nume } {}
	virtual void tipareste() override {
		Personal::tipareste();
		cout << " Director";
	}
	virtual ~Director() override = default;
};

class Angajat :public Participant {
private:
	Participant* p;
public:
	Angajat(Participant* p):p{p}{}
	virtual void tipareste() override {
		p->tipareste();
		cout << " angajat";
	}
	virtual bool eVoluntar() override {
		return false;
	}

	virtual ~Angajat() override {
		delete p;
	}
};

class ONG : public vector<Participant*> {
public:
	void add(Participant* p) {
		push_back(p);
	}
	vector<Participant*>getAll(bool voluntar) {
		vector<Participant*>lst;
		for (auto& el : *this) {
			if (el->eVoluntar() == voluntar)
				lst.push_back(el);
		}
		return lst;
	}
};

ONG get() {
	ONG a;
	a.add(new Administrator{ "admin_vol" });
	a.add(new Angajat{ new Administrator{"admin2"} });
	a.add(new Director{ "director_vol" });
	a.add(new Angajat{ new Director{"director2"} });
	return a;
}

template<typename ElemType>class Cos {
private:
	vector<ElemType> alimente;
public:
	Cos<ElemType> operator+(ElemType s) {
		alimente.push_back(s);
		return *this;
	}
	Cos<ElemType> operator=(const Cos<ElemType>& ot) {
		alimente = ot.alimente;
		return ot;
	}
	Cos<ElemType>& undo() {
		alimente.pop_back();
		return *this;
	}
	void tipareste(ostream& os) {
		for (auto& el : alimente)
			os << el<<' ';
	}

};

void cumparaturi() {
	Cos<string> cos;//creaza un cos de cumparaturi
	cos = cos + "Mere"; //adauga Mere in cos
	cos.undo();//elimina Mere din cos
	cos + "Mere"; //adauga Mere in cos
	cos = cos + "Paine" + "Lapte";//adauga Paine si Lapte in cos
	cos.undo().undo();//elimina ultimele doua produse adaugate
	cos.tipareste(cout);//tipareste elementele din cos (Mere)
}

int main() {
	/*
	cout << "VOLUNTARI:\n";
	auto a = get();
	auto lst = a.getAll(true);
	for (auto el : lst) {
		el->tipareste();
		cout << '\n';
	}
	cout << "Angajati:\n";
	lst = a.getAll(false);
	for (auto el : lst) {
		el->tipareste();
		cout << '\n';
	}
	*/
	cumparaturi();
	return 0;
}