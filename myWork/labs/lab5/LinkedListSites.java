//linkedlists are best for:
//when you need FAST insertions and deletions anywhere in the list.
//>managing a playlist where songs move around
//LinkedLists are NOT good for:
//        searching
//        indexing
//        random access
//You can enforce stack rules on a LinkedList/function, etc → it becomes a Stack

import java.util.LinkedList;

public class LinkedListSites {

    public static void main(String[] args) {

        LinkedList<String> siteList = new LinkedList<>();

        // 1. Add 3 sites + print

        siteList.add("https://www.ripplehouse.media");
        siteList.add("https://www.cattolystmedia.com");
        siteList.add("https://www.wikipedia.org");

        System.out.println("After adding 3 sites:");
        System.out.println(siteList);
        System.out.println();

        // 2. Remove 1 site + print
        siteList.remove("https://www.cattolystmedia.com");

        System.out.println("After removing 1 site:");
        System.out.println(siteList);
        System.out.println();

        // 3. Add the site back + print
        siteList.add("https://www.cattolystmedia.com");

        System.out.println("After adding the site back:");
        System.out.println(siteList);
        System.out.println();


        // 4. Remove the *first* site + print
        siteList.removeFirst();

        System.out.println("After removing the first site:");
        System.out.println(siteList);
        System.out.println();

        // 5. Check if a specific site is in the list
        String checkSite = "https://www.cattolystmedia.com";
        boolean exists = siteList.contains(checkSite);

        System.out.println("Checking if site is in list: " + checkSite);
        System.out.println("Exists? " + exists);
        System.out.println();

        // 6. Clear the list
        siteList.clear();
        System.out.println("After clearing the list:");
        System.out.println(siteList);
    }
}

////without pre-made java utility class, making from scratch -->
//public class LinkedListSites {
//
//    // ---- Simple Node class for our linked list ----
//    private static class Node {
//        String data;  // the website URL
//        Node next;    // pointer to the next node
//
//        Node(String data) {
//            this.data = data;
//            this.next = null;
//        }
//    }
//    private static class SiteList {
//        private Node head; // start of the list
//
//        // Add a site to the end of the list
//        public void add(String site) {
//            Node newNode = new Node(site);
//
//            if (head == null) {
//                head = newNode;
//                return;
//            }
//
//            Node current = head;
//            while (current.next != null) {
//                current = current.next;
//            }
//            current.next = newNode;
//        }
//
//        // Remove first node whose data equals site
//        public boolean remove(String site) {
//            if (head == null) {
//                return false;
//            }
//
//            // If the head node is the one to remove
//            if (head.data.equals(site)) {
//                head = head.next;
//                return true;
//            }
//
//            // Search through the rest of the list
//            Node current = head;
//            while (current.next != null && !current.next.data.equals(site)) {
//                current = current.next;
//            }
//
//            if (current.next == null) {
//                // Not found
//                return false;
//            }
//
//            // Skip over the node to remove it
//            current.next = current.next.next;
//            return true;
//        }
//
//        // Remove the very first node in the list
//        public void removeFirst() {
//            if (head != null) {
//                head = head.next;
//            }
//        }
//
//
//        public boolean contains(String site) {
//            Node current = head;
//            while (current != null) {
//                if (current.data.equals(site)) {
//                    return true;
//                }
//                current = current.next;
//            }
//            return false;
//        }
//
//
//        public void clear() {
//            head = null;
//        }
//
//
//        @Override
//        public String toString() {
//            if (head == null) {
//                return "[]";
//            }
//
//            StringBuilder sb = new StringBuilder();
//            sb.append("[");
//
//            Node current = head;
//            while (current != null) {
//                sb.append(current.data);
//                if (current.next != null) {
//                    sb.append(", ");
//                }
//                current = current.next;
//            }
//
//            sb.append("]");
//            return sb.toString();
//        }
//    }
//
//
//    public static void main(String[] args) {
//        SiteList siteList = new SiteList();
//
//        // 1. Add 3 sites; print out your LinkedList
//        siteList.add("https://www.google.com");
//        siteList.add("https://www.reddit.com");
//        siteList.add("https://www.youtube.com");
//
//        System.out.println("After adding 3 sites:");
//        System.out.println(siteList);
//        System.out.println();
//
//        System.out.println("Removing https://www.reddit.com ...");
//        siteList.remove("https://www.reddit.com");
//        System.out.println("After removing one site:");
//        System.out.println(siteList);
//        System.out.println();
//
//        System.out.println("Adding https://www.reddit.com back...");
//        siteList.add("https://www.reddit.com");
//        System.out.println("After adding it back:");
//        System.out.println(siteList);
//        System.out.println();
//
//        System.out.println("Removing the first site in the list...");
//        siteList.removeFirst();
//        System.out.println("After removing the first site:");
//        System.out.println(siteList);
//        System.out.println();
//
//         String checkSite = "https://www.youtube.com";
//        System.out.println("Checking if list contains: " + checkSite);
//        if (siteList.contains(checkSite)) {
//            System.out.println("Yes, " + checkSite + " is in the list.");
//        } else {
//            System.out.println("No, " + checkSite + " is NOT in the list.");
//        }
//        System.out.println();
//
//        System.out.println("Clearing the list...");
//        siteList.clear();
//        System.out.println("After clearing:");
//        System.out.println(siteList);
//    }
