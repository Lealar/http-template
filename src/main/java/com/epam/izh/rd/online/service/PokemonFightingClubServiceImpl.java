package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.Pokemon;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Класс по проведению боёв между покемонами
 */
public class PokemonFightingClubServiceImpl implements PokemonFightingClubService {


    /**
     * Инициирует бой между двумя покемонами, должен использовать метод doDamage
     * @param p1 атакующий покемон
     * @param p2 защищающийся покемон
     * @return победителя
     */
    @Override
    public Pokemon doBattle(Pokemon p1, Pokemon p2) {


        Pokemon firstFighter = p1.getPokemonId() > p2.getPokemonId() ? p1 : p2;
        Pokemon secondFighter = p1.getPokemonId() < p2.getPokemonId() ? p1 : p2;
        Pokemon winner = null;

        while (p1.getHp() > 0 || p2.getHp() > 0){
            doDamage(firstFighter,secondFighter);
            if (secondFighter.getHp() <= 0){
                winner = firstFighter;
                break;
            }
            doDamage(secondFighter,firstFighter);
            if (firstFighter.getHp() <= 0){
                winner = secondFighter;
                break;
            }
        }
        if (winner != null) {
            showWinner(winner);
        }
        return winner;
    }


    /**
     * Метод загружает картинку победителя в корень проекта
     * @param winner победитель
     */
    @Override
    public void showWinner(Pokemon winner) {
        try(FileOutputStream fileOutputStream = new FileOutputStream("winner.png")) {
            PokemonFetchingServiceImpl fetchServ = new PokemonFetchingServiceImpl();
            fileOutputStream.write(fetchServ.getPokemonImage(winner.getPokemonName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Метод высчитывает урон покемона from и вычитает его из hp покемона to
     * @param from атакующий покемон
     * @param to защищающийся покемон
     */
    @Override
    public void doDamage(Pokemon from, Pokemon to) {
        short hpTo = to.getHp();
        short defTo = to.getDefense();
        short attackFrom = from.getAttack();

        to.setHp((short) (hpTo - (attackFrom - (attackFrom * defTo / 100))));
    }
}
