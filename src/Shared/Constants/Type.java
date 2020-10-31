/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared.Constants;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Type {
    // shared
    public static final int EXIT = 0;
    
    // client -> server
    public static final int LOGIN = 1;
    public static final int LOGOUT = 2;
    public static final int SIGNUP = 3;
    
    // server -> client
    public static final int LOGIN_RESULT = 4;
    public static final int LOGOUT_RESULT = 5;
    public static final int SIGNUP_RESULT = 6;
    
    // games
    public static final int GAME_EVENT = 7;
    public static final int CHANGE_GAME = 8;
}
