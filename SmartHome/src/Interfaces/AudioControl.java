package Interfaces;

public interface AudioControl {
    void adjustVolume(int volume);
    void mute();
    void unmute();
    int getVolume();
    boolean isMuted();
}

