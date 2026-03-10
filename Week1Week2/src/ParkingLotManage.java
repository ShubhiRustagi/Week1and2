import java.util.*;

class ParkingSpot {
    String licensePlate;
    long entryTime;
    boolean occupied;

    ParkingSpot() {
        licensePlate = null;
        entryTime = 0;
        occupied = false;
    }
}

public class ParkingLotManage {

    private int size = 500;
    private ParkingSpot[] table = new ParkingSpot[size];
    private int occupiedSpots = 0;
    private int totalProbes = 0;
    private int totalParks = 0;

    public ParkingLotManage() {
        for (int i = 0; i < size; i++) {
            table[i] = new ParkingSpot();
        }
    }

    // hash function
    private int hash(String plate) {
        return Math.abs(plate.hashCode()) % size;
    }

    // park vehicle using linear probing
    public void parkVehicle(String plate) {

        int index = hash(plate);
        int probes = 0;

        while (table[index].occupied) {
            index = (index + 1) % size;
            probes++;
        }

        table[index].licensePlate = plate;
        table[index].entryTime = System.currentTimeMillis();
        table[index].occupied = true;

        occupiedSpots++;
        totalProbes += probes;
        totalParks++;

        System.out.println("Assigned spot #" + index + " (" + probes + " probes)");
    }

    // exit vehicle
    public void exitVehicle(String plate) {

        int index = hash(plate);

        while (table[index].occupied) {

            if (plate.equals(table[index].licensePlate)) {

                long durationMs = System.currentTimeMillis() - table[index].entryTime;
                double hours = durationMs / (1000.0 * 60 * 60);

                double fee = hours * 5; // $5 per hour

                table[index].occupied = false;
                table[index].licensePlate = null;

                occupiedSpots--;

                System.out.println("Spot #" + index + " freed, Duration: "
                        + String.format("%.2f", hours) + "h, Fee: $" + String.format("%.2f", fee));

                return;
            }

            index = (index + 1) % size;
        }

        System.out.println("Vehicle not found");
    }

    // statistics
    public void getStatistics() {

        double occupancy = (occupiedSpots * 100.0) / size;
        double avgProbes = totalParks == 0 ? 0 : (double) totalProbes / totalParks;

        System.out.println("Occupancy: " + String.format("%.2f", occupancy) + "%");
        System.out.println("Avg Probes: " + String.format("%.2f", avgProbes));
        System.out.println("Total Parked Vehicles: " + totalParks);
    }

    public static void main(String[] args) {

        ParkingLotManage system = new ParkingLotManage();

        system.parkVehicle("ABC-1234");
        system.parkVehicle("ABC-1235");
        system.parkVehicle("XYZ-9999");

        system.exitVehicle("ABC-1234");

        system.getStatistics();
    }
}