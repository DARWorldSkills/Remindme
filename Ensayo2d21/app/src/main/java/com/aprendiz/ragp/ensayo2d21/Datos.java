package com.aprendiz.ragp.ensayo2d21;

public class Datos {
    private String title;
    private String image;
    private String audio;

    public Datos(String title, String image, String audio) {
        this.title = title;
        this.image = image;
        this.audio = audio;
    }

    public Datos() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }
}
