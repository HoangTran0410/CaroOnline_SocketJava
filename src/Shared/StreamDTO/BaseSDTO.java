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
public class BaseSDTO {

    public int type;
    public String contentStr;

    public BaseSDTO(int type, String contentStr) {
        this.type = type;
        this.contentStr = contentStr;
    }
}
