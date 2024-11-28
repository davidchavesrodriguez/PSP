package org.example;

import java.io.IOException;

public class JavaRuntimeClass {
    public static void main(String[] args) throws IOException {
        Runtime runtime= Runtime.getRuntime();
        // check processors
        System.out.println("You have " + runtime.availableProcessors() + " available processors");

        String[] commandChrome= {"C:/Program Files/Google/Chrome/Application/chrome.exe"};
        String[] commandYoutube= {"C:/Program Files/Google/Chrome/Application/chrome.exe", "youtube.com"};
        String[] commandNotepad= {"C:/Windows/notepad.exe"};

        runtime.exec(commandNotepad);
    }
}
