import java.util.Date;

public class FootageAndReporter {
	private final Reporter reporter;
	private final Footage footage;

	public FootageAndReporter(String title, Date date, Integer duration, Integer cpr, String firstName, String lastName, String streetName, Integer civicNumber, Integer zipCode, String country) {
		reporter = new Reporter(cpr, firstName, lastName, streetName, civicNumber, zipCode, country);
		footage = new Footage(title, date, duration);
	}

	public Reporter getReporter() {
		return reporter;
	}

	public Footage getFootage() {
		return footage;
	}
}
