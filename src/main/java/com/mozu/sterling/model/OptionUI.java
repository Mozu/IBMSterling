package com.mozu.sterling.model;

/**
 * A record for a select list in the UI.
 * @author bob_hewett
 *
 */
public class OptionUI {
    
    String id;
    String name;
    
    public OptionUI() {
    }
    
    public OptionUI (String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
