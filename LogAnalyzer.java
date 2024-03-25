import java.util.Arrays;

/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author Matt Romond
 * @version    2024.03.23
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    private int[][] dailyCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer(String filename)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        dailyCounts = new int[31][24];
        // Create the reader to obtain the data.
        reader = new LogfileReader(filename);
        analyzeHourlyData();
        analyzeDailyData();
    }
    
    /** 
     * Determine which month is the quietest using the log data.
     * @return The quietest month (1-12).
     */
    public int quietestMonth() {
        int[] monthlyAccesses = new int[12];
        Arrays.fill(monthlyAccesses, 0); 

        for (int[] dayCounts : dailyCounts) {
            for (int hourCount : dayCounts) {
                monthlyAccesses[hourCount]++;
            }
        }

        int minAccesses = Integer.MAX_VALUE;
        int quietestMonth = 0;

        for (int month = 0; month < monthlyAccesses.length; month++) {
            if (monthlyAccesses[month] < minAccesses) {
                minAccesses = monthlyAccesses[month];
                quietestMonth = month + 1; 
            }
        }

        return quietestMonth;
    }
    
    /** 
     * Examine the log file's daily access data.
     */
    private void analyzeDailyData() {
        while (reader.hasNext()) {
            LogEntry entry = reader.next();
            int day = entry.getDay() - 1; 
            int hour = entry.getHour();
            dailyCounts[day][hour]++;
        }
    }
    
    /** 
     * Calculates the monthly average of accesses using the log data.
     * @return The average accesses per month.
     */
    public double averageAccessesPerMonth() {
        int totalAccesses = 0;

        for (int[] dayCounts : dailyCounts) {
            for (int hourCount : dayCounts) {
                totalAccesses += hourCount;
            }
        }

        return (double) totalAccesses / 12; // Divide by the number of months
    }
    
    /** 
     * Using the log data, determine the busiest month.
     * @return The busiest month (1-12).
     */
    public int busiestMonth() {
        int[] monthlyAccesses = new int[12];
        Arrays.fill(monthlyAccesses, 0); // Initialize array with zeros

        for (int[] dayCounts : dailyCounts) {
            for (int hourCount : dayCounts) {
                monthlyAccesses[hourCount]++;
            }
        }

        int maxAccesses = Integer.MIN_VALUE;
        int busiestMonth = 0;

        for (int month = 0; month < monthlyAccesses.length; month++) {
            if (monthlyAccesses[month] > maxAccesses) {
                maxAccesses = monthlyAccesses[month];
                busiestMonth = month + 1; // Adjust to 1-based index
            }
        }

        return busiestMonth;
    }
    
    /** 
     * Determine the quietest day by using the log data.
     * @return The quietest day (1-31).
     */
    public int quietestDay() {
        int minTotalAccesses = Integer.MAX_VALUE;
        int quietestDay = 0;

        for (int day = 0; day < dailyCounts.length; day++) {
            int totalAccesses = 0;
            for (int hour = 0; hour < dailyCounts[day].length; hour++) {
                totalAccesses += dailyCounts[day][hour];
            }

            if (totalAccesses < minTotalAccesses) {
                minTotalAccesses = totalAccesses;
                quietestDay = day + 1; 
            }
        }
        return quietestDay;
    }

    /** 
     * Using the log data, finds the total number of accesses per month.
     * @return An array containing the total accesses for each month.
     */
    public int[] totalAccessesPerMonth() {
        int[] totalAccesses = new int[12]; 

        for (int[] dayCounts : dailyCounts) {
            for (int hourCount : dayCounts) {
                totalAccesses[hourCount]++;
            }
        }

        return totalAccesses;
    }
    
    /** 
     * Determine which day is the busiest by using the log data.
     * 
     */
    public int busiestDay() {
        int maxTotalAccesses = Integer.MIN_VALUE;
        int busiestDay = 0;

        for (int day = 0; day < dailyCounts.length; day++) {
            int totalAccesses = 0;
            for (int hour = 0; hour < dailyCounts[day].length; hour++) {
                totalAccesses += dailyCounts[day][hour];
            }

            if (totalAccesses > maxTotalAccesses) {
                maxTotalAccesses = totalAccesses;
                busiestDay = day + 1; 
            }
        }
        return busiestDay;
    }
    
    /**
     * Retrieve the total number of logged accesses.
     */
    public int numberOfAccesses() {
        int totalAccesses = 0;
        while (reader.hasNext()) {
            reader.next(); 
            totalAccesses++;
        }
        return totalAccesses;
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hour: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }

    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }

    /** 
     * Get the busiest hour based on the log data.
     * 
     */
    public int busiestHour() {
        int maxCount = 0;
        int busiestHour = 0;
        for (int hour = 0; hour < hourCounts.length; hour++) {
            if (hourCounts[hour] > maxCount) {
                maxCount = hourCounts[hour];
                busiestHour = hour;
            }
        }
        return busiestHour;
    }

    /**
     * Get the quietest hour based on the log data.
     * 
     */
    public int quietestHour() {
        int minCount = Integer.MAX_VALUE;
        int quietestHour = 0;
        for (int hour = 0; hour < hourCounts.length; hour++) {
            if (hourCounts[hour] < minCount) {
                minCount = hourCounts[hour];
                quietestHour = hour;
            }
        }
        return quietestHour;
    }

    /**
     * Get the busiest two-hour period based on the log data.
     * @return The starting hour of the busiest two-hour period.
     */
    public int busiestTwoHour() {
        int maxTotalAccesses = Integer.MIN_VALUE;
        int busiestStartHour = 0;

        for (int startHour = 0; startHour < 24; startHour++) {
            int endHour = (startHour + 1) % 24; // Get the ending hour of the two-hour period
            int totalAccesses = hourCounts[startHour] + hourCounts[endHour];

            if (totalAccesses > maxTotalAccesses) {
                maxTotalAccesses = totalAccesses;
                busiestStartHour = startHour;
            }
        }
        return busiestStartHour;
    }
    
}