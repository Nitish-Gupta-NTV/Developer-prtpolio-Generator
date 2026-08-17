package com.example.developer.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@AllArgsConstructor
@Data
public class AnalyticsDTO {
    private Long viewCount;
    private LocalDateTime lastViewedAt;
    private String slug;
}
