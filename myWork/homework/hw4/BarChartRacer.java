// CS 514: Bar Chart Racer Class
// Name: <add your name here>

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class BarChartRacer {
    protected BarChart chart;
    protected String fname;
    private static String currentYear = "";

//constructors:
    public BarChartRacer() {
        StdDraw.setCanvasSize(1000, 700);
        StdDraw.enableDoubleBuffering();
    }
    public BarChartRacer(String fname) {
        this.fname = fname;
        StdDraw.setCanvasSize(1000, 700);
        StdDraw.enableDoubleBuffering();
    }

    public void processHeader(Scanner input) {
        // Read the first three lines of the data file + store
        String title = input.nextLine();
        String xAxis = input.nextLine();
        String source = input.nextLine();
        // Create a new BarChart and assign it to the instance variables
        this.chart = new BarChart(title, xAxis, source);
    }

    public void drawChart(int nbars, int pauseTime) {
        try {
            // Create a Scanner
            File file = new File(this.fname);
            Scanner input = new Scanner(file);

            //  calls Process headers helper function of current scanner object
            this.processHeader(input);
            //at this stage, have the titles of the chart

            // Set up display
            StdDraw.setCanvasSize(1000, 700);
            StdDraw.enableDoubleBuffering();

            // Animation loop
            while (true) {
                // get next group of bars + use helper method getNextBars
                Bar[] bars = this.getNextBars(input);
                if (bars == null) break; // if end of file

                String year = currentYear;
                // clear previous data (so text isn;t written over itself)
                chart.reset();

                // sort the bars in ascending order by value
                Arrays.sort(bars);
                int start = Math.max(0, bars.length - nbars);

                for (int i = bars.length - 1; i >= start; i--) {
                    Bar b = bars[i];
                    chart.add(b.getName(), b.getValue(), b.getCategory());
                }


                chart.setCaption(currentYear);
                StdDraw.clear();
                chart.draw();
                StdDraw.show();
                StdDraw.pause(pauseTime);
            }

            input.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Error: Could not open file " + this.fname);
        }
    }

    //returns bar object
    public Bar[] getNextBars(Scanner input) {
        // Skip blank lines
        while (input.hasNextLine()) {
            String countLine = input.nextLine().trim();
            if (countLine.isEmpty()) continue;

            // Read how many records follow
            int count = Integer.parseInt(countLine); //233
            // If count is 0, just continue (no data)
            if (count == 0) continue;

            // an array of Bars of size count
            Bar[] bars = new Bar[count];
            String year = "";

            // Loop through
            for (int i = 0; i < count; i++) {
                if (!input.hasNextLine()) break;
                String line = input.nextLine();
                String[] parts = line.split(",");
                // structure: [0]=year, [1]=name, [2]=region, [3]=value, [4]=category
                year = parts[0];
                String name = parts[1];
                String countryOrRegion = parts[2];
                int value = Integer.parseInt(parts[3]);
                String category = parts[4];
                chart.setCaption(year);

                // Prevent crash: value must be >= 1
                value = Math.max(value, 1);

                // Create a Bar object
                bars[i] = new Bar(name, value, category);
            }
            BarChartRacer.currentYear = year;
            return bars; // Return the group of Bars
        }
        // No more data
        return null;
    }

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        System.out.print("Please enter the data filename to read from:");
        String filename = "data/" + console.nextLine();

        System.out.print("Enter the number of country bars to display at once: ");
        int nbars = console.nextInt();

        System.out.print("Enter the pause time between frames (in milliseconds) from one year to the next: ");
        int pauseTime = console.nextInt();

        console.close();

        BarChartRacer racer = new BarChartRacer(filename);

        racer.drawChart(nbars, pauseTime);
    }}

