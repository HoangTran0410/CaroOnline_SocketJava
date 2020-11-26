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
        INGAME
    }

    public static ConnectServer connectServerScene;
    public static Login loginScene;
    public static Signup signupScene;
    public static MainMenu mainMenuScene;
    public static ChangePassword changePasswordScene;
    public static InGame inGameScene;

    public static SocketHandler socketHandler;

    public RunClient() {
        socketHandler = new SocketHandler();
        openScene(SceneName.CONNECTSERVER);
    }

    public static void openScene(SceneName sceneName) {

        if (sceneName == SceneName.CONNECTSERVER) {
            connectServerScene = new ConnectServer();
            connectServerScene.setVisible(true);

        } else if (sceneName == SceneName.LOGIN) {
            loginScene = new Login();
            loginScene.setVisible(true);

        } else if (sceneName == SceneName.SIGNUP) {
            signupScene = new Signup();
            signupScene.setVisible(true);

        } else if (sceneName == SceneName.MAINMENU) {
            mainMenuScene = new MainMenu();
            mainMenuScene.setVisible(true);

        } else if (sceneName == SceneName.CHANGEPASSWORD) {
            changePasswordScene = new ChangePassword();
            changePasswordScene.setVisible(true);

        } else if (sceneName == SceneName.INGAME) {
            inGameScene = new InGame();
            inGameScene.setVisible(true);
        }
    }

    public static void closeScene(SceneName sceneName) {
        if (sceneName == SceneName.CONNECTSERVER) {
            connectServerScene.dispose();

        } else if (sceneName == SceneName.LOGIN) {
            loginScene.dispose();

        } else if (sceneName == SceneName.SIGNUP) {
            signupScene.dispose();

        } else if (sceneName == SceneName.MAINMENU) {
            mainMenuScene.dispose();

        } else if (sceneName == SceneName.CHANGEPASSWORD) {
            changePasswordScene.dispose();

        } else if (sceneName == SceneName.INGAME) {
            inGameScene.dispose();
        }
    }

    public static void main(String[] args) {
        LookAndFeel.setNimbusLookAndFeel();
        new RunClient();
    }
}
