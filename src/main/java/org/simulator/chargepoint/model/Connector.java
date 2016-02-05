package org.simulator.chargepoint.model;

public class Connector {

	private int id;

	private String status;
	
	public Connector() {
         
    }
	

    public Connector(int id, String status) {
        super();
        this.id = id;
        this.status = status;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
	
 
	
}
