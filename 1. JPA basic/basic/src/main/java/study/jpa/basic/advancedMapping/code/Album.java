package study.jpa.basic.advancedMapping.code;

import javax.persistence.Entity;

/**
 * @author Joonhyuck Hyoung
 */
@Entity
public class Album extends Item{
    private String artist;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
