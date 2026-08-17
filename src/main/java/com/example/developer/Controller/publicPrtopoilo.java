/*package com.example.developer.Controller;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/developer/portfolio")
public class publicPrtopoilo {
    //private final publicPrtopolio prtopoilo;
    @PostMapping("/see")
    public ResponseEntity<?> publicacessController(String Slug)
    {

        //return prtopoilo.publicacessController(Slug);
        return ResponseEntity.ok("some thinks need to fix");
    }
    @PostMapping("/theme")
    public ResponseEntity<?>onlyfaketest()
    {
        return ResponseEntity.ok("fake request");}


}*/
package com.example.developer.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/developer/portfolio")
public class publicPrtopoilo {

    @GetMapping("/see")
    public ResponseEntity<?> getPortfolio() {
        // TODO: get portfolio for authenticated user
        return ResponseEntity.ok("portfolio data");
    }

    @GetMapping("/theme")
    public ResponseEntity<?> getThemes() {
        // TODO: get themes from database
        return ResponseEntity.ok(List.of(
                Map.of(
                        "id", 1,
                        "layout_type", "minimal_dark"
                ),
                Map.of(
                        "id", 2,
                        "layout_type", "minimal_light"
                )
        ));
    }

    @PatchMapping("/theme")
    public ResponseEntity<?> saveTheme(
            @RequestBody Map<String, Object> request
    ) {
        Object themeId = request.get("themeId");

        if (themeId == null) {
            return ResponseEntity.badRequest()
                    .body("themeId is required");
        }

        // TODO:
        // 1. Get authenticated user's portfolio
        // 2. Find theme by themeId
        // 3. Assign theme to portfolio
        // 4. Save portfolio

        return ResponseEntity.ok(
                Map.of(
                        "message", "Theme saved",
                        "themeId", themeId
                )
        );
    }

    @PatchMapping("/publish")
    public ResponseEntity<?> togglePublish() {

        // TODO:
        // Get authenticated user's portfolio
        // Toggle published
        // Save

        return ResponseEntity.ok(
                Map.of(
                        "message", "Publish status updated"
                )
        );
    }
}
