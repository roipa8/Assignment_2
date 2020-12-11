package bgu.spl.mics.application;
import bgu.spl.mics.Message;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


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
			C3POMicroservice c3POMicroservice=new C3POMicroservice();
			Ewoks ewoks=Ewoks.getInstance();
			ewoks.initialize(json.getEwoks());
//			System.out.println(json.getAttacks()[0].getSerials().get(0));
//			AttackEvent event = new AttackEvent(1000,json.getAttacks()[0].getSerials());
//			Ewok[] ewok=ewoks.getEwoksArr();
//			for(int i=0; i<event.getSerials().size(); i++){
//                while (!ewoks1[event.getSerials().get(i)]){
//
//                }

//			}
////			System.out.println(json);
//			Ewoks ewoks=new Ewoks(json.getEwoks());
//			Diary diary=new Diary();
//			C3POMicroservice m=new C3POMicroservice();
//			System.out.println(m.getName());
//			System.out.println(json.getEwoks());
			Runnable leia=new LeiaMicroservice(json.getAttacks());
			Thread tLeia=new Thread(leia);
			Runnable hanSolo=new HanSoloMicroservice();
			Thread tHanSolo=new Thread(hanSolo);
			Runnable c3po=new C3POMicroservice();
			Thread tC3po=new Thread(c3po);
			Runnable r2d2=new R2D2Microservice(json.getR2D2());
			Thread tR2d2=new Thread(r2d2);
			Runnable lando=new LandoMicroservice(json.getLando());
			Thread tLando=new Thread(lando);
			tLeia.start();
//			tHanSolo.start();
			tC3po.start();
//			tR2d2.start();
//			tLando.start();
		}
		catch (Exception e){}
		Gson gson= new GsonBuilder().setPrettyPrinting().create();
	}
}
