package dev.nova.module;

public enum Category {
    COMBAT("Combat"), MOVEMENT("Movement"), RENDER("Render");

    public final String label;
    Category(String label) { this.label = label; }
}
