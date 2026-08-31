package com.construction.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tool {
    private String id;
    private String name;
    private String description;
    private String safetyTips;
}
