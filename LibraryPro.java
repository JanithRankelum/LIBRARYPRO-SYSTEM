import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.parseException;
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
	private static int[] memberCount = 0;


	// Arrays for Issued Books
	private static String[] issuedBookIds = new String[MAX_ISSUED_BOOKS];
	private static String[] issuedMembersIds = new String[MAX_ISSUED_BOOKS];
	private static String[] issuedDueDates = new String[MAX_ISSUED_BOOKS];
	private static int issuedCount = 0;


	private static Scanner scanner = new Scanner(System.in);


	// Predefined admin credentials
	private static final String ADMIN_USERNAME = "admin";
	private static final String ADMIN_PASSWORD = "admin123"; 


}