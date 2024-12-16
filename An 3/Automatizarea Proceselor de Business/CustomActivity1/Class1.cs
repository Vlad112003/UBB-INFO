using System;
using System.Activities;
using System.ComponentModel;
using System.Data;

namespace CustomActivity1
{
    // This is your custom activity
    [DisplayName("Calculate Revenue Expenses")]
    [Category("Custom")]
    [Description("Calculates total revenue minus expenses from a DataTable.")]
    public class CalculateRevenueExpenses : CodeActivity
    {
        // Define input and output arguments
        public InArgument<DataTable> DtLocations { get; set; }
        public OutArgument<decimal> TotalRevenueMinusExpenses { get; set; }
        public OutArgument<int> NumberOfLocations { get; set; }

        // Implement the activity execution logic
        protected override void Execute(CodeActivityContext context)
        {
            var dtLocations = DtLocations.Get(context);
            decimal totalRevenue = 0;
            decimal totalExpenses = 0;
            int numberOfLocations = dtLocations.Rows.Count;

            // Loop through the DataTable to calculate total revenue and expenses
            foreach (DataRow row in dtLocations.Rows)
            {
                totalRevenue += Convert.ToDecimal(row["Revenue"]);
                totalExpenses += Convert.ToDecimal(row["Expenses"]);
            }

            // Calculate total revenue minus expenses
            decimal totalRevenueMinusExpenses = totalRevenue - totalExpenses;

            // Set the output arguments
            TotalRevenueMinusExpenses.Set(context, totalRevenueMinusExpenses);
            NumberOfLocations.Set(context, numberOfLocations);
        }
    }
}