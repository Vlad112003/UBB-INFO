
using labCSharp;
using labCSharp.Repository;
using labCSharp.Service;
using labCSharp.Ui;

public class MainApp
{
    public static void Main(string[] args)
    {
        var dRepo = new DocumentFileRepo(Const.DocumentFilename);
        var aRepo = new AchizitieFileRepo(Const.AchizitieFilename);
        var fRepo = new FacturaFileRepo(Const.FacturaFilename);
        var dService = new DocumentService(dRepo);
        var aService = new AchizitieService(aRepo);
        var fService = new FacturaService(fRepo);
        var MainService = new ServiceMain(dService, aService, fService);
        var Ui = new Ui(MainService);
        Ui.Run();
    }
}