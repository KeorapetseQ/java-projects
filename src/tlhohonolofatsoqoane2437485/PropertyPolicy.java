/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tlhohonolofatsoqoane2437485;


public class PropertyPolicy extends Policy {
    private double propertyValue;
    private double contentsValue;
    private String propertyType; // Residential, Commercial

    public PropertyPolicy(int policyNumber, int customerId, String start, String end, double coverage, String status,
                          double propertyValue, double contentsValue, String propertyType) {
        super(policyNumber, customerId, start, end, coverage, status);
        this.propertyValue = propertyValue;
        this.contentsValue = contentsValue;
        this.propertyType = propertyType;
        this.premium = calculateAnnualPremium();
    }

    @Override
    public double calculateAnnualPremium() {
        double rate = propertyType.equalsIgnoreCase("Commercial") ? 0.025 : 0.015;
        double propertyPremium = propertyValue * rate;
        double contentsPremium = contentsValue * 0.01; // Contents cover rate = 1%
        return propertyPremium + contentsPremium;
    }
}
