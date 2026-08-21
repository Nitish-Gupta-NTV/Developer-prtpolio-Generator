package com.example.developer.DTO;

import lombok.Data;

@Data
public class CustomizeThemeRequestDTO {
    private Long baseThemeId;   // which layout to base it on
    private String fontFamily;  // null = keep base's font
    private String accentColour; // null = keep base's accent
}