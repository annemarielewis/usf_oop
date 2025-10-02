import java.util.Date;

public class DateExample {
    public static void main(String[] args) {
        // Create a Date object representing the current time
        Date currentDate = new Date();
        System.out.println("Current Date and Time: " + currentDate);

        // Get milliseconds since the Unix epoch
        long currentTimeMillis = currentDate.getTime();
        System.out.println("Milliseconds since Unix epoch: " + currentTimeMillis);

        // Create a Date object from a specific millisecond value
        Date specificDate = new Date(currentTimeMillis-10000); //#s are in milliseconds
        System.out.println("Specific Date: " + specificDate);
    }
}

//output:
//Current Date and Time: Thu Oct 02 12:05:56 PDT 2025
//Milliseconds since Unix epoch: 1759431956939
//Specific Date: Thu Oct 02 12:05:46 PDT 2025
