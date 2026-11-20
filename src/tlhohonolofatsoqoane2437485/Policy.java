/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tlhohonolofatsoqoane2437485;

public abstract class Policy {
    protected int policyNumber;
    protected int customerId;
    protected String startDate;
    protected String endDate;
    protected double coverageAmount;
    protected double premium;
    protected String status; // PENDING, ACTIVE, EXPIRED, CANCELLED

    public Policy(int policyNumber, int customerId, String startDate, String endDate, double coverageAmount, String status) {
        this.policyNumber = policyNumber;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverageAmount = coverageAmount;
        this.status = status;
        this.premium = calculateAnnualPremium(); // Polymorphic call
    }

    // Abstract method forcing subclasses to implement their specific pricing rules
    public abstract double calculateAnnualPremium();

    // Getters and Setters
    public double getPremium() { return premium; }
    public String getStatus() { return status; }
    public int getPolicyNumber() { return policyNumber; }
}
