package com.example.developer.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class themeDto {
    private Long id;
    private String layout_type;
    private String font_family;
    private String primary_colour;
    private String secondary_colour;
}
