package de.voidplus.leapmotion;

import java.lang.reflect.InvocationTargetException;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Vector;
import com.leapmotion.leap.Controller;

import processing.core.PApplet;
import processing.core.PVector;

public class LeapMotion {

	private final PApplet parent;
	private final PVector world;
	private final Controller controller;
	private final Listener listener;
	
	/**
	 * LeapMotion constructor to initialize the controller and listener.
	 * @param parent Reference (this) of sketch.
	 * @param width World width dimension (in mm).
	 * @param height World height dimension (in mm).
	 * @param depth World depth dimension (in mm).
	 */
	public LeapMotion(PApplet parent, int width, int height, int depth){
		this.parent = parent;
		parent.registerMethod("dispose", this);
		
		this.world = new PVector(width, height, depth);
		
		this.controller = new Controller();
		this.listener = new Listener(){
		    public void onInit(Controller controller) {
		    	dispatch("leapOnInit", controller);
		    }
		    public void onConnect(Controller controller) {
		    	dispatch("leapOnConnect", controller);
		    }
		    public void onDisconnect(Controller controller) {
		    	dispatch("leapOnDisconnect", controller);
		    }
		    public void onExit(Controller controller) {
		    	dispatch("leapOnExit", controller);
		    }
			public void onFrame(Controller controller){
				dispatch("leapOnFrame", controller);
			}
		};
		this.controller.addListener(this.listener);
	}
	
	/**
	 * @param parent Reference (this) of sketch.
	 */
	public LeapMotion(PApplet parent){
		this(parent, 200, 500, 200);
	}
	
	/**
	 * Map the Vector data to sketch PVector data.
	 * @param position
	 * @return
	 */
	public PVector map(Vector position){
		PVector response = new PVector();
		
		// width
		if (position.getX() > 0) {
			response.x = PApplet.lerp(
				(this.parent.width * 0.5f),
				this.parent.width,
				(position.getX() / this.world.x)
			);
		} else {
			response.x = PApplet.lerp(
				(this.parent.width * 0.5f),
				0.0f,
				(-position.getX() / this.world.x)
			);
		}
		
		// height
		response.y = PApplet.lerp( // height
			this.parent.height,
			0.0f,
			(position.getY() / world.y)
		);
		
		// depth
		response.z = PApplet.lerp( // depth
			50.0f,
			0.0f,
			(position.getZ() / world.z)
		);
		
		return response;
	}
	
	/**
	 * Method to route the callbacks.
	 * @param method
	 * @param controller
	 */
	private void dispatch(final String method, final Controller controller) {	
		try {
			this.parent.getClass().getMethod(
				method,
				Controller.class
			).invoke(
				this.parent,
				controller
			);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
		} catch (SecurityException e) {
			// e.printStackTrace();
		} catch (IllegalAccessException e) {
			// e.printStackTrace();
		} catch (InvocationTargetException e) {
			// e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// e.printStackTrace();
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}
	
	/**
	 * Remove the listener.
	 */
	public void dispose(){
		this.controller.removeListener(this.listener);
	}
	
}