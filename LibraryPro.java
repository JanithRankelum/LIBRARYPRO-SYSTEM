import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

public class LibraryPro {
    
    // Constants for array sizes
    private static final int MAX_BOOKS = 100;
    private static final int MAX_MEMBERS = 100;
    private static final int MAX_ISSUED_BOOKS = 200;
    
    // Arrays for Books
    private static String[] bookIds = new String[MAX_BOOKS];
    private static String[] bookTitles = new String[MAX_BOOKS];
    private static String[] bookAuthors = new String[MAX_BOOKS];
    private static String[] bookGenres = new String[MAX_BOOKS];
    private static int[] bookQuantities = new int[MAX_BOOKS];
    private static int bookCount = 0;
    
    // Arrays for Members
    private static String[] memberIds = new String[MAX_MEMBERS];
    private static String[] memberNames = new String[MAX_MEMBERS];
    private static String[] memberContacts = new String[MAX_MEMBERS];
    private static String[] memberEmails = new String[MAX_MEMBERS];
    private static int memberCount = 0;
    
    // Arrays for Issued Books
    private static String[] issuedBookIds = new String[MAX_ISSUED_BOOKS];
    private static String[] issuedMemberIds = new String[MAX_ISSUED_BOOKS];
    private static String[] issuedDueDates = new String[MAX_ISSUED_BOOKS];
    private static int issuedCount = 0;
    
    private static Scanner scanner = new Scanner(System.in);
    
    // Predefined admin credentials
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    
    public static void main(String[] args) {
        clearConsole();
        printHeader("WELCOME TO LIBRARYPRO SYSTEM");
        
        // Login system
        if (!login()) {
            System.out.println("Too many failed attempts. Exiting system.");
            return;
        }
        
        boolean running = true;
        while (running) {
            clearConsole();
            printHeader("LIBRARYPRO - MAIN MENU");
            System.out.println("1. Manage Books");
            System.out.println("2. Manage Members");
            System.out.println("3. Issue Books");
            System.out.println("4. Return Books");
            System.out.println("5. View Reports");
            System.out.println("6. Exit System");
            System.out.println("----------------------------------------");
            System.out.print("Enter your choice: ");
            
            int choice = getValidIntInput();
            
            switch (choice) {
                case 1:
                    manageBooksMenu();
                    break;
                case 2:
                    manageMembersMenu();
                    break;
                case 3:
                    issueBook();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    viewReportsMenu();
                    break;
                case 6:
                    System.out.println("\nThank you for using LibraryPro System!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Press Enter to continue...");
                    waitForEnter();
            }
        }
        scanner.close();
    }
    
    // ==================== LOGIN SYSTEM ====================
    
    private static boolean login() {
        int attempts = 0;
        while (attempts < 3) {
            System.out.println("\n=== LIBRARIAN LOGIN ===");
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();
            
            if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                System.out.println("\nLogin Successful! Press Enter to continue...");
                waitForEnter();
                return true;
            } else {
                attempts++;
                System.out.println("Invalid credentials. Attempts left: " + (3 - attempts));
            }
        }
        return false;
    }
    
    // ==================== MANAGE BOOKS MENU ====================
    
    private static void manageBooksMenu() {
        boolean back = false;
        while (!back) {
            clearConsole();
            printHeader("MANAGE BOOKS");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Delete Book");
            System.out.println("4. Search Book");
            System.out.println("5. View All Books");
            System.out.println("6. Back to Main Menu");
            System.out.println("----------------------------------------");
            System.out.print("Enter your choice: ");
            
            int choice = getValidIntInput();
            
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    updateBook();
                    break;
                case 3:
                    deleteBook();
                    break;
                case 4:
                    searchBook();
                    break;
                case 5:
                    viewAllBooks();
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    // ==================== BOOK OPERATIONS ====================
    
    private static void addBook() {
        clearConsole();
        printHeader("ADD NEW BOOK");
        
        if (bookCount >= MAX_BOOKS) {
            System.out.println("Cannot add more books. Maximum limit reached!");
            waitForEnter();
            return;
        }
        
        String bookId;
        while (true) {
            System.out.print("Enter Book ID: ");
            bookId = scanner.nextLine().trim();
            
            if (isBookIdExists(bookId)) {
                System.out.println("Book ID already exists! Please enter a unique ID.");
            } else if (bookId.isEmpty()) {
                System.out.println("Book ID cannot be empty!");
            } else {
                break;
            }
        }
        
        System.out.print("Enter Title: ");
        String title = scanner.nextLine().trim();
        
        System.out.print("Enter Author: ");
        String author = scanner.nextLine().trim();
        
        System.out.print("Enter Genre: ");
        String genre = scanner.nextLine().trim();
        
        int quantity;
        while (true) {
            System.out.print("Enter Quantity: ");
            quantity = getValidIntInput();
            if (quantity > 0) {
                break;
            }
            System.out.println("Quantity must be a positive integer!");
        }
        
        bookIds[bookCount] = bookId;
        bookTitles[bookCount] = title;
        bookAuthors[bookCount] = author;
        bookGenres[bookCount] = genre;
        bookQuantities[bookCount] = quantity;
        bookCount++;
        
        System.out.println("\n✓ Book added successfully!");
        waitForEnter();
    }
    
    private static void updateBook() {
        clearConsole();
        printHeader("UPDATE BOOK");
        
        System.out.print("Enter Book ID to update: ");
        String bookId = scanner.nextLine().trim();
        
        int index = findBookIndex(bookId);
        if (index == -1) {
            System.out.println("Book not found!");
            waitForEnter();
            return;
        }
        
        System.out.println("\nCurrent Book Details:");
        System.out.println("Title: " + bookTitles[index]);
        System.out.println("Author: " + bookAuthors[index]);
        System.out.println("Genre: " + bookGenres[index]);
        System.out.println("Quantity: " + bookQuantities[index]);
        
        System.out.println("\nEnter new details (press Enter to keep current value):");
        
        System.out.print("New Title [" + bookTitles[index] + "]: ");
        String newTitle = scanner.nextLine().trim();
        if (!newTitle.isEmpty()) bookTitles[index] = newTitle;
        
        System.out.print("New Author [" + bookAuthors[index] + "]: ");
        String newAuthor = scanner.nextLine().trim();
        if (!newAuthor.isEmpty()) bookAuthors[index] = newAuthor;
        
        System.out.print("New Genre [" + bookGenres[index] + "]: ");
        String newGenre = scanner.nextLine().trim();
        if (!newGenre.isEmpty()) bookGenres[index] = newGenre;
        
        System.out.print("New Quantity [" + bookQuantities[index] + "]: ");
        String newQuantityStr = scanner.nextLine().trim();
        if (!newQuantityStr.isEmpty()) {
            int newQuantity = Integer.parseInt(newQuantityStr);
            if (newQuantity > 0) {
                bookQuantities[index] = newQuantity;
            } else {
                System.out.println("Invalid quantity! Keeping current value.");
            }
        }
        
        System.out.println("\n✓ Book updated successfully!");
        waitForEnter();
    }
    
    private static void deleteBook() {
        clearConsole();
        printHeader("DELETE BOOK");
        
        System.out.print("Enter Book ID to delete: ");
        String bookId = scanner.nextLine().trim();
        
        int index = findBookIndex(bookId);
        if (index == -1) {
            System.out.println("Book not found!");
            waitForEnter();
            return;
        }
        
        System.out.println("\nBook to delete:");
        System.out.println("ID: " + bookIds[index]);
        System.out.println("Title: " + bookTitles[index]);
        System.out.print("\nAre you sure you want to delete this book? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y")) {
            // Shift all elements left
            for (int i = index; i < bookCount - 1; i++) {
                bookIds[i] = bookIds[i + 1];
                bookTitles[i] = bookTitles[i + 1];
                bookAuthors[i] = bookAuthors[i + 1];
                bookGenres[i] = bookGenres[i + 1];
                bookQuantities[i] = bookQuantities[i + 1];
            }
            bookCount--;
            System.out.println("\n✓ Book deleted successfully!");
        } else {
            System.out.println("\nDeletion cancelled.");
        }
        waitForEnter();
    }
    
    private static void searchBook() {
        clearConsole();
        printHeader("SEARCH BOOK");
        
        System.out.print("Enter Book ID to search: ");
        String bookId = scanner.nextLine().trim();
        
        int index = findBookIndex(bookId);
        if (index == -1) {
            System.out.println("Book not found!");
        } else {
            System.out.println("\n=== BOOK DETAILS ===");
            System.out.println("Book ID: " + bookIds[index]);
            System.out.println("Title: " + bookTitles[index]);
            System.out.println("Author: " + bookAuthors[index]);
            System.out.println("Genre: " + bookGenres[index]);
            System.out.println("Quantity Available: " + bookQuantities[index]);
        }
        waitForEnter();
    }
    
    private static void viewAllBooks() {
        clearConsole();
        printHeader("ALL BOOKS IN CATALOG");
        
        if (bookCount == 0) {
            System.out.println("No books available in the catalog.");
            waitForEnter();
            return;
        }
        
        System.out.println("+----------+--------------------------+--------------------------+----------------+----------+");
        System.out.printf("| %-8s | %-24s | %-24s | %-14s | %-8s |\n", "Book ID", "Title", "Author", "Genre", "Quantity");
        System.out.println("+----------+--------------------------+--------------------------+----------------+----------+");
        
        for (int i = 0; i < bookCount; i++) {
            System.out.printf("| %-8s | %-24s | %-24s | %-14s | %-8d |\n",
                bookIds[i],
                truncateString(bookTitles[i], 24),
                truncateString(bookAuthors[i], 24),
                truncateString(bookGenres[i], 14),
                bookQuantities[i]);
        }
        System.out.println("+----------+--------------------------+--------------------------+----------------+----------+");
        
        waitForEnter();
    }
    
    // ==================== MANAGE MEMBERS MENU ====================
    
    private static void manageMembersMenu() {
        boolean back = false;
        while (!back) {
            clearConsole();
            printHeader("MANAGE MEMBERS");
            System.out.println("1. Add Member");
            System.out.println("2. Update Member");
            System.out.println("3. Delete Member");
            System.out.println("4. Search Member");
            System.out.println("5. View All Members");
            System.out.println("6. Back to Main Menu");
            System.out.println("----------------------------------------");
            System.out.print("Enter your choice: ");
            
            int choice = getValidIntInput();
            
            switch (choice) {
                case 1:
                    addMember();
                    break;
                case 2:
                    updateMember();
                    break;
                case 3:
                    deleteMember();
                    break;
                case 4:
                    searchMember();
                    break;
                case 5:
                    viewAllMembers();
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    // ==================== MEMBER OPERATIONS ====================
    
    private static void addMember() {
        clearConsole();
        printHeader("ADD NEW MEMBER");
        
        if (memberCount >= MAX_MEMBERS) {
            System.out.println("Cannot add more members. Maximum limit reached!");
            waitForEnter();
            return;
        }
        
        String memberId;
        while (true) {
            System.out.print("Enter Member ID: ");
            memberId = scanner.nextLine().trim();
            
            if (isMemberIdExists(memberId)) {
                System.out.println("Member ID already exists! Please enter a unique ID.");
            } else if (memberId.isEmpty()) {
                System.out.println("Member ID cannot be empty!");
            } else {
                break;
            }
        }
        
        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        
        String contact;
        while (true) {
            System.out.print("Enter Contact Number: ");
            contact = scanner.nextLine().trim();
            if (isValidPhoneNumber(contact)) {
                break;
            }
            System.out.println("Invalid phone number! Should be 10 digits.");
        }
        
        String email;
        while (true) {
            System.out.print("Enter Email: ");
            email = scanner.nextLine().trim();
            if (isValidEmail(email)) {
                break;
            }
            System.out.println("Invalid email format! (example: name@domain.com)");
        }
        
        memberIds[memberCount] = memberId;
        memberNames[memberCount] = name;
        memberContacts[memberCount] = contact;
        memberEmails[memberCount] = email;
        memberCount++;
        
        System.out.println("\n✓ Member added successfully!");
        waitForEnter();
    }
    
    private static void updateMember() {
        clearConsole();
        printHeader("UPDATE MEMBER");
        
        System.out.print("Enter Member ID to update: ");
        String memberId = scanner.nextLine().trim();
        
        int index = findMemberIndex(memberId);
        if (index == -1) {
            System.out.println("Member not found!");
            waitForEnter();
            return;
        }
        
        System.out.println("\nCurrent Member Details:");
        System.out.println("Name: " + memberNames[index]);
        System.out.println("Contact: " + memberContacts[index]);
        System.out.println("Email: " + memberEmails[index]);
        
        System.out.println("\nEnter new details (press Enter to keep current value):");
        
        System.out.print("New Name [" + memberNames[index] + "]: ");
        String newName = scanner.nextLine().trim();
        if (!newName.isEmpty()) memberNames[index] = newName;
        
        System.out.print("New Contact Number [" + memberContacts[index] + "]: ");
        String newContact = scanner.nextLine().trim();
        if (!newContact.isEmpty()) {
            if (isValidPhoneNumber(newContact)) {
                memberContacts[index] = newContact;
            } else {
                System.out.println("Invalid phone number! Keeping current value.");
            }
        }
        
        System.out.print("New Email [" + memberEmails[index] + "]: ");
        String newEmail = scanner.nextLine().trim();
        if (!newEmail.isEmpty()) {
            if (isValidEmail(newEmail)) {
                memberEmails[index] = newEmail;
            } else {
                System.out.println("Invalid email format! Keeping current value.");
            }
        }
        
        System.out.println("\n✓ Member updated successfully!");
        waitForEnter();
    }
    
    private static void deleteMember() {
        clearConsole();
        printHeader("DELETE MEMBER");
        
        System.out.print("Enter Member ID to delete: ");
        String memberId = scanner.nextLine().trim();
        
        int index = findMemberIndex(memberId);
        if (index == -1) {
            System.out.println("Member not found!");
            waitForEnter();
            return;
        }
        
        // Check if member has any books issued
        boolean hasIssuedBooks = false;
        for (int i = 0; i < issuedCount; i++) {
            if (issuedMemberIds[i].equals(memberId)) {
                hasIssuedBooks = true;
                break;
            }
        }
        
        if (hasIssuedBooks) {
            System.out.println("Cannot delete member. Member has books that need to be returned first!");
            waitForEnter();
            return;
        }
        
        System.out.println("\nMember to delete:");
        System.out.println("ID: " + memberIds[index]);
        System.out.println("Name: " + memberNames[index]);
        System.out.print("\nAre you sure you want to delete this member? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y")) {
            for (int i = index; i < memberCount - 1; i++) {
                memberIds[i] = memberIds[i + 1];
                memberNames[i] = memberNames[i + 1];
                memberContacts[i] = memberContacts[i + 1];
                memberEmails[i] = memberEmails[i + 1];
            }
            memberCount--;
            System.out.println("\n✓ Member deleted successfully!");
        } else {
            System.out.println("\nDeletion cancelled.");
        }
        waitForEnter();
    }
    
    private static void searchMember() {
        clearConsole();
        printHeader("SEARCH MEMBER");
        
        System.out.print("Enter Member ID to search: ");
        String memberId = scanner.nextLine().trim();
        
        int index = findMemberIndex(memberId);
        if (index == -1) {
            System.out.println("Member not found!");
        } else {
            System.out.println("\n=== MEMBER DETAILS ===");
            System.out.println("Member ID: " + memberIds[index]);
            System.out.println("Name: " + memberNames[index]);
            System.out.println("Contact: " + memberContacts[index]);
            System.out.println("Email: " + memberEmails[index]);
        }
        waitForEnter();
    }
    
    private static void viewAllMembers() {
        clearConsole();
        printHeader("ALL MEMBERS");
        
        if (memberCount == 0) {
            System.out.println("No members registered.");
            waitForEnter();
            return;
        }
        
        System.out.println("+----------+--------------------------+----------------+----------------------------------+");
        System.out.printf("| %-8s | %-24s | %-14s | %-32s |\n", "Member ID", "Name", "Contact", "Email");
        System.out.println("+----------+--------------------------+----------------+----------------------------------+");
        
        for (int i = 0; i < memberCount; i++) {
            System.out.printf("| %-8s | %-24s | %-14s | %-32s |\n",
                memberIds[i],
                truncateString(memberNames[i], 24),
                memberContacts[i],
                truncateString(memberEmails[i], 32));
        }
        System.out.println("+----------+--------------------------+----------------+----------------------------------+");
        
        waitForEnter();
    }
    
    // ==================== ISSUE BOOK ====================
    
    private static void issueBook() {
        clearConsole();
        printHeader("ISSUE BOOK");
        
        if (bookCount == 0) {
            System.out.println("No books available in catalog. Please add books first.");
            waitForEnter();
            return;
        }
        
        if (memberCount == 0) {
            System.out.println("No members registered. Please add members first.");
            waitForEnter();
            return;
        }
        
        String memberId;
        int memberIndex;
        while (true) {
            System.out.print("Enter Member ID: ");
            memberId = scanner.nextLine().trim();
            memberIndex = findMemberIndex(memberId);
            if (memberIndex != -1) {
                break;
            }
            System.out.println("Member not found!");
        }
        
        String bookId;
        int bookIndex;
        while (true) {
            System.out.print("Enter Book ID: ");
            bookId = scanner.nextLine().trim();
            bookIndex = findBookIndex(bookId);
            if (bookIndex == -1) {
                System.out.println("Book not found!");
            } else if (bookQuantities[bookIndex] <= 0) {
                System.out.println("Book is not available for issue (Quantity = 0)!");
            } else {
                break;
            }
        }
        
        System.out.print("Enter Due Date (yyyy-MM-dd): ");
        String dueDate = scanner.nextLine().trim();
        
        // Validate date format
        if (!isValidDate(dueDate)) {
            System.out.println("Invalid date format! Using default: 14 days from today");
            dueDate = getDefaultDueDate();
        }
        
        // Record issued book
        issuedBookIds[issuedCount] = bookId;
        issuedMemberIds[issuedCount] = memberId;
        issuedDueDates[issuedCount] = dueDate;
        issuedCount++;
        
        // Reduce book quantity
        bookQuantities[bookIndex]--;
        
        System.out.println("\n✓ Book issued successfully!");
        System.out.println("Book: " + bookTitles[bookIndex]);
        System.out.println("Member: " + memberNames[memberIndex]);
        System.out.println("Due Date: " + dueDate);
        waitForEnter();
    }
    
    // ==================== RETURN BOOK ====================
    
    private static void returnBook() {
        clearConsole();
        printHeader("RETURN BOOK");
        
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();
        
        int memberIndex = findMemberIndex(memberId);
        if (memberIndex == -1) {
            System.out.println("Member not found!");
            waitForEnter();
            return;
        }
        
        System.out.print("Enter Book ID: ");
        String bookId = scanner.nextLine().trim();
        
        int bookIndex = findBookIndex(bookId);
        if (bookIndex == -1) {
            System.out.println("Book not found!");
            waitForEnter();
            return;
        }
        
        // Find the issued record
        int issueIndex = -1;
        for (int i = 0; i < issuedCount; i++) {
            if (issuedBookIds[i].equals(bookId) && issuedMemberIds[i].equals(memberId)) {
                issueIndex = i;
                break;
            }
        }
        
        if (issueIndex == -1) {
            System.out.println("No record found for this book issued to this member!");
            waitForEnter();
            return;
        }
        
        // Calculate fine if overdue
        String dueDate = issuedDueDates[issueIndex];
        long daysOverdue = calculateDaysOverdue(dueDate);
        
        if (daysOverdue > 0) {
            double fine = daysOverdue * 50; // 50 LKR per day
            System.out.println("\n⚠ Book is OVERDUE by " + daysOverdue + " day(s)!");
            System.out.println("Fine Amount: LKR " + fine);
        } else {
            System.out.println("\n✓ Book returned on time!");
        }
        
        // Remove issued record (shift left)
        for (int i = issueIndex; i < issuedCount - 1; i++) {
            issuedBookIds[i] = issuedBookIds[i + 1];
            issuedMemberIds[i] = issuedMemberIds[i + 1];
            issuedDueDates[i] = issuedDueDates[i + 1];
        }
        issuedCount--;
        
        // Increase book quantity
        bookQuantities[bookIndex]++;
        
        System.out.println("\n✓ Book returned successfully!");
        waitForEnter();
    }
    
    // ==================== VIEW REPORTS MENU ====================
    
    private static void viewReportsMenu() {
        boolean back = false;
        while (!back) {
            clearConsole();
            printHeader("VIEW REPORTS");
            System.out.println("1. Overdue Books");
            System.out.println("2. Books Issued Per Member");
            System.out.println("3. Back to Main Menu");
            System.out.println("----------------------------------------");
            System.out.print("Enter your choice: ");
            
            int choice = getValidIntInput();
            
            switch (choice) {
                case 1:
                    viewOverdueBooks();
                    break;
                case 2:
                    viewBooksIssuedPerMember();
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    private static void viewOverdueBooks() {
        clearConsole();
        printHeader("OVERDUE BOOKS");
        
        boolean hasOverdue = false;
        int overdueCount = 0;
        
        System.out.println("+----------+----------+------------+---------------+----------------+");
        System.out.printf("| %-8s | %-8s | %-10s | %-13s | %-14s |\n", "Book ID", "Member ID", "Due Date", "Days Overdue", "Fine (LKR)");
        System.out.println("+----------+----------+------------+---------------+----------------+");
        
        for (int i = 0; i < issuedCount; i++) {
            long daysOverdue = calculateDaysOverdue(issuedDueDates[i]);
            if (daysOverdue > 0) {
                hasOverdue = true;
                overdueCount++;
                double fine = daysOverdue * 50;
                System.out.printf("| %-8s | %-8s | %-10s | %-13d | %-14.2f |\n",
                    issuedBookIds[i],
                    issuedMemberIds[i],
                    issuedDueDates[i],
                    daysOverdue,
                    fine);
            }
        }
        
        System.out.println("+----------+----------+------------+---------------+----------------+");
        
        if (!hasOverdue) {
            System.out.println("No overdue books found.");
        } else {
            System.out.println("\nTotal Overdue Books: " + overdueCount);
        }
        
        waitForEnter();
    }
    
    private static void viewBooksIssuedPerMember() {
        clearConsole();
        printHeader("BOOKS ISSUED PER MEMBER");
        
        if (memberCount == 0) {
            System.out.println("No members registered.");
            waitForEnter();
            return;
        }
        
        System.out.println("+----------+--------------------------+-------------------+");
        System.out.printf("| %-8s | %-24s | %-17s |\n", "Member ID", "Member Name", "Books Issued");
        System.out.println("+----------+--------------------------+-------------------+");
        
        for (int i = 0; i < memberCount; i++) {
            int issuedCountForMember = 0;
            for (int j = 0; j < issuedCount; j++) {
                if (issuedMemberIds[j].equals(memberIds[i])) {
                    issuedCountForMember++;
                }
            }
            System.out.printf("| %-8s | %-24s | %-17d |\n",
                memberIds[i],
                truncateString(memberNames[i], 24),
                issuedCountForMember);
        }
        
        System.out.println("+----------+--------------------------+-------------------+");
        
        waitForEnter();
    }
    
    // ==================== HELPER METHODS ====================
    
    private static boolean isBookIdExists(String bookId) {
        for (int i = 0; i < bookCount; i++) {
            if (bookIds[i].equals(bookId)) {
                return true;
            }
        }
        return false;
    }
    
    private static int findBookIndex(String bookId) {
        for (int i = 0; i < bookCount; i++) {
            if (bookIds[i].equals(bookId)) {
                return i;
            }
        }
        return -1;
    }
    
    private static boolean isMemberIdExists(String memberId) {
        for (int i = 0; i < memberCount; i++) {
            if (memberIds[i].equals(memberId)) {
                return true;
            }
        }
        return false;
    }
    
    private static int findMemberIndex(String memberId) {
        for (int i = 0; i < memberCount; i++) {
            if (memberIds[i].equals(memberId)) {
                return i;
            }
        }
        return -1;
    }
    
    private static boolean isValidPhoneNumber(String phone) {
        return phone.matches("\\d{10}");
    }
    
    private static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    private static boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
    private static String getDefaultDueDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long currentTime = System.currentTimeMillis();
        long dueTime = currentTime + (14L * 24 * 60 * 60 * 1000); // 14 days later
        return sdf.format(new Date(dueTime));
    }
    
    private static long calculateDaysOverdue(String dueDateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dueDate = sdf.parse(dueDateStr);
            Date currentDate = new Date();
            
            if (currentDate.after(dueDate)) {
                long diffInMillis = currentDate.getTime() - dueDate.getTime();
                return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
            }
        } catch (ParseException e) {
            return 0;
        }
        return 0;
    }
    
    private static String truncateString(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
    
    private static int getValidIntInput() {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                return input;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
    
    private static void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private static void printHeader(String title) {
        System.out.println("========================================");
        System.out.println("        " + title);
        System.out.println("========================================");
    }
    
    private static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            // If clearing fails, just print new lines
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }
}