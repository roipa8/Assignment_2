package bgu.spl.mics.application;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.passiveObjects.Input;
import bgu.spl.mics.application.passiveObjects.JsonInputReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



/** This is the Main class of the application. You should parse the input file, 
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) {
//		System.out.println("a");
		try{
			String input="C:/Studies/Semester3/SPL/Assignment2/src/main/java/bgu/spl/mics/application/input.json";
			Input json = JsonInputReader.getInputFromJson(input);
//			System.out.println(json);
			Ewoks ewoks=new Ewoks(json.getEwoks());
			Diary diary=new Diary();
			System.out.println(json.getEwoks());
			for(int i=0; i<json.getEwoks(); i++){
				System.out.println(ewoks.getEwoksArr()[i]);
			}
		}
		catch (Exception e){}
		Gson gson= new GsonBuilder().setPrettyPrinting().create();
	}
}
