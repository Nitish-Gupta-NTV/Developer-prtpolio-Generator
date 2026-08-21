package com.example.developer.Service.Imlementservices;

import com.example.developer.DTO.CustomizeThemeRequestDTO;
import com.example.developer.DTO.themeDto;
import com.example.developer.Repository.PortfolioRepo;
import com.example.developer.Repository.ThemeRepo;
import com.example.developer.model.Portfolio;
import com.example.developer.model.User;
import com.example.developer.model.theme;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class themeService {

    private final ThemeRepo themeRepo;
    private final PortfolioRepo portfolioRepo;
    private final AuthenticatedUserlogined isuserlogin;

    // GET /theme — list of real themes for the picker
    public ResponseEntity<?> gettheme() {
        List<themeDto> themes = themeRepo.findAll().stream()
                .map(t -> new themeDto(t.getId(), t.getLayout_type(), t.getFont_family(), t.getPrimary_colour(), t.getSecondary_colour()))
                .toList();
        return ResponseEntity.ok(themes);
    }

    // PATCH /theme — actually save it onto the user's portfolio
    public ResponseEntity<?> saveTheme(Long themeId) {
        User user = isuserlogin.userlogined();
        theme selectedTheme = themeRepo.findById(themeId)
                .orElseThrow(() -> new RuntimeException("Theme not found"));


        Portfolio portfolio = portfolioRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Portfolio not found, please create one first"));

        portfolio.setTheme_id(selectedTheme.getId()); // this is the line that was completely missing
        portfolioRepo.save(portfolio);

        return ResponseEntity.ok(Map.of(
                "message", "Theme saved",
                "themeId", selectedTheme.getId(),
                "layout_type", selectedTheme.getLayout_type()
        ));
    }
    public ResponseEntity<?> customizeTheme(CustomizeThemeRequestDTO dto) {
        theme base = themeRepo.findById(dto.getBaseThemeId())
                .orElseThrow(() -> new RuntimeException("Base theme not found"));

        User user = isuserlogin.userlogined();
        Portfolio portfolio = portfolioRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));

        // Clone — never mutate the shared preset row
        theme personal = new theme();
        personal.setLayout_type(base.getLayout_type()); // structure stays the same
        personal.setFont_family(dto.getFontFamily() != null ? dto.getFontFamily() : base.getFont_family());
        personal.setPrimary_colour(base.getPrimary_colour()); // background stays fixed for cohesion
        personal.setSecondary_colour(dto.getAccentColour() != null ? dto.getAccentColour() : base.getSecondary_colour());
        themeRepo.save(personal);

        portfolio.setTheme_id(personal.getId());
        portfolioRepo.save(portfolio);

        return ResponseEntity.ok(new themeDto(personal.getId(), personal.getLayout_type(), personal.getFont_family(), personal.getPrimary_colour(), personal.getSecondary_colour()));
    }
}