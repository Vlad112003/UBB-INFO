using labCSharp.Domain;
using labCSharp.Service;

namespace labCSharp.Ui;

public class Ui
{
    protected ServiceMain ServiceMain;
    
    public Ui(ServiceMain serviceMain)
    {
        ServiceMain = serviceMain;
    }

    public void Run()
    {
        while (true)
        {
            Console.WriteLine("1. Afisati toate documentele emise in anul 2023");
            Console.WriteLine("2. Afisati toate facturile cu data scadenta in luna curenta");
            Console.WriteLine("3. Afisati toate facturile care au cel putin 3 produse achizitionate");
            Console.WriteLine("4. Afisati toate achizitiile care au categoria Utilities");
            Console.WriteLine("5. Afisati Categoria care a inregistrat cele mai multe cheltuieli");

            Console.WriteLine("0. Exit");
            Console.WriteLine("Introduceti optiunea: ");

            var optiune = Console.ReadLine();
                switch (optiune)
                {
                    case "0":
                        return;
                    case "1":
                        Console.WriteLine("Documentele emise in anul 2023 sunt: ");
                        var documents = ServiceMain.getDocumentsFrom2023();
                        if (documents.Count() == 0)
                        {
                            Console.WriteLine("Nu exista documente emise in anul 2023!");
                        }
                        else
                        {
                            foreach (var document in ServiceMain.getDocumentsFrom2023())
                            {
                                Console.WriteLine(document);
                            }
                        }

                        break;
                    case "2":
                        Console.WriteLine("Facturile cu data scadenta in luna curenta sunt: ");
                        var facturi = ServiceMain.getFacturiDataScCurrentMonth();
                        if (facturi.Count() == 0)
                        {
                            Console.WriteLine("Nu exista facturi cu data scadenta in luna curenta!");
                        }
                        else
                        {
                            foreach (var factura in ServiceMain.getFacturiDataScCurrentMonth())
                            {
                                Console.WriteLine(factura.nume + " " + factura.dataScadenta);
                            }
                        }

                        break;
                    case "3":
                        Console.WriteLine("Facturile care au cel putin 3 produse achizitionate sunt: ");
                        var facturi2 = ServiceMain.getFacturiAtLeast3AcquiredProducts();
                        if (facturi2.Count() == 0)
                        {
                            Console.WriteLine("Nu exista facturi care au cel putin 3 produse achizitionate!");
                        }
                        else
                        {
                            foreach (var factura in facturi2)
                            {
                                Console.WriteLine(factura.nume + " " + ServiceMain.getNrProduseForFactura(factura.Id));
                            }
                        }
                        

                        break;
                    case "4":
                        Console.WriteLine("Achizitiile care au categoria Utilities sunt: ");
                        var achizitii = ServiceMain.getAchizitieWithCategoryUtilities();
                        if (achizitii.Count() == 0)
                        {
                            Console.WriteLine("Nu exista achizitii care au categoria Utilities!");
                        }
                        else
                        {
                            foreach (var achizitie in ServiceMain.getAchizitieWithCategoryUtilities())
                            {
                                Console.WriteLine(achizitie.produs + " " + ServiceMain.getNumeDocFromFactura(achizitie.facturaId));
                            }
                        }

                        break;
                    case "5":
                        Console.WriteLine("Categoria care a inregistrat cele mai multe cheltuieli este: ");
                        var categorie = ServiceMain.getCategorieCuMaxCheltuieli();
                        if (categorie == null)
                        {
                            Console.WriteLine("Nu exista categorii!");
                        }
                        else
                        {
                            Console.WriteLine(categorie);
                        }
                        break;
                    default:
                        Console.WriteLine("Optiune invalida!");
                        break;
                }
            }
        }
    }