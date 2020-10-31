/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DB;

import Server.DB.Layers.BUS.MatchBUS;
import Server.DB.Layers.BUS.PlayerBUS;
import Server.DB.Layers.DAL.MatchDAL;
import Server.DB.Layers.DAL.PlayerDAL;
import Server.DB.Layers.DTO.Match;
import Server.DB.Layers.DTO.Player;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author nguye
 */
public class TestLayers {
    public static void main(String[] args) {

        Seeds seed = new Seeds();
        seed.generate();
    }
}
