import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Wildlife Tracking System Server
 * Handles multiple client connections and manages the SQLite database
 * Listens on port 8888 for client connections
 * Creates a new thread per client
 * Receives text commands (ADD_SPECIES, GET_ALL_SPECIES, ADD_ZONE, RECORD_SIGHTING, GET_ALL_ZONES, GET_SIGHTINGS, GET_ZONE_STATS, QUIT)
 * Parses the command, calls the database layer, and
 * Returns results or errors as text responses
 * Cleans up sockets/resources and supports graceful shutdown
 */

//Server = translator + traffic controller
//WildlifeServer.java = network layer
//
//        Listens on a port (8888) and accepts client connections
//
//        Reads text commands from clients (like ADD_SPECIES:...)
//
//        Parses the command + parameters
//
//        Calls DatabaseManager methods to do the actual DB work
//
//        Sends the response text back to the client
//
//        Manages threads (one ClientHandler per client)


public class WildlifeServer {
    private static final int PORT = 8888;
    private DatabaseManager dbManager;
    private ServerSocket serverSocket;
    private volatile boolean running = true;

    public WildlifeServer() {
        try {
            dbManager = new DatabaseManager();
            serverSocket = new ServerSocket(PORT);
            System.out.println("Wildlife Tracking Server started on port " + PORT);
            System.out.println("Waiting for client connections...");
        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Server socket error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Main server loop - accepts client connections
     */
    public void start() {
        while (running) {
            //CS514: While the server is running, the Socket obj is looking for incoming requests
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                //CS514: once a connection is made, a client handler obj is created
                ClientHandler handler = new ClientHandler(clientSocket, dbManager);
                handler.start();

            } catch (IOException e) {
                if (running) {
                    System.err.println("Error accepting client: " + e.getMessage());
                }
            }
        }
    }

    /**
     * ClientHandler - Inner class to handle individual client connections
     * Each client gets their own thread
     */
    private static class ClientHandler extends Thread {
        private Socket socket;
        private DatabaseManager dbManager;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket, DatabaseManager dbManager) {
            this.socket = socket;
            this.dbManager = dbManager;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                out.println("Connected to Wildlife Tracking System");

                //CS514: the messages coming from the client connection are expected to be DB requests
                // that have been formatted in a specific protocol
                String clientMessage;
                while ((clientMessage = in.readLine()) != null) {
                    if (clientMessage.equals("QUIT")) {
                        out.println("Goodbye!");
                        break;
                    }

                    System.out.println("Received from " + socket.getInetAddress() + ": " + clientMessage);
                    String response = processCommand(clientMessage);
                    out.println(response);
                }

            } catch (IOException e) {
                System.err.println("Client handler error: " + e.getMessage());
            } finally {
                //CS514: Note that we are trying to ensure that we close ALL connection resources
                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    System.err.println("Error closing resources: " + e.getMessage());
                }
                System.out.println("Client disconnected: " + socket.getInetAddress());
            }
        }

        /**
         * Process commands from client
         * Protocol format: COMMAND:param1:param2:param3...
         */
        private String processCommand(String command) {
            if (command == null || command.trim().isEmpty()) {
                return "ERROR: Empty command";
            }

            String[] parts = command.split(":", -1); // -1 to keep empty strings

            if (parts.length == 0) {
                return "ERROR: Invalid command format";
            }

            String cmd = parts[0].toUpperCase();

            try {
                switch (cmd) {
                    case "ADD_SPECIES":
                        if (parts.length != 6) {
                            return "ERROR: ADD_SPECIES requires 5 parameters (name, scientific name, status, habitat, population)";
                        }
                        String commonName = parts[1];
                        String sciName = parts[2];
                        String status = parts[3];
                        String habitat = parts[4];
                        int population = Integer.parseInt(parts[5]);
                        return dbManager.addSpecies(commonName, sciName, status, habitat, population);

                    case "ADD_ZONE":
                        if (parts.length != 5) {
                            return "ERROR: ADD_ZONE requires 4 parameters (name, location, area, year)";
                        }
                        String zoneName = parts[1];
                        String location = parts[2];
                        double area = Double.parseDouble(parts[3]);
                        int year = Integer.parseInt(parts[4]);
                        return dbManager.addZone(zoneName, location, area, year);

                    case "RECORD_SIGHTING":
                        if (parts.length != 7) {
                            return "ERROR: RECORD_SIGHTING requires 6 parameters (species_id, zone_id, date, observer, count, notes)";
                        }
                        int speciesId = Integer.parseInt(parts[1]);
                        int zoneId = Integer.parseInt(parts[2]);
                        String date = parts[3];
                        String observer = parts[4];
                        int count = Integer.parseInt(parts[5]);
                        String notes = parts[6];
                        return dbManager.recordSighting(speciesId, zoneId, date, observer, count, notes);

                    case "GET_ALL_SPECIES":
                        List<String> species = dbManager.getAllSpecies();
                        if (species.isEmpty()) {
                            return "No species found in database";
                        }
                        return "COUNT:" + species.size() + "\n" + String.join("\n", species);

                    case "GET_ALL_ZONES":
                        List<String> zones = dbManager.getAllZones();
                        if (zones.isEmpty()) {
                            return "No zones found in database";
                        }
                        return "COUNT:" + zones.size() + "\n" + String.join("\n", zones);

                    case "GET_SIGHTINGS":
                        if (parts.length != 2) {
                            return "ERROR: GET_SIGHTINGS requires 1 parameter (species_id)";
                        }
                        int searchSpeciesId = Integer.parseInt(parts[1]);
                        List<String> sightings = dbManager.getSightingsBySpecies(searchSpeciesId);
                        if (sightings.isEmpty()) {
                            return "No sightings found for species ID " + searchSpeciesId;
                        }
                        if (sightings.get(0).startsWith("ERROR")) {
                            return sightings.get(0);
                        }
                        return "COUNT:" + sightings.size() + "\n" + String.join("\n", sightings);

                    case "GET_ZONE_STATS":
                        if (parts.length != 2) {
                            return "ERROR: GET_ZONE_STATS requires 1 parameter (zone_id)";
                        }
                        int searchZoneId = Integer.parseInt(parts[1]);
                        return dbManager.getZoneStatistics(searchZoneId);

                    default:
                        return "ERROR: Unknown command: " + cmd;
                }
            } catch (NumberFormatException e) {
                return "ERROR: Invalid number format - " + e.getMessage();
            } catch (Exception e) {
                return "ERROR: " + e.getMessage();
            }
        }
    }

    /**
     * Shutdown server gracefully
     */
    public void shutdown() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            if (dbManager != null) {
                dbManager.close();
            }
            System.out.println("Server shutdown complete");
        } catch (IOException e) {
            System.err.println("Error during shutdown: " + e.getMessage());
        }
    }

    /**
     * Main method to start the server
     */
    public static void main(String[] args) {
        WildlifeServer server = new WildlifeServer();

        // Add shutdown hook for graceful shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nShutting down server...");
            server.shutdown();
        }));

        server.start();
    }
}

/**
 * PROTOCOL DOCUMENTATION:
 *
 * Commands (case-insensitive):
 * - ADD_SPECIES:commonName:scientificName:status:habitat:population
 * - ADD_ZONE:zoneName:location:areaKm2:establishedYear
 * - RECORD_SIGHTING:speciesId:zoneId:date:observer:count:notes
 * - GET_ALL_SPECIES (no parameters)
 * - GET_ALL_ZONES (no parameters)
 * - GET_SIGHTINGS:speciesId
 * - GET_ZONE_STATS:zoneId
 * - QUIT (disconnect)
 *
 * Response format:
 * - Success: "SUCCESS: [message]"
 * - Error: "ERROR: [message]"
 * - Lists: "COUNT:n\nitem1\nitem2\n..."
 *
 */