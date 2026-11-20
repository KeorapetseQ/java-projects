/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tlhohonolofatsoqoane2437485;

public class HealthPolicy extends Policy {
    private String healthPlan; // Basic, Standard, Premium
    private int numDependants;

    public HealthPolicy(int policyNumber, int customerId, String start, String end, double coverage, String status,
                        String healthPlan, int numDependants) {
        super(policyNumber, customerId, start, end, coverage, status);
        this.healthPlan = healthPlan;
        this.numDependants = numDependants;
        this.premium = calculateAnnualPremium();
    }

    @Override
    public double calculateAnnualPremium() {
        double basePremium = 3000.0;
        if (healthPlan.equalsIgnoreCase("Standard")) basePremium = 5000.0;
        else if (healthPlan.equalsIgnoreCase("Premium")) basePremium = 8000.0;

        return basePremium + (numDependants * 1000.0);
    }
}
