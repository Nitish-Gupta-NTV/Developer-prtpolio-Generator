package com.example.developer.CommandRunner;


import com.example.developer.Repository.ThemeRepo;
import com.example.developer.model.theme;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThemeSeeder {

    @Bean
    CommandLineRunner seedThemes(ThemeRepo themeRepo) {
        return args -> {
            if (themeRepo.count() > 0) return;

            theme dark = new theme();
            dark.setLayout_type("MINIMAL_DARK"); // must match frontend THEME_KEYS exactly
            dark.setFont_family("JetBrains Mono");
            dark.setPrimary_colour("#0B0E14");
            dark.setSecondary_colour("#E8B34A");
            themeRepo.save(dark);

            theme light = new theme();
            light.setLayout_type("MODERN_LIGHT");
            light.setFont_family("Sora");
            light.setPrimary_colour("#F7F9FC");
            light.setSecondary_colour("#4F46E5");
            themeRepo.save(light);

            theme gradient = new theme();
            gradient.setLayout_type("CREATIVE_GRADIENT");
            gradient.setFont_family("Space Grotesk");
            gradient.setPrimary_colour("#7C3AED");
            gradient.setSecondary_colour("#EC4899");
            themeRepo.save(gradient);
        };
    }
}
