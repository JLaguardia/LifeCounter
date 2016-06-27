package com.prismsoftworks.lifecounter.object;

/**
 * Created by jameslaguardia on 6/15/16.
 *
 * @author James/CarbonDawg
 */
public class PlayerObject {
    public int id;
    public String playerName;
    public int lifeTotal;

    public PlayerObject(int id, String playerName, int lifeTotal){
        this.id = id;
        this.playerName = playerName;
        this.lifeTotal = lifeTotal;
    }
}
