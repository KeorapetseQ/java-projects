/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tlhohonolofatsoqoane2437485;
public class MotorPolicy extends Policy {
    private double vehicleValue;
    private String vehicleType; // Motorcycle, Private Car, Minibus, Truck
    private String coverType;   // Third Party, Comprehensive

    public MotorPolicy(int policyNumber, int customerId, String start, String end, double coverage, String status,
                         double vehicleValue, String vehicleType, String coverType) {
        super(policyNumber, customerId, start, end, coverage, status);
        this.vehicleValue = vehicleValue;
        this.vehicleType = vehicleType;
        this.coverType = coverType;
        this.premium = calculateAnnualPremium(); // Recalculate with subclass fields
    }

    @Override
    public double calculateAnnualPremium() {
        double riskRate = 0.02; // Default fallback
        boolean isComp = coverType.equalsIgnoreCase("Comprehensive");

        switch (vehicleType) {
            case "Motorcycle":
                riskRate = isComp ? 0.04 : 0.02;
                break;
            case "Private Car":
                riskRate = isComp ? 0.05 : 0.025;
                break;
            case "Minibus":
                riskRate = isComp ? 0.06 : 0.03;
                break;
            case "Truck":
                riskRate = isComp ? 0.07 : 0.035;
                break;
        }
        return vehicleValue * riskRate;
    }
}

