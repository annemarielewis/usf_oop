import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Wildlife Tracking System Client (Console)
 * Connects to server and provides text-based interface for wildlife tracking operations
 * WildlifeClient.java — User Interface (Client)
 * Runs in the user’s terminal
 * Shows menus and collects user input
 * Converts user actions into text commands
 * e.g. ADD_SPECIES:..., GET_ALL_SPECIES
 * Sends commands to the server over a socket
 * Receives and displays responses from the server
 * @author (your name) AM Lewis
 *
] */

public class WildlifeClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8888;

    // Network components
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;  //specifies where out goes (in out.println())
    private Scanner scanner;

    public WildlifeClient() {
        scanner = new Scanner(System.in);
        connectToServer();
    }

    /**
     * Connect to the server
     */

    private void connectToServer() {
        try {
            // TODO 1a: Create socket connection to server (socket=a network communication channel between two applications)
            System.out.println("Connecting to Wildlife Tracking Server...");
            socket = new Socket(SERVER_HOST, SERVER_PORT);

            // TODO 1b: Create BufferedReader for input
            // Wrap socket's InputStream with InputStreamReader and then with BufferedReader-->to be "read"
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // TODO 1c: Create PrintWriter for output
            // Use socket's OutputStream and enable auto-flush to send data--> ??
            out = new PrintWriter(socket.getOutputStream(), true);

            // TODO 1d: Read welcome message from server
            String welcome = in.readLine(); // reading the welcome message sent by the server
            System.out.println("** " + welcome);
            System.out.println("** Connected successfully!\n");

        } catch (IOException e) {
            System.err.println("ERROR: could not connect to server");
            System.err.println("  " + e.getMessage());
            System.err.println("\nPlease ensure the server is running at " + SERVER_HOST + ":" + SERVER_PORT);
            System.exit(1);
        }
    }

    /**
     * Display main menu and handle user choices
     */
    public void run() {
        boolean running = true;

        // Main menu loop
        while (running) {
            displayMenu();

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();
            System.out.println();

            // Switch statement to handle user choices
            switch (choice) {
                case "1":
                    // Add a species
                    addSpecies();
                    break;
                case "2":
                    // Add a zone
                    addZone();
                    break;
                case "3":
                    // Record a sighting
                    recordSighting();
                    break;
                case "4":
                    // View all species
                    viewAllSpecies();
                    break;
                case "5":
                    // View all zones
                    viewAllZones();
                    break;
                case "6":
                    // View all sightings by species
                    viewSightingsBySpecies();
                    break;
                case "7":
                    // View all zone statistics
                    viewZoneStatistics();
                    break;
                case "8":
                    // Exit the program
                    System.out.println("Exiting... Disconnecting...");
                    running = false;  // running set to false to exit the loop
                    break;
                default:
                    System.out.println("Invalid choice. Try again!\n");
            }
        }
        // Close connection once the loop exits
        closeConnection();
    }

    /**
     * Display the main menu
     */
    // CS514: No change needed for this method; it's already implemented for you
    private void displayMenu() {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║          WILDLIFE TRACKING SYSTEM - MAIN MENU          ║");
        System.out.println("╠════════════════════════════════════════════════════════╣");
        System.out.println("║  1. Add New Species                                    ║");
        System.out.println("║  2. Add Conservation Zone                              ║");
        System.out.println("║  3. Record Wildlife Sighting                           ║");
        System.out.println("║  4. View All Species                                   ║");
        System.out.println("║  5. View All Conservation Zones                        ║");
        System.out.println("║  6. View Sightings by Species                          ║");
        System.out.println("║  7. View Zone Statistics                               ║");
        System.out.println("║  8. Exit                                               ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
    }

    /**
     * Add a new species to the database
     */
    //methods that were called above in running cases ^, defined below:
    private void addSpecies() {
        // This is menu item #1
        System.out.println("═══ ADD NEW SPECIES ═══\n");

        // TODO 4: Get species information from user
        // HINT: Use scanner.nextLine().trim() for each field
        // HINT: Fields needed: commonName, scientificName, status, habitat, population

        // Get the common name of the species
        System.out.print("Common Name: ");
        String commonName = scanner.nextLine().trim();

        // Get the scientific name of the species
        System.out.print("Scientific Name: ");
        String scientificName = scanner.nextLine().trim();

        // CS514: Display conservation status menu: no changes needed
        System.out.println("\nConservation Status Options:");
        System.out.println("  1. Critically Endangered");
        System.out.println("  2. Endangered");
        System.out.println("  3. Vulnerable");
        System.out.println("  4. Near Threatened");
        System.out.println("  5. Least Concern");

        System.out.print("Select status (1-5): ");
        // TODO 4a: Get the input value for the status choice
        String statusChoice = scanner.nextLine().trim();

        // TODO 4b: Convert status choice to string
        String status = "";
        switch (statusChoice) {
            case "1":
                status = "Critically Endangered";
                break;
            case "2":
                status = "Endangered";
                break;
            case "3":
                status = "Vulnerable";
                break;
            case "4":
                status = "Near Threatened";
                break;
            case "5":
                status = "Least Concern";
                break;
            default:
                System.out.println("ERROR: Invalid status choice.");
                return; // Return early if the status choice is invalid
        }

        // Get habitat from user
        System.out.print("Habitat Type (e.g., Forest, Ocean, Desert): ");
        String habitat = scanner.nextLine().trim();

        // Get population from user
        System.out.print("Population Estimate: ");
        String populationStr = scanner.nextLine().trim();

        // TODO 4c: Validate input
        if (commonName.isEmpty() || scientificName.isEmpty() || status.isEmpty() || habitat.isEmpty() || populationStr.isEmpty()) {
            System.out.println("ERROR: All fields required.\n");
            return;
        }

        try {
            // TODO 4d: Parse population to integer
            int populationInt = Integer.parseInt(populationStr);

            if (populationInt < 0) {
                System.out.println("ERROR: Population cannot be negative\n");
                return;
            }

            // TODO 4e: Build and send command to server
            // HINT: Format: "ADD_SPECIES:commonName:scientificName:status:habitat:population"
            //how server needs to be sent the info:
            //% for less concact.
            String command = String.format("ADD_SPECIES:%s:%s:%s:%s:%d",
                    commonName, scientificName, status, habitat, populationInt);

            // TODO 4g: Send command with out.println()
            out.println(command);  // Send the formatted command to the server

            // TODO 4f: Receive and display response
            String response = in.readLine();  // Read response from server (anything coming in inherently comes from the server then?)

            // HINT: Call displayResponse() method to see how the server responded
            displayResponse(response);

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Population must be a valid number\n");
        } catch (IOException e) {
            System.out.println("ERROR: Communication error, " + e.getMessage() + "\n");
        }
    }

    /**
     * Add a new conservation zone
     */
    private void addZone() {
        System.out.println("═══ ADD CONSERVATION ZONE ═══\n");


        // TODO 5: Similar to addSpecies() - get zone information from user
        // HINT: Fields needed: zoneName, location, area (double), year (int)
        // HINT: Validate area > 0 and 1800 <= year <= current year
        // HINT: Command format: "ADD_ZONE:zoneName:location:area:year"

        System.out.print("Zone Name: ");
        String zoneName = scanner.nextLine().trim();  // Get zone name from user

        System.out.print("Location: ");
        String location = scanner.nextLine().trim();  // Get location from user

        System.out.print("Area (km²): ");
        String areaStr = scanner.nextLine().trim();  // Get area from user

        System.out.print("Established Year: ");
        String yearStr = scanner.nextLine().trim();  // Get year from user

        // TODO 5a: Validate all fields
        if (zoneName.isEmpty() || location.isEmpty() || areaStr.isEmpty() || yearStr.isEmpty()) {
            System.out.println("ERROR: All fields are required.\n");
            return;  // Return early if any field is empty
        }

        try {
            // TODO 5b: Parse area to double and year to int
            double area = Double.parseDouble(areaStr);
            int year = Integer.parseInt(yearStr);

            // TODO 5c: Validate ranges
            if (area <= 0) {
                System.out.println("ERROR: Area must be greater than 0\n");
                return;
            }

            int currentYear = java.time.Year.now().getValue();  // Get the current year
            if (year < 1800 || year > currentYear) {
                System.out.println("ERROR: Year must be between 1800 and the current year... Animals were invented in 1801! ;)\n");
                return;
            }

            // TODO 5d: Build and send command
            String command = String.format("ADD_ZONE:%s:%s:%.2f:%d", zoneName, location, area, year);

            // TODO 5e: Receive and display response
            out.println(command);

            String response = in.readLine();  // Read response from server --> what if something else sends something "in", is this possible? then wouldn't response not be teh sevrer's response?
            displayResponse(response);  // Display the server response

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid number format. Please check area and year\n");
        } catch (IOException e) {
            System.out.println("ERROR: Communication error: " + e.getMessage() + "\n");
        }
    }


    /**
     * Record a wildlife sighting
     */
    private void recordSighting() {
        System.out.println("═══ RECORD WILDLIFE SIGHTING ═══\n");

        // TODO 6: Get sighting information from user
        // HINT: Fields: speciesId (int), zoneId (int), date (String), observer, count (int), notes
        // HINT: For date, if user presses Enter, use LocalDate.now().toString()
        // HINT: Command format: "RECORD_SIGHTING:speciesId:zoneId:date:observer:count:notes"

        System.out.print("Species ID: ");
        String speciesIdStr = scanner.nextLine().trim();

        System.out.print("Zone ID: ");
        String zoneIdStr = scanner.nextLine().trim();

        System.out.print("Date (YYYY-MM-DD) [press enter for today]: ");
        String date = scanner.nextLine().trim();
        // TODO 6a: If date is empty, set to LocalDate.now().toString()
        if (date.isEmpty()) {
            date = java.time.LocalDate.now().toString();
        }

        System.out.print("Observer Name: ");
        String observer = scanner.nextLine().trim(); // Get observer name from user

        System.out.print("Individual Count: ");
        String countStr = scanner.nextLine().trim(); // Get individual count from user

        System.out.print("Behavior Notes: ");
        String notes = scanner.nextLine().trim(); // Get behavior notes from user

        // TODO 6b: Validate required fields (not notes)
        if (speciesIdStr.isEmpty() || zoneIdStr.isEmpty() || observer.isEmpty() || countStr.isEmpty()) {
            System.out.println("ERROR: Species ID, Zone ID, Observer Name, and Count are all required!\n");
            return;  // Return early if any required field is empty
        }

        try {
            // TODO 6c: Parse 'IDs' and 'count' into integers
            int speciesId = Integer.parseInt(speciesIdStr);
            int zoneId = Integer.parseInt(zoneIdStr);
            int count = Integer.parseInt(countStr);

            // TODO 6d: Validate count > 0
            if (count <= 0) {
                System.out.println("ERROR: Count must be greater than 0\n");
                return;
            }

            // TODO 6e: Build and send command
            String command = String.format("RECORD_SIGHTING:%d:%d:%s:%s:%d:%s", speciesId, zoneId, date, observer, count, notes);  // Format the command string how server needs info/db

            // TODO 6f: Receive and display response
            out.println(command);

            // Receive response from the server
            String response = in.readLine();

            // Display the server's response
            if (response != null) {
                System.out.println("Server Response: " + response);
            } else {
                System.out.println("ERROR: No response from server. :(");
            }

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Species ID, Zone ID, and Count must be valid integers\n");
        } catch (IOException e) {
            System.out.println("ERROR: Communication error, " + e.getMessage() + "\n");
        }
    }

    /**
     * View all species in the database
     */
    private void viewAllSpecies() {
        System.out.println("═══ ALL SPECIES ═══\n");

        try {
            // 7a: Send GET_ALL_SPECIES command to server and display results
            out.println("GET_ALL_SPECIES");

            // TODO 7b: Read first line (should be "COUNT:n" or error message)
            String firstLine = in.readLine(); // Replacing null with inbound socket connection

            if (firstLine == null) {
                System.out.println("No response from server :( \n");
                return;
            }

            // TODO 7c: Check if response starts with "No species" or is an error on the server side
            if (firstLine.startsWith("No species")) {
                System.out.println(firstLine + "\n");
                return;
            }

            // HINT: First line response will be "COUNT:n" where n is number of results
            // HINT: Then read n more lines, each containing species data separated by "|"

            // TODO 7d: Parse count from "COUNT:n" format
            if (firstLine.startsWith("COUNT:")) {
                int count = Integer.parseInt(firstLine.substring(6));

                System.out.println("Found " + count + " species:\n");
                System.out.println("─".repeat(100));

                // TODO 7e: Loop to read 'count' lines
                // HINT: Each line format: "ID:1|Common Name|Scientific Name|Status|Habitat|Population"
                for (int i = 0; i < count; i++) {
                    String speciesData = in.readLine();

                    if (speciesData != null) {
                        String[] speciesFields = speciesData.split("\\|");

                        if (speciesFields.length == 5) {
                            System.out.printf("%s\n", speciesFields[0]); // ID:...
                            System.out.printf("Name: %s\n", speciesFields[1]); // Common (Scientific)
                            System.out.printf("%s\n", speciesFields[2]); // Status:...
                            System.out.printf("%s\n", speciesFields[3]); // Habitat:...
                            System.out.printf("%s\n", speciesFields[4]); // Pop:...
                            System.out.println("─".repeat(100));
                        } else {
                            System.out.println("ERROR: Invalid species data format.\n");
                        }
                    } else {
                        System.out.println("ERROR: Failed to read species data.\n");
                        break;
                    }
                }

                System.out.println("─".repeat(100));  // Final separator
                System.out.println();
            } else {
                System.out.println("ERROR: Invalid response from server!\n");
            }

        } catch (IOException e) {
            System.out.println("ERROR: Communication error - " + e.getMessage() + "\n");
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid response format\n");
        }
    }

    private void viewAllZones() {
        System.out.println("═══ ALL CONSERVATION ZONES ═══\n");

        try {
            // TODO 8: Send "GET_ALL_ZONES" command
            out.println("GET_ALL_ZONES");

            // TODO 8a: Read first line (should be "COUNT:n" or error message)
            String firstLine = in.readLine();

            if (firstLine == null) {
                System.out.println("No response from server\n");
                return;
            }

            // TODO 8b: Check if response starts with "No zones" or is an error message
            if (firstLine.startsWith("No zones")) {
                System.out.println(firstLine + "\n");
                return;
            }

            // HINT: First line response will be "COUNT:n" where n is the number of results
            // HINT: Then read n more lines, each containing zone data separated by "|"

            // TODO 8c: Parse count from "COUNT:n" format
            if (firstLine.startsWith("COUNT:")) {
                int count = Integer.parseInt(firstLine.substring(6)); // Parse the count of all zones from firstLine

                System.out.println("Found " + count + " zones:\n");
                System.out.println("─".repeat(100));

                // TODO 8d: Loop to read 'count' lines
                // HINT: Each line format: "ID:1|Zone Name|Location|Area|Year"
                for (int i = 0; i < count; i++) {
                    String zoneData = in.readLine();

                    if (zoneData != null) {
                        String[] zoneFields = zoneData.split("\\|"); // Split by "|"

                        if (zoneFields.length == 5) {
                            System.out.printf("ID: %s\n", zoneFields[0]);
                            System.out.printf("Zone Name: %s\n", zoneFields[1]);
                            System.out.printf("Location: %s\n", zoneFields[2]);
                            System.out.printf("Area (km²): %s\n", zoneFields[3]);
                            System.out.printf("Established Year: %s\n", zoneFields[4]);
                            System.out.println("─".repeat(100));  // Separate each zone output
                        } else {
                            System.out.println("ERROR: Invalid zone data format.\n");
                        }
                    } else {
                        System.out.println("ERROR: Failed to read zone data.\n");
                        break;
                    }
                }

                System.out.println("─".repeat(100));
                System.out.println();
            } else {
                System.out.println("ERROR: Invalid response from server.\n");
            }

        } catch (IOException e) {
            System.out.println("ERROR: Communication error - " + e.getMessage() + "\n");
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid response format.\n");
        }
    }


    /**
     * View sightings for a specific species
     */
    private void viewSightingsBySpecies() {
        System.out.println("═══ VIEW SIGHTINGS BY SPECIES ═══\n");

        // TODO 9: Get species ID and display sightings
        // HINT: Prompt user for species ID
        // HINT: Send "GET_SIGHTINGS:speciesId" command
        // HINT: Parse response similar to viewAllSpecies()
        // HINT: Sighting format: "Species|Zone|Date|Observer|Count|Notes"

        System.out.print("Enter Species ID: ");
        String speciesIdStr = scanner.nextLine().trim(); // Get input from user

        // TODO 9a: Validate speciesIdStr is not empty
        if (speciesIdStr.isEmpty()) {
            System.out.println("ERROR: Species ID cannot be empty.\n");
            return;
        }

        try {
            // TODO 9b: Parse to integer
            int speciesId = Integer.parseInt(speciesIdStr);

            // TODO 9c: Send command to server
            String command = "GET_SIGHTINGS:" + speciesId;
            out.println(command);

            // TODO 9d: Read and display response (may have multiple lines)
            String firstLine = in.readLine();

            if (firstLine == null) {
                System.out.println("No response from server.\n");
                return;
            }

            if (firstLine.startsWith("No sightings")) { //? correct startswith... ? come back
                System.out.println(firstLine + "\n");
                return;
            }

            if (firstLine.startsWith("COUNT:")) {
                int count = Integer.parseInt(firstLine.substring(6));
                System.out.println("Found " + count + " sightings for Species ID " + speciesId + ":\n");

                System.out.println("─".repeat(100));

                // Loop to read each sighting line (for each sighting)
                for (int i = 0; i < count; i++) {
                    String sightingLine = in.readLine();  // Read each sighting line
                    if (sightingLine != null) {
                        String[] sightingDetails = sightingLine.split("\\|");  // Split by "|"
                        if (sightingDetails.length == 6) {
                            System.out.printf("Species: %s\n", sightingDetails[0]);
                            System.out.printf("Zone: %s\n", sightingDetails[1]);
                            System.out.printf("Date: %s\n", sightingDetails[2]);
                            System.out.printf("Observer: %s\n", sightingDetails[3]);
                            System.out.printf("Count: %s\n", sightingDetails[4]);
                            System.out.printf("Notes: %s\n", sightingDetails[5]);
                            System.out.println("─".repeat(100));  // Print a separator line
                        }
                    }
                }
                System.out.println();  // Print a final newline after displaying all sightings
            }

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Species ID must be a valid integer.\n");
        } catch (IOException e) {
            System.out.println("ERROR: Communication error - " + e.getMessage() + "\n");
        }
    }

    /**
     * View statistics for a specific zone
     */
    private void viewZoneStatistics() {
        System.out.println("═══ VIEW ZONE STATISTICS ═══\n");

        // TODO 10: Get zone ID and display statistics
        // HINT: Prompt user for zone ID
        System.out.print("Enter Zone ID: ");
        String zoneIdStr = scanner.nextLine().trim();

        // TODO 10a: Validate and parse zone ID
        if (zoneIdStr.isEmpty()) {
            System.out.println("ERROR: Zone ID cannot be empty.\n");
            return;
        }

        int zoneId = 0;
        try {
            zoneId = Integer.parseInt(zoneIdStr);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid Zone ID. Please enter a valid number.\n");
            return;
        }

        // TODO 10b: Send command
        // HINT: Send "GET_ZONE_STATS:zoneId" command
        String command = String.format("GET_ZONE_STATS:%d", zoneId);
        out.println(command);  // Sent to serverrr!

        // TODO 10c: Read response
        try {
            String response = in.readLine();

            if (response == null || response.isEmpty()) {
                System.out.println("ERROR: No response from the server.\n");
                return;
            }

            // TODO 10d: Spliting by "|" + displaying stats
            String[] stats = response.split("\\|");

            if (stats.length != 4) {
                System.out.println("ERROR: Invalid stats format\n");
                return;
            }

            // Parsing and displaying the statistics
            String zoneName = stats[0].substring(5);  // Removing "Zone:" part
            String speciesCount = stats[1].substring(8);  // Removing "Species:" part
            String sightingsCount = stats[2].substring(10);  // Removing "Sightings:" part
            String totalIndividuals = stats[3].substring(15);  // Removing "TotalIndividuals:" part

            // Display formatted statistics
            System.out.println("Zone: " + zoneName);
            System.out.println("Species Count: " + speciesCount);
            System.out.println("Sightings Count: " + sightingsCount);
            System.out.println("Total Individuals: " + totalIndividuals);

        } catch (IOException e) {
            System.out.println("ERROR: Communication error - " + e.getMessage() + "\n");
        }
    }


    /**
     * Display server response with formatting
     */
    private void displayResponse(String response) {
        if (response.startsWith("SUCCESS")) {
            System.out.println("=== SUCCESS ===");
        } else if (response.startsWith("ERROR")) {
            System.out.println("=== ERROR ===");
        }

        System.out.println(response);
        System.out.println();
    }

    /**
     * Close connection when client exits
     */
    private void closeConnection() {
        try {
            // TODO 12: Clean up resources
            // Send quit to server:
            if (out != null) {
                out.println("QUIT");
            }

            // TODO 12a: Send QUIT command
            System.out.println("Sending QUIT command to the server...");

            // TODO 12b: Close input reader
            if (in != null) {
                in.close();
                System.out.println("Input stream closed.");
            }

            // TODO 12c: Close output writer (what would happen is before quit send to server, ^?)
            if (out != null) {
                out.close();
                System.out.println("Output stream closed.");
            }

            // TODO 12d: Close socket
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("Socket closed!");
            }

            // TODO 12e: Close scanner
            if (scanner != null) {
                scanner.close();
                System.out.println("Scanner closed.");
            }

            System.out.println("Disconnected successfully. Goodbye!!!");

        } catch (IOException e) {
            System.err.println("Error closing connection: you cannot escpae me!" + e.getMessage());
        }
    }

    /**
     * Main method to start the client
     */

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║        WILDLIFE TRACKING SYSTEM - CONSOLE CLIENT       ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");

        WildlifeClient client = new WildlifeClient();
        client.run();
    }




}
