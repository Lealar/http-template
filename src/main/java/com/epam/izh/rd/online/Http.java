package com.epam.izh.rd.online;


import com.epam.izh.rd.online.entity.Pokemon;
import com.epam.izh.rd.online.service.PokemonFetchingServiceImpl;
import com.epam.izh.rd.online.service.PokemonFightingClubServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Http {
    public static void main(String[] args)  {

        PokemonFetchingServiceImpl pokemonFetchingService = new PokemonFetchingServiceImpl();

        Pokemon firstFighter = pokemonFetchingService.fetchByName("pikachu");
        Pokemon secondFighter = pokemonFetchingService.fetchByName("slowpoke");


        Pokemon winner = new PokemonFightingClubServiceImpl().doBattle(firstFighter, secondFighter);
        System.out.println("Победитель: " + winner.getPokemonName());


    }
}
