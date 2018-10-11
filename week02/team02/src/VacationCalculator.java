import java.util.ArrayList;
import java.util.List;

public class VacationCalculator {
    public static void main(String[] args) {
        VacationCalculator vc = new VacationCalculator();
        int numDaysJapan = 5;
        int numDaysMexico = 3;
        int numDaysEurope = 7;

        // calculate some costs
        float japanCost = vc.calculateVacationCost(Destination.JAPAN, numDaysJapan);
        float mexicoCost = vc.calculateVacationCost(Destination.MEXICO, numDaysMexico);
        float europeCost = vc.calculateVacationCost(Destination.EUROPE, numDaysEurope);

        // print the results
        String format = "The total cost to vacation in %s for %d days is $%.2f.%n";
        System.out.printf(format, Destination.JAPAN, numDaysJapan, japanCost);
        System.out.printf(format, Destination.MEXICO, numDaysMexico, mexicoCost);
        System.out.printf(format, Destination.EUROPE, numDaysEurope, europeCost);
    }

    /**
     * Calculates the total cost for vacationing at a given location for
     * a specific number of nights.
     * 
     * @param  dest the destination of the vacation
     * @return      the total cost of the vacation
     */
    public float calculateVacationCost(Destination dest, int days) {
        List<Expense> expenses = new ArrayList<Expense>();
        expenses.add(new Cruise(dest));
        expenses.add(new Dining(dest, days));
        expenses.add(new Lodging(dest, days));

        return tallyExpenses(expenses);
    }

    /**
     * An internal method used by VacationCalculator to loop through
     * a List of items that implement the Expense interface and
     * determine their cost.
     * 
     * @param  expenses a list of items that implement the Expense interface
     * @return          the total cost calculated by the items
     */
    float tallyExpenses(List<Expense> expenses) {
        float tally = 0.0f;

        for (Expense exp : expenses) {
            tally += exp.getCost();
        }

        return tally;
    }
}