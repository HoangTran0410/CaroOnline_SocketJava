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
public class SignupSDTO {

    public String email;
    public String password;
    public String name;
    public String gender;
    public String birthday;
    public String avatar;

    public SignupSDTO(String email, String password, String name, String gender, String birthday, String avatar) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.avatar = avatar;
    }
}
