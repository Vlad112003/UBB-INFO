using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using Networking;
using Services;

namespace Client
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            // Application.EnableVisualStyles();
            // Application.SetCompatibleTextRenderingDefault(false);
            string host = "127.0.0.1";
            int port = 55555;
            IService server = new ServerProxy(host, port);
            Controller ctrl = new Controller(server);
            LoginWindow loginWindow = new LoginWindow(ctrl);
            Application.Run(loginWindow);
        }
    }
}