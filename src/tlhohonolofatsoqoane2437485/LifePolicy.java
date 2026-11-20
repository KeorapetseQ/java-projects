/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tlhohonolofatsoqoane2437485;

public final class LifePolicy extends Policy {
    private final int age;
    private final String occupation;
    private final String beneficiaryName;

    public LifePolicy(int policyNumber, int customerId, String start, String end, double coverage, String status,
                      int age, String occupation, String beneficiaryName) {
        super(policyNumber, customerId, start, end, coverage, status);
        this.age = age;
        this.occupation = occupation;
        this.beneficiaryName = beneficiaryName;
        this.premium = calculateAnnualPremium();
    }

    @Override
    public double calculateAnnualPremium() {
        double ageRate = 0.01;
        if (age >= 18 && age <= 30) ageRate = 0.01;
        else if (age >= 31 && age <= 40) ageRate = 0.015;
        else if (age >= 41 && age <= 50) ageRate = 0.02;
        else if (age >= 51 && age <= 60) ageRate = 0.03;
        else if (age >= 61 && age <= 65) ageRate = 0.04;

        return coverageAmount * ageRate;
    }
}
