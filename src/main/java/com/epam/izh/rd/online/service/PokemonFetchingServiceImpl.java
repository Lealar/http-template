package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.Pokemon;
import com.epam.izh.rd.online.factory.ObjectMapperFactory;
import com.epam.izh.rd.online.factory.ObjectMapperFactoryImpl;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * Сервис по загрузке данных покемонов из внешнего сервиса
 */
public class PokemonFetchingServiceImpl implements PokemonFetchingService {

    /**
     * @param name - имя покемона
     * @return сущность Pokemon
     * @throws IllegalArgumentException при условии, если имя покемона указано неверно
     */
    @Override
    public Pokemon fetchByName(String name) throws IllegalArgumentException {
        HttpURLConnection url;
        Pokemon pokemon = null;
        try {
            url = (HttpURLConnection) new URL("https://pokeapi.co/api/v2/pokemon/" + name.toLowerCase(Locale.ROOT)).openConnection();
            url.setDoInput(true);
            url.setRequestProperty("User-Agent", "User-Agent");

            if (url.getResponseCode() != 200){
                throw new IllegalArgumentException("Ошибка в имени покемона: " + name);
            }
            ObjectMapperFactory objMapper = new ObjectMapperFactoryImpl();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    url.getInputStream(), StandardCharsets.UTF_8))) {
                pokemon =  objMapper.getObjectMapper().readValue(
                        reader.readLine(),Pokemon.class
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pokemon;
    }


    /**
     * @param name - имя покемона
     * @return картинка покемона в виде массива байтов
     * @throws IllegalArgumentException при условии, если имя покемона указано неверно
     */
    @Override
    public byte[] getPokemonImage(String name) throws IllegalArgumentException {
        Pokemon winner = fetchByName(name);
        byte[] bytes = null;

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            HttpURLConnection url = (HttpURLConnection) new URL(winner.getSprites().getFront_default())
                .openConnection();

            BufferedImage image = ImageIO.read(url.getInputStream());
            baos.flush();
            ImageIO.write(image, "png", baos);
            bytes = baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
