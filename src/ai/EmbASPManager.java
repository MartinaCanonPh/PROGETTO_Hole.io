package ai;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.IllegalAnnotationException;
import it.unical.mat.embasp.languages.ObjectNotValidException;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv.desktop.DLVDesktopService;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;
import logic.HoleLogic.Direction;

import java.awt.Rectangle;

import directions.Down;
import directions.Left;
import directions.Right;
import directions.Up;
import graphic.Scene;

public class EmbASPManager {
	
	private String encodingResource="encodings/holeMotion";
	private String choose="encodings/chooseItem";
	private Handler handler;
	private InputProgram encoding = null;
	private InputProgram facts = null;
	private Class[] classes;
	
	private static EmbASPManager istance=null;	//instance reference
	private EmbASPManager() {}	//private constructor
	public static EmbASPManager getIstance() {
		if(istance == null)
			istance = new EmbASPManager();
		return istance;
	}
	
	public EmbASPManager(Class... list) {
		
		handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2-windows-64_6.exe"));
		encoding = new ASPInputProgram();
		facts = new ASPInputProgram();
		this.classes = list;
		setPrograms();
	}		
	
	public void reset()
	{
		facts = new ASPInputProgram();
		encoding = new ASPInputProgram();
	}
	
	private void mapClasses()
	{
		for (Class c : classes)
		{
			try
			{
				ASPMapper.getInstance().registerClass(c);

			} catch (ObjectNotValidException | IllegalAnnotationException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void setFacts(Object input)
	{
		try
		{
			facts.addObjectInput(input);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private Item getChoice() {
    	for(Rectangle r: Scene.items)
    	{
    		setFacts(new Item(r.x,r.y));
    	}
		handler.addProgram(facts);
		encoding.addFilesPath(choose);
		handler.addProgram(encoding);
		
		Output o = handler.startSync();
		AnswerSets answers = (AnswerSets) o;
		
		for(AnswerSet a : answers.getAnswersets())
		{
			try {
				for(Object obj : a.getAtoms())
				{	
					if(obj instanceof Item)
					{
						Item d = (Item) obj;
						System.out.println("SCELTA ITEM: "+d);
						return d;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return null;
	}

	private void setPrograms()
	{	
		try {
			facts.addObjectInput(getChoice());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		handler.addProgram(facts);
		encoding.addFilesPath(encodingResource);
		handler.addProgram(encoding);		
	}
	
	
	public Direction getAnswerSets() {
		
		mapClasses();
		setPrograms();

		Output o = handler.startSync();
		AnswerSets answers = (AnswerSets) o;
		
		System.out.println("num answerset: "+answers.getAnswersets().size());
		System.out.println(answers.getAnswerSetsString());
	
		for(AnswerSet a : answers.getAnswersets())
		{
			try {
				for(Object obj : a.getAtoms())
				{	
					if(obj instanceof Up)
					{
						Up d = (Up) obj;
						System.out.println("SCELTA: "+d);
						return Direction.up;
					}
					else if(obj instanceof Down)
					{
						Down d = (Down) obj;
						System.out.println("SCELTA: "+d);
						return Direction.down;
					}
					else if(obj instanceof Right)
					{
						Right d = (Right) obj;
						System.out.println("SCELTA: "+d);
						return Direction.right;
					}
					else if(obj instanceof Left)
					{
						Left d = (Left) obj;
						System.out.println("SCELTA: "+d);
						return Direction.left;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		return null;
	}

}
