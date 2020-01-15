package imckechn;
import java.util.List;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MainTest {
	public static void main(String [] args) {
		//Testing begins
		System.out.println("Just a little note, a lot of my methods actually print stuff back because I wanted to simplify other methods. hence why there will be random text in places.");
		System.out.println();



		System.out.println("*** RUNNING CHAMBER TEST\n");

		Result chamberResults = JUnitCore.runClasses(ChamberTest.class);

		System.out.println("Failed Tests: ");
		List <Failure> chamberFailedResults = chamberResults.getFailures();
		chamberFailedResults.forEach(f->{System.out.println(f);});
		System.out.println("Total failed Chamber tests: " + chamberFailedResults.size());
		System.out.println();



		System.out.println("*** RUNNING DOOR TEST\n");

		Result doorTests = JUnitCore.runClasses(DoorTest.class);
		System.out.println("Failed Tests: ");
        List <Failure> failedDoorTests = doorTests.getFailures();
		failedDoorTests.forEach(f->{System.out.println(f);});
		System.out.println("Total failed door tests:" + failedDoorTests.size());
		System.out.println();
		


		System.out.println("*** RUNNING PASSAGE TEST\n");

		Result passageTests = JUnitCore.runClasses(PassageTest.class);
		System.out.println("Failed Tests: ");
		List <Failure> failedPassageTests = passageTests.getFailures();
		System.out.println("Total failed passage tests:" + failedPassageTests.size());
		failedPassageTests.forEach(f->{System.out.println(f);});
		System.out.println();



		System.out.println("*** RUNNING PASSAGE SECTION TEST\n");

		Result passageSectionTests = JUnitCore.runClasses(PassageSectionTest.class);
		List <Failure> failedPassageSectionTests = passageSectionTests.getFailures();
		System.out.println("Failed Tests: ");
		System.out.println("Total failed passage section tests:" + failedPassageSectionTests.size());
		failedPassageSectionTests.forEach(f->{System.out.println(f);});
		System.out.println();

		//Finished testing
	}
}