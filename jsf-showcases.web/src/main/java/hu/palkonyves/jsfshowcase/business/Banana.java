package hu.palkonyves.jsfshowcase.business;


public class Banana {

    Long id;

    String name;
    Integer yellowness;
    Integer browness;

    public Banana(String name, Integer yellowness, Integer browness) {
	super();
	this.name = name;
	this.yellowness = yellowness;
	this.browness = browness;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Integer getYellowness() {
	return yellowness;
    }

    public void setYellowness(Integer yellowness) {
	this.yellowness = yellowness;
    }

    public Integer getBrowness() {
	return browness;
    }

    public void setBrowness(Integer browness) {
	this.browness = browness;
    }

    public Long getId() {
	return id;
    }
}
