
import java.sql.*;
import java.util.Scanner;

import com.sun.net.httpserver.Authenticator.Result;

public class JDBC {
  static String USER = "username";
  static String PASS = "password";
  static String DBNAME = "JDBCP";
  private static Scanner in = new Scanner(System.in);
   static final String publisherFormat="|%-25s|%-25s|%-25s|%-32s\n"; 
  static final String displayFormat="%-5s%-15s%-15s%-15s\n";
  static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
  static String DB_URL = "jdbc:derby://localhost:1527/";
  
    public static String dispNull(String input) {
      if (input == null || input.length() == 0)
        return "N/A";
      else
        return input;
    }

    public static void main(String[] args) {
      DB_URL = DB_URL + DBNAME + ";user=" + USER + ";password=" + PASS;
      Connection c = null;
      Statement stmt = null;
      try {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        System.out.println("Connecting to database...");
        c = DriverManager.getConnection(DB_URL);

        CaseMenu(c);
      } catch (SQLException se) {
        se.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          if (stmt != null) {
            stmt.close();
          }
        } catch (SQLException se2) {
        }
        try {
          if (c != null) {
            c.close();
          }
        } catch (SQLException se) {
          se.printStackTrace();
        }
      }
    }

    public static void menu() {
      System.out.println("Please Select One of the Following ^̮^");
      System.out.println("1) List all Writing Groups");
      System.out.println("2) List Information of Specific Writing Group");
      System.out.println("3) List all Publishers");
      System.out.println("4) List Information of Specific Publishers");
      System.out.println("5) List all Book Titles");
      System.out.println("6) List Information of a Specific Book");
      System.out.println("7) Insert a New Book");
      System.out.println("8) Remove a Book");
      System.out.println("9) Replace a Publisher");
      System.out.println("10) Exit the Program\n");
    }

    public static void CaseMenu(Connection c) throws SQLException {
        int userChoice = 1;
        while (userChoice>0 && userChoice<=10){
          menu();
          System.out.print("Enter menu option : ");
          userChoice = in.nextInt();
          while (userChoice<=0 || userChoice>10){
              menu();
              System.err.println("You selected an invalid option.");
              System.err.print("Enter menu option(1-10) : ");
              userChoice = in.nextInt();
        }
          switch (userChoice) {
          case 1:
            WritingGroupsP(c);
            System.out.println();
            break;
          case 2:
            WritingGroupsFullData(c);
            System.out.println();
            break;
          case 3:
            PublishersP(c);
            System.out.println();
            break;
          case 4:
            PublisherFullData(c);
            System.out.println();
            break;
          case 5:
            BooksP(c);
            System.out.println();
            break;
          case 6:
            bookFullData(c);
            System.out.println();
            break;
          case 7:
            insertBookp(c);
            System.out.println();
            break;
          case 8:
            removeBookp(c);
            System.out.println();
            break;
          case 9:
            replacePublisher(c);
            System.out.println();
            break;
          case 10:
            return;
          default:
            System.out.println("Invalid Value");
            break;
          }
        }
      }

    public static void WritingGroupsP(Connection c) {
      try {
        Statement stmt = c.createStatement();
        String sql = "SELECT * FROM WritingGroup";
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println();
        String format = "|%1$-25s|%2$-25s|%3$-20s|%4$-17s\n";

        System.out.println();
        System.out.println("Writing Groups");
        System.out.println("--------------");
        System.out.format(format, "Group Name", "Head Writer", "Year Formed", "Subject\n");

        while (rs.next()) {
          String gname = rs.getString("Group_Name");
          String headwriter = rs.getString("Head_Writer");
          int year = rs.getInt("Year_Formed");
          String subject = rs.getString("SUBJECT");

          // System.out.println(dispNull(gname)+"\t"+dispNull(headwriter)+"\t"+dispNull(Integer.toString(year))+"\t"+dispNull(subject));
          System.out.format(format, dispNull(gname), dispNull(headwriter), dispNull(Integer.toString(year)),
              dispNull(subject));
        }
        System.out.println();
        rs.close();
        stmt.close();
      } catch (SQLException exe) {
        exe.printStackTrace();
      } catch (Exception err) {
        err.printStackTrace();
      }
    }

    public static void WritingGroupsFullData(Connection c) throws SQLException {
      PreparedStatement preStmt = null;
      ResultSet rs = null;
      String dne = "Writing Group Does Not Exist";
      String sql, groupName, headWriter, subject, pubName, bookTitle, pubAddr, pubPhone, pubEmail;
      int yearFormed, yearPublish, pageNo;
      String format = "|%1$-24s|%2$-22s|%3$-15s|%4$-20s|%5$-20s|%6$-25s|%7$-15s|%8$-20s|%9$-24s|%10$-15s|%11$-20s\n";
      System.out.print("Please Enter a Writing Group Name: ");
      in.nextLine();
      groupName = in.nextLine();
      sql = "SELECT * FROM WritingGroup INNER JOIN (BOOKS NATURAL JOIN PUBLISHERS) USING(Group_Name) WHERE Group_Name = ?";

      System.out.println("Specific Writing Groups");
      System.out.println("-----------------------");
      try {
        preStmt = c.prepareStatement(sql);
        preStmt.setString(1, groupName);
        rs = preStmt.executeQuery();
        System.out.format(format, "Group_Name", "Head_Writer", "Year Formed", "SUBJECT", "Publisher_Name", "Book_Title",
            "Year_Published", "Number_Pages", "Address", "Pub_Phone", "Pub_Email\n");

        while (rs.next()) {
          groupName = rs.getString("Group_Name");
          headWriter = rs.getString("Head_Writer");
          yearFormed = rs.getInt("Year_Formed");
          subject = rs.getString("SUBJECT");
          pubName = rs.getString("Publisher_Name");
          bookTitle = rs.getString("Book_Title");
          yearPublish = rs.getInt("Year_Published");
          pageNo = rs.getInt("Number_Pages");
          pubAddr = rs.getString("Publisher_Address");
          pubPhone = rs.getString("Publisher_Phone");
          pubEmail = rs.getString("Publisher_Email");
          System.out.format(format, dispNull(groupName), dispNull(headWriter), dispNull(Integer.toString(yearFormed)),
              dispNull(subject), dispNull(pubName), dispNull(bookTitle), dispNull(Integer.toString(yearPublish)),
              dispNull(Integer.toString(pageNo)), dispNull(pubAddr), dispNull(pubPhone), dispNull(pubEmail));
        }
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      } finally {
      }
      if (preStmt != null) {
        preStmt.close();
      }
    }

    public static void PublishersP(Connection c) {
      try {
        Statement stmt = c.createStatement();
        String sql = "SELECT * FROM Publishers";
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println();
        System.out.println("Publishers");
        System.out.println("----------");
        System.out.printf(publisherFormat, "Publisher_Name", "Publisher_Address", "Publisher_Phone", "Publisher_Email\n");
        while (rs.next()) {
          String pname = rs.getString("Publisher_Name");
          String paddress = rs.getString("Publisher_Address");
          String pphone = rs.getString("Publisher_Phone");
          String pemail = rs.getString("Publisher_Email");

          System.out.printf(publisherFormat, dispNull(pname), dispNull(paddress), dispNull(pphone), dispNull(pemail));
        }
        System.out.println();
        rs.close();
        stmt.close();
      } catch (SQLException exe) {
        exe.printStackTrace();
      } catch (Exception err) {
        err.printStackTrace();
      }
    }

    public static void PublisherFullData(Connection c) throws SQLException {
      PreparedStatement preStmt = null;
      ResultSet rs = null;
      String sql, groupName, headWriter, subject, pubName, bookTitle, pubAddr, pubPhone, pubEmail;
      int yearFormed, yearPublish, pageNo;
      System.out.print("Please Enter a Publisher's Name: ");
      in.nextLine();
      pubName = in.nextLine();
      sql = "SELECT * FROM Publishers INNER JOIN (Books NATURAL JOIN WritingGroup) using (Publisher_Name) WHERE Publisher_Name = ?";
      String format = "|%1$-25s|%2$-20s|%3$-20s|%4$-26s|%5$-25s|%6$-35s|%7$-35s|%8$-20s|%9$-15s|%10$-15s|%11$-25s\n";

      System.out.println();
      System.out.println("Specific Publishers");
      System.out.println("-------------------");
      try {
        preStmt = c.prepareStatement(sql);
        preStmt.setString(1, pubName);
        rs = preStmt.executeQuery();
        System.out.format(format, "Publisher_Name", "Publisher_Address", "Publisher_Phone", "Email", "Group_Name",
            "Head Writer", "Book Title", "Year Published", "Page No.", "Year Formed", "Subject\n");

        while (rs.next()) {
          groupName = rs.getString("Group_Name");
          headWriter = rs.getString("Head_Writer");
          yearFormed = rs.getInt("Year_Formed");
          subject = rs.getString("SUBJECT");
          pubName = rs.getString("Publisher_Name");
          bookTitle = rs.getString("Book_Title");
          yearPublish = rs.getInt("Year_Published");
          pageNo = rs.getInt("Number_Pages");
          pubAddr = rs.getString("Publisher_Address");
          pubPhone = rs.getString("Publisher_Phone");
          pubEmail = rs.getString("Publisher_Email");

          System.out.format(format, dispNull(pubName), dispNull(pubAddr), dispNull(pubPhone), dispNull(pubEmail),
              dispNull(groupName), dispNull(headWriter), dispNull(bookTitle), dispNull(Integer.toString(yearPublish)),
              dispNull(Integer.toString(pageNo)), dispNull(Integer.toString(yearFormed)), dispNull(subject));
        }
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      } finally {
      }
      if (preStmt != null) {
        preStmt.close();
      }
    }

    public static String insertPublisher(Connection c) throws SQLException {
      String publisherName, publisherAddress, publisherPhone, publisherEmail;
      System.out.println("\nPublisher To Be Added");
      System.out.println("---------------------");
      System.out.print("Enter the Publisher(s) name(s)  : ");
      publisherName = in.nextLine();
      System.out.println();
      System.out.print("Enter Publisher(s) address : ");
      publisherAddress = in.nextLine();
      System.out.println();
      System.out.print("Enter the Publisher(s) Phone Number : ");
      String tempPhone = in.nextLine();
      publisherPhone = String.valueOf(tempPhone).replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3");
      System.out.println();
      System.out.print("Enter Publisher(s)' Email (ex. name@email.com) : ");
      publisherEmail = in.nextLine();

      String query = "INSERT INTO Publishers (" + " PUBLISHER_NAME," + " PUBLISHER_ADDRESS," + " PUBLISHER_PHONE,"
          + " PUBLISHER_EMAIL) " + "VALUES (" + "?, ?, ?, ?)";
      try {
        PreparedStatement ps = c.prepareStatement(query);
        // putting value for all placeholder (?)
        ps.setString(1, publisherName);
        ps.setString(2, publisherAddress);
        ps.setString(3, publisherPhone);
        ps.setString(4, publisherEmail);

        ps.executeUpdate();
        ps.close();

      } catch (SQLException se) {
        se.printStackTrace();
      }
      System.out.println("\n\n");
      System.out.println(publisherName);
      return publisherName;
    }

    public static void BooksP(Connection c) throws SQLException {
      try {
        Statement stmt = c.createStatement();
        String sql = "SELECT * FROM Books";
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println();
        String format = "|%1$-40s|%2$-27s|%3$-25s|%4$-20s|%5$-13s\n";

        System.out.println();
        System.out.println("Books");
        System.out.println("-----");
        System.out.format(format, "Book_Title", "Group_Name", "Publisher_Name", "Year_Published", "Number_Pages\n");
        while (rs.next()) {
          String gname = rs.getString("Group_Name");
          String bTitle = rs.getString("Book_Title");
          String pname = rs.getString("Publisher_Name");
          int year = rs.getInt("Year_Published");
          int npages = rs.getInt("Number_Pages");

          System.out.format(format, dispNull(bTitle), dispNull(gname), dispNull(pname), dispNull(Integer.toString(year)),
              dispNull(Integer.toString(npages)));
        }
        System.out.println();
        rs.close();
        stmt.close();
      } catch (SQLException exe) {
        exe.printStackTrace();
      } catch (Exception err) {
        err.printStackTrace();
      }
    }

    public static void bookFullData(Connection c) throws SQLException {
      PreparedStatement Prepstmt = null;
      ResultSet rs = null;
      String sql, groupName, headWriter, subject, pubName, bookTitle, pubAddr, pubPhone, pubEmail;
      int yearFormed, yearPublish, pageNo;
      System.out.println();
      System.out.println("Specified Book");
      System.out.println("--------------");
      System.out.print("Please Select a Book to display: ");
      in.nextLine();
      bookTitle = in.nextLine();
      sql = "SELECT * FROM Books NATURAL JOIN WritingGroup INNER JOIN Publishers ON Publishers.Publisher_Name = Books.Publisher_Name WHERE Book_Title = ?";

      try {
        Prepstmt = c.prepareStatement(sql);
        Prepstmt.setString(1, bookTitle);
        rs = Prepstmt.executeQuery();
        String format = "|%1$-24s|%2$-33s|%3$-20s|%4$-17s|%5$-17s|%6$-25s|%7$-16s|%8$-25s|%9$-20s|%10$-20s|%11$-20s\n";
        System.out.println("Publishers");
        System.out.format(format, "Group_Name", "Book_Title", "Publisher_Name", "Year_Published", "Number_Pages",
            "Publisher_Address", "Publisher_Phone", "Publisher_Email", "Head_Writer", "Year_Formed", "SUBJECT\n");

        while (rs.next()) {
          groupName = rs.getString("Group_Name");
          bookTitle = rs.getString("Book_Title");
          pubName = rs.getString("Publisher_Name");
          yearPublish = rs.getInt("Year_Published");
          pageNo = rs.getInt("Number_Pages");
          pubAddr = rs.getString("Publisher_Address");
          pubPhone = rs.getString("Publisher_Phone");
          pubEmail = rs.getString("Publisher_Email");
          headWriter = rs.getString("Head_Writer");
          yearFormed = rs.getInt("Year_Formed");
          subject = rs.getString("SUBJECT");

          System.out.format(format, dispNull(groupName), dispNull(bookTitle), dispNull(pubName),
              dispNull(Integer.toString(yearPublish)), dispNull(Integer.toString(pageNo)), dispNull(pubAddr),
              dispNull(pubPhone), dispNull(pubEmail), dispNull(headWriter), dispNull(Integer.toString(yearFormed)),
              dispNull(subject));
        }
      } catch (SQLException exe) {
        System.out.println(exe.getMessage());
      } finally {
      }
      if (Prepstmt != null) {
        Prepstmt.close();
      }
    }

    public static void insertBookp(Connection conn) {
      int numberPages, yearPublished;
      String groupName, bookTitle, publisherName;
      System.out.println();
      System.out.print("Enter the Group Name : ");
      in.nextLine();
      groupName = in.nextLine();
      System.out.println();
      System.out.print("Enter the book title : ");
      bookTitle = in.nextLine();
      System.out.println();
      System.out.print("Enter the name of the Publisher : ");
      publisherName = in.nextLine();
      System.out.println();
      System.out.print("Enter the year is was published : ");
      yearPublished = in.nextInt();

      System.out.println();
      System.out.print("Enter the number of pages : ");
      numberPages = in.nextInt();
      System.out.println();

      String query = "INSERT INTO Books (Group_Name,Book_Title,Publisher_Name,Year_Published,Number_Pages) VALUES (?, ?, ?, ?, ?)";
      try {
        PreparedStatement ps = conn.prepareStatement(query);
        // putting value for all placeholder (?)
        ps.setString(1, groupName);
        ps.setString(2, bookTitle);
        ps.setString(3, publisherName);
        ps.setInt(4, yearPublished);
        ps.setInt(5, numberPages);
        System.out.println("Inserting Book...");
        ps.execute();
        ps.close();
        System.out.println("Book Inserted...");
      } catch (SQLException se) {
        se.printStackTrace();
      }
    }

    public static void removeBookp(Connection c) throws SQLException {
      String sql, fiSql, remove, bookTitle;
      Statement stmt = null;
      PreparedStatement stmtFi = null;
      boolean bexists = false;

      System.out.println("-----BOOK REMOVAL-----");
      System.out.print("What is the Book's Title?: ");
      in.nextLine();
      bookTitle = in.nextLine();
      try {
        stmt = c.createStatement();
        sql = "SELECT Book_Title FROM Books";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
          remove = rs.getString("Book_Title");
          if (remove.equals(bookTitle)) {
            bexists = true;
          }
        }
        if (bexists == false) {
          System.out.println("Book Does Not Exist!!\n");
        } else {
          System.out.println("Book Exists Configuring Solution...\n");

          fiSql = "DELETE FROM Books WHERE Book_Title = ?";
          stmtFi = c.prepareStatement(fiSql);
          stmtFi.setString(1, bookTitle);
          stmtFi.execute();
          System.out.println(bookTitle + " has been Removed from the table\n\n");
        }
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      } finally {
        if (stmt != null) {
          stmt.close();
        }
        if (stmtFi != null) {
          stmtFi.close();
        }
      }
    }

    public static void replacePublisher(Connection c) throws SQLException {
      String newPubName = insertPublisher(c);
      if (newPubName == null)
        return;

      PreparedStatement statemt = null;
      System.out.print("Please Enter the Publisher You'd Like to update:");
      String pubName = in.nextLine();
      String sql = "UPDATE Books SET Publisher_Name = ? WHERE Publisher_Name = ?";

      try {
        statemt = c.prepareStatement(sql);
        statemt.setString(1, newPubName);
        statemt.setString(2, pubName);
        statemt.execute();
        System.out.println("Processing Update...");
      } catch (SQLException exe) {
        System.out.println(exe.getMessage());
      } finally {
      }
      if (statemt != null) {
        statemt.close();
      }
    }
}
