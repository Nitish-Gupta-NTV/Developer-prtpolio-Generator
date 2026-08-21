package com.example.developer.Controller;

import com.example.developer.DTO.CustomizeThemeRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.developer.Service.Imlementservices.themeService;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/developer/portfolio")
public class themeContoller {

    private final themeService service;

    @GetMapping("/theme")
    public ResponseEntity<?> getThemes() {
        return service.gettheme();
    }

    @PatchMapping("/theme/getthemeid")
    public ResponseEntity<?> saveTheme(@RequestBody Map<String, Object> request) {
        Object themeIdRaw = request.get("themeId");
        if (themeIdRaw == null) {
            return ResponseEntity.badRequest().body("themeId is required");
        }
        Long themeId = Long.valueOf(themeIdRaw.toString());
        return service.saveTheme(themeId);
    }
    @PostMapping("/theme/customize")
    public ResponseEntity<?> customizeTheme(@RequestBody CustomizeThemeRequestDTO dto) {
        return service.customizeTheme(dto);
    }
}