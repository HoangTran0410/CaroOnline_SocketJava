/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared.StreamDTO;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class ChatMessageSDTO {
    public String time;
    public String owner;
    public String message;
    
    public ChatMessageSDTO(String time, String owner, String message) {
        this.time = time;
        this.owner = owner;
        this.message = message;
    }
}
