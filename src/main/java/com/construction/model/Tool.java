package com.construction.model;

public class Tool {
    private String id;
    private String name;
    private String category;
    private String safetyRules;

    public Tool(String id, String name, String category, String safetyRules) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.safetyRules = safetyRules;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getSafetyRules() { return safetyRules; }
}