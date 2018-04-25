package fr.sciencesu.memoire.service.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

    @Value("${customprop.savepath}")
    public static String savePath;
}
