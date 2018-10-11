import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void displayList(List<String> books) {
        for (String book : books) {
            System.out.println(book);
        }
    }
     
    public static void main(String[] args) {
        DataLoader loader = new DataLoader();
        
        List<String> books = loader.getBooks();
        
        System.out.println("Unsorted");
        System.out.println("==========================");
        displayList(books);
        
        // sort by name
        books.sort((String lhs, String rhs) -> {
            return lhs.compareTo(rhs);
        });
        
        System.out.printf("%n%nSorted forward by name%n");
        System.out.println("==========================");
        displayList(books);
        
        // sort by name, backwards
        books.sort((String lhs, String rhs) -> {
            return rhs.compareTo(lhs);
        });
        
        System.out.printf("%n%nSorted backward by name%n");
        System.out.println("==========================");
        displayList(books);
        
        List<String> booksAndChapters = loader.getBooksAndChapters();
        Map<String, Integer> bookMap = new HashMap<>();
        for (String book : booksAndChapters) {
            String[] parts = book.split(":");
            String name = parts[0];
            int chapters = Integer.parseInt(parts[1]);
            bookMap.put(name, chapters);
        }
        
        //List<String> terms = loader.getTerms();
    }
}