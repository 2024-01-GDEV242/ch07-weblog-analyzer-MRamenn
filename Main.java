import java.util.Arrays;

/**
 * 
 *
 * @author Matt Romond
 * @version    2024.03.23
 */
public class Main
{
    public static void main(String[] args) {
        /**
         * Create a log file with random entries
         */
        LogfileCreator creator = new LogfileCreator();
        creator.createFile("weblog.txt", 100);
        
        /**
         * Analyze the log file
         */
        LogAnalyzer analyzer = new LogAnalyzer("weblog.txt");
        analyzer.analyzeHourlyData();
        analyzer.printHourlyCounts();

        /**
         * Get and print the total number of accesses
         */
        int totalAccesses = analyzer.numberOfAccesses();
        System.out.println("Total accesses: " + totalAccesses);
        
        /**
         * Get and print the busiest hour
         */
        int busiestHour = analyzer.busiestHour();
        System.out.println("Busiest hour: " + busiestHour);
        
        /**
         * Get and print the quietest hour
         */
        int quietestHour = analyzer.quietestHour();
        System.out.println("Quietest hour: " + quietestHour);
        
        /**
         * Get and print the starting hour of the busiest two-hour period
         */
        int busiestStartHour = analyzer.busiestTwoHour();
        System.out.println("Starting hour of busiest two-hour period: " + busiestStartHour);
        
        /**
         * Get and print the quietest day
         */
        int quietestDay = analyzer.quietestDay();
        System.out.println("Quietest day: " + quietestDay);
        
        /**
         * Get and print the busiest day
         */
        int busiestDay = analyzer.busiestDay();
        System.out.println("Busiest day: " + busiestDay);
        
        /**
         * Get and print the total accesses per month
         */
        int[] totalAccessesPerMonth = analyzer.totalAccessesPerMonth();
        System.out.println("Total accesses per month: " + Arrays.toString(totalAccessesPerMonth));
        
        /**
         * Get and print the quietest month
         */
        int quietestMonth = analyzer.quietestMonth();
        System.out.println("Quietest month: " + quietestMonth);
        
        /**
         * Get and print the busiest month
         */
        int busiestMonth = analyzer.busiestMonth();
        System.out.println("Busiest month: " + busiestMonth);

        /**
         * Get and print the average accesses per month
         */
        double averageAccesses = analyzer.averageAccessesPerMonth();
        System.out.println("Average accesses per month: " + averageAccesses);
    }
}