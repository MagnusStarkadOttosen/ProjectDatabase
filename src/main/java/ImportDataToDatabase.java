import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.logging.SimpleFormatter;

public class ImportDataToDatabase {

    FootagesAndReportersLoader loader = new FootagesAndReportersLoader();
    List<FootageAndReporter> footagesAndReporters;

    String host = "localhost";
    String port = "3306";
    String database = "tv3network";
    String cp = "utf8";

    String username = "root";
    String password = "root";

    String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?characterEncoding=" + cp;

    public ImportDataToDatabase(){
        try {
            footagesAndReporters = loader.loadFootagesAndReporters("uploads.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ImportDataToDatabase importDataToDatabase = new ImportDataToDatabase();

        importDataToDatabase.ImportJournalists();
        importDataToDatabase.ImportFootage();
    }

    public void ImportJournalists(){
        try{
            Connection connection = DriverManager.getConnection(url, username, password);

            for (FootageAndReporter reporter : footagesAndReporters) {
                try {
                    PreparedStatement statement = connection.prepareStatement("insert into journalist values (?, ?, ?, ?, ?, ?, ?, ?)");
                    statement.setString(1, reporter.getReporter().getCPR().toString());
                    statement.setString(2, reporter.getReporter().getFirstName());
                    statement.setString(3, reporter.getReporter().getLastName());
                    statement.setString(4, reporter.getReporter().getStreetName());
                    statement.setString(5, reporter.getReporter().getCivicNumber().toString());
                    statement.setString(6, null);
                    statement.setString(7, reporter.getReporter().getZIPCode().toString());
                    statement.setString(8, reporter.getReporter().getCountry());
                    System.out.println("trying: " + statement);
                    statement.execute();
                } catch (SQLException e) {}
            }
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void ImportFootage(){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        try{
            Connection connection = DriverManager.getConnection(url, username, password);

            for (FootageAndReporter footage : footagesAndReporters) {
                try {
                    PreparedStatement statement = connection.prepareStatement("insert into footage values (?, ?, ?, ?)");
                    statement.setString(1, footage.getFootage().getTitle());
                    statement.setString(2, formatter.format(footage.getFootage().getDate()));
                    statement.setString(3, footage.getFootage().getDuration().toString());
                    statement.setString(4, footage.getReporter().getCPR().toString());
                    System.out.println("trying: " + statement);
                    statement.execute();
                } catch (SQLException e) {}
            }
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
