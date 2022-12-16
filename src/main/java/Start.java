import java.sql.*;
import java.util.ArrayList;


public class Start {

    public static void main(String[] args) {

        Connection con = null;

        try{
            String url = "jdbc:mysql://localhost:3306/newdb";
            String user = "root";
            String password = "PinoDaniele03";

            con = DriverManager.getConnection(url, user, password);

            Statement s = con.createStatement();

            String sql = "CREATE TABLE student " +
                    "(student_id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    " last_name VARCHAR(30), " +
                    " first_name VARCHAR(30))";

            ArrayList<String> surnames = new ArrayList<>();

            s.executeUpdate(sql);

            s.executeUpdate("INSERT INTO newdb.student (last_name, first_name) VALUES('De Fenzo', 'Marco');");
            s.executeUpdate("INSERT INTO newdb.student (last_name, first_name) VALUES('Vassarotti', 'Vittoria');");
            s.executeUpdate("INSERT INTO newdb.student (last_name, first_name) VALUES('Di Leo', 'Luca');");
            s.executeUpdate("INSERT INTO newdb.student (last_name, first_name) VALUES('Mustata', 'Alina Elena');");

            ResultSet rS = s.executeQuery("SELECT last_name, first_name FROM newdb.student;");

            while(rS.next()){
                surnames.add(rS.getString("last_name"));
                System.out.println(rS.getString("first_name"));
            }

            System.out.println(surnames);

            String sql1 = "ALTER TABLE student ADD country VARCHAR(30)";
            s.executeUpdate(sql1);

            s.executeUpdate("UPDATE student SET country = 'Italy' where last_name = 'De Fenzo'");
            s.executeUpdate("UPDATE student SET country = 'Italy' where last_name = 'Vassarotti'");
            s.executeUpdate("UPDATE student SET country = 'Germany' where last_name = 'Di Leo'");
            s.executeUpdate("UPDATE student SET country = 'Germany' where last_name = 'Mustata'");

            ArrayList<Student> italianStudents = new ArrayList<>();
            ArrayList<Student> germanStudents = new ArrayList<>();

            String strIta = "CREATE VIEW italian_students AS\n" +
                    "SELECT last_name, first_name\n" +
                    "FROM student\n" +
                    "WHERE Country = \"Italy\";";

            String strGer = "CREATE VIEW german_students AS\n" +
                    "SELECT last_name, first_name\n" +
                    "FROM student\n" +
                    "WHERE Country = \"Germany\";";

            s.executeUpdate(strIta);
            s.executeUpdate(strGer);

            ResultSet rSi = s.executeQuery("SELECT first_name, last_name FROM italian_students");

            while(rSi.next()){
                italianStudents.add(new Student(rSi.getString("first_name"), rSi.getString("last_name")));
            }

            for(Student student : italianStudents){
                student.printStudent();
            }


            ResultSet rSg = s.executeQuery("SELECT first_name, last_name FROM german_students");

            while(rSg.next()){
                germanStudents.add(new Student(rSg.getString("first_name"), rSg.getString("last_name")));
            }

            for(Student student : germanStudents){
                student.printStudent();
            }



        }catch(SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            try{
                if(con != null){
                    con.close();
                }
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}