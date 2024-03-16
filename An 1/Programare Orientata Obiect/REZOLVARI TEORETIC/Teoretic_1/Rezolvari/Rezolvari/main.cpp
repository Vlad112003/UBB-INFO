#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

class Smoothy {
private:
	int pret = 0;
public:
	virtual int getPret() { return pret; }
	virtual string descriere() = 0;
	virtual ~Smoothy() = default;
};

class DecoratorSmoothy : public Smoothy {
private:
	Smoothy* s;
public:
	DecoratorSmoothy(Smoothy* s) :s{ s } {}
	virtual int getPret() override {
		return s->getPret();
	}
	virtual string descriere() override {
		return s->descriere();
	}
	virtual ~DecoratorSmoothy() override { delete s; }

};

class BasicSmoothy :public Smoothy {
private:
	string nume;
public:
	BasicSmoothy(string nume) :nume{ nume } {}
	virtual string descriere() override { return nume; }
};

class SmoothyCuFrisca :public DecoratorSmoothy {

public:
	SmoothyCuFrisca(Smoothy* s) :DecoratorSmoothy{ s } {}
	virtual string descriere()override { return DecoratorSmoothy::descriere() + " cu frisca"; }
	virtual int getPret() override { return DecoratorSmoothy::getPret() + 2; }
};
class SmoothyCuUmbreluta :public DecoratorSmoothy {

public:
	SmoothyCuUmbreluta(Smoothy* s) :DecoratorSmoothy{ s } {}
	virtual string descriere()override { return DecoratorSmoothy::descriere() + " cu umbreluta"; }
	virtual int getPret() override { return DecoratorSmoothy::getPret() + 2; }
};

vector<Smoothy*> get_list() {
	vector<Smoothy*> lista;
	Smoothy* sm1 = new SmoothyCuUmbreluta(new SmoothyCuFrisca(new BasicSmoothy("kivi")));
	lista.push_back(sm1);
	Smoothy* sm2 = new SmoothyCuFrisca(new BasicSmoothy("capsuni"));
	lista.push_back(sm2);
	Smoothy* sm3 = new BasicSmoothy("kivi");
	lista.push_back(sm3);
	return lista;
}

template<typename Nume>class Geanta {
private:
	Nume nume;
	vector<string>obiecte;
public:
	Geanta<Nume>(Nume nume):nume{nume}{}
	Geanta<Nume>(const Geanta<Nume>& g):nume{g.nume},obiecte{g.obiecte}{}

	Geanta<Nume> operator+(const string ob) {
		Geanta<Nume> g = *this;
		g.obiecte.push_back(ob);
		return g;
	}

	auto begin() {
		return obiecte.begin();
	}

	auto end() {
		return obiecte.end();
	}
};

void calatorie() {
	Geanta<string> ganta{ "Ion" };//creaza geanta pentru Ion
	ganta = ganta + string{ "haine" }; //adauga obiect in ganta
	ganta = ganta + string{ "ctln" }; //adauga obiect in ganta
	ganta + string{ "pahar" };
	for (auto o : ganta) {//itereaza obiectele din geanta
		cout << o << "\n";
	}
}

int main() {
	auto lst = get_list();
	sort(lst.begin(), lst.end(), [](Smoothy* s1, Smoothy* s2) {
		return s1->descriere() < s2->descriere();
		});
	for (auto& el : lst)
	{
		cout << el->descriere() << " " << el->getPret() << endl;
		delete el;
	}

	calatorie();

	return 0;
}
