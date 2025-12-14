import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages all database operations for the Wildlife Tracking System
 * Tracks endangered species sightings, conservation zones, and research data
 * Connects to wildlife_tracker.db via JDBC.
 * Creates tables if they don’t exist: species, conservation_zones, sightings (with foreign keys).
 * Provides thread-safe (synchronized) database methods to:
 * Insert new species, zones, and sightings (using PreparedStatement placeholders ?).
 * Query and return formatted lists of all species/zones.
 * Query sightings by species using JOINs across tables.
 * Compute zone statistics (species count, sightings count, total individuals).
 * Handles SQL errors by returning "ERROR: ..." messages (or adding an error string to lists).
 * Closes the database connection cleanly with close().
 */

//DatabaseManager = the actual database worker
//DatabaseManager.java = database layer
//
//        Talks directly to SQLite via JDBC (Connection, PreparedStatement, ResultSet)
//
//        Knows how to:
//
//        create tables
//
//        insert species/zones/sightings
//
//        query lists + JOINs
//
//        compute zone stats
//
//        Has no networking (no sockets, no clients)

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:wildlife_tracker.db";
    private Connection connection;

    public DatabaseManager() throws SQLException {
        initializeDatabase();
    }

    /**
     * Initialize database connection and create tables if they don't exist
     */
    private void initializeDatabase() throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        createTables();
    }

    /**
     * Create the necessary tables for the wildlife tracking system
     */
    //schemas:

    private void createTables() throws SQLException {
        String createSpeciesTable = """
            CREATE TABLE IF NOT EXISTS species (
                species_id INTEGER PRIMARY KEY AUTOINCREMENT,
                common_name TEXT NOT NULL,
                scientific_name TEXT UNIQUE NOT NULL,
                conservation_status TEXT NOT NULL,
                habitat_type TEXT NOT NULL,
                population_estimate INTEGER
            )
        """;

        String createZonesTable = """
            CREATE TABLE IF NOT EXISTS conservation_zones (
                zone_id INTEGER PRIMARY KEY AUTOINCREMENT,
                zone_name TEXT UNIQUE NOT NULL,
                location TEXT NOT NULL,
                area_km2 REAL NOT NULL,
                established_year INTEGER NOT NULL
            )
        """;

        String createSightingsTable = """
            CREATE TABLE IF NOT EXISTS sightings (
                sighting_id INTEGER PRIMARY KEY AUTOINCREMENT,
                species_id INTEGER NOT NULL,
                zone_id INTEGER NOT NULL,
                sighting_date TEXT NOT NULL,
                observer_name TEXT NOT NULL,
                individual_count INTEGER NOT NULL,
                behavior_notes TEXT,
                FOREIGN KEY (species_id) REFERENCES species(species_id),
                FOREIGN KEY (zone_id) REFERENCES conservation_zones(zone_id)
            )
        """;

        //Each call:
        //Sends one CREATE TABLE command
        //Creates one table if it doesn’t exist
        //This guarantees:
        //species table exists
        //conservation_zones table exists
        //sightings table exists
        //Even if the database is empty or new.

        //-->
        //CREATE TABLE IF NOT EXISTS ...
        //That means:
        //First run → tables are created
        //Later runs → SQLite skips creation

        // NOTES for CS514:
        /*  We are using the JDBC's connection object to create a Statement Obj
            - We are passing in the CREATE table string for each table into the
              statement's execute method
         */
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createSpeciesTable);
            stmt.execute(createZonesTable);
            stmt.execute(createSightingsTable);
        }
    }

    /**
     * Add a new species to the database
     //adding elements to tables:
     */
    public synchronized String addSpecies(String commonName, String scientificName,
                                          String status, String habitat, int population) {
        String sql = "INSERT INTO species (common_name, scientific_name, conservation_status, habitat_type, population_estimate) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, commonName);
            pstmt.setString(2, scientificName);
            pstmt.setString(3, status);
            pstmt.setString(4, habitat);
            pstmt.setInt(5, population);
            pstmt.executeUpdate();
            return "SUCCESS: Species added successfully";
        } catch (SQLException e) {
            return "ERROR: " + e.getMessage();
        }
    }

    /**
     * Add a new conservation zone to the database
     */
    public synchronized String addZone(String zoneName, String location, double areaKm2, int year) {
        String sql = "INSERT INTO conservation_zones (zone_name, location, area_km2, established_year) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, zoneName);
            pstmt.setString(2, location);
            pstmt.setDouble(3, areaKm2);
            pstmt.setInt(4, year);
            pstmt.executeUpdate();
            return "SUCCESS: Conservation zone added successfully";
        } catch (SQLException e) {
            return "ERROR: " + e.getMessage();
        }
    }

    /**
     * Record a new wildlife sighting
     */
    public synchronized String recordSighting(int speciesId, int zoneId, String date,
                                              String observer, int count, String notes) {
        String sql = "INSERT INTO sightings (species_id, zone_id, sighting_date, observer_name, individual_count, behavior_notes) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, speciesId);
            pstmt.setInt(2, zoneId);
            pstmt.setString(3, date);
            pstmt.setString(4, observer);
            pstmt.setInt(5, count);
            pstmt.setString(6, notes);
            pstmt.executeUpdate();
            return "SUCCESS: Sighting recorded successfully";
        } catch (SQLException e) {
            return "ERROR: " + e.getMessage();
        }
    }

    /**
     * Get all species from the database
     */
    public synchronized List<String> getAllSpecies() {
        List<String> species = new ArrayList<>();
        String sql = "SELECT * FROM species ORDER BY common_name";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                species.add(String.format("ID:%d|%s (%s)|Status:%s|Habitat:%s|Pop:%d",
                        rs.getInt("species_id"),
                        rs.getString("common_name"),
                        rs.getString("scientific_name"),
                        rs.getString("conservation_status"),
                        rs.getString("habitat_type"),
                        rs.getInt("population_estimate")));
            }
        } catch (SQLException e) {
            species.add("ERROR: " + e.getMessage());
        }
        return species;
    }

    /**
     * Get all conservation zones
     */
    public synchronized List<String> getAllZones() {
        List<String> zones = new ArrayList<>();
        String sql = "SELECT * FROM conservation_zones ORDER BY zone_name";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                zones.add(String.format("ID:%d|%s|Location:%s|Area:%.2f km²|Est:%d",
                        rs.getInt("zone_id"),
                        rs.getString("zone_name"),
                        rs.getString("location"),
                        rs.getDouble("area_km2"),
                        rs.getInt("established_year")));
            }
        } catch (SQLException e) {
            zones.add("ERROR: " + e.getMessage());
        }
        return zones;
    }

    /**
     * Get sightings for a specific species
     */
    public synchronized List<String> getSightingsBySpecies(int speciesId) {
        List<String> sightings = new ArrayList<>();
        String sql = """
            SELECT s.*, sp.common_name, z.zone_name 
            FROM sightings s
            JOIN species sp ON s.species_id = sp.species_id
            JOIN conservation_zones z ON s.zone_id = z.zone_id
            WHERE s.species_id = ?
            ORDER BY s.sighting_date DESC
        """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, speciesId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                sightings.add(String.format("%s|Zone:%s|Date:%s|Observer:%s|Count:%d|Notes:%s",
                        rs.getString("common_name"),
                        rs.getString("zone_name"),
                        rs.getString("sighting_date"),
                        rs.getString("observer_name"),
                        rs.getInt("individual_count"),
                        rs.getString("behavior_notes")));
            }
        } catch (SQLException e) {
            sightings.add("ERROR: " + e.getMessage());
        }
        return sightings;
    }

    /**
     * Get statistics for a conservation zone
     */
    public synchronized String getZoneStatistics(int zoneId) {
        String sql = """
            SELECT z.zone_name, COUNT(DISTINCT s.species_id) as species_count, 
                   COUNT(s.sighting_id) as total_sightings,
                   SUM(s.individual_count) as total_individuals
            FROM conservation_zones z
            LEFT JOIN sightings s ON z.zone_id = s.zone_id
            WHERE z.zone_id = ?
            GROUP BY z.zone_id
        """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, zoneId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return String.format("Zone:%s|Species:%d|Sightings:%d|TotalIndividuals:%d",
                        rs.getString("zone_name"),
                        rs.getInt("species_count"),
                        rs.getInt("total_sightings"),
                        rs.getInt("total_individuals"));
            }
        } catch (SQLException e) {
            return "ERROR: " + e.getMessage();
        }
        return "ERROR: Zone not found";
    }

    /**
     * Close database connection
     */
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database: " + e.getMessage());
        }
    }
}