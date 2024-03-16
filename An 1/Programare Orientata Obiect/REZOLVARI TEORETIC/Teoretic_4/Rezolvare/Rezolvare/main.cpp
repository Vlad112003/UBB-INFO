#include <iostream>
#include <string>
#include <vector>

using namespace std;
class Channel {
public:
	virtual void send(string msg) = 0;
	virtual ~Channel() = default;
};

class Telefon : public Channel {
private:
	int nrTel;
public:
	Telefon(int nrTel) : nrTel{ nrTel }{}
	virtual void send(string msg) override { 
		int nr = rand() % 100;
		if (nr % 2 == 0)
			throw exception("Linie ocupata");
		cout << "dail: " << nrTel; }
};

class Fax : public Telefon {
public:
	Fax(int nrTel) :Telefon{ nrTel } {}
	virtual void send(string msg) override {
		Telefon::send(msg); //aici poate prinde exceptie
		cout << "sending fax";
	}
};

class SMS : public Telefon {
public:
	SMS(int nrTel) :Telefon{ nrTel } {}
	virtual void send(string msg) override {
		Telefon::send(msg); //aici poate prinde exceptie
		cout << "sending SMS"; 
	}
};

class Failover :public Channel {
private:
	Channel* t1, * t2;
public:
	Failover(Channel* t1, Channel* t2): t1{t1},t2{t2}{}
	virtual void send(string msg) override {
		try { t1->send(msg); }
		catch (exception& exp) { t2->send(msg); }
	}
	~Failover() override { delete t1; delete t2; }
};

class Contact :public Channel {
private:
	Channel* t1;
	Channel* t2;
	Channel* t3;
public:
	Contact(Channel* t1, Channel* t2, Channel* t3) : t1{ t1 }, t2{ t2 }, t3{ t3 }{}
	virtual void send(string msg) override {
		int ok = 0;
		while (ok == 0)
		{
			try {
				t1->send(msg); ok = 1;
				break;
			}
			catch(exception& exp) {}
			try {
				t2->send(msg); ok = 1;
				break;
			}
			catch (exception& exp) {}
			try {
				t3->send(msg); ok = 1;
				break;
			}
			catch (exception& exp) {}
		}
	}
	~Contact() override { delete t1; delete t2; delete t3; }
};

Contact* get_contact()
{
	return new Contact{ new Telefon{12234} ,new Failover{new Fax{1111},new SMS{2222}} ,new Failover{new Failover{new Telefon{3333},new Fax{4444}},new SMS{5555}} };
}

template <typename ElemType> class Expresie {
private:
	ElemType operand;
	vector<ElemType>undo_list;
public:
	Expresie<ElemType>(int operand):operand{operand}{
		undo_list.push_back(operand);
	}

	Expresie<ElemType>(const Expresie<ElemType>& exp) : operand{ exp.operand }, undo_list{ exp.undo_list }{
	}

	Expresie& operator=(const Expresie<ElemType>& exp) {
		operand = exp.operand;
		undo_list = exp.undo_list;
		return *this;
	}

	Expresie operator+(ElemType op) {
		Expresie<ElemType> exp=*this;
		exp.operand += op;
		exp.undo_list.push_back(exp.operand);
		return exp;
	}
	Expresie operator-(ElemType op) {
		Expresie<ElemType> exp=*this;
		exp.operand =exp.operand - op;
		exp.undo_list.push_back(exp.operand);
		return exp;
	}
	Expresie& undo() {
		operand = undo_list[undo_list.size() - 2];
		undo_list.pop_back();
		return *this;
	}
	ElemType valoare() {
		return operand;
	}
};

void operatii() {
	Expresie<int> exp{ 3 };//construim o expresie,pornim cu operandul 3
	//se extinde expresia in dreapta cu operator (+ sau -) si operand
	exp = exp + 7 + 3;
	exp = exp - 8;
	//tipareste valoarea expresiei (in acest caz:5 rezultat din 3+7+3-8)
	cout << exp.valoare() << "\n";
	exp.undo(); //reface ultima operatie efectuata
	//tipareste valoarea expresiei (in acest caz:13 rezultat din 3+7+3)
	cout << exp.valoare() << "\n";
	exp.undo().undo();
	cout << exp.valoare() << "\n"; //tipareste valoarea expresiei (in acest caz:3)
}

int main() {
	/*
	srand(time(NULL));
	Contact* contact = get_contact();
	contact->send("msg1");
	cout << endl;
	contact->send("msg2");
	cout << endl;
	contact->send("msg3");
	*/
	operatii();
	
	return 0;
}