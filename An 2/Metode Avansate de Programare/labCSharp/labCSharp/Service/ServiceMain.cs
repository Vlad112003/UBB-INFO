using System.Collections.ObjectModel;
using labCSharp.Domain;

namespace labCSharp.Service;

public class ServiceMain
{
    
    protected DocumentService dService;
    protected AchizitieService aService;
    protected FacturaService fService;
    
    public ServiceMain (DocumentService dService, AchizitieService aService, FacturaService fService)
    {
        this.dService = dService;
        this.aService = aService;
        this.fService = fService;
    }
    
    public IEnumerable<Document> getDocumentsFrom2023()
    {
        return dService.FindAll().Where(x => x.dataEmitere.Year == 2023);
    }
    
    public IEnumerable<Factura> getFacturiDataScCurrentMonth()
    {
        return fService.FindAll().Where(x => x.dataScadenta.Month == DateTime.Now.Month);
    }

    public IEnumerable<Factura> getFacturiAtLeast3AcquiredProducts()
    {
        List<Factura> facturi = new List<Factura>();
        IEnumerable<Achizitie> achizitii = aService.FindAll();
        IEnumerable<Factura> facturs = fService.FindAll();
        IEnumerable<Document> documents = dService.FindAll();
        
        foreach(var fac in facturs)
        {
            foreach(var doc in documents)
            {
                if (fac.Id == doc.Id)
                {
                    fac.nume = doc.nume;
                    fac.dataEmitere = doc.dataEmitere;
                }
            }
        }
        
        foreach(var fac in facturs)
        {
            fac.achizitii = new List<Achizitie>();
        }
        foreach(var achizitie in achizitii)
        {
            foreach (var factura in facturs)
            {
                if (achizitie.facturaId == factura.Id)
                {
                    factura.achizitii.Add(achizitie);
                }
            }
            
            
        }
        foreach (var factura in facturs)
        {
            if (factura.achizitii.Count >= 3)
                facturi.Add(factura);
            else
            {
                foreach (var achizitie in factura.achizitii)
                {
                    if (achizitie.cantitate >= 3)
                        facturi.Add(factura);
                }
            }
        }
        
        
        return facturi;
    }
    
    public string getNrProduseForFactura(string facturaId)
    {
        IEnumerable<Achizitie> achizitii = aService.FindAll();
        int nrProduse = 0;
        foreach (var achizitie in achizitii)
        {
            if (achizitie.facturaId.Equals(facturaId))
            {
                nrProduse = achizitie.cantitate;
            }
        }

        return nrProduse.ToString();
    }

    public IEnumerable<Achizitie> getAchizitieWithCategoryUtilities()
    {
        List<Achizitie> _achizitii = new List<Achizitie>();
        var achizitii = aService.FindAll();
        var facturi = fService.FindAll();
        foreach (var achizitie in achizitii)
        {
            foreach (var factura in facturi)
            {
                if (achizitie.facturaId.Equals(factura.Id))
                {
                    var _categorie = factura.categorie;
                    if (_categorie.ToString().Equals( "Utilities"))
                        _achizitii.Add(achizitie);
                }
            }
        }

        return _achizitii;
    }

    public string getCategorieCuMaxCheltuieli()
    {
        var facturi = fService.FindAll();
        var achizitii = aService.FindAll();
        var categorii = new HashSet<Categorie>();
        foreach (var factura in facturi)
        {
            categorii.Add(factura.categorie);
        }

        double max = 0;
        Categorie _categorie = new Categorie();
        foreach (var factura in facturi)
        {
            double suma = 0;
            foreach (var achizitie in achizitii)
            {
                if (achizitie.facturaId.Equals(factura.Id))
                {
                    suma += achizitie.cantitate * achizitie.pretProdus;
                }
            }

            if (suma > max)
            {
                max = suma;
                _categorie = factura.categorie;
            }

        }

        return _categorie.ToString();
    }
    
    public String getNumeDocFromFactura(string facturaId)
    {
        IEnumerable<Document> documents = dService.FindAll();
        string nume = "";
        foreach (var document in documents)
        {
            if (document.Id.Equals(facturaId))
            {
                nume = document.nume;
            }
        }

        return nume;
    }
}