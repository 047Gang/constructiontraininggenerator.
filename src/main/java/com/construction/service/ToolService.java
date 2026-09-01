package com.construction.service;

import com.construction.model.Tool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToolService {

    private final List<Tool> tools = new ArrayList<>();

    public ToolService() {
        // Норвежский / Английский варианты
        tools.add(new Tool("1", "Vinkelsliper / Angle Grinder", "Elektrisk verktøy / Power Tools", 
            "Bruk alltid vernebriller og hørselvern. Sjekk at slipeskiven ikke har sprekker før bruk.\nAlways wear safety glasses and hearing protection. Inspect the grinding disc before use."));
        
        tools.add(new Tool("2", "Sirkelsag / Circular Saw", "Elektrisk verktøy / Power Tools", 
            "Hold begge hender på håndtakene. Pass på at bladbeskyttelsen fungerer som den skal.\nKeep both hands on handles. Ensure the blade guard operates freely."));

        tools.add(new Tool("3", "Borehammer / Rotary Hammer", "Elektrisk verktøy / Power Tools", 
            "Bruk støvmaske og hørselvern. Sjekk kabler før tilkobling.\nUse dust mask and ear protection. Check power cords before plugging in."));

        tools.add(new Tool("4", "Motorsag / Chainsaw", "Skog og hage / Outdoor Tools", 
            "Påkrevd verneutstyr: Sagbukse, hjelm med visir og vernestøvler.\nRequired PPE: Chainsaw trousers, helmet with visor, and safety boots."));

        tools.add(new Tool("5", "Spikerpistol / Nail Gun", "Trykkluftverktøy / Pneumatic Tools", 
            "Koble fra trykkluft ved fastkjøring. Pek aldri pistolen mot noen.\nDisconnect air supply when clearing jams. Never point the tool at anyone."));

        tools.add(new Tool("6", "Slagdrill / Impact Driver", "Batteriverktøy / Cordless Tools", 
            "Sikre arbeidsstykket. Bruk riktig bits for å unngå slitasje.\nSecure workpiece. Use correct bits to prevent slipping."));

        tools.add(new Tool("7", "Gjæringssag / Miter Saw", "Elektrisk verktøy / Power Tools", 
            "Hold hendene unna sagbladets bane. Vent til bladet stopper før du løfter hodet.\nKeep hands away from blade path. Wait for blade to stop before lifting."));
        
        // Добавьте остальные инструменты аналогичным образом...
    }

    public List<Tool> getAllTools() {
        return tools;
    }

    public Tool getToolById(String id) {
        return tools.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
    }
}