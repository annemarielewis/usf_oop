//Note: Use hashmaps when I need a quick search of something, accessing information via a key for very fast
// lookup!

//importing map + hashmap classes from java's utility collection:
import java.util.HashMap;
import java.util.Map;

public class HashMapCourses {

    public static void main(String[] args) {

        // creating an empty hashmap:
        HashMap<String, String> springCourses = new HashMap<>();
            //^<string key, string value^>

// Add 2 courses + print
        springCourses.put("896498", "CS 521: The Art of the Universe, how to tackle All");
        springCourses.put("387009", "CS 514: Intro to AI");
        System.out.println();

        System.out.println("Added two courses ");
        System.out.println(springCourses);
        System.out.println();

        // 2. Get size() of hashmap + print
        System.out.println("Size: " + springCourses.size());
        System.out.println();

        // 3. Remove 1 course, print
        springCourses.remove("896498");

        System.out.println("After removing a course: ");
        System.out.println(springCourses);
        System.out.println("Size: " + springCourses.size());
        System.out.println();

        // 5. Search by key using get() --> Calling search helper method (see helper method below)
        searchCourse(springCourses, "387009");
        //adding:
        springCourses.put("0283u39i8", "CS 521: How to Train your Dragon... with Programming-Mind-Control");
        searchCourse(springCourses, "896498");
        System.out.println();

        // 4. Iterate using entrySet() through hash-map
        System.out.println("Iterating through hashmap:");
        //added lots of notes here because was initially confused how enhanced for loop searching applied the same to a hashmap
        for (Map.Entry<String, String> entry : springCourses.entrySet())
        // Each key-value pair is an object called Map.Entry of type //variable holding current key-value pair in loop + //for each thing IN this collection //gives a set of all key-value pairs in the ma // -->“For every key–value pair, call that pair ‘entry’.”
        {
            System.out.println("Course number: " + entry.getKey());
            System.out.println("Name of course: " + entry.getValue());
            System.out.println();
            System.out.println("iteration check for accuracy");
            System.out.println(springCourses);
            System.out.println();
        }


        // 6. Clear the list; print hashmap
        springCourses.clear();
        System.out.println("After clearing the hashmap:");
        System.out.println(springCourses);
        System.out.println();
    }

    //5. re-used too much code, so made into helped function to prevent re-use:
    public static void searchCourse(Map<String, String> map, String key) {
        System.out.println("Searching for course '" + key + "'...");
        String courseFound = map.get(key); //prints value
        if (courseFound != null) {
            System.out.println("Course found: " + courseFound);
        } else {
            System.out.println("Course not found.");
        }
        System.out.println();
    }
}
