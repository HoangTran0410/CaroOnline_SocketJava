/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import client.controller.SocketHandler;
import client.view.helper.LookAndFeel;
import client.view.scene.ChangePassword;
import client.view.scene.ConnectServer;
import client.view.scene.InGame;
import client.view.scene.Login;
import client.view.scene.MainMenu;
import client.view.scene.Profile;
import client.view.scene.Signup;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class RunClient {

    public enum SceneName {
        CONNECTSERVER,
        LOGIN,
        SIGNUP,
        MAINMENU,
        CHANGEPASSWORD,
        INGAME,
        PROFILE
    }

    // scenes
    public static ConnectServer connectServerScene;
    public static Login loginScene;
    public static Signup signupScene;
    public static MainMenu mainMenuScene;
    public static ChangePassword changePasswordScene;
    public static InGame inGameScene;
    public static Profile profileScene;

    // controller 
    public static SocketHandler socketHandler;

    public RunClient() {
        socketHandler = new SocketHandler();
        initScene();
        openScene(SceneName.CONNECTSERVER);
    }

    public void initScene() {
        connectServerScene = new ConnectServer();
        loginScene = new Login();
        signupScene = new Signup();
        mainMenuScene = new MainMenu();
        changePasswordScene = new ChangePassword();
        inGameScene = new InGame();
        profileScene = new Profile();
    }

    public static void openScene(SceneName sceneName) {
        if (null != sceneName) {
            switch (sceneName) {
                case CONNECTSERVER:
                    // tạo lại scene để tạo lại state mặc định
                    // nếu chỉ setVisible(true) thì cũng open được scene cũ, nhưng state thì không phải mặc định
                    connectServerScene = new ConnectServer();
                    connectServerScene.setVisible(true);
                    break;
                case LOGIN:
                    loginScene = new Login();
                    loginScene.setVisible(true);
                    break;
                case SIGNUP:
                    signupScene = new Signup();
                    signupScene.setVisible(true);
                    break;
                case MAINMENU:
                    mainMenuScene = new MainMenu();
                    mainMenuScene.setVisible(true);
                    break;
                case CHANGEPASSWORD:
                    changePasswordScene = new ChangePassword();
                    changePasswordScene.setVisible(true);
                    break;
                case INGAME:
                    inGameScene = new InGame();
                    inGameScene.setVisible(true);
                    break;
                case PROFILE:
                    profileScene = new Profile();
                    profileScene.setVisible(true);
                    break;
                default:
                    break;
            }
        }
    }

    public static void closeScene(SceneName sceneName) {
        if (null != sceneName) {
            switch (sceneName) {
                case CONNECTSERVER:
                    connectServerScene.dispose();
                    break;
                case LOGIN:
                    loginScene.dispose();
                    break;
                case SIGNUP:
                    signupScene.dispose();
                    break;
                case MAINMENU:
                    mainMenuScene.dispose();
                    break;
                case CHANGEPASSWORD:
                    changePasswordScene.dispose();
                    break;
                case INGAME:
                    inGameScene.dispose();
                    break;
                case PROFILE:
                    profileScene.dispose();
                    break;
                default:
                    break;
            }
        }
    }

    public static void closeAllScene() {
        connectServerScene.dispose();
        loginScene.dispose();
        signupScene.dispose();
        mainMenuScene.dispose();
        changePasswordScene.dispose();
        inGameScene.dispose();
        profileScene.dispose();
    }

    public static void main(String[] args) {
        LookAndFeel.setNimbusLookAndFeel();
        new RunClient();
    }
}
